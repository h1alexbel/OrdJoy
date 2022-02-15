package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.*;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @DisplayName("save order test")
    void makeOrder() {
        OrderService orderService = OrderService.getInstance();
        Order order = Order.builder()
                .price(new BigDecimal(35))
                .orderStatus(OrderStatus.ACCEPTED)
                .track(Track.builder()
                        .title("Test")
                        .songUrl("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                        .album(Album.builder()
                                .title("Another")
                                .build())
                        .build())
                .userAccount(UserAccount.builder()
                        .email("test@gmail.com")
                        .login("test")
                        .userData(UserData.builder()
                                .userRole(UserRole.CLIENT_ROLE)
                                .build())
                        .build())
                .build();
        try {
            OrderDto saved = orderService.makeOrder(order);
            OrderDto expected = OrderDto.builder()
                    .price(new BigDecimal(35))
                    .status(OrderStatus.ACCEPTED)
                    .track(TrackDto.builder()
                            .title("Test")
                            .url("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                            .build())
                    .userAccount(UserAccountDto.builder()
                            .email("test@gmail.com")
                            .login("test")
                            .userRole(UserRole.CLIENT_ROLE)
                            .build())
                    .build();
            assertEquals(expected.getPrice(), saved.getPrice(), "Orders price must be equal");
            assertEquals(expected.getStatus(), saved.getStatus(), "Orders status must be equal");
            assertEquals(expected.getTrack().getTitle(), saved.getTrack().getTitle(),
                    "Orders track's title must be equal");
            assertEquals(expected.getTrack().getUrl(), saved.getTrack().getUrl(),
                    "Orders track's url must be equal");
            assertEquals(expected.getUserAccount().getEmail(), saved.getUserAccount().getEmail(),
                    "Orders user's email must be equal");
            assertEquals(expected.getUserAccount().getLogin(), saved.getUserAccount().getLogin(),
                    "Orders user's login must be equal");
            assertEquals(expected.getUserAccount().getRole(), saved.getUserAccount().getRole(),
                    "Orders user's role must be equal");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateOrderPriceNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.updateOrderPrice(new BigDecimal(35), null)),
                () -> assertDoesNotThrow(() -> orderService.updateOrderPrice(null, null)),
                () -> assertDoesNotThrow(() -> orderService.updateOrderPrice(null, 1L))
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("calculate order price by orderId null case test")
    void calculateOrderPriceNullCase(Long orderId) {
        OrderService orderService = OrderService.getInstance();
        assertDoesNotThrow(() -> orderService.calculateOrderPrice(orderId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find order by id null case test")
    void findOrderByIdNullCase(Long orderId) {
        OrderService orderService = OrderService.getInstance();
        assertDoesNotThrow(() -> orderService.findOrderById(orderId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update order null case test")
    void updateOrderNullCase(Order order) {
        OrderService orderService = OrderService.getInstance();
        assertDoesNotThrow(() -> orderService.updateOrder(order));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete order null case test")
    void deleteOrderByIdNullCase(Long id) {
        OrderService orderService = OrderService.getInstance();
        assertDoesNotThrow(() -> orderService.deleteOrderById(id));
    }

    @Test
    void updateOrderStatusNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.updateOrderStatus(OrderStatus.IN_PROGRESS, null)),
                () -> assertDoesNotThrow(() -> orderService.updateOrderStatus(null, null)),
                () -> assertDoesNotThrow(() -> orderService.updateOrderStatus(null, 1L))
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find orders by price null case")
    void findOrdersByPriceNullCase(BigDecimal price) {
        OrderService orderService = OrderService.getInstance();
        assertDoesNotThrow(() -> orderService.findOrdersByPrice(price));
    }

    @Test
    void findOrdersByUserIdNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserId(1L, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserId(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    void findOrdersByUserEmailNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserEmail("test@gmail.com", null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserEmail(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserEmail(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    void findOrdersByUserLoginNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserLogin("test", null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserLogin(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByUserLogin(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    void findOrdersByTrackIdNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackId(1L, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackId(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    void findOrdersByTrackNameNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackName("Test", null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackName(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByTrackName(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    void findOrdersByOrderStatusNullCase() {
        OrderService orderService = OrderService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> orderService.findOrdersByOrderStatus(OrderStatus.ACCEPTED, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByOrderStatus(null, null)),
                () -> assertDoesNotThrow(() -> orderService.findOrdersByOrderStatus(null,
                        new DefaultFilter(20, 0)))
        );
    }
}