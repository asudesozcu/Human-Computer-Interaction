package org.example.Controller;

import org.example.Model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookController {
    private final List<Book> books;

    public BookController() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
    public Optional<Book> findBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public boolean updateBook(int id, String title, String author, String genre, int year) {
        Optional<Book> bookOpt = findBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setYear(year);
            return true;
        }
        return false;
    }



    public boolean returnBook(int bookId) {
        Optional<Book> bookOpt = findBookById(bookId);
        if (bookOpt.isPresent() && bookOpt.get().isBorrowed()) {
            bookOpt.get().setBorrowed(false);
            return true;
        }
        return false;
    }
}
