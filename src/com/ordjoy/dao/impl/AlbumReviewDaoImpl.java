package com.ordjoy.dao.impl;

import com.ordjoy.dao.AlbumReviewDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.DAO_LAYER_EXCEPTION_MESSAGE;
import static java.util.stream.Collectors.joining;

public class AlbumReviewDaoImpl implements AlbumReviewDao {

    private static final AlbumReviewDaoImpl INSTANCE = new AlbumReviewDaoImpl();

    private AlbumReviewDaoImpl() {

    }

    public static AlbumReviewDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_ALBUM_REVIEW = """
            INSERT INTO review_storage.review_about_album(review_text, user_account_id, album_id)
            VALUES (?, (SELECT id FROM user_storage.user_account_data WHERE login = ?),
                    (SELECT id FROM audio_tracks_storage.album WHERE title = ?));
            """;

    private static final String SQL_FIND_ALBUM_REVIEW_BY_ID = """
            SELECT al.id                          AS id,
                   al.title                       AS title,
                   raa.id                         AS raa_id,
                   raa.review_text                AS raa_review_text,
                   raa.user_account_id            AS raa_user_account_id,
                   raa.album_id                   AS raa_album_id,
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
            FROM review_storage.review_about_album raa
                     JOIN user_storage.user_account_data data ON raa.user_account_id = data.id
                     JOIN audio_tracks_storage.album al ON raa.album_id = al.id
            WHERE raa.id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT al.id                          AS id,
                   al.title                       AS title,
                   raa.id                         AS raa_id,
                   raa.review_text                AS raa_review_text,
                   raa.user_account_id            AS raa_user_account_id,
                   raa.album_id                   AS raa_album_id,
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
            FROM review_storage.review_about_album raa
                     JOIN user_storage.user_account_data data ON raa.user_account_id = data.id
                     JOIN audio_tracks_storage.album al ON raa.album_id = al.id
             """;

    private static final String SQL_UPDATE_ALBUM_REVIEW = """
            UPDATE review_storage.review_about_album
            SET review_text = ?,
                user_account_id = ?,
                album_id = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_ALBUM_REVIEW_BY_ID = """
            DELETE
            FROM review_storage.review_about_album
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALBUM_REVIEW_BY_USER_LOGIN = """
            SELECT al.id                          AS id,
                   al.title                       AS title,
                   raa.id                         AS raa_id,
                   raa.review_text                AS raa_review_text,
                   raa.user_account_id            AS raa_user_account_id,
                   raa.album_id                   AS raa_album_id,
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
            FROM review_storage.review_about_album raa
                     JOIN user_storage.user_account_data data ON raa.user_account_id = data.id
                     JOIN audio_tracks_storage.album al ON raa.album_id = al.id
            WHERE data.login LIKE ?
            """;

    private static final String SQL_FIND_ALBUM_REVIEW_BY_USER_ID = """
            SELECT al.id                          AS id,
                   al.title                       AS title,
                   raa.id                         AS raa_id,
                   raa.review_text                AS raa_review_text,
                   raa.user_account_id            AS raa_user_account_id,
                   raa.album_id                   AS raa_album_id,
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
            FROM review_storage.review_about_album raa
                     JOIN user_storage.user_account_data data ON raa.user_account_id = data.id
                     JOIN audio_tracks_storage.album al ON raa.album_id = al.id
            WHERE data.id = ?
            """;

    @Override
    public AlbumReview save(AlbumReview albumReview) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveAlbumReviewStatement = connection.prepareStatement(SQL_SAVE_ALBUM_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            saveAlbumReviewStatement.setString(1, albumReview.getReviewText());
            saveAlbumReviewStatement.setString(2, albumReview.getUserAccount().getLogin());
            saveAlbumReviewStatement.setString(3, albumReview.getAlbum().getTitle());
            saveAlbumReviewStatement.executeUpdate();
            ResultSet generatedKeys = saveAlbumReviewStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long albumReviewId = generatedKeys.getLong("id");
                albumReview.setId(albumReviewId);
            }
            return albumReview;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<AlbumReview> findById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_ALBUM_REVIEW_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            AlbumReview albumReview = null;
            if (resultSet.next()) {
                albumReview = buildAlbumReview(resultSet);
            }
            return Optional.ofNullable(albumReview);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<AlbumReview> findAll(ReviewFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.reviewText() != null) {
            whereSql.add("raa.review_text LIKE ?");
            parameters.add("%" + filter.reviewText() + "%");
        }
        if (filter.userAccountLogin() != null) {
            whereSql.add("login LIKE ?");
            parameters.add("%" + filter.userAccountLogin() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        String sql = SQL_FIND_ALL + where;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<AlbumReview> albumReviews = new ArrayList<>();
            while (resultSet.next()) {
                albumReviews.add(buildAlbumReview(resultSet));
            }
            return albumReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(AlbumReview albumReview) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ALBUM_REVIEW)) {
            updateStatement.setString(1, albumReview.getReviewText());
            updateStatement.setLong(2, albumReview.getUserAccount().getId());
            updateStatement.setLong(3, albumReview.getAlbum().getId());
            updateStatement.setLong(4, albumReview.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_ALBUM_REVIEW_BY_ID)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALBUM_REVIEW_BY_USER_LOGIN + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setString(1, login);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<AlbumReview> albumReviews = new ArrayList<>();
            while (resultSet.next()) {
                albumReviews.add(buildAlbumReview(resultSet));
            }
            return albumReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<AlbumReview> findAlbumReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ALBUM_REVIEW_BY_USER_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setLong(1, userId);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<AlbumReview> albumReviews = new ArrayList<>();
            while (resultSet.next()) {
                albumReviews.add(buildAlbumReview(resultSet));
            }
            return albumReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private AlbumReview buildAlbumReview(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildUserAccount(resultSet);
        Album album = buildAlbum(resultSet);
        return AlbumReview.builder()
                .id(resultSet.getLong("raa_id"))
                .reviewText(resultSet.getString("raa_review_text"))
                .userAccount(userAccount)
                .album(album)
                .build();
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        return Album.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .build();
    }

    private UserAccount buildUserAccount(ResultSet resultSet) throws SQLException {
        UserData data = buildUserData(resultSet);
        return UserAccount.builder()
                .id(resultSet.getLong("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .discountPercentageLevel(resultSet.getInt("discount_percentage_level"))
                .userData(data)
                .build();
    }

    private UserData buildUserData(ResultSet resultSet) throws SQLException {
        return UserData.builder()
                .userRole(UserRole.valueOf(resultSet.getString("role")))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .age(resultSet.getInt("age"))
                .cardNumber(resultSet.getString("card_number"))
                .build();
    }
}