package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {
    private OrderClient orderClient;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check orders list")
    public void checkOrderList() {
        Response response = orderClient.getOrders();
        response.then()
                .log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}