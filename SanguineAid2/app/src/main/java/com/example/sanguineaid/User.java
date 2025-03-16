package com.example.sanguineaid;

public class User {
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private int age;
    private String gender;
    private String date_of_birth;
    private String city;
    private String address;
    private boolean hasdonatedbloodbefore;

    public User(String username, String password, String email, String first_name, String last_name, int age, String gender, String date_of_birth, String city, String address, boolean hasdonatedbloodbefore) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.city = city;
        this.address = address;
        this.hasdonatedbloodbefore = hasdonatedbloodbefore;
    }
}
