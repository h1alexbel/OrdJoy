package com.ordjoy.dao.impl;

import com.ordjoy.dao.UserDao;
import com.ordjoy.dbconnection.ConnectionManager;
import com.ordjoy.dto.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserAccountData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class UserDaoImpl implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM user_storage.user_account
            WHERE id = ?
            """;
    private static final String SQL_SAVE_USER = """
            INSERT INTO user_storage.user_account(email, login, password, role)
            VALUES (?,?,?,?);     
            """;

    private static final String SQL_SAVE_USER_DATA = """
            INSERT INTO user_storage.user_data (first_name, last_name, age, user_account_id) 
            VALUES (?,?,?,?);
            """;

    private static final String SQL_UPDATE_USER = """
            UPDATE user_storage.user_account
            SET email = ?,
                login = ?,
                password = ?,
                role = ?
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_USER_DATA = """
            UPDATE user_storage.user_data
            SET first_name = ?,
                last_name = ?,
                age = ?,
                user_account_id = ?
            WHERE user_account_id = ?   
            """;

    private static final String SQL_FIND_USER_BY_ID = """
            SELECT id, email, login, password, role
            FROM user_storage.user_account
            WHERE id = ?
            """;

    private static final String SQL_FIND_USER_BY_NAME = """
            SELECT id, email, login, password, role
            FROM user_storage.user_account
            WHERE login = ?
            """;

    private static final String SQL_FIND_USER_BY_EMAIL = """
            SELECT id, email, login, password, role
            FROM user_storage.user_account
            WHERE email = ?
            """;

    private static final String SQL_FIND_USER_DATA_BY_USER_ID = """
            SELECT first_name,
                   last_name,
                   age,
                   user_account_id,
                   ua.id    AS id,
                   ua.email AS email,
                   ua.login AS login,
                   ua.password AS password,
                   ua.role  AS role
            FROM user_storage.user_data
                     JOIN user_storage.user_account ua ON ua.id = user_data.user_account_id
            WHERE ua.id = ?;
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id, email, login, password, role
            FROM user_storage.user_account
            """;

    @Override
    public UserAccount saveUser(UserAccount userAccount) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement saveUserStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            saveUserStatement.setString(1, userAccount.getEmail());
            saveUserStatement.setString(2, userAccount.getLogin());
            saveUserStatement.setString(3, userAccount.getPassword());
            saveUserStatement.setString(4, userAccount.getUserRole().toString());
            saveUserStatement.executeUpdate();
            ResultSet generatedKeys = saveUserStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userAccount.setId(generatedKeys.getLong("id"));
            }
            return userAccount;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UserAccount userAccount) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            updateStatement.setString(1, userAccount.getEmail());
            updateStatement.setString(2, userAccount.getLogin());
            updateStatement.setString(3, userAccount.getPassword());
            updateStatement.setString(4, userAccount.getUserRole().toString());
            updateStatement.setLong(5, userAccount.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserAccount> findByName(String login) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByNameStatement = connection.prepareStatement(SQL_FIND_USER_BY_NAME)) {
            findByNameStatement.setString(1, login);
            ResultSet resultSet = findByNameStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserAccount> findUserByEmail(String email) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findUserByEmailStatement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            findUserByEmailStatement.setString(1, email);
            ResultSet resultSet = findUserByEmailStatement.executeQuery();
            UserAccount userAccount = null;
            if (resultSet.next()) {
                userAccount = buildAccount(resultSet);
            }
            return Optional.ofNullable(userAccount);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserAccount> findAll(UserAccountFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.email() != null) {
            whereSql.add("email LIKE ?");
            parameters.add("%" + filter.email() + "%");
        }
        if (filter.login() != null) {
            whereSql.add("login LIKE ?");
            parameters.add(filter.login());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ?  OFFSET ? "));
        String sql = SQL_FIND_ALL + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserAccount> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(buildAccount(resultSet));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserAccount buildAccount(ResultSet resultSet) throws SQLException {
        return new UserAccount(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                UserRole.valueOf(resultSet.getString("role"))
        );
    }

    @Override
    public UserAccount saveUserWithAccountData(UserAccount userAccount, UserAccountData userAccountData) {
        Connection connection = null;
        PreparedStatement saveUserStatement = null;
        PreparedStatement dataStatement = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            saveUserStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS);
            saveUserStatement.setString(1, userAccount.getEmail());
            saveUserStatement.setString(2, userAccount.getLogin());
            saveUserStatement.setString(3, userAccount.getPassword());
            saveUserStatement.setString(4, userAccount.getUserRole().toString());
            saveUserStatement.executeUpdate();
            ResultSet generatedKeys = saveUserStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong("id");
                userAccount.setId(userId);
                dataStatement = connection.prepareStatement(SQL_SAVE_USER_DATA);
                dataStatement.setString(1, userAccountData.getFirstName());
                dataStatement.setString(2, userAccountData.getLastName());
                dataStatement.setInt(3, userAccountData.getAge());
                dataStatement.setLong(4, userId);
                dataStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
            throw new DaoException(e);
        } finally {
            closeConnection(connection);
            closeStatement(saveUserStatement);
            closeStatement(dataStatement);
        }
        return userAccount;
    }

    @Override
    public void updateData(UserAccountData userAccountData) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateDataStatement = connection.prepareStatement(SQL_UPDATE_USER_DATA)) {
            updateDataStatement.setString(1, userAccountData.getFirstName());
            updateDataStatement.setString(2, userAccountData.getLastName());
            updateDataStatement.setInt(3, userAccountData.getAge());
            updateDataStatement.setLong(4, userAccountData.getUserAccount().getId());
            updateDataStatement.setLong(5, userAccountData.getUserAccount().getId());
            updateDataStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserAccountData> findUserDataByUserId(Long userId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findDataByUserIdStatement = connection.prepareStatement(SQL_FIND_USER_DATA_BY_USER_ID)) {
            findDataByUserIdStatement.setLong(1, userId);
            ResultSet resultSet = findDataByUserIdStatement.executeQuery();
            UserAccountData accountData = null;
            if (resultSet.next()) {
                accountData = buildUserData(resultSet);
            }
            return Optional.ofNullable(accountData);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserAccountData buildUserData(ResultSet resultSet) throws SQLException {
        UserAccount account = buildAccount(resultSet);
        return new UserAccountData(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age"),
                account
        );
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