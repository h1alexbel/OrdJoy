package com.ordjoy.dao.impl;

import com.ordjoy.dao.AlbumDao;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.util.ConnectionManager;

import java.sql.*;
import java.util.*;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class AlbumDaoImpl implements AlbumDao {

    private static final AlbumDaoImpl INSTANCE = new AlbumDaoImpl();

    private AlbumDaoImpl() {

    }

    public static AlbumDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String SQL_SAVE_ALBUM = """
            INSERT INTO audio_tracks_storage.album (title, genre_id, artist_id)
            VALUES (?, (SELECT id FROM audio_tracks_storage.genre WHERE name = ?),
                        (SELECT id FROM audio_tracks_storage.artist WHERE name = ?));
            """;

    private static final String SQL_FIND_GENRE_ID = """
            SELECT id FROM audio_tracks_storage.genre
            WHERE name = ?
            """;

    private static final String SQL_SAVE_ARTIST = """
            INSERT INTO audio_tracks_storage.artist (name)
            VALUES (?)
            """;

    private static final String SQL_FIND_ARTIST_ID = """
            SELECT id FROM audio_tracks_storage.artist
            WHERE name = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_GENRE_ID = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            WHERE g.id = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_GENRE_NAME = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            WHERE g.name = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_ARTIST_NAME = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            WHERE a.name = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_ID = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     LEFT JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     LEFT JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            WHERE album.id = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_NAME = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     LEFT JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     LEFT JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            WHERE album.title = ?
            """;

    private static final String SQL_DELETE_ALBUM_BY_ID = """
            DELETE FROM audio_tracks_storage.album
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_ALBUM = """
            UPDATE audio_tracks_storage.album
            SET title = ?,
                genre_id = ?,
                artist_id = ?
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL_ALBUMS = """
            SELECT album.id        AS album_id,
                   album.title     AS album_title,
                   album.artist_id AS artist_id,
                   g.id            AS g_id,
                   g.name          AS genre_name,
                   a.name          AS artist_name
            FROM audio_tracks_storage.album album
                     LEFT JOIN audio_tracks_storage.genre g ON g.id = album.genre_id
                     LEFT JOIN audio_tracks_storage.artist a ON album.artist_id = a.id
            """;

    @Override
    public Album saveAlbumWithExistingArtist(Album album) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement saveAlbumStatement = connection.prepareStatement(SQL_SAVE_ALBUM, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement findGenreIdStatement = connection.prepareStatement(SQL_FIND_GENRE_ID);
             PreparedStatement findArtistIdStatement = connection.prepareStatement(SQL_FIND_ARTIST_ID)) {
            findGenreIdStatement.setString(1, album.getGenre().getName().getGenreName());
            ResultSet resultSet = findGenreIdStatement.executeQuery();
            if (resultSet.next()) {
                long genreId = resultSet.getLong("id");
                album.getGenre().setId(genreId);
                findArtistIdStatement.setString(1, album.getArtist().getName());
                ResultSet query = findArtistIdStatement.executeQuery();
                if (query.next()) {
                    long artistId = query.getLong("id");
                    album.getArtist().setId(artistId);
                }
                saveAlbumStatement.setString(1, album.getTitle());
                saveAlbumStatement.setString(2, album.getGenre().getName().getGenreName());
                saveAlbumStatement.setString(3, album.getArtist().getName());
                saveAlbumStatement.executeUpdate();
                ResultSet generatedKeys = saveAlbumStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong("id");
                    album.setId(id);
                }
            }
            return album;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Album uploadNewAlbumWithData(Album album) {
        Connection connection = null;
        PreparedStatement saveArtistStatement = null;
        PreparedStatement saveAlbumStatement = null;
        PreparedStatement findGenreIdStatement = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            saveArtistStatement = connection.prepareStatement(SQL_SAVE_ARTIST, Statement.RETURN_GENERATED_KEYS);
            findGenreIdStatement = connection.prepareStatement(SQL_FIND_GENRE_ID);
            saveAlbumStatement = connection.prepareStatement(SQL_SAVE_ALBUM, Statement.RETURN_GENERATED_KEYS);
            saveArtistStatement.setString(1, album.getArtist().getName());
            saveArtistStatement.executeUpdate();
            ResultSet generatedKeys = saveArtistStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long artistId = generatedKeys.getLong("id");
                album.getArtist().setId(artistId);
                findGenreIdStatement.setString(1, album.getGenre().getName().getGenreName());
                ResultSet resultSet = findGenreIdStatement.executeQuery();
                if (resultSet.next()) {
                    long genreId = resultSet.getLong("id");
                    album.getGenre().setId(genreId);
                    saveAlbumStatement.setString(1, album.getTitle());
                    saveAlbumStatement.setString(2, album.getGenre().getName().getGenreName());
                    saveAlbumStatement.setString(3, album.getArtist().getName());
                    saveAlbumStatement.executeUpdate();
                    ResultSet keys = saveAlbumStatement.getGeneratedKeys();
                    if (keys.next()) {
                        long id = keys.getLong("id");
                        album.setId(id);
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            rollbackTransaction(connection);
            throw new DaoException(e);
        } finally {
            closeConnection(connection);
            closeStatement(saveArtistStatement);
            closeStatement(saveAlbumStatement);
            closeStatement(findGenreIdStatement);
        }
        return album;
    }

    @Override
    public List<Album> findAll(AlbumFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.genreType() != null) {
            whereSql.add("g.name LIKE ?");
            parameters.add("%" + filter.genreType() + "%");
        }
        if (filter.artistName() != null) {
            whereSql.add("a.name LIKE ?");
            parameters.add("%" + filter.artistName() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ?  OFFSET ? "));
        String sql = SQL_FIND_ALL_ALBUMS + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                albums.add(buildAlbum(resultSet));
            }
            return albums;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Album>> findAlbumsByGenreName(String genreName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALBUM_BY_GENRE_NAME + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAlbumsByGenreNamedStatement = connection.prepareStatement(sql)) {
            findAlbumsByGenreNamedStatement.setString(1, genreName);
            findAlbumsByGenreNamedStatement.setObject(2, filter.limit());
            findAlbumsByGenreNamedStatement.setObject(3, filter.offset());
            ResultSet resultSet = findAlbumsByGenreNamedStatement.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                albums.add(buildAlbum(resultSet));
            }
            return Optional.of(albums);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Album>> findAlbumsByGenreId(Long genreId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALBUM_BY_GENRE_ID + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAlbumsByGenreIdStatement = connection.prepareStatement(sql)) {
            findAlbumsByGenreIdStatement.setLong(1, genreId);
            findAlbumsByGenreIdStatement.setObject(2, filter.limit());
            findAlbumsByGenreIdStatement.setObject(3, filter.offset());
            ResultSet resultSet = findAlbumsByGenreIdStatement.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                albums.add(buildAlbum(resultSet));
            }
            return Optional.of(albums);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Album>> findAlbumsByArtistName(String artistName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALBUM_BY_ARTIST_NAME + """
                LIMIT ?
                OFFSET ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAlbumByArtistName = connection.prepareStatement(sql)) {
            findAlbumByArtistName.setString(1, artistName);
            findAlbumByArtistName.setObject(2, filter.limit());
            findAlbumByArtistName.setObject(3, filter.offset());
            ResultSet resultSet = findAlbumByArtistName.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                albums.add(buildAlbum(resultSet));
            }
            return Optional.of(albums);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Album> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_ALBUM_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Album album = null;
            if (resultSet.next()) {
                album = buildAlbum(resultSet);
            }
            return Optional.ofNullable(album);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Album> findByName(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_ALBUM_BY_NAME)) {
            findByIdStatement.setString(1, name);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Album album = null;
            if (resultSet.next()) {
                album = buildAlbum(resultSet);
            }
            return Optional.ofNullable(album);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Album album) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ALBUM)) {
            updateStatement.setString(1, album.getTitle());
            updateStatement.setLong(2, album.getGenre().getId());
            updateStatement.setLong(3, album.getArtist().getId());
            updateStatement.setLong(4, album.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteByIdStatement = connection.prepareStatement(SQL_DELETE_ALBUM_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        Genre genre = buildGenre(resultSet);
        Artist artist = buildArtist(resultSet);
        return new Album(
                resultSet.getLong("album_id"),
                resultSet.getString("album_title"),
                genre,
                artist
        );
    }

    private Artist buildArtist(ResultSet resultSet) throws SQLException {
        return new Artist(
                resultSet.getLong("artist_id"),
                resultSet.getString("artist_name")
        );
    }

    private Genre buildGenre(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("g_id"),
                GenreType.valueOf(resultSet.getString("genre_name"))
        );
    }

    private void rollbackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, throwables);
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