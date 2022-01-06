package com.ordjoy.dao.impl;

import com.ordjoy.dao.TrackDao;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.TrackFilter;
import com.ordjoy.util.ConnectionManager;

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

    private static final String SQL_FIND_TRACK_BY_ID = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE tr.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_NAME = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE tr.title = ?
            """;

    private static final String SQL_UPDATE_TRACK = """
            UPDATE audio_tracks_storage.track
            SET album_id = ?,
                genre_id = ?,
                title    = ?,
                song_url = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_TRACK_BY_ID = """
            DELETE
            FROM audio_tracks_storage.track
            WHERE id = ?
            """;

    private static final String SQL_SAVE_TRACK = """
            INSERT INTO audio_tracks_storage.track(song_url, title, genre_id, album_id)
            VALUES (?, ?, (SELECT id FROM audio_tracks_storage.genre WHERE name = ?),
                    (SELECT id FROM audio_tracks_storage.album WHERE album.title = ?));
            """;

    private static final String SQL_FIND_ALBUM_ID = """
            SELECT id
            FROM audio_tracks_storage.album
            WHERE title = ?
            """;

    private static final String SQL_FIND_GENRE_ID = """
            SELECT id FROM audio_tracks_storage.genre
            WHERE name = ?
            """;

    private static final String SQL_SAVE_TRACK_WITH_ARTIST_IN_MUTUAL_TABLE = """
            INSERT INTO audio_tracks_storage.artist_tracks(artist_id, track_id)
            VALUES ((SELECT id FROM audio_tracks_storage.artist WHERE name = ?),
                    (SELECT id FROM audio_tracks_storage.track WHERE title = ?));
            """;

    private static final String SQL_FIND_ARTIST_ID = """
            SELECT id
            FROM audio_tracks_storage.artist
            WHERE name = ?
            """;

    private static final String SQL_FIND_ALL_TRACKS = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            """;

    private static final String SQL_SAVE_TRACK_WITH_MIX_IN_MUTUAL_TABLE = """
            INSERT INTO audio_tracks_storage.track_mixes(track_id, mix_id)
            VALUES ((SELECT id FROM audio_tracks_storage.track WHERE title = ?),
                    (SELECT id FROM audio_tracks_storage.mix WHERE name = ?));
            """;

    private static final String SQL_FIND_TRACK_BY_GENRE_ID = """
            SELECT tr.title    AS title,
                    tr.id       AS id,
                    art.name    AS artist,
                    art.id      AS art_id,
                    tr.song_url AS url,
                    a.title     AS album_title,
                    a.id        AS album_id,
                    g.id        AS g_id,
                    g.name      AS genre_name
             FROM audio_tracks_storage.track tr
                      INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                      INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                      INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
             WHERE g.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_GENRE_NAME = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE g.name = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ALBUM_ID = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE a.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ALBUM_NAME = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE a.title = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ARTIST_ID = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE art.id = ?
            """;

    private static final String SQL_FIND_TRACK_BY_ARTIST_NAME = """
            SELECT tr.title    AS title,
                   tr.id       AS id,
                   art.name    AS artist,
                   art.id      AS art_id,
                   tr.song_url AS url,
                   a.title     AS album_title,
                   a.id        AS album_id,
                   g.id        AS g_id,
                   g.name      AS genre_name
            FROM audio_tracks_storage.track tr
                     INNER JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     INNER JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
                     INNER JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
            WHERE art.name = ?
            """;

    @Override
    public Optional<Track> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_TRACK_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Track track = null;
            if (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Track> findByName(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByNameStatement = connection.prepareStatement(SQL_FIND_TRACK_BY_NAME)) {
            findByNameStatement.setString(1, name);
            ResultSet resultSet = findByNameStatement.executeQuery();
            Track track = null;
            if (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
            }
            return Optional.ofNullable(track);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Track track) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_TRACK)) {
            updateStatement.setLong(1, track.getAlbum().getId());
            updateStatement.setLong(2, track.getGenre().getId());
            updateStatement.setString(3, track.getTitle());
            updateStatement.setString(4, track.getSongUrl());
            updateStatement.setLong(5, track.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_TRACK_BY_ID)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Track saveTrackInExistingAlbum(Track trackToSave, Album albumThatExists, Artist artistThatExists) {
        Connection connection = null;
        PreparedStatement findGenreStatement = null;
        PreparedStatement findArtistStatement = null;
        PreparedStatement findAlbumStatement = null;
        PreparedStatement saveTrackStatement = null;
        PreparedStatement saveArtistAndTrackIdInMutualTableStatement = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            findGenreStatement = connection.prepareStatement(SQL_FIND_GENRE_ID);
            findArtistStatement = connection.prepareStatement(SQL_FIND_ARTIST_ID);
            findAlbumStatement = connection.prepareStatement(SQL_FIND_ALBUM_ID);
            saveTrackStatement = connection.prepareStatement(SQL_SAVE_TRACK, Statement.RETURN_GENERATED_KEYS);
            saveArtistAndTrackIdInMutualTableStatement = connection.prepareStatement(SQL_SAVE_TRACK_WITH_ARTIST_IN_MUTUAL_TABLE);
            findGenreStatement.setString(1, albumThatExists.getGenre().getName().getGenreName());
            ResultSet resultSet = findGenreStatement.executeQuery();
            if (resultSet.next()) {
                long genreId = resultSet.getLong("id");
                albumThatExists.getGenre().setId(genreId);
                trackToSave.getGenre().setId(genreId);
                findArtistStatement.setString(1, artistThatExists.getName());
                ResultSet query = findArtistStatement.executeQuery();
                if (query.next()) {
                    long artistId = query.getLong("id");
                    albumThatExists.getArtist().setId(artistId);
                    findAlbumStatement.setString(1, albumThatExists.getTitle());
                    ResultSet executeQuery = findAlbumStatement.executeQuery();
                    if (executeQuery.next()) {
                        long albumId = executeQuery.getLong("id");
                        albumThatExists.setId(albumId);
                        trackToSave.setAlbum(albumThatExists);
                        saveTrackStatement.setString(1, trackToSave.getSongUrl());
                        saveTrackStatement.setString(2, trackToSave.getTitle());
                        saveTrackStatement.setString(3, trackToSave.getGenre().getName().getGenreName());
                        saveTrackStatement.setString(4, trackToSave.getAlbum().getTitle());
                        saveTrackStatement.executeUpdate();
                        ResultSet generatedKeys = saveTrackStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            long trackId = generatedKeys.getLong("id");
                            trackToSave.setId(trackId);
                            saveArtistAndTrackIdInMutualTableStatement.setString(1, artistThatExists.getName());
                            saveArtistAndTrackIdInMutualTableStatement.setString(2, trackToSave.getTitle());
                            saveArtistAndTrackIdInMutualTableStatement.executeUpdate();
                            Set<Artist> artists = new HashSet<>();
                            artists.add(artistThatExists);
                            trackToSave.setArtists(artists);
                            connection.commit();
                        }
                    }
                }
            }
            return trackToSave;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, throwables);
            }
            throw new DaoException(e);
        } finally {
            closeConnection(connection);
            closeStatement(findGenreStatement);
            closeStatement(findArtistStatement);
            closeStatement(findAlbumStatement);
            closeStatement(saveTrackStatement);
            closeStatement(saveArtistAndTrackIdInMutualTableStatement);
        }
    }

    @Override
    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement saveTrackAndMixIdInMutualTable = connection.prepareStatement(SQL_SAVE_TRACK_WITH_MIX_IN_MUTUAL_TABLE)) {
            saveTrackAndMixIdInMutualTable.setString(1, trackThatExists.getTitle());
            saveTrackAndMixIdInMutualTable.setString(2, mixThatExists.getName());
            return saveTrackAndMixIdInMutualTable.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Track> findAll(TrackFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.genreType() != null) {
            whereSql.add("g.name LIKE ?");
            parameters.add("%" + filter.genreType() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ?  OFFSET ? "));
        String sql = SQL_FIND_ALL_TRACKS + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            while (resultSet.next()) {
                Track track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return tracks;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Track>> findTracksByGenreId(Long genreId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_GENRE_ID + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setLong(1, genreId);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Track>> findTracksByGenreName(String genreName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_GENRE_NAME + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setString(1, genreName);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Set<Track>> findTracksByAlbumId(Long albumId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ALBUM_ID + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setLong(1, albumId);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            Set<Track> tracks = new HashSet<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Set<Track>> findTracksByAlbumName(String albumName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ALBUM_NAME + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setString(1, albumName);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            Set<Track> tracks = new HashSet<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Track>> findTracksByArtistId(Long artistId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ARTIST_ID + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setLong(1, artistId);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Track>> findTracksByArtistName(String artistName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_BY_ARTIST_NAME + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByGenreIdStatement = connection.prepareStatement(sql)) {
            findByGenreIdStatement.setString(1, artistName);
            findByGenreIdStatement.setObject(2, filter.limit());
            findByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByGenreIdStatement.executeQuery();
            List<Track> tracks = new ArrayList<>();
            Track track = null;
            while (resultSet.next()) {
                track = buildTrack(resultSet);
                Set<Artist> artists = new HashSet<>();
                artists.add(track.getAlbum().getArtist());
                track.setArtists(artists);
                tracks.add(track);
            }
            return Optional.of(tracks);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Album album = buildAlbum(resultSet);
        return new Track(
                resultSet.getLong("id"),
                resultSet.getString("url"),
                resultSet.getString("title"),
                album.getGenre(),
                album
        );
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        Artist artist = buildArtist(resultSet);
        Genre genre = buildGenre(resultSet);
        return new Album(
                resultSet.getLong("album_id"),
                resultSet.getString("album_title"),
                genre,
                artist
        );
    }

    private Artist buildArtist(ResultSet resultSet) throws SQLException {
        return new Artist(
                resultSet.getLong("art_id"),
                resultSet.getString("artist")
        );
    }

    private Genre buildGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getLong("g_id"),
                GenreType.valueOf(resultSet.getString("genre_name")));
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

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}