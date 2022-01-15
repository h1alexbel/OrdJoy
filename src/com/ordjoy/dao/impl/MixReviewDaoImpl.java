package com.ordjoy.dao.impl;

import com.ordjoy.dao.MixReviewDao;
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

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class MixReviewDaoImpl implements MixReviewDao {

    private static final MixReviewDaoImpl INSTANCE = new MixReviewDaoImpl();
    public static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private MixReviewDaoImpl() {

    }

    public static MixReviewDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String SQL_SAVE_MIX_REVIEW = """
            INSERT INTO review_storage.review_about_mix(review_text, user_account_id, mix_id)
            VALUES (?, (SELECT id FROM user_storage.user_account_data WHERE login = ?),
                    (SELECT id FROM audio_tracks_storage.mix WHERE name = ?));
            """;

    private static final String SQL_FIND_USER_ACCOUNT_ID = """
            SELECT id
            FROM user_storage.user_account_data
            WHERE login = ?
            """;

    private static final String SQL_FIND_MIX_ID = """
            SELECT id
            FROM audio_tracks_storage.mix
            WHERE name = ?
            """;

    private static final String SQL_FIND_REVIEW_BY_ID = """
            SELECT mix.id                         AS id,
                   mix.name                       AS name,
                   mix.description                AS description,
                   rm.id                          AS rm_id,
                   rm.review_text                 AS rm_review_text,
                   rm.user_account_id             AS rm_user_account_id,
                   rm.mix_id                      AS rm_mix_id,
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
            FROM review_storage.review_about_mix rm
                     JOIN audio_tracks_storage.mix ON rm.mix_id = mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            WHERE rm.id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT mix.id                         AS id,
                   mix.name                       AS name,
                   mix.description                AS description,
                   rm.id                          AS rm_id,
                   rm.review_text                 AS rm_review_text,
                   rm.user_account_id             AS rm_user_account_id,
                   rm.mix_id                      AS rm_mix_id,
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
            FROM review_storage.review_about_mix rm
                     JOIN audio_tracks_storage.mix ON rm.mix_id = mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            """;

    private static final String SQL_UPDATE_MIX_REVIEW = """
            UPDATE review_storage.review_about_mix
            SET review_text     = ?,
                user_account_id = ?,
                mix_id          = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_MIX_REVIEW = """
            DELETE
            FROM review_storage.review_about_mix
            WHERE id = ?
            """;

    private static final String SQL_FIND_REVIEWS_BY_USER_LOGIN = """
            SELECT mix.id                         AS id,
                   mix.name                       AS name,
                   mix.description                AS description,
                   rm.id                          AS rm_id,
                   rm.review_text                 AS rm_review_text,
                   rm.user_account_id             AS rm_user_account_id,
                   rm.mix_id                      AS rm_mix_id,
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
            FROM review_storage.review_about_mix rm
                     JOIN audio_tracks_storage.mix ON rm.mix_id = mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            WHERE data.login = ?
            """;

    private static final String SQL_FIND_REVIEWS_BY_USER_ID = """
            SELECT mix.id                         AS id,
                   mix.name                       AS name,
                   mix.description                AS description,
                   rm.id                          AS rm_id,
                   rm.review_text                 AS rm_review_text,
                   rm.user_account_id             AS rm_user_account_id,
                   rm.mix_id                      AS rm_mix_id,
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
            FROM review_storage.review_about_mix rm
                     JOIN audio_tracks_storage.mix ON rm.mix_id = mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            WHERE data.id = ?
            """;

    @Override
    public MixReview save(MixReview review) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveReviewStatement = connection.prepareStatement(SQL_SAVE_MIX_REVIEW, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement findMixIdStatement = connection.prepareStatement(SQL_FIND_MIX_ID);
             PreparedStatement findUserAccountIdStatement = connection.prepareStatement(SQL_FIND_USER_ACCOUNT_ID)) {
            findUserAccountIdStatement.setString(1, review.getUserAccount().getLogin());
            ResultSet resultSet = findUserAccountIdStatement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong("id");
                review.getUserAccount().setId(userId);
                findMixIdStatement.setString(1, review.getMix().getName());
                ResultSet query = findMixIdStatement.executeQuery();
                if (query.next()) {
                    long mixId = query.getLong("id");
                    review.getMix().setId(mixId);
                    saveReviewStatement.setString(1, review.getReviewText());
                    saveReviewStatement.setString(2, review.getUserAccount().getLogin());
                    saveReviewStatement.setString(3, review.getMix().getName());
                    saveReviewStatement.executeUpdate();
                    ResultSet generatedKeys = saveReviewStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        long reviewId = generatedKeys.getLong("id");
                        review.setId(reviewId);
                    }
                }
            }
            return review;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<MixReview> findById(Long id) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewById = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID)) {
            findReviewById.setLong(1, id);
            ResultSet resultSet = findReviewById.executeQuery();
            MixReview mixReview = null;
            if (resultSet.next()) {
                mixReview = buildMixReview(resultSet);
            }
            return Optional.ofNullable(mixReview);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findAll(ReviewFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.reviewText() != null) {
            whereSql.add("rm.review_text LIKE ?");
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
            List<MixReview> mixReviews = new ArrayList<>();
            while (resultSet.next()) {
                mixReviews.add(buildMixReview(resultSet));
            }
            return mixReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(MixReview mixReview) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_MIX_REVIEW)) {
            updateStatement.setString(1, mixReview.getReviewText());
            updateStatement.setLong(2, mixReview.getUserAccount().getId());
            updateStatement.setLong(3, mixReview.getMix().getId());
            updateStatement.setLong(4, mixReview.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteMixReviewByIdStatement = connection.prepareStatement(SQL_DELETE_MIX_REVIEW)) {
            deleteMixReviewByIdStatement.setLong(1, id);
            return deleteMixReviewByIdStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_USER_LOGIN + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setString(1, login);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<MixReview> mixReviews = new ArrayList<>();
            while (resultSet.next()) {
                mixReviews.add(buildMixReview(resultSet));
            }
            return mixReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_USER_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByAccountId = connection.prepareStatement(sql)) {
            findReviewsByAccountId.setLong(1, userId);
            findReviewsByAccountId.setObject(2, filter.limit());
            findReviewsByAccountId.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByAccountId.executeQuery();
            List<MixReview> mixReviews = new ArrayList<>();
            while (resultSet.next()) {
                mixReviews.add(buildMixReview(resultSet));
            }
            return mixReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private MixReview buildMixReview(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildUserAccount(resultSet);
        Mix mix = buildMix(resultSet);
        return new MixReview(
                resultSet.getLong("rm_id"),
                resultSet.getString("rm_review_text"),
                userAccount,
                mix
        );
    }

    private Mix buildMix(ResultSet resultSet) throws SQLException {
        return new Mix(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
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
}