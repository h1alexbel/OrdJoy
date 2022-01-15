package com.ordjoy.dao.impl;

import com.ordjoy.dao.TrackDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.DataBaseException;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;

import java.sql.*;
import java.util.*;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class TrackDaoImpl implements TrackDao {

    private static final TrackDaoImpl INSTANCE = new TrackDaoImpl();

    private TrackDaoImpl() {

    }

    public static TrackDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_TRACK = """
            INSERT INTO audio_tracks_storage.track(song_url, title, album_id)
            VALUES (?, ?, (SELECT id FROM audio_tracks_storage.album WHERE album.title = ?));
            """;

    private static final String SQL_FIND_ALBUM_ID = """
            SELECT id
            FROM audio_tracks_storage.album
            WHERE album.title = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ID = """
            SELECT tr.id       AS id,
                   tr.song_url AS song_url,
                   tr.title    AS title,
                   tr.album_id AS album_id,
                   a.id        AS a_id,
                   a.title     AS a_title
            FROM audio_tracks_storage.track tr
                     JOIN audio_tracks_storage.album a on a.id = tr.album_id
            WHERE tr.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_TITLE = """
            SELECT tr.id       AS id,
                   tr.song_url AS song_url,
                   tr.title    AS title,
                   tr.album_id AS album_id,
                   a.id        AS a_id,
                   a.title     AS a_title
            FROM audio_tracks_storage.track tr
                     JOIN audio_tracks_storage.album a on a.id = tr.album_id
            WHERE tr.title = ?
            """;

    private static final String SQL_FIND_ALL_TRACKS = """
            SELECT tr.id       AS id,
                   tr.song_url AS song_url,
                   tr.title    AS title,
                   tr.album_id AS album_id,
                   a.id        AS a_id,
                   a.title     AS a_title
            FROM audio_tracks_storage.track tr
                     JOIN audio_tracks_storage.album a on a.id = tr.album_id
            """;

    private static final String SQL_UPDATE_TRACK = """
            UPDATE audio_tracks_storage.track
            SET song_url = ?,
                title = ?,
                album_id = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_TRACK_BY_ID = """
            DELETE
            FROM audio_tracks_storage.track
            WHERE id = ?
            """;

    private static final String SQL_DELETE_FROM_ORDER_TABLE = """
            DELETE
            FROM user_storage.order
            WHERE track_id = ?
            """;

    private static final String SQL_DELETE_FROM_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_track
            WHERE track_id = ?
            """;

    private static final String SQL_SAVE_TRACK_AND_MIX_IN_MUTUAL_TABLE = """
            INSERT INTO audio_tracks_storage.track_mixes(track_id, mix_id)
            VALUES ((SELECT id FROM audio_tracks_storage.track WHERE title = ?),
                    (SELECT id FROM audio_tracks_storage.mix WHERE name = ?));
            """;

    private static final String SQL_DELETE_FROM_MUTUAL_TABLE = """
            DELETE
            FROM audio_tracks_storage.track_mixes
            WHERE track_id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ALBUM_ID = """
            SELECT tr.id       AS id,
                   tr.song_url AS song_url,
                   tr.title    AS title,
                   tr.album_id AS album_id,
                   a.id        AS a_id,
                   a.title     AS a_title
            FROM audio_tracks_storage.track tr
                     JOIN audio_tracks_storage.album a on a.id = tr.album_id
            WHERE a.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ALBUM_TITLE = """
            SELECT tr.id       AS id,
                   tr.song_url AS song_url,
                   tr.title    AS title,
                   tr.album_id AS album_id,
                   a.id        AS a_id,
                   a.title     AS a_title
            FROM audio_tracks_storage.track tr
                     JOIN audio_tracks_storage.album a on a.id = tr.album_id
            WHERE a.title = ?
            """;

    @Override
    public Track save(Track track) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findAlbumIdStatement = connection.prepareStatement(SQL_FIND_ALBUM_ID);
             PreparedStatement saveTrackStatement = connection.prepareStatement(SQL_SAVE_TRACK, Statement.RETURN_GENERATED_KEYS)) {
            findAlbumIdStatement.setString(1, track.getAlbum().getTitle());
            ResultSet resultSet = findAlbumIdStatement.executeQuery();
            if (resultSet.next()) {
                long albumId = resultSet.getLong("id");
                track.getAlbum().setId(albumId);
                saveTrackStatement.setString(1, track.getSongUrl());
                saveTrackStatement.setString(2, track.getTitle());
                saveTrackStatement.setString(3, track.getAlbum().getTitle());
                saveTrackStatement.executeUpdate();
                ResultSet generatedKeys = saveTrackStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long trackId = generatedKeys.getLong("id");
                    track.setId(trackId);
                }
            }
            return track;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Track> findById(Long id) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findTrackByIdStatement = connection.prepareStatement(SQL_FIND_TRACK_BY_ID)) {
            findTrackByIdStatement.setLong(1, id);
            ResultSet resultSet = findTrackByIdStatement.executeQuery();
            Track track = null;
            if (resultSet.next()) {
                track = buildTrack(resultSet);
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Track> findAll(TrackFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.albumTitle() != null) {
            whereSql.add("a.title LIKE ?");
            parameters.add("%" + filter.albumTitle() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        String sql = SQL_FIND_ALL_TRACKS + where;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (resultSet.next()) {
                tracks.add(buildTrack(resultSet));
            }
            return tracks;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Track track) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_TRACK)) {
            updateStatement.setString(1, track.getSongUrl());
            updateStatement.setString(2, track.getTitle());
            updateStatement.setLong(3, track.getAlbum().getId());
            updateStatement.setLong(4, track.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }


    @Override
    public boolean deleteById(Long id) {
        ProxyConnection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement deleteFromMutualTableStatement = null;
        PreparedStatement deleteFromReviewTableStatement = null;
        PreparedStatement deleteFromOrderTableStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteStatement = connection.prepareStatement(SQL_DELETE_TRACK_BY_ID);
            deleteFromOrderTableStatement = connection.prepareStatement(SQL_DELETE_FROM_ORDER_TABLE);
            deleteFromMutualTableStatement = connection.prepareStatement(SQL_DELETE_FROM_MUTUAL_TABLE);
            deleteFromReviewTableStatement = connection.prepareStatement(SQL_DELETE_FROM_REVIEW_TABLE);
            deleteFromOrderTableStatement.setLong(1, id);
            deleteFromOrderTableStatement.executeUpdate();
            deleteFromMutualTableStatement.setLong(1, id);
            deleteFromMutualTableStatement.executeUpdate();
            deleteFromReviewTableStatement.setLong(1, id);
            deleteFromReviewTableStatement.executeUpdate();
            deleteStatement.setLong(1, id);
            connection.commit();
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            rollbackTransaction(connection);
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        } finally {
            closeConnection(connection);
            closeStatement(deleteStatement);
            closeStatement(deleteFromMutualTableStatement);
            closeStatement(deleteFromReviewTableStatement);
            closeStatement(deleteFromOrderTableStatement);
        }
    }

    @Override
    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveStatement = connection.prepareStatement(SQL_SAVE_TRACK_AND_MIX_IN_MUTUAL_TABLE)) {
            saveStatement.setString(1, trackThatExists.getTitle());
            saveStatement.setString(2, mixThatExists.getName());
            return saveStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Track> findByTrackTitle(String trackTitle) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findTrackByTitle = connection.prepareStatement(SQL_FIND_TRACK_BY_TITLE)) {
            findTrackByTitle.setString(1, trackTitle);
            ResultSet resultSet = findTrackByTitle.executeQuery();
            Track track = null;
            if (resultSet.next()) {
                track = buildTrack(resultSet);
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ALBUM_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findTrackByTitle = connection.prepareStatement(sql)) {
            findTrackByTitle.setLong(1, albumId);
            findTrackByTitle.setObject(2, filter.limit());
            findTrackByTitle.setObject(3, filter.offset());
            ResultSet resultSet = findTrackByTitle.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                tracks.add(track);
            }
            return tracks;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Track> findTracksByAlbumName(String albumName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ALBUM_TITLE + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findTrackByTitle = connection.prepareStatement(sql)) {
            findTrackByTitle.setString(1, albumName);
            findTrackByTitle.setObject(2, filter.limit());
            findTrackByTitle.setObject(3, filter.offset());
            ResultSet resultSet = findTrackByTitle.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                tracks.add(track);
            }
            return tracks;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Album album = buildAlbum(resultSet);
        return new Track(
                resultSet.getLong("id"),
                resultSet.getString("song_url"),
                resultSet.getString("title"),
                album
        );
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        return new Album(
                resultSet.getLong("a_id"),
                resultSet.getString("a_title")
        );
    }

    private void rollbackTransaction(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DataBaseException(ex);
            }
        }
    }

    private void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeConnection(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}