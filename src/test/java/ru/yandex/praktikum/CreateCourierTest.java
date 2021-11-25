package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Create courier and check answer")
    public void createCourierCheck() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        CourierClient courierClient = new CourierClient();
        boolean isCourierCreated = courierClient.create(courierLogin, courierPassword, courierFirstName);
        int courierId = courierClient.login(courierLogin, courierPassword);
        assertTrue("Courier isn't created", isCourierCreated);
        assertThat("Courier ID is incorrect", courierId, is(CoreMatchers.not(0)));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create courier without login check error")
    public void createCourierWithoutLoginCheckError() {
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        String requestBody = "{\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("api/v1/courier");
        response.then().
                assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Create courier without password check error")
    public void createCourierWithoutPasswordCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        String requestBody = "{\"password\":\"" + courierLogin + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Create courier with equal login check error")
    public void createCourierWithEqualLoginCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        CourierClient courierClient = new CourierClient();
        courierClient.create(courierLogin, courierPassword, courierFirstName);
        int courierId = courierClient.login(courierLogin, courierPassword);
        String courierPassword2 = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName2 = RandomStringUtils.randomAlphabetic(10);
        String requestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword2 + "\","
                + "\"firstName\":\"" + courierFirstName2 + "\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("api/v1/courier");
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create equal couriers check error")
    public void createEqualCourierCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        CourierClient courierClient = new CourierClient();
        courierClient.create(courierLogin, courierPassword, courierFirstName);
        int courierId = courierClient.login(courierLogin, courierPassword);
        String requestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        Response secondResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("api/v1/courier");
        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        courierClient.delete(courierId);
    }
}