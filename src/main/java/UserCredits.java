public class UserCredits {

    private String email;
    private String password;
    private String name;

    public UserCredits() {
    }

    public UserCredits(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCredits from (User user){
        return new UserCredits(user.getEmail(),user.getPassword(), user.getName());
    }

    public String getEmail () {
        return email;
    }

    public void setEmail(String login) {
        this.email = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}