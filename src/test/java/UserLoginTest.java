import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserLoginTest {
    private User user;
    private UserClient userClient;
    @Before
    public void start(){
        user= UserGenerator.getDefault();
        userClient = new UserClient();
        userClient.createUser(user);
    }

    @Test
    @DisplayName("Авторизация пользователя с валидными данными")
    public void checkLoginUserWithValidData(){
        Response response = userClient.loginUser(UserCredits.from(user));
        response.then().assertThat().statusCode(200).body("success",equalTo(true)).and()
                .and().body("accessToken",notNullValue())
                .and().body("refreshToken",notNullValue())
                .and().body("user.email",equalTo(user.getEmail()))
                .and().body("user.name",equalTo(user.getName()));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидной почтой")
    public void checkLoginUserWithInvalidEmail(){
        user.setEmail(user.getEmail() + "rts");
        Response response = userClient.loginUser(UserCredits.from(user));
        response.then().assertThat().statusCode(401).and().body("success",equalTo(false))
                .and().body("message",equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидным паролем")
    public void checkLoginUserWithInvalidPassword(){
        user.setPassword(user.getPassword() + "rts");
        Response response = userClient.loginUser(UserCredits.from(user));
        response.then().assertThat().statusCode(401).and().body("success",equalTo(false))
                .and().body("message",equalTo("email or password are incorrect"));
    }

    @After
    public void end(){
        Response response = userClient.loginUser(UserCredits.from(user));
        String accessToken = response.then().extract().path("accessToken");
        userClient.deleteUser(accessToken);
    }
}