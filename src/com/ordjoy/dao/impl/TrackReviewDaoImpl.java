package com.ordjoy.dao.impl;

import com.ordjoy.dao.TrackReviewDao;
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

public class TrackReviewDaoImpl implements TrackReviewDao {

    private static final TrackReviewDaoImpl INSTANCE = new TrackReviewDaoImpl();

    private TrackReviewDaoImpl() {

    }

    public static TrackReviewDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_TRACK_REVIEW = """
            INSERT INTO review_storage.review_about_track(review_text, user_account_id, track_id)
            VALUES (?, (SELECT id FROM user_storage.user_account_data WHERE login = ?),
                    (SELECT id FROM audio_tracks_storage.track WHERE title = ?));
            """;

    private static final String SQL_FIND_TRACK_REVIEW_BY_ID = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            WHERE rat.id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            """;

    private static final String SQL_FIND_TRACK_REVIEW_BY_USER_LOGIN = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            WHERE data.login = ?
            """;

    private static final String SQL_FIND_TRACK_REVIEW_BY_USER_ID = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            WHERE data.id = ?
            """;

    private static final String SQL_UPDATE_REVIEW = """
            UPDATE review_storage.review_about_track
            SET review_text = ?,
                user_account_id = ?,
                track_id = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_TRACK_REVIEW_BY_ID = """
            DELETE
            FROM review_storage.review_about_track
            WHERE id = ?
            """;

    private static final String SQL_FIND_TRACK_REVIEW_BY_TRACK_TITLE = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            WHERE track.title = ?
            """;

    private static final String SQL_FIND_TRACK_REVIEW_BY_TRACK_ID = """
            SELECT track.id                       AS id,
                   track.title                    AS title,
                   track.song_url                 AS song_url,
                   track.album_id                 AS album_id,
                   rat.id                         AS rat_id,
                   rat.review_text                AS rat_review_text,
                   rat.user_account_id            AS rat_user_account_id,
                   rat.track_id                   AS rat_track_id,
                   data.id                        AS user_id,
                   data.email                     AS email,
                   data.login                     AS login,
                   data.password                  AS password,
                   data.discount_percentage_level AS discount_percentage_level,
                   data.role                      AS role,
                   data.first_name                AS first_name,
                   data.last_name                 AS last_name,
                   data.age                       AS age,
                   data.card_number               AS card_number,
                   a.id                           AS a_id,
                   a.title                        AS a_title
            FROM review_storage.review_about_track rat
                     JOIN user_storage.user_account_data data ON rat.user_account_id = data.id
                     JOIN audio_tracks_storage.track ON rat.track_id = track.id
                     JOIN audio_tracks_storage.album a ON track.album_id = a.id
            WHERE track.id = ?
            """;

    @Override
    public TrackReview save(TrackReview trackReview) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveStatement = connection.prepareStatement(SQL_SAVE_TRACK_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            saveStatement.setString(1, trackReview.getReviewText());
            saveStatement.setString(2, trackReview.getUserAccount().getLogin());
            saveStatement.setString(3, trackReview.getTrack().getTitle());
            saveStatement.executeUpdate();
            ResultSet generatedKeys = saveStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long trackReviewId = generatedKeys.getLong("id");
                trackReview.setId(trackReviewId);
            }
            return trackReview;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<TrackReview> findById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_TRACK_REVIEW_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            TrackReview trackReview = null;
            if (resultSet.next()) {
                trackReview = buildTrackReview(resultSet);
            }
            return Optional.ofNullable(trackReview);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<TrackReview> findAll(ReviewFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.reviewText() != null) {
            whereSql.add("rat.review_text LIKE ?");
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
            List<TrackReview> trackReviews = new ArrayList<>();
            while (resultSet.next()) {
                trackReviews.add(buildTrackReview(resultSet));
            }
            return trackReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(TrackReview trackReview) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_REVIEW)) {
            updateStatement.setString(1, trackReview.getReviewText());
            updateStatement.setLong(2, trackReview.getUserAccount().getId());
            updateStatement.setLong(3, trackReview.getTrack().getId());
            updateStatement.setLong(4, trackReview.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteTrackReviewStatement = connection.prepareStatement(SQL_DELETE_TRACK_REVIEW_BY_ID)) {
            deleteTrackReviewStatement.setLong(1, id);
            return deleteTrackReviewStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<TrackReview> findTrackReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_REVIEW_BY_USER_LOGIN + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setString(1, login);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<TrackReview> trackReviews = new ArrayList<>();
            while (resultSet.next()) {
                trackReviews.add(buildTrackReview(resultSet));
            }
            return trackReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<TrackReview> findTrackReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_REVIEW_BY_USER_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setLong(1, userId);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<TrackReview> trackReviews = new ArrayList<>();
            while (resultSet.next()) {
                trackReviews.add(buildTrackReview(resultSet));
            }
            return trackReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_REVIEW_BY_TRACK_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setLong(1, trackId);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<TrackReview> trackReviews = new ArrayList<>();
            while (resultSet.next()) {
                trackReviews.add(buildTrackReview(resultSet));
            }
            return trackReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<TrackReview> findTrackReviewsByTrackTitle(String title, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_TRACK_REVIEW_BY_TRACK_TITLE + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setString(1, title);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<TrackReview> trackReviews = new ArrayList<>();
            while (resultSet.next()) {
                trackReviews.add(buildTrackReview(resultSet));
            }
            return trackReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private TrackReview buildTrackReview(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildUserAccount(resultSet);
        Track track = buildTrack(resultSet);
        return TrackReview.builder()
                .id(resultSet.getLong("rat_id"))
                .reviewText(resultSet.getString("rat_review_text"))
                .userAccount(userAccount)
                .track(track)
                .build();
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Album album = buildAlbum(resultSet);
        return Track.builder()
                .id(resultSet.getLong("id"))
                .songUrl(resultSet.getString("song_url"))
                .title(resultSet.getString("title"))
                .album(album)
                .build();
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        return Album.builder()
                .id(resultSet.getLong("a_id"))
                .title(resultSet.getString("a_title"))
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