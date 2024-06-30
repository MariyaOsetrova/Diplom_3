package ru.osetrova.Steps;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.osetrova.Model.User;

import static io.restassured.RestAssured.given;
import static ru.osetrova.utils.Config.URL_HOME;
import static ru.osetrova.utils.EndPoints.*;

public class UserSteps {
    protected RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(URL_HOME)
            .build();

    @Step("Создание уникального пользователя")
    public Response createUser(User user) {
        return given()
                .spec(spec)
                .body(user)
                .post(USE_REG);

    }

    @Step("Логин пользователя")
    public Response userLogin(User user) {
        return given()
                .spec(spec)
                .body(user)
                .post(LOGIN);

    }

    @Step("Получение токена")
    public String getToken(User user) {
        return userLogin(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(spec)
                .when()
                .delete(USER);

    }
}
