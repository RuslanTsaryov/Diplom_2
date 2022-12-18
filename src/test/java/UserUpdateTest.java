import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserUpdateTest {
    private User user;
    private UserClient userClient;
    private Response response;

    @Before
    public void start() {
        user = UserGenerator.getDefault();
        userClient = new UserClient();
        response = userClient.createUser(user);
    }

    @Test
    @DisplayName("Обновление данных пользователя после авторизации")
    public void updateUserDataWithAuthorization(){
        response = userClient.loginUser(user);
        String accessToken = response.then().extract().path("accessToken");
        userClient.getInfo(accessToken);
        user = UserGenerator.getDefault();
        response = userClient.updateInfo(user, accessToken);
        response.then().assertThat().statusCode(200).body("success",equalTo(true))
                .and().body("user.email",equalTo(user.getEmail()))
                .and().body("user.name",equalTo(user.getName()));
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    public void updateUserDataWithoutAuthorization() {
        String accessToken = response.then().extract().path("accessToken");
        userClient.getInfo(accessToken);
        user = UserGenerator.getDefault();
        response = userClient.updateInfoWithoutAuthorization(user);
        response.then().assertThat().statusCode(401).body("success", equalTo(false))
                .and().body( "message", equalTo("You should be authorised"));
    }

    @After
    public void end() {
        Response response = userClient.loginUser(UserCredits.from(user));
        String accessToken = response.then().extract().path("accessToken");
        userClient.deleteUser(accessToken);
    }
}