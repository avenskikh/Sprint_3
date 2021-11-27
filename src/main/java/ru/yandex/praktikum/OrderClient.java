package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step
    public Response createOrder(String color) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String comment = RandomStringUtils.randomAlphabetic(10);
        String requestBody = "{\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"address\":\"" + address + "\","
                + "\"metroStation\":\"" + "3" + "\","
                + "\"phone\":\"" + "+7 800 355 35 35" + "\","
                + "\"rentTime\":\"" + "3" + "\","
                + "\"deliveryDate\":\"" + "2020-06-06" + "\","
                + "\"comment\":\"" + comment + "\","
                + "\"color\": [\"" + color + "\"] }";
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(requestBody)
                .when()
                .post(ORDER_PATH);
        return response;
    }


    @Step
    public Response getOrders() {
        Response response = given()
                .spec(getBaseSpec())
                .get(ORDER_PATH);
        return response;
    }
}