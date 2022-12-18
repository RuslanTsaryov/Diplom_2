import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class UserCreationWithoutRequiredParameters {
    private User user;
    private UserClient userClient;
    private int expectedStatusCode;
    private String expectedMessage;
    private boolean expectedSuccess;

    public UserCreationWithoutRequiredParameters(User user, int expectedStatusCode, String expectedMessage,boolean expectedSuccess) {
        this.user = user;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
        this.expectedSuccess = expectedSuccess;
    }
    @Parameterized.Parameters
    public static Object[][] getData(){
        return new Object[][]{
                {UserGenerator.getWithoutEmail(),403, "Email, password and name are required fields",false},
                {UserGenerator.getWithoutPassword(),403,"Email, password and name are required fields",false},
                {UserGenerator.getWithoutName(),403,"Email, password and name are required fields",false},
                {UserGenerator.getEmptyEmail(),403,"Email, password and name are required fields",false},
                {UserGenerator.getEmptyPassword(),403,"Email, password and name are required fields",false},
                {UserGenerator.getEmptyName(),403,"Email, password and name are required fields",false}
        };
    }

    @Before
    public void start (){
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя с невалидными данными")
    public void createUserInvalidData(){
        Response response = userClient.createUser(user);
        response.then().assertThat().statusCode(expectedStatusCode).and().body("success", equalTo(expectedSuccess)).and().body("message",equalTo(expectedMessage));
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {}
    }

    @After
    public void end(){
        Response response = userClient.loginUser(UserCredits.from(user));
        String accessToken = response.then().extract().path("accessToken");
        userClient.deleteUser(accessToken);
    }
}