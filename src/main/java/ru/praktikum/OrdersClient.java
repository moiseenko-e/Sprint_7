package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrdersClient extends DataForTests {
    @Step("Get response for Orders")
    public ValidatableResponse getResponseFromOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(API_ORDER)
                .then();
    }
}
