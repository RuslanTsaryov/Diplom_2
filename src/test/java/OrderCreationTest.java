import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderCreationTest {

    private User user ;
    private UserClient userClient;
    private Order order;
    private OrderOfClient orderClient;
    private String accessToken;

    @Before
    public void start(){
        user= UserGenerator.getDefault();
        userClient = new UserClient();
        accessToken = userClient.createUser(user).then().extract().path("accessToken");
        orderClient = new OrderOfClient();
        Response response = orderClient.getIngredients();
        List<String>jsonResponse =  response.then().extract().body().jsonPath().getList("data._id");
        order = OrderGenerator.getDefault(jsonResponse);
    }

    @Test
    @DisplayName("Создание заказа после авторизации")
    public void createOrderWithAuthorization(){
        Response responseCreateOrder = orderClient.createOrderByAuthUser(order,accessToken);
        responseCreateOrder.then().assertThat().statusCode(200).and().body("success", equalTo(true)).
                and().body("name",notNullValue()).
                and().body("order.number",notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorization(){
        Response responseCreateOrder = orderClient.createOrderByNoAuthUser(order);
        responseCreateOrder.then().assertThat().statusCode(401).and().body("success", equalTo(false)).
                and().body("message",equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Создать заказ без ингредиентов")
    public void createOrderWithoutIngredients(){
        order.getIngredients().clear();
        Response responseCreateOrder = orderClient.createOrderByAuthUser(order,accessToken);
        responseCreateOrder.then().assertThat().statusCode(400).and().body("success", equalTo(false)).
                and().body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создать заказ с невалидными ингредиентами")
    public void createOrderWithInvalidIngredients() {
        order = OrderGenerator.getInvalidHashIngredients();
        Response responseCreateOrder = orderClient.createOrderByAuthUser(order, accessToken);
        responseCreateOrder.then().assertThat().statusCode(500);
    }

    @After
    public void end(){
        userClient.deleteUser(accessToken);
    }
}