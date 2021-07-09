package com.epam.ftask.entities;

public class User implements Entity {
    private String username;
    private String password;
    private String name;
    private UserRole role;
    private Long id = null;
    public static final String ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String NAME = "NAME";
    public static final String ROLE = "ROLE";

    public User(long id, String name, String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public User(String name, String username, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public Long getId() {
        return id;
    }
}
