package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LoginCourierTest {
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
    @DisplayName("Login courier and check answer")
    public void loginCourierCheck() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> loginPass = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        Response response = courierClient.loginCourier(loginPass.get(0), loginPass.get(1));
        assertEquals("StatusCode is incorrect", response.statusCode(), 200);
        assertThat("ID is incorrect", response.path("id"), is(CoreMatchers.not(0)));
        courierId = response.path("id");
    }
}