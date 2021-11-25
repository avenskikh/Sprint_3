package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check orders list")
    public void checkOrderList() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        CourierClient courierClient = new CourierClient();
        courierClient.create(courierLogin, courierPassword, courierFirstName);
        int courierId = courierClient.login(courierLogin, courierPassword);
        OrderClient orderClient = new OrderClient();
        int track = orderClient.createOrder("BlACK");
        orderClient.acceptOrder(track, courierId);
        Response response = given()
                .queryParam("courierId", courierId)
                .when()
                .get("api/v1/orders");
        response.then()
                .log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
        courierClient.delete(courierId);
    }
}