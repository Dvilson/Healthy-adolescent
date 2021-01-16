package com.dvilson.healthyadolescent.models;

public class User {
    String fullName;
    String pseudo;

    public User(String fullName, String pseudo) {
        this.fullName = fullName;
        this.pseudo = pseudo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
