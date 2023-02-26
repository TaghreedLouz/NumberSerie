package com.devtaghreed.numberseries;

public class Game {
    public Game(int id, String username, String date, int score) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Game(String date) {
        this.date = date;
    }

    private String username;
    private String date;
    private int score;

    public Game(String username, String date, int score) {
        this.username = username;
        this.date = date;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

