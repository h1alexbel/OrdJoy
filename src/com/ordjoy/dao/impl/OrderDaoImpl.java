package com.ordjoy.dao.impl;

import com.ordjoy.dao.OrderDao;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.dbmanager.ProxyConnection;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.OrderFilter;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class OrderDaoImpl implements OrderDao {

    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();

    private OrderDaoImpl() {

    }

    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_ORDER = """
            INSERT INTO user_storage.order(price, user_account_id, order_status, track_id)
            VALUES (?, (SELECT id FROM user_storage.user_account_data WHERE login = ?), ?,
                    (SELECT id FROM audio_tracks_storage.track WHERE title = ?));
            """;

    private static final String SQL_FIND_ORDER_BY_ID = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE ord.id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            """;

    private static final String SQL_UPDATE_ORDER = """
            UPDATE user_storage.order
            SET price = ?,
                user_account_id = ?,
                order_status = ?,
                track_id = ?
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_STATUS = """
            UPDATE user_storage.order
            SET order_status = ?
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_PRICE = """
            UPDATE user_storage.order
            SET price = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM user_storage.order
            WHERE id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_PRICE = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE ord.price = ?
            """;

    private static final String SQL_FIND_ORDER_BY_USER_ID = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE ord.user_account_id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_USER_EMAIL = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE uad.email LIKE ?
            """;

    private static final String SQL_FIND_ORDER_BY_USER_LOGIN = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE uad.login LIKE ?
            """;

    private static final String SQL_FIND_ORDER_BY_TRACK_ID = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE tr.id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_TRACK_NAME = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE tr.title LIKE ?
            """;

    private static final String SQL_FIND_ORDER_BY_ORDER_STATUS = """
            SELECT ord.id                        AS id,
                   ord.price                     AS price,
                   ord.user_account_id           AS user_account_id,
                   ord.order_status              AS order_status,
                   ord.track_id                  AS track_id,
                   uad.id                        AS uad_id,
                   uad.login                     AS uad_login,
                   uad.first_name                AS uad_first_name,
                   uad.last_name                 AS uad_last_name,
                   uad.password                  AS pass,
                   uad.role                      AS role,
                   uad.age                       AS age,
                   uad.discount_percentage_level AS discount_percentage_level,
                   uad.card_number               AS card_number,
                   uad.email                     AS email,
                   tr.id                         AS tr_id,
                   tr.song_url                   AS song_url,
                   tr.title                      AS title,
                   tr.album_id                   AS album_id,
                   a.id                          AS al_id,
                   a.title                       AS al_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account_data uad ON uad.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
            WHERE ord.order_status LIKE ?
            """;

    private static final String SQL_GET_RECORDS = """
            SELECT count(*)
            FROM user_storage.order
            WHERE order_status LIKE 'ACCEPTED'
            """;

    @Override
    public Order save(Order order) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement saveOrderStatement = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            saveOrderStatement.setBigDecimal(1, order.getPrice());
            saveOrderStatement.setString(2, order.getUserAccount().getLogin());
            saveOrderStatement.setString(3, order.getOrderStatus().toString());
            saveOrderStatement.setString(4, order.getTrack().getTitle());
            saveOrderStatement.executeUpdate();
            ResultSet generatedKeys = saveOrderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long orderId = generatedKeys.getLong("id");
                order.setId(orderId);
            }
            return order;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_ORDER_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = buildOrder(resultSet);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Long getTableRecords() throws DaoException {
        long totalRecords;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement recordsStatement = connection.prepareStatement(SQL_GET_RECORDS)) {
            ResultSet resultSet = recordsStatement.executeQuery();
            if (resultSet.next()) {
                totalRecords = resultSet.getLong("count");
            } else {
                totalRecords = 0;
            }
            return totalRecords;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findAll(OrderFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.price() != null) {
            whereSql.add("price = ?");
            parameters.add(filter.price());
        }
        if (filter.orderStatus() != null) {
            whereSql.add("order_status LIKE ?");
            parameters.add("%" + filter.orderStatus() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " ORDER BY ord.id DESC LIMIT ? OFFSET ?"));
        String sql = SQL_FIND_ALL + where;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Order order) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            updateStatement.setBigDecimal(1, order.getPrice());
            updateStatement.setLong(2, order.getUserAccount().getId());
            updateStatement.setString(3, order.getOrderStatus().toString());
            updateStatement.setLong(4, order.getTrack().getId());
            updateStatement.setLong(5, order.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void updateOrderStatus(OrderStatus newStatus, Long orderId) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updateStatusStatement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            updateStatusStatement.setString(1, newStatus.toString());
            updateStatusStatement.setLong(2, orderId);
            updateStatusStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void updateOrderPrice(BigDecimal price, Long orderId) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement updatePriceStatement = connection.prepareStatement(SQL_UPDATE_PRICE)) {
            updatePriceStatement.setBigDecimal(1, price);
            updatePriceStatement.setLong(2, orderId);
            updatePriceStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByPrice(BigDecimal price) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPrice = connection.prepareStatement(SQL_FIND_ORDER_BY_PRICE)) {
            findOrderByPrice.setBigDecimal(1, price);
            ResultSet resultSet = findOrderByPrice.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByUserId(Long userAccountId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_USER_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setLong(1, userAccountId);
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByUserEmail(String email, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_USER_EMAIL + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setString(1, email);
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByUserLogin(String login, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_USER_LOGIN + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setString(1, login);
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByTrackId(Long trackId, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_TRACK_ID + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setLong(1, trackId);
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByTrackName(String trackName, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_TRACK_NAME + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setString(1, trackName);
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus, DefaultFilter filter) throws DaoException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_ORDER_STATUS + LIMIT_OFFSET;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement findOrderByPriceStatement = connection.prepareStatement(sql)) {
            findOrderByPriceStatement.setString(1, orderStatus.toString());
            findOrderByPriceStatement.setObject(2, filter.limit());
            findOrderByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findOrderByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildUserAccount(resultSet);
        Track track = buildTrack(resultSet);
        return Order.builder()
                .id(resultSet.getLong("id"))
                .price(resultSet.getBigDecimal("price"))
                .userAccount(userAccount)
                .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                .track(track)
                .build();
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Album album = buildAlbum(resultSet);
        return Track.builder()
                .id(resultSet.getLong("tr_id"))
                .songUrl(resultSet.getString("song_url"))
                .title(resultSet.getString("title"))
                .album(album)
                .build();
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        return Album.builder()
                .id(resultSet.getLong("al_id"))
                .title(resultSet.getString("al_title"))
                .build();
    }

    private UserAccount buildUserAccount(ResultSet resultSet) throws SQLException {
        UserData data = buildUserData(resultSet);
        return UserAccount.builder()
                .id(resultSet.getLong("uad_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("uad_login"))
                .password(resultSet.getString("pass"))
                .discountPercentageLevel(resultSet.getInt("discount_percentage_level"))
                .userData(data)
                .build();
    }

    private UserData buildUserData(ResultSet resultSet) throws SQLException {
        return UserData.builder()
                .userRole(UserRole.valueOf(resultSet.getString("role")))
                .firstName(resultSet.getString("uad_first_name"))
                .lastName(resultSet.getString("uad_last_name"))
                .age(resultSet.getInt("age"))
                .cardNumber(resultSet.getString("card_number"))
                .build();
    }
}