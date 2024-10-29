package org.example.Model;



public class User {
    private static int idCounter = 0; // Static counter shared across all instances
    private final int id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = ++idCounter; // Assign a unique ID to each new User instance
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

