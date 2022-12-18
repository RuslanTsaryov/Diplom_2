import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderOfClient extends Client {
    private final String INGREDIENTS = "/api/ingredients";
    private final String ORDERS = "/api/orders";

    @Step("Получить список всех ингредиентов")
    public Response getIngredients(){
        return given()
                .spec(getSpec())
                .when()
                .get(INGREDIENTS);
    }

    @Step("Получить список заказов авторизованного пользователя")
    public Response getOrdersOfAuthUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .when()
                .get(ORDERS);
    }

    @Step("Получить список заказов неавторизованного пользователя")
    public Response getOrdersOfNoAuthUser() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS);
    }

    @Step("Создать заказ авторизованным пользователем")
    public Response createOrderByAuthUser(Order ingredients, String accessToken){
        return given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(ORDERS);
    }

    @Step("Создать заказ неавторизованным пользователем")
    public Response createOrderByNoAuthUser(Order ingredients){
        return given()
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(ORDERS);
    }
}