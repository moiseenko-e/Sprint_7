package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

// "https://qa-scooter.praktikum-services.ru"
// Content-type application/json

public class CreateCourierTests extends BaseMethods {
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @DisplayName("Создание курьера с уникальными данными. Запрос возвращает код 201 и сообщение ok: true")
    @Test
    public void testBodyInRandomAccountCreation(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse duplicateLogin  = courierClient.getCourierResponse(
                Courier.getRandomCourier());
        duplicateLogin
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    //
    @Test
    @DisplayName("Создание уже существующего курьера. Запрос возвращает код 409 и сообщение 'Этот логин уже используется'")
    public void testErrorMessageForRequestWithDuplicateLogin(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse duplicateLogin  = courierClient.getCourierResponse(
                new Courier(EXISTING_LOGIN, EXISTING_PASSWORD, EXISTING_FIRSTNAME));
        duplicateLogin
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без ввода логина. Запрос возвращает код 400 и сообщение 'Недостаточно данных для создания учетной записи'")
    public void testErrorMessageForRequestWithoutLogin(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse emptyLoginField  = courierClient.getCourierResponse(
                new Courier(null, EXISTING_PASSWORD, EXISTING_FIRSTNAME));
        emptyLoginField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без ввода пароля. Запрос возвращает код 400 и сообщение 'Недостаточно данных для создания учетной записи'")
    public void testErrorMessageForRequestWithoutPassword(){
        CourierClient courierClient = new CourierClient();
        ValidatableResponse emptyPasswordField  = courierClient.getCourierResponse(
                new Courier(EXISTING_LOGIN, null, EXISTING_FIRSTNAME));
        emptyPasswordField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown(){
        deleteAccount();
    }
}
