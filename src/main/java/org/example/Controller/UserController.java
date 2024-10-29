package org.example.Controller;

import org.example.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users ;

    public UserController() {
        this.users = new ArrayList<>();

    }

    public void addUser(String name, String email) {
        User user = new User( name, email);
        users.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean updateUser(int id, String name, String email) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(name);
                user.setEmail(email);
                return true;
            }
        }
        return false;
    }

    public List<User> getUsers() {
        return users;
    }


    public boolean removeUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                users.remove(user);
return true;
            }
        }
        return false;

    }
}