package ru.yandex.praktikum;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step
    public int createOrder(String color) {
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
        return given()
                .spec(getBaseSpec())
                .and()
                .body(requestBody)
                .when()
                .post(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Step
    public boolean acceptOrder(int id, int courierId){
        return given()
                .spec(getBaseSpec())
                .and()
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "accept/" + id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
