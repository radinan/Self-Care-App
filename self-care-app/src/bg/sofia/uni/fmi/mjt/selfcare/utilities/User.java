package bg.sofia.uni.fmi.mjt.selfcare.utilities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private boolean isLogged;
    private List<Journal> journals;

    public User() {
        username = null;
        isLogged = false;
        journals = new ArrayList<>();
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

    public boolean isLogged() {
        return isLogged;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void addJournal(Journal journal) {
        journals.add(journal);
    }

}
