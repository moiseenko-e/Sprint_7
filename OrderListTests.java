package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTests extends DataForTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Получение списка заказов курьера. В тело ответа возвращается список заказов")
    @Test
    public void checkOrdersFieldInListOfOrdersTest(){
        Response response = given()
                .header("Content-type", "application/json")
                .get(API_ORDER);
        response.then().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
