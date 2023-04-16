package ru.praktikum;
import org.apache.commons.lang.RandomStringUtils;

public class Login {
    private String login;
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login(Courier randomCourier) {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Login getRandomLogin(){
        return new Login(
                RandomStringUtils.randomAlphabetic(10) + "",
                RandomStringUtils.randomAlphanumeric(10) + ""
        );
    }
}
