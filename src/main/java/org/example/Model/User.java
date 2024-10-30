package org.example.Model;


import java.util.ArrayList;
import java.util.List;

public class User {
    private static int idCounter = 0; // Static counter shared across all instances
    private final int id;
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    public User(String name, String email) {
        this.id = ++idCounter;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
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
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public boolean returnBook(int bookId) {
        return borrowedBooks.removeIf(book -> book.getId() == bookId);
    }
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s", id, name, email);
    }
}

