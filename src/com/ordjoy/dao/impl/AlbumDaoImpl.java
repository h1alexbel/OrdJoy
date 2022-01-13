package com.ordjoy.dao.impl;

import com.ordjoy.dao.AlbumDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.DataBaseException;
import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.filter.DefaultFilter;

import java.sql.*;
import java.util.*;

import static com.ordjoy.util.ExceptionMessageUtils.*;

public class AlbumDaoImpl implements AlbumDao {

    private static final AlbumDaoImpl INSTANCE = new AlbumDaoImpl();

    private AlbumDaoImpl() {

    }

    public static AlbumDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_ALBUM = """
            INSERT INTO audio_tracks_storage.album(title)
            VALUES (?);
            """;

    private static final String SQL_FIND_ALBUM_BY_ID = """
            SELECT id, title
            FROM audio_tracks_storage.album
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id, title
            FROM audio_tracks_storage.album
            """;

    private static final String SQL_UPDATE_ALBUM = """
            UPDATE audio_tracks_storage.album
            SET title = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_ALBUM_BY_ID = """
            DELETE
            FROM audio_tracks_storage.album
            WHERE id = ?
            """;

    private static final String SQL_DELETE_FROM_TRACK_TABLE = """
            DELETE
            FROM audio_tracks_storage.track
            WHERE album_id = ?
            """;

    private static final String SQL_DELETE_FROM_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_album
            WHERE album_id = ?
            """;

    private static final String SQL_FIND_ALBUM_BY_TITLE = """
            SELECT id, title
            FROM audio_tracks_storage.album
            WHERE title LIKE ?
            """;

    private static final String SQL_FIND_REVIEWS_BY_ALBUM_ID = """
            SELECT a.id                           AS id,
                   a.title                        AS title,
                   ra.id                          AS ra_id,
                   ra.review_text                 AS ra_review_text,
                   ra.user_account_id             AS ra_user_account_id,
                   ra.album_id                    AS ra_album_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number
            FROM audio_tracks_storage.album a
                     JOIN review_storage.review_about_album ra ON a.id = ra.album_id
                     JOIN user_storage.user_account_data data ON data.id = ra.user_account_id
            WHERE a.id = ?
            """;

    private static final String SQL_FIND_REVIEWS_BY_ALBUM_TITLE = """
            SELECT a.id                           AS id,
                   a.title                        AS title,
                   ra.id                          AS ra_id,
                   ra.review_text                 AS ra_review_text,
                   ra.user_account_id             AS ra_user_account_id,
                   ra.album_id                    AS ra_album_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number
            FROM audio_tracks_storage.album a
                     JOIN review_storage.review_about_album ra ON a.id = ra.album_id
                     JOIN user_storage.user_account_data data ON data.id = ra.user_account_id
            WHERE a.title LIKE ?
            """;

    @Override
    public Album save(Album album) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveAlbumStatement = connection.prepareStatement(SQL_SAVE_ALBUM, Statement.RETURN_GENERATED_KEYS)) {
            saveAlbumStatement.setString(1, album.getTitle());
            saveAlbumStatement.executeUpdate();
            ResultSet generatedKeys = saveAlbumStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long albumId = generatedKeys.getLong("id");
                album.setId(albumId);
            }
            return album;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Album> findById(Long id) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
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
    public List<Album> findAll(AlbumFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALL + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
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
    public void update(Album album) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ALBUM)) {
            updateStatement.setString(1, album.getTitle());
            updateStatement.setLong(2, album.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        ProxyConnection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement deleteFromTrackTableStatement = null;
        PreparedStatement deleteFromAlbumReviewTableStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteStatement = connection.prepareStatement(SQL_DELETE_ALBUM_BY_ID);
            deleteFromTrackTableStatement = connection.prepareStatement(SQL_DELETE_FROM_TRACK_TABLE);
            deleteFromAlbumReviewTableStatement = connection.prepareStatement(SQL_DELETE_FROM_REVIEW_TABLE);
            deleteFromTrackTableStatement.setLong(1, id);
            deleteFromTrackTableStatement.executeUpdate();
            deleteFromAlbumReviewTableStatement.setLong(1, id);
            deleteFromAlbumReviewTableStatement.executeUpdate();
            deleteStatement.setLong(1, id);
            deleteFromTrackTableStatement.executeUpdate();
            connection.commit();
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            rollbackTransaction(connection);
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        } finally {
            closeConnection(connection);
            closeStatement(deleteStatement);
            closeStatement(deleteFromTrackTableStatement);
            closeStatement(deleteFromAlbumReviewTableStatement);
        }
    }

    @Override
    public Optional<Album> findAlbumByTitle(String albumTitle) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_ALBUM_BY_TITLE)) {
            findByIdStatement.setString(1, albumTitle);
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
    public List<AlbumReview> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_ALBUM_TITLE + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByTitleStatement = connection.prepareStatement(sql)) {
            findReviewsByTitleStatement.setString(1, albumTitle);
            findReviewsByTitleStatement.setObject(2, filter.limit());
            findReviewsByTitleStatement.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByTitleStatement.executeQuery();
            List<AlbumReview> albumReviews = new ArrayList<>();
            AlbumReview albumReview = null;
            while (resultSet.next()) {
                albumReview = buildAlbumReview(resultSet);
                albumReviews.add(albumReview);
            }
            return albumReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_ALBUM_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByTitleStatement = connection.prepareStatement(sql)) {
            findReviewsByTitleStatement.setLong(1, albumId);
            findReviewsByTitleStatement.setObject(2, filter.limit());
            findReviewsByTitleStatement.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByTitleStatement.executeQuery();
            List<AlbumReview> albumReviews = new ArrayList<>();
            AlbumReview albumReview = null;
            while (resultSet.next()) {
                albumReview = buildAlbumReview(resultSet);
                albumReviews.add(albumReview);
            }
            return albumReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        return new Album(
                resultSet.getLong("id"),
                resultSet.getString("title")
        );
    }

    private AlbumReview buildAlbumReview(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildUserAccount(resultSet);
        Album album = buildAlbum(resultSet);
        return new AlbumReview(
                resultSet.getLong("ra_id"),
                resultSet.getString("ra_review_text"),
                userAccount,
                album
        );
    }

    private UserAccount buildUserAccount(ResultSet resultSet) throws SQLException {
        UserData data = buildUserData(resultSet);
        return new UserAccount(
                resultSet.getLong("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getInt("discount_percentage_level"),
                data
        );
    }

    private UserData buildUserData(ResultSet resultSet) throws SQLException {
        return new UserData(
                UserRole.valueOf(resultSet.getString("role")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age"),
                resultSet.getString("card_number")
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