package ru.masha;

public class AuthCourier {
    private String login;
    private String password;

    public AuthCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
