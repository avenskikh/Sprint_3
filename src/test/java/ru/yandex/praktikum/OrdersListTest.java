package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

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
        assertEquals("StatusCode is incorrect", 200, response.statusCode());
        assertThat("ID is incorrect", response.path("orders"), notNullValue());
    }
}