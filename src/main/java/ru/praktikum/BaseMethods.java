package ru.praktikum;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

// создание и удаление аккаунта курьера
public class BaseMethods extends DataForTests {
    public void createAccount(){
        Courier successfulCourier = new Courier(EXISTING_LOGIN, EXISTING_PASSWORD, EXISTING_FIRSTNAME);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(successfulCourier)
                .when()
                .post(API_COURIER);
    }

    public void deleteAccount(){
        Login login = new Login(EXISTING_LOGIN, EXISTING_PASSWORD);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(API_LOGIN);
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete(API_LOGIN + id);
    }
}
