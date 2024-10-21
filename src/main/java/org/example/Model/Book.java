package org.example.Model;

public class Book {
    private String title;
    private String author;
    private int stock ;
    private String type;

    public Book(String title, String author, String type) {
        this.title = title;
        this.author = author;
        this.stock = 1;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getStock() {
        return stock;
    }
    public String getType() {
        return type;
    }

    public static String getInfo(Book book) {
        return "Title: " + book.getTitle()
                + "\nAuthor: " + book.getAuthor()
                + "\nStock: " + book.getStock()
                + "\nType:  " + book.getType();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setType(String type) {
        this.type = type;
    }
}