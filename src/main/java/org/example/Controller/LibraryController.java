package org.example.Controller;

import org.example.Model.Book;
import org.example.Model.Library;
import org.example.view.LibraryView;

import java.io.IOException;

// The controller for the Library Management System
public class LibraryController {
    private Library library;
    private LibraryView view;

    public LibraryController(Library library, LibraryView view) {
        this.library = library;
        this.view = view;
    }

    public LibraryController() {

    }

    // Display the list of books
    public void displayBooks() throws IOException {
        StringBuilder bookList = new StringBuilder();
        for (Book book : library.getBooks()) {
            bookList.append(book.toString()).append("\n");
        }
        view.displayBooks(bookList.toString());
    }

    // Prompt the user to add a book
    public void addBook() throws IOException {
        view.displayAddBook();
    }

    // Add a book to the library
    public void addBook(String title, String author, String type) {
        boolean isFound = false;
        for (Book book : library.getBooks()) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getType().equals(type)) {
                book.setStock(book.getStock() + 1);
                isFound = true;
            }

        }
        if (!isFound) {
            library.addBook(new Book(title, author, type));
        }
    }
}