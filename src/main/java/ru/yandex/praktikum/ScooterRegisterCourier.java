package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ScooterRegisterCourier {


    @Step
    public ArrayList<String> registerNewCourierAndReturnLoginPassword(){

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        ArrayList<String> loginPass = new ArrayList<>();
        JSONObject requestBodyJson = new JSONObject();
        String registerRequestBody = requestBodyJson
                .put("login", courierLogin)
                .put("password", courierPassword)
                .put("firstName", courierFirstName)
                .toString();
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

        if (response.statusCode() == 201) {
            loginPass.add(courierLogin);
            loginPass.add(courierPassword);
        }

        return loginPass;
    }
} 