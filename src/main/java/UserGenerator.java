import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class UserGenerator {
    private static  Faker faker = new Faker();
    public static String setEmailUserGenerator(){
        return faker.internet().emailAddress();
    }

    public static String setPasswordUserGenerator(){
        return faker.internet().password(6, 12);
    }

    public static String setNameUserGenerator(){
        return faker.name().firstName();
    }

    @Step("Пользователь с валидными данными")
    public static User getDefault (){
        return new User(setEmailUserGenerator(),setPasswordUserGenerator(),setNameUserGenerator());
    }

    @Step("Пользователь без email")
    public static User getWithoutEmail(){
        return new User(null,setEmailUserGenerator(),setNameUserGenerator());
    }

    @Step ("Пользователь без пароля")
    public static User getWithoutPassword(){
        return  new User(setEmailUserGenerator(),null,setNameUserGenerator());
    }

    @Step("Пользователь без имени")
    public static User getWithoutName(){
        return  new User(setEmailUserGenerator(),setPasswordUserGenerator(),null);
    }

    @Step("Пользователь с пустым паролем")
    public static User getEmptyPassword(){
        return new User(setEmailUserGenerator(),"",setNameUserGenerator());
    }

    @Step("Пользователь с пустым email")
    public static User getEmptyEmail(){
        return new User("",setPasswordUserGenerator(),setNameUserGenerator());
    }

    @Step("Пользователь с пустым именем")
    public static User getEmptyName(){
        return new User(setEmailUserGenerator(),setPasswordUserGenerator(),"");
    }
}