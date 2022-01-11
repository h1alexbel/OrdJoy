package com.ordjoy.dao.impl;

import com.ordjoy.dao.UserDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.UserData;
import com.ordjoy.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;

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
            WHERE login = ?
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
            WHERE email = ?
            """;

    private static final String SQL_FIND_DISCOUNT_LEVEL_BY_USER_ID = """
            SELECT discount_percentage_level
            FROM user_storage.user_account_data
            WHERE id = ?
            """;

    private static final String SQL_FIND_DISCOUNT_LEVEL_BY_USER_EMAIL = """
            SELECT discount_percentage_level
            FROM user_storage.user_account_data
            WHERE email = ?
            """;

    private static final String SQL_ADD_DISCOUNT_PERCENTAGE_LEVEL = """
            UPDATE user_storage.user_account_data
            SET discount_percentage_level = ?
            WHERE email = ?
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

    @Override
    public UserAccount save(UserAccount userAccount) {
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
    public Optional<UserAccount> findById(Long id) {
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
    public List<UserAccount> findAll(UserAccountFilter filter) {
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
    public void update(UserAccount userAccount) {
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
    public boolean deleteById(Long id) {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteUserById = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            deleteUserById.setLong(1, id);
            return deleteUserById.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) {
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
    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) {
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
    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) {
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
    public Optional<UserAccount> findUserByLogin(String login) {
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
    public Optional<UserAccount> findUserByEmail(String email) {
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
        return new UserAccount(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getInt(DISCOUNT_PERCENTAGE_LEVEL),
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