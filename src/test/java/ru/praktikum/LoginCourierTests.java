package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

// "https://qa-scooter.praktikum-services.ru"
// Content-type application/json

public class LoginCourierTests extends BaseMethods {
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @DisplayName("Авторизация курьера. Запрос возвращает код 200 и id курьера")
    @Test
    public void SuccessfulLoginTest(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourier = courierClient.getLoginResponse(
                new Login(EXISTING_LOGIN, EXISTING_PASSWORD));
        dataCourier.statusCode(200);
        MatcherAssert.assertThat("id", notNullValue());
    }

    @DisplayName("Попытка авторизации без логина. Запрос возвращает код 400 и сообщение 'Недостаточно данных для входа'")
    @Test
    public void loginWithoutLoginTest(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourierWithoutLogin = courierClient.getLoginResponse(
                new Login(null, EXISTING_PASSWORD));
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Попытка авторизации без пароля. Запрос возвращает код 400 и сообщение 'Недостаточно данных для входа'")
    @Test
    public void loginWithoutPasswordTest(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourierWithoutLogin = courierClient.getLoginResponse(
                new Login(EXISTING_LOGIN, null));
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Попытка авторизации с несуществующей парой логин-пароль. Запрос возвращает код 404 и сообщение 'Учетная запись не найдена'")
    @Test
    public void testRequestNonExistentData(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse nonExistentData  = courierClient.getLoginResponse(
                Login.getRandomLogin());
                 nonExistentData
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown(){
        deleteAccount();
    }
}
