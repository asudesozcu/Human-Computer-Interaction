package org.example.Controller;

import org.example.view.LibraryView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryController {
    private final List<String> books;
    private final LibraryView view;

    // Constructor takes LibraryView as a parameter
    public LibraryController(LibraryView view) {
        this.view = view;
        this.books = new ArrayList<>();
    }

    // Display the list of books
    public void displayBooks() throws IOException {
        StringBuilder bookList = new StringBuilder();
        for (String book : books) {
            bookList.append(book).append("\n");
        }
        view.displayBooks(bookList.toString()); // Call displayBooks in LibraryView
    }

    // Add a book
    public void addBook(String title, String author, String extraInfo) {
        books.add("Title: " + title + ", Author: " + author);
    }
}
