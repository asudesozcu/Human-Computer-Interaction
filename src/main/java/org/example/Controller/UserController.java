package org.example.Controller;

import org.example.Model.Book;
import org.example.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users ;
    private final BookController bookController;


    public UserController(BookController bookController) {
        this.bookController = bookController;
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
    public User findUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }
    public User findUserByName(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean borrowBookForUser(String username, Book book) {
        User user = findUserByName(username);
        System.out.println(book.isBorrowed());
        if (user != null && !book.isBorrowed()) {
            user.borrowBook(book);
            book.setBorrowed(true);  // Mark the book as borrowed
            return true;
        }
        return false;
    }

    public boolean returnBookForUser(String username, int bookId) {
        User user = findUserByName(username);
        if (user == null) return false;

        boolean removed = user.getBorrowedBooks().removeIf(book -> book.getId() == bookId);
        if (removed) {
            Book book = bookController.findBookById(bookId).orElse(null);  // Access via instance
            if (book != null) {
                book.setBorrowed(false);  // Update the book's borrowed status
            }
        }
        return removed;
    }
}