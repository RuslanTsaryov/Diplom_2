import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;

public class Client {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType("application/json")
                .setBaseUri(BASE_URL)
                .build();
    }
}