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

    private MixReviewDaoImpl() {

    }

    public static MixReviewDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_MIX_REVIEW = """
            INSERT INTO review_storage.review_about_mix(review_text, user_account_id, mix_id)
            VALUES (?, (SELECT id FROM user_storage.user_account_data WHERE login = ?),
                    (SELECT id FROM audio_tracks_storage.mix WHERE name = ?));
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
                     JOIN user_storage.user_account_data data ON rm.user_account_id = data.id
                     JOIN audio_tracks_storage.mix mix ON rm.mix_id = mix.id
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
                     JOIN user_storage.user_account_data data ON rm.user_account_id = data.id
                     JOIN audio_tracks_storage.mix mix ON rm.mix_id = mix.id
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
                     JOIN user_storage.user_account_data data ON rm.user_account_id = data.id
                     JOIN audio_tracks_storage.mix mix ON rm.mix_id = mix.id
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
                     JOIN user_storage.user_account_data data ON rm.user_account_id = data.id
                     JOIN audio_tracks_storage.mix mix ON rm.mix_id = mix.id
            WHERE data.id = ?
            """;

    private static final String SQL_GET_RECORDS = """
            SELECT count(*)
            FROM review_storage.review_about_mix
            """;

    @Override
    public MixReview save(MixReview review) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveReviewStatement = connection.prepareStatement(SQL_SAVE_MIX_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            saveReviewStatement.setString(1, review.getReviewText());
            saveReviewStatement.setString(2, review.getUserAccount().getLogin());
            saveReviewStatement.setString(3, review.getMix().getName());
            saveReviewStatement.executeUpdate();
            ResultSet generatedKeys = saveReviewStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long reviewId = generatedKeys.getLong("id");
                review.setId(reviewId);
            }
            return review;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<MixReview> findById(Long id) throws DaoException {
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
    public List<MixReview> findAll(ReviewFilter filter) throws DaoException {
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
                .collect(joining(" AND ", " WHERE ", " ORDER BY rm.id DESC LIMIT ? OFFSET ?"));
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
    public Long getTableRecords() throws DaoException {
        long records;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement allRecords = connection.prepareStatement(SQL_GET_RECORDS)) {
            ResultSet resultSet = allRecords.executeQuery();
            if (resultSet.next()) {
                records = resultSet.getLong("count");
            } else {
                records = 0;
            }
            return records;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(MixReview mixReview) throws DaoException {
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
    public boolean deleteById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteMixReviewByIdStatement = connection.prepareStatement(SQL_DELETE_MIX_REVIEW)) {
            deleteMixReviewByIdStatement.setLong(1, id);
            return deleteMixReviewByIdStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException {
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
    public List<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException {
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
        return MixReview.builder()
                .id(resultSet.getLong("rm_id"))
                .reviewText(resultSet.getString("rm_review_text"))
                .userAccount(userAccount)
                .mix(mix)
                .build();
    }

    private Mix buildMix(ResultSet resultSet) throws SQLException {
        return Mix.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
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