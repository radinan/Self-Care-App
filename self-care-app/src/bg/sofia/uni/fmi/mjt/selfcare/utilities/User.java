package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import java.util.List;

public class User {
    private String username;
    private boolean isLogged;
    private List<Journal> journals;

    public User() {
        isLogged = false;
    }

    public User(String username, List<Journal> journals) {
        this.username = username;
        this.journals = journals;
    }

    public void login() {
        isLogged = true;
    }

    public void logout() {
        isLogged = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return  username;
    }

    public List<Journal> getJournals() {
        return journals;
    }

}
