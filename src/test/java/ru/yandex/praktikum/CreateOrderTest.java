package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru/";
    }

    private final String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {""},
                {"BLACK\", \"GREY"}
        };
    }

    @Test
    @DisplayName("Create order and check answer")
    public void createOrderCheck() {
        OrderClient orderClient = new OrderClient();
        int track = orderClient.createOrder(color);
        assertThat("Order ID is incorrect", track, is(CoreMatchers.not(0)));
    }
}