package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCourierTest {
    private int courierId;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        new CourierClient().delete(courierId);
    }

    @Test
    @DisplayName("Create courier and check answer")
    public void createCourierCheck() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        Response response = courierClient.create(courierLogin, courierPassword, courierFirstName);
        courierId = courierClient.login(courierLogin, courierPassword);
        assertEquals("StatusCode is incorrect", 201, response.statusCode());
        assertEquals("Courier isn't created", true, response.path("ok"));
    }

    @Test
    @DisplayName("Create courier with equal login check error")
    public void createCourierWithEqualLoginCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        courierClient.create(courierLogin, courierPassword, courierFirstName);
        courierId = courierClient.login(courierLogin, courierPassword);
        String courierPassword2 = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName2 = RandomStringUtils.randomAlphabetic(10);
        Response response = courierClient.create(courierLogin, courierPassword2, courierFirstName2);
        assertEquals("StatusCode is incorrect", 409, response.statusCode());
        assertEquals("Error message is incorrect", "Этот логин уже используется. Попробуйте другой.", response.path("message"));
    }

    @Test
    @DisplayName("Create equal couriers check error")
    public void createEqualCourierCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        courierClient.create(courierLogin, courierPassword, courierFirstName);
        courierId = courierClient.login(courierLogin, courierPassword);
        Response response = courierClient.create(courierLogin, courierPassword, courierFirstName);
        assertEquals("StatusCode is incorrect", 409, response.statusCode());
        assertEquals("Error message is incorrect", "Этот логин уже используется. Попробуйте другой.", response.path("message"));
    }
}