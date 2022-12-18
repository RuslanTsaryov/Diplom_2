import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private final String REGISTER = "/api/auth/register";
    private final String LOGIN = "/api/auth/login";
    private final String USER = "/api/auth/user";

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Удаление пользователя после авторизации")
    public void deleteUser(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .when()
                .delete(USER)
                .then()
                .statusCode(202);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(Object object) {
        return given()
                .spec(getSpec())
                .body(object)
                .when()
                .post(LOGIN);
    }

    @Step("Получение информации о пользователе")
    public void getInfo(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .when()
                .get(USER);
    }

    @Step("Обновление информации о пользователе после авторизации")
    public Response updateInfo(User user, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .body(user)
                .when()
                .patch(USER);
    }

    @Step("Обновление информации о пользователе без авторизации")
    public Response updateInfoWithoutAuthorization(Object object) {
        return given()
                .spec(getSpec())
                .body(object)
                .when()
                .patch(USER);
    }
}