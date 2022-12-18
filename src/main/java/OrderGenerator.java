import io.qameta.allure.Step;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class OrderGenerator {
    public static List<String> ingredients= new ArrayList<>();
    private static Faker faker = new Faker();

    public static List<String> getIngredients(List<String> jsonResponse) {
        ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
        ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
        ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
        ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
        return ingredients;
    }
    public static List<String> getInvalidIngredients() {
        ingredients.add(faker.bothify("beef##??#?#"));
        ingredients.add(faker.bothify("turkey##??#?#"));
        ingredients.add(faker.bothify("cucumber##??#?#"));
        ingredients.add(faker.bothify("tomato##??#?#"));
        return ingredients;
    }
    @Step("Геттер для заказа с валидными ингредиентами")
    public static  Order getDefault(List<String> jsonResponse){
        return new Order(getIngredients(jsonResponse));
    }
    @Step("Геттер для заказа с невалидными ингредиентами")
    public static Order getInvalidHashIngredients(){
        return new Order(getInvalidIngredients());
    }
}