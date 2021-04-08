package com.crazyreefs.beans;

public class User {
    private int userId; // primary key
    private String username; // not null, unique
    private String password; // not null
    private String firstname; // not null
    private String lastname; // not null
    private String email; // not null

    public User() {
        userId = 0;
        username = "";
        password = "";
        firstname = "";
        lastname = "";
        email = "";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
