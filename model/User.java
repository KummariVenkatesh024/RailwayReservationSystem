package model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String name;
    private String phone;

    public User(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void displayUser() {
        System.out.println("Username : " + username);
        System.out.println("Name     : " + name);
        System.out.println("Phone    : " + phone);
    }
    @Override
    public String toString() {
        return username + " - " + name + " - " + phone;
    }
}