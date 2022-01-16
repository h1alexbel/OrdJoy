package com.ordjoy.dao.impl;

import com.ordjoy.dao.MixDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.DataBaseException;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;

import java.sql.*;
import java.util.*;

import static com.ordjoy.util.ExceptionMessageUtils.*;

public class MixDaoImpl implements MixDao {

    private static final MixDaoImpl INSTANCE = new MixDaoImpl();

    private MixDaoImpl() {

    }

    public static MixDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_MIX = """
            INSERT INTO audio_tracks_storage.mix(name, description)
            VALUES (?, ?);
            """;

    private static final String SQL_FIND_MIX_BY_ID = """
            SELECT id, name, description
            FROM audio_tracks_storage.mix
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id, name, description
            FROM audio_tracks_storage.mix
            """;

    private static final String SQL_DELETE_MIX_BY_ID = """
            DELETE
            FROM audio_tracks_storage.mix
            WHERE id = ?
            """;

    private static final String SQL_DELETE_FROM_MUTUAL_TABLE = """
            DELETE
            FROM audio_tracks_storage.track_mixes
            WHERE mix_id = ?
            """;

    private static final String SQL_DELETE_FROM_MIX_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_mix
            WHERE mix_id = ?
            """;

    private static final String SQL_UPDATE_MIX = """
            UPDATE audio_tracks_storage.mix
            SET name        = ?,
                description = ?
            WHERE id = ?
            """;

    private static final String SQL_FIND_MIX_BY_NAME = """
            SELECT id, name, description
            FROM audio_tracks_storage.mix
            WHERE name = ?
            """;

    private static final String SQL_FIND_REVIEWS_BY_MIX_ID = """
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
            FROM audio_tracks_storage.mix mix
                     JOIN review_storage.review_about_mix rm ON mix.id = rm.mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            WHERE mix.id = ?            
            """;

    private static final String SQL_FIND_REVIEWS_BY_MIX_NAME = """
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
            FROM audio_tracks_storage.mix mix
                     JOIN review_storage.review_about_mix rm ON mix.id = rm.mix_id
                     JOIN user_storage.user_account_data data ON data.id = rm.user_account_id
            WHERE mix.name = ?      
            """;

    @Override
    public Mix save(Mix mix) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveMixStatement = connection.prepareStatement(SQL_SAVE_MIX, Statement.RETURN_GENERATED_KEYS)) {
            saveMixStatement.setString(1, mix.getName());
            saveMixStatement.setString(2, mix.getDescription());
            saveMixStatement.executeUpdate();
            ResultSet generatedKeys = saveMixStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long mixId = generatedKeys.getLong("id");
                mix.setId(mixId);
            }
            return mix;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Mix> findById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_MIX_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Mix mix = null;
            if (resultSet.next()) {
                mix = buildMix(resultSet);
            }
            return Optional.ofNullable(mix);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Mix> findAll(MixFilter filter) throws DaoException {
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
            List<Mix> mixes = new ArrayList<>();
            while (resultSet.next()) {
                mixes.add(buildMix(resultSet));
            }
            return mixes;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Mix mix) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_MIX)) {
            updateStatement.setString(1, mix.getName());
            updateStatement.setString(2, mix.getDescription());
            updateStatement.setLong(3, mix.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }


    @Override
    public boolean deleteById(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement deleteFromMutualTableStatement = null;
        PreparedStatement deleteFromMixReviewStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteStatement = connection.prepareStatement(SQL_DELETE_MIX_BY_ID);
            deleteFromMutualTableStatement = connection.prepareStatement(SQL_DELETE_FROM_MUTUAL_TABLE);
            deleteFromMixReviewStatement = connection.prepareStatement(SQL_DELETE_FROM_MIX_REVIEW_TABLE);
            deleteFromMutualTableStatement.setLong(1, id);
            deleteFromMutualTableStatement.executeUpdate();
            deleteFromMixReviewStatement.setLong(1, id);
            deleteFromMixReviewStatement.executeUpdate();
            deleteStatement.setLong(1, id);
            deleteFromMutualTableStatement.executeUpdate();
            connection.commit();
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            rollbackTransaction(connection);
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        } finally {
            closeConnection(connection);
            closeStatement(deleteStatement);
            closeStatement(deleteFromMutualTableStatement);
            closeStatement(deleteFromMixReviewStatement);
        }
    }

    @Override
    public Optional<Mix> findMixByMixName(String mixName) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_MIX_BY_NAME)) {
            findByIdStatement.setString(1, mixName);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Mix mix = null;
            if (resultSet.next()) {
                mix = buildMix(resultSet);
            }
            return Optional.ofNullable(mix);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findMixReviewByMixName(String mixName, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_MIX_NAME + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByMixName = connection.prepareStatement(sql)) {
            findReviewsByMixName.setString(1, mixName);
            findReviewsByMixName.setObject(2, filter.limit());
            findReviewsByMixName.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByMixName.executeQuery();
            List<MixReview> mixReviews = new ArrayList<>();
            MixReview mixReview = null;
            while (resultSet.next()) {
                mixReview = buildMixReview(resultSet);
                mixReviews.add(mixReview);
            }
            return mixReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<MixReview> findMixReviewsByMixId(Long mixId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_REVIEWS_BY_MIX_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findReviewsByMixName = connection.prepareStatement(sql)) {
            findReviewsByMixName.setLong(1, mixId);
            findReviewsByMixName.setObject(2, filter.limit());
            findReviewsByMixName.setObject(3, filter.offset());
            ResultSet resultSet = findReviewsByMixName.executeQuery();
            List<MixReview> mixReviews = new ArrayList<>();
            MixReview mixReview = null;
            while (resultSet.next()) {
                mixReview = buildMixReview(resultSet);
                mixReviews.add(mixReview);
            }
            return mixReviews;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Mix buildMix(ResultSet resultSet) throws SQLException {
        return new Mix(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }

    private MixReview buildMixReview(ResultSet resultSet) throws SQLException {
        UserAccount account = buildUserAccount(resultSet);
        Mix mix = buildMix(resultSet);
        return new MixReview(
                resultSet.getLong("rm_id"),
                resultSet.getString("rm_review_text"),
                account,
                mix
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