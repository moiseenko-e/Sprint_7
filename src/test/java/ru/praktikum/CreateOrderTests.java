package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

// "https://qa-scooter.praktikum-services.ru"
// Content-type application/json

@RunWith(Parameterized.class)
public class CreateOrderTests extends DataForTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "firstName = {0}, lastName = {1}, address = {2}, metroStation = {3}, phone = {4}, rentTime = {5}, " +
            "deliveryDate = {6}, comment = {7}, color = {8}")
    public static Object[][] getData() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, Бродников, 4", "Полянка", "+79050464500", 3, "10.04.2023", "Тестовый заказ", List.of("BLACK", "GREY")},
                {"Ivan", "Ivanov", "Moscow, Lenina, 6", "Zil", "+79992289999", 10, "31.12.2022", "Test Test", List.of("GREY")},
                {"Елена", "Ленина", "Санкт-Петербург, Восстания, 1", "Беговая", "+79033333333", 6, "01.01.2024", "Тестовый заказ", null},
        };
    }

    @Test
    @DisplayName("Создание заказа. Запрос возвращает код 201 и трек-номер заказа")
    public void checkOrderCreation() {
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse emptyPasswordField  = ordersClient.getResponseFromOrder(
                new Order(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate, comment, color));
        emptyPasswordField
                .statusCode(201);
        MatcherAssert.assertThat("track", notNullValue());
    }
}
