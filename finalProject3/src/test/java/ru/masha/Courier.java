package ru.masha;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom(){
        String login = RandomStringUtils.random(10, true, false);
        String password = RandomStringUtils.random(8, true, true);
        String firstName = RandomStringUtils.random(7, true, false);
        return new Courier(login, password, firstName);
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
