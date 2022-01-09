package com.ordjoy.dao.impl;

import com.ordjoy.dao.OrderDao;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.OrderFilter;
import com.ordjoy.dbmanager.ConnectionManager;

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
            INSERT INTO user_storage.order(price, card_number, user_account_id, track_id)
            VALUES (?, ?, (SELECT id FROM user_storage.user_account WHERE login = ?),
                    (SELECT id FROM audio_tracks_storage.track WHERE title = ?));
            """;

    private static final String SQL_FIND_USER_ACCOUNT_ID = """
            SELECT id
            FROM user_storage.user_account
            WHERE login = ?
            """;

    private static final String SQL_FIND_TRACK_ID = """
            SELECT id
            FROM audio_tracks_storage.track
            WHERE title = ?
            """;

    private static final String SQL_FIND_ARTIST_ID = """
            SELECT id FROM audio_tracks_storage.artist WHERE name = ?
            """;

    private static final String SQL_FIND_ORDER_BY_ID = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE ord.id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_PRICE = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE ord.price = ?
            """;

    private static final String SQL_FIND_ORDER_BY_TRACK_ID = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE tr.id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_CARD_NUMBER = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE ord.card_number LIKE ?
            """;

    private static final String SQL_FIND_ORDER_BY_ACCOUNT_ID = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE ua.id = ?
            """;

    private static final String SQL_FIND_ORDER_BY_ACCOUNT_NAME = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id
            WHERE ua.login LIKE ?
            """;

    private static final String SQL_FIND_ALL_ORDERS = """
            SELECT ord.id              AS ord_id,
                   ord.price           AS price,
                   ord.card_number     AS card_number,
                   ord.user_account_id AS user_id,
                   ord.track_id        AS ord_track_id,
                   ua.id               AS ua_id,
                   ua.login            AS login,
                   ua.email            AS email,
                   ua.password         AS pass,
                   ua.role             AS role,
                   tr.id               AS tr_id,
                   tr.title            AS track_title,
                   tr.song_url         AS url,
                   tr.album_id         AS album_id,
                   tr.genre_id         As genre_id,
                   g.id                AS g_id,
                   g.name              AS genre_name,
                   art.id              AS art_id,
                   art.name            AS artist_name,
                   a.id                AS a_id,
                   a.title             AS a_title
            FROM user_storage.order ord
                     JOIN user_storage.user_account ua ON ua.id = ord.user_account_id
                     JOIN audio_tracks_storage.track tr ON tr.id = ord.track_id
                     JOIN audio_tracks_storage.album a ON a.id = tr.album_id
                     JOIN audio_tracks_storage.genre g ON g.id = a.genre_id
                     JOIN audio_tracks_storage.artist art ON art.id = a.artist_id          
            """;

    private static final String SQL_DELETE_ORDER_BY_ID = """
            DELETE
            FROM user_storage.order
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_ORDER = """
            UPDATE user_storage.order
            SET price           = ?,
                card_number     = ?,
                user_account_id = ?,
                track_id        = ?
            WHERE id = ?
            """;

    @Override
    public Order saveOrder(Order order) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findUserAccountIdStatement = connection.prepareStatement(SQL_FIND_USER_ACCOUNT_ID);
             PreparedStatement findTrackIdStatement = connection.prepareStatement(SQL_FIND_TRACK_ID);
             PreparedStatement findArtistStatement = connection.prepareStatement(SQL_FIND_ARTIST_ID);
             PreparedStatement saveOrderStatement = connection.prepareStatement(SQL_SAVE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            findUserAccountIdStatement.setString(1, order.getUserAccount().getLogin());
            ResultSet resultSet = findUserAccountIdStatement.executeQuery();
            if (resultSet.next()) {
                long userAccountId = resultSet.getLong("id");
                UserAccount userAccount = order.getUserAccount();
                userAccount.setId(userAccountId);
                order.setUserAccount(userAccount);
                Track orderedTrack = order.getTrack();
                findArtistStatement.setString(1, orderedTrack.getAlbum().getArtist().getName());
                ResultSet executeQuery = findArtistStatement.executeQuery();
                if (executeQuery.next()) {
                    long artistId = executeQuery.getLong("id");
                    Artist artist = orderedTrack.getAlbum().getArtist();
                    artist.setId(artistId);
                    Set<Artist> artists = new HashSet<>();
                    artists.add(artist);
                    orderedTrack.setArtists(artists);
                }
                findTrackIdStatement.setString(1, orderedTrack.getTitle());
                ResultSet query = findTrackIdStatement.executeQuery();
                if (query.next()) {
                    long trackId = query.getLong("id");
                    orderedTrack.setId(trackId);
                    order.setTrack(orderedTrack);
                    saveOrderStatement.setBigDecimal(1, order.getPrice());
                    saveOrderStatement.setString(2, order.getCardNumber());
                    saveOrderStatement.setString(3, order.getUserAccount().getLogin());
                    saveOrderStatement.setString(4, orderedTrack.getTitle());
                    saveOrderStatement.executeUpdate();
                    ResultSet generatedKeys = saveOrderStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        long orderId = generatedKeys.getLong("id");
                        order.setId(orderId);
                    }
                }
            }
            return order;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
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
    public Optional<List<Order>> findOrdersByPrice(BigDecimal price, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_PRICE + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByPriceStatement = connection.prepareStatement(sql)) {
            findByPriceStatement.setBigDecimal(1, price);
            findByPriceStatement.setObject(2, filter.limit());
            findByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
                orders.add(order);
            }
            return Optional.of(orders);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Order>> findOrdersByTrackId(Long trackId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_TRACK_ID + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByPriceStatement = connection.prepareStatement(sql)) {
            findByPriceStatement.setLong(1, trackId);
            findByPriceStatement.setObject(2, filter.limit());
            findByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
                orders.add(order);
            }
            return Optional.of(orders);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Order>> findOrdersByCardNumber(String cardNumber, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_CARD_NUMBER + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByPriceStatement = connection.prepareStatement(sql)) {
            findByPriceStatement.setString(1, cardNumber);
            findByPriceStatement.setObject(2, filter.limit());
            findByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
                orders.add(order);
            }
            return Optional.of(orders);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Order>> findOrdersByUserAccountId(Long userId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_ACCOUNT_ID + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByPriceStatement = connection.prepareStatement(sql)) {
            findByPriceStatement.setLong(1, userId);
            findByPriceStatement.setObject(2, filter.limit());
            findByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
                orders.add(order);
            }
            return Optional.of(orders);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Order>> findOrdersByUserAccountName(String userName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_ORDER_BY_ACCOUNT_NAME + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByPriceStatement = connection.prepareStatement(sql)) {
            findByPriceStatement.setString(1, userName);
            findByPriceStatement.setObject(2, filter.limit());
            findByPriceStatement.setObject(3, filter.offset());
            ResultSet resultSet = findByPriceStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
                orders.add(order);
            }
            return Optional.of(orders);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Order> findAll(OrderFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.price() != null) {
            whereSql.add("ord.price = ?");
            parameters.add(filter.price());
        }
        if (filter.cardNumber() != null) {
            whereSql.add("ord.card_number LIKE ?");
            parameters.add("%" + filter.cardNumber() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ?  OFFSET ? "));
        String sql = SQL_FIND_ALL_ORDERS + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Order> all = new ArrayList<>();
            while (resultSet.next()) {
                Order order = buildOrder(resultSet);
                all.add(order);
            }
            return all;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteByIdStatement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Order order) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            updateStatement.setBigDecimal(1, order.getPrice());
            updateStatement.setString(2, order.getCardNumber());
            updateStatement.setLong(3, order.getUserAccount().getId());
            updateStatement.setLong(4, order.getTrack().getId());
            updateStatement.setLong(5, order.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        UserAccount userAccount = buildAccount(resultSet);
        Track track = buildTrack(resultSet);
        Set<Artist> artists = new HashSet<>();
        artists.add(buildArtist(resultSet));
        track.setArtists(artists);
        return new Order(
                resultSet.getLong("ord_id"),
                resultSet.getBigDecimal("price"),
                resultSet.getString("card_number"),
                userAccount,
                track
        );
    }

    private Track buildTrack(ResultSet resultSet) throws SQLException {
        Album album = buildAlbum(resultSet);
        return new Track(
                resultSet.getLong("tr_id"),
                resultSet.getString("url"),
                resultSet.getString("track_title"),
                album.getGenre(),
                album
        );
    }

    private Album buildAlbum(ResultSet resultSet) throws SQLException {
        Artist artist = buildArtist(resultSet);
        Genre genre = buildGenre(resultSet);
        return new Album(
                resultSet.getLong("a_id"),
                resultSet.getString("a_title"),
                genre,
                artist
        );
    }

    private Genre buildGenre(ResultSet resultSet) throws SQLException {
        return new Genre(resultSet.getLong("g_id"),
                GenreType.valueOf(resultSet.getString("genre_name")));
    }

    private Artist buildArtist(ResultSet resultSet) throws SQLException {
        return new Artist(
                resultSet.getLong("art_id"),
                resultSet.getString("artist_name")
        );
    }

    private UserAccount buildAccount(ResultSet resultSet) throws SQLException {
        return new UserAccount(
                resultSet.getLong("ua_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("pass"),
                UserRole.valueOf(resultSet.getString("role"))
        );
    }
}
