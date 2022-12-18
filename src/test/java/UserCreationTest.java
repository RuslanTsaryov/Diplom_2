import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserCreationTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void start(){
        user = UserGenerator.getDefault();
        userClient = new UserClient();
    }

    @Test
    public void checkUserIsCreated(){
        Response response = userClient.createUser(user);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true))
                .and().body("accessToken",notNullValue())
                .and().body("refreshToken",notNullValue());
    }

    @Test
    public void checkUserAlreadyExists(){
        userClient.createUser(user);
        Response response = userClient.createUser(user);
        response.then().statusCode(403).and().assertThat().body("success", equalTo(false))
                .and().body("message",equalTo("User already exists"));
    }

    @After
    public void deleteUser(){
        Response response = userClient.loginUser(UserCredits.from(user));
        accessToken = response.then().extract().path("accessToken");
        userClient.deleteUser(accessToken);
    }
}