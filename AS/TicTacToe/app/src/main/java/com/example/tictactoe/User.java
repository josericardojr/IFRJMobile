package com.example.tictactoe;

public class User {
    private String name;
    private String ficToken;
    private String hash;

    public User(){}

    public User(String name){
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFicToken() {
        return ficToken;
    }

    public void setFicToken(String ficToken) {
        this.ficToken = ficToken;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
