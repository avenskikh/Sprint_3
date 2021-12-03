package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step
    public Response createOrder(String color) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String comment = RandomStringUtils.randomAlphabetic(10);
        JSONObject requestBodyJson = new JSONObject();
        List<String> colorList = new ArrayList<String>();
        colorList.add(color);
        String requestBody = requestBodyJson
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("address", address)
                .put("metroStation", 3)
                .put("phone", "+7 800 355 35 35")
                .put("rentTime", 3)
                .put("deliveryDate", "2020-06-06")
                .put("comment", comment)
                .put("color", colorList)
                .toString();
        return given()
                .spec(getBaseSpec())
                .and()
                .body(requestBody)
                .when()
                .post(ORDER_PATH);
    }

    @Step
    public Response getOrders() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH);
    }
}