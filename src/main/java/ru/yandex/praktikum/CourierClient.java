package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";


    @Step
    public Response create(String login, String password, String firstName) {
        JSONObject requestBodyJson = new JSONObject();
        String requestBody = requestBodyJson
                .put("login", login)
                .put("password", password)
                .put("firstName", firstName)
                .toString();
        Response response = given()
                .spec(getBaseSpec())
                .body(requestBody)
                .when()
                .post(COURIER_PATH);
        return response;
    }

    @Step
    public int login(String login, String password) {
        JSONObject requestBodyJson = new JSONObject();
        String requestBody = requestBodyJson
                .put("login", login)
                .put("password", password)
                .toString();
        return given()
                .spec(getBaseSpec())
                .body(requestBody)
                .when()
                .post(COURIER_PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step
    public void delete(int courierId) {
        given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Step
    public Response loginCourier (String login, String password) {
        JSONObject requestBodyJson = new JSONObject();
        String requestBody = requestBodyJson
                .put("login", login)
                .put("password", password)
                .toString();
        Response response = given()
                .spec(getBaseSpec())
                .body(requestBody)
                .when()
                .post(COURIER_PATH + "login/");
        return response;
    }
}