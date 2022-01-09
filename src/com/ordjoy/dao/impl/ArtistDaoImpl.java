package com.ordjoy.dao.impl;

import com.ordjoy.dao.ArtistDao;
import com.ordjoy.dbmanager.ConnectionManager;
import com.ordjoy.exception.DaoException;
import com.ordjoy.filter.ArtistFilter;
import com.ordjoy.entity.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;

public class ArtistDaoImpl implements ArtistDao {

    private static final ArtistDaoImpl INSTANCE = new ArtistDaoImpl();

    private ArtistDaoImpl() {

    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    public static ArtistDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String SQL_SAVE_ARTIST = """
            INSERT INTO audio_tracks_storage.artist (name)
            VALUES (?);
            """;

    private static final String SQL_FIND_ALL_ARTISTS = """
            SELECT id, name
            FROM audio_tracks_storage.artist
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT id, name
            FROM audio_tracks_storage.artist
            WHERE id = ?
            """;

    private static final String SQL_FIND_BY_NAME = """
            SELECT id, name
            FROM audio_tracks_storage.artist
            WHERE name LIKE ?
            """;

    private static final String SQL_UPDATE_ARTIST = """
            UPDATE audio_tracks_storage.artist
            SET name = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM audio_tracks_storage.artist
            WHERE id = ?
            """;

    @Override
    public Artist saveArtist(Artist artist) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement saveArtistStatement = connection.prepareStatement(SQL_SAVE_ARTIST, Statement.RETURN_GENERATED_KEYS)) {
            saveArtistStatement.setString(1, artist.getName());
            saveArtistStatement.executeUpdate();
            ResultSet generatedKeys = saveArtistStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong("id");
                artist.setId(id);
            }
            return artist;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Artist> findAll(ArtistFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String sql = SQL_FIND_ALL_ARTISTS + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()) {
                artists.add(buildArtist(resultSet));
            }
            return artists;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Artist buildArtist(ResultSet resultSet) throws SQLException {
        return new Artist(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }

    @Override
    public Optional<Artist> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Artist artist = null;
            if (resultSet.next()) {
                artist = buildArtist(resultSet);
            }
            return Optional.ofNullable(artist);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Artist> findByName(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByNameStatement = connection.prepareStatement(SQL_FIND_BY_NAME)) {
            findByNameStatement.setString(1, name);
            ResultSet resultSet = findByNameStatement.executeQuery();
            Artist artist = null;
            if (resultSet.next()) {
                artist = buildArtist(resultSet);
            }
            return Optional.ofNullable(artist);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Artist artist) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ARTIST)) {
            updateStatement.setString(1, artist.getName());
            updateStatement.setLong(2, artist.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}