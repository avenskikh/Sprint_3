package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NegativeLoginCourierTest {
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Login courier without login and check error")
    public void loginCourierWithoutLoginCheck() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> loginPass = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        Response response = courierClient.loginCourier("", loginPass.get(1));
        assertEquals("StatusCode is incorrect", 400, response.statusCode());
        assertEquals("Error message is incorrect", "Недостаточно данных для входа", response.path("message"));
    }

    @Test
    @DisplayName("Login courier without password and check error")
    public void loginCourierWithoutPasswordCheck() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> loginPass = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        Response response = courierClient.loginCourier( loginPass.get(0), "");
        assertEquals("StatusCode is incorrect", 400, response.statusCode());
        assertEquals("Error message is incorrect", "Недостаточно данных для входа", response.path("message"));
    }

    @Test
    @DisplayName("Login courier with incorrect login and password and check error")
    public void loginCourierWithIncorrectLogPassCheck() {
        Response response = courierClient.loginCourier("test", "test");
        assertEquals("StatusCode is incorrect", 404, response.statusCode());
        assertEquals("Error message is incorrect", "Учетная запись не найдена", response.path("message"));
    }
}