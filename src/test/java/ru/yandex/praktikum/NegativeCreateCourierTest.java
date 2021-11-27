package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NegativeCreateCourierTest {
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Create courier without login check error")
    public void createCourierWithoutLoginCheckError() {
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        Response response = courierClient.create("", courierPassword, courierFirstName);
        assertEquals("StatusCode is incorrect", response.statusCode(), 400);
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", response.path("message"));
    }

    @Test
    @DisplayName("Create courier without password check error")
    public void createCourierWithoutPasswordCheckError() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        Response response = courierClient.create(courierLogin, "", courierFirstName);
        assertEquals("StatusCode is incorrect", response.statusCode(), 400);
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", response.path("message"));
    }
}
