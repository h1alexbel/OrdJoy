package com.ordjoy.dao.impl;

import com.ordjoy.dao.UserDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.UserData;
import com.ordjoy.exception.DataBaseException;
import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class UserDaoImpl implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String DISCOUNT_PERCENTAGE_LEVEL = "discount_percentage_level";

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_USER_ACCOUNT = """
            INSERT INTO user_storage.user_account_data(email, login, password, discount_percentage_level, role, first_name,
                                                       last_name, age, card_number)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String SQL_FIND_USER_BY_USER_ID = """
            SELECT id,
                   email,
                   login,
                   password,
                   discount_percentage_level,
                   role,
                   first_name,
                   last_name,
                   age,
                   card_number
            FROM user_storage.user_account_data
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_ACCOUNT = """
            UPDATE user_storage.user_account_data
            SET email = ?,
                login = ?,
                password = ?,
                discount_percentage_level = ?,
                role = ?,
                first_name = ?,
                last_name = ?,
                age = ?,
                card_number = ?
            WHERE id = ?     
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM user_storage.user_account_data
            WHERE id = ?
            """;

    private static final String SQL_DELETE_FROM_MIX_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_mix
            WHERE user_account_id = ?
            """;

    private static final String SQL_DELETE_FROM_ALBUM_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_album
            WHERE user_account_id = ?
            """;

    private static final String SQL_DELETE_FROM_TRACK_REVIEW_TABLE = """
            DELETE
            FROM review_storage.review_about_track
            WHERE user_account_id = ?
            """;

    private static final String SQL_DELETE_FROM_ORDER_TABLE = """
            DELETE
            FROM user_storage.order
            WHERE user_account_id = ?
            """;

    private static final String SQL_FIND_BY_LOGIN = """
            SELECT id,
                   email,
                   login,
                   password,
                   discount_percentage_level,
                   role,
                   first_name,
                   last_name,
                   age,
                   card_number
            FROM user_storage.user_account_data
            WHERE login LIKE ?
            """;

    private static final String SQL_FIND_USER_BY_EMAIL = """
            SELECT id,
                   email,
                   login,
                   password,
                   discount_percentage_level,
                   role,
                   first_name,
                   last_name,
                   age,
                   card_number
            FROM user_storage.user_account_data
            WHERE email LIKE ?
            """;

    private static final String SQL_FIND_DISCOUNT_LEVEL_BY_USER_ID = """
            SELECT discount_percentage_level
            FROM user_storage.user_account_data
            WHERE id = ?
            """;

    private static final String SQL_FIND_DISCOUNT_LEVEL_BY_USER_EMAIL = """
            SELECT discount_percentage_level
            FROM user_storage.user_account_data
            WHERE email LIKE ?
            """;

    private static final String SQL_ADD_DISCOUNT_PERCENTAGE_LEVEL = """
            UPDATE user_storage.user_account_data
            SET discount_percentage_level = ?
            WHERE email LIKE ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id,
                   email,
                   login,
                   password,
                   discount_percentage_level,
                   role,
                   first_name,
                   last_name,
                   age,
                   card_number
            FROM user_storage.user_account_data
            """;

    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASS = """
            SELECT id,
                   email,
                   login,
                   password,
                   discount_percentage_level,
                   role,
                   first_name,
                   last_name,
                   age,
                   card_number
            FROM user_storage.user_account_data
            WHERE login = ? AND password = ?
            """;

    private static final String SQL_GET_USER_ROLE_RECORDS = """
            SELECT count(*)
            FROM user_storage.user_account_data
            WHERE role LIKE 'CLIENT_ROLE'
            """;

    private static final String SQL_GET_ADMIN_ROLE_RECORDS = """
            SELECT count(*)
            FROM user_storage.user_account_data
            WHERE role LIKE 'ADMIN_ROLE'
            """;

    @Override
    public UserAccount save(UserAccount userAccount) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveUserAccountStatement = connection.prepareStatement(SQL_SAVE_USER_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            saveUserAccountStatement.setString(1, userAccount.getEmail());
            saveUserAccountStatement.setString(2, userAccount.getLogin());
            saveUserAccountStatement.setString(3, userAccount.getPassword());
            saveUserAccountStatement.setInt(4, userAccount.getDiscountPercentageLevel());
            saveUserAccountStatement.setString(5, userAccount.getUserData().getUserRole().toString());
            saveUserAccountStatement.setString(6, userAccount.getUserData().getFirstName());
            saveUserAccountStatement.setString(7, userAccount.getUserData().getLastName());
            saveUserAccountStatement.setInt(8, userAccount.getUserData().getAge());
            saveUserAccountStatement.setString(9, userAccount.getUserData().getCardNumber());
            saveUserAccountStatement.executeUpdate();
            ResultSet generatedKeys = saveUserAccountStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userAccountId = generatedKeys.getLong("id");
                userAccount.setId(userAccountId);
            }
            return userAccount;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<UserAccount> findById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_USER_BY_USER_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildUserAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<UserAccount> findUserAccountByLoginAndPassword(String login, String password) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findUserByLoginAndPassword = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASS)) {
            findUserByLoginAndPassword.setString(1, login);
            findUserByLoginAndPassword.setString(2, password);
            ResultSet resultSet = findUserByLoginAndPassword.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildUserAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<UserAccount> findAll(UserAccountFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.role() != null) {
            whereSql.add("role LIKE ?");
            parameters.add("%" + filter.role() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " ORDER BY id DESC LIMIT ? OFFSET ?"));
        String sql = SQL_FIND_ALL + where;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<UserAccount> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(buildUserAccount(resultSet));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Long getUserRoleTableRecords() throws DaoException {
        long total;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement allRecordsStatement = connection.prepareStatement(SQL_GET_USER_ROLE_RECORDS)) {
            ResultSet resultSet = allRecordsStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getLong("count");
            } else {
                total = 0;
            }
            return total;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Long getAdminRoleTableRecords() throws DaoException {
        long total;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement allRecordsStatement = connection.prepareStatement(SQL_GET_ADMIN_ROLE_RECORDS)) {
            ResultSet resultSet = allRecordsStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getLong("count");
            } else {
                total = 0;
            }
            return total;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(UserAccount userAccount) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT)) {
            updateStatement.setString(1, userAccount.getEmail());
            updateStatement.setString(2, userAccount.getLogin());
            updateStatement.setString(3, userAccount.getPassword());
            updateStatement.setInt(4, userAccount.getDiscountPercentageLevel());
            updateStatement.setString(5, userAccount.getUserData().getUserRole().toString());
            updateStatement.setString(6, userAccount.getUserData().getFirstName());
            updateStatement.setString(7, userAccount.getUserData().getLastName());
            updateStatement.setInt(8, userAccount.getUserData().getAge());
            updateStatement.setString(9, userAccount.getUserData().getCardNumber());
            updateStatement.setLong(10, userAccount.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement deleteFromMixReviewTable = null;
        PreparedStatement deleteFromAlbumReviewTable = null;
        PreparedStatement deleteFromTrackReviewTable = null;
        PreparedStatement deleteFromOrderTable = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            deleteStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            deleteFromMixReviewTable = connection.prepareStatement(SQL_DELETE_FROM_MIX_REVIEW_TABLE);
            deleteFromAlbumReviewTable = connection.prepareStatement(SQL_DELETE_FROM_ALBUM_REVIEW_TABLE);
            deleteFromTrackReviewTable = connection.prepareStatement(SQL_DELETE_FROM_TRACK_REVIEW_TABLE);
            deleteFromOrderTable = connection.prepareStatement(SQL_DELETE_FROM_ORDER_TABLE);
            deleteFromMixReviewTable.setLong(1, id);
            deleteFromMixReviewTable.executeUpdate();
            deleteFromAlbumReviewTable.setLong(1, id);
            deleteFromAlbumReviewTable.executeUpdate();
            deleteFromTrackReviewTable.setLong(1, id);
            deleteFromTrackReviewTable.executeUpdate();
            deleteFromOrderTable.setLong(1, id);
            deleteFromOrderTable.executeUpdate();
            deleteStatement.setLong(1, id);
            connection.commit();
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            rollbackTransaction(connection);
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        } finally {
            closeConnection(connection);
            closeStatement(deleteStatement);
            closeStatement(deleteFromOrderTable);
            closeStatement(deleteFromTrackReviewTable);
            closeStatement(deleteFromAlbumReviewTable);
            closeStatement(deleteFromMixReviewTable);
        }
    }

    @Override
    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement addDiscountPercentageLevelStatement = connection.prepareStatement(SQL_ADD_DISCOUNT_PERCENTAGE_LEVEL)) {
            addDiscountPercentageLevelStatement.setInt(1, discountPercentageLevel);
            addDiscountPercentageLevelStatement.setString(2, userEmail);
            addDiscountPercentageLevelStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findDiscountLevelByUserId = connection.prepareStatement(SQL_FIND_DISCOUNT_LEVEL_BY_USER_ID)) {
            findDiscountLevelByUserId.setLong(1, userId);
            ResultSet resultSet = findDiscountLevelByUserId.executeQuery();
            Integer discountPercentageLevel = null;
            if (resultSet.next()) {
                discountPercentageLevel = resultSet.getInt(DISCOUNT_PERCENTAGE_LEVEL);
            }
            return Optional.ofNullable(discountPercentageLevel);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findDiscountLevelByUserId = connection.prepareStatement(SQL_FIND_DISCOUNT_LEVEL_BY_USER_EMAIL)) {
            findDiscountLevelByUserId.setString(1, email);
            ResultSet resultSet = findDiscountLevelByUserId.executeQuery();
            Integer discountPercentageLevel = null;
            if (resultSet.next()) {
                discountPercentageLevel = resultSet.getInt(DISCOUNT_PERCENTAGE_LEVEL);
            }
            return Optional.ofNullable(discountPercentageLevel);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<UserAccount> findUserByLogin(String login) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_BY_LOGIN)) {
            findByIdStatement.setString(1, login);
            ResultSet resultSet = findByIdStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildUserAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<UserAccount> findUserByEmail(String email) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            findByIdStatement.setString(1, email);
            ResultSet resultSet = findByIdStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildUserAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private UserAccount buildUserAccount(ResultSet resultSet) throws SQLException {
        UserData data = buildUserData(resultSet);
        return UserAccount.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .discountPercentageLevel(resultSet.getInt(DISCOUNT_PERCENTAGE_LEVEL))
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