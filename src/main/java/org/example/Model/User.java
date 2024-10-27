package org.example.Model;



public class User {
    private  int id=0;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = ++id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s", id, name, email);
    }
}

