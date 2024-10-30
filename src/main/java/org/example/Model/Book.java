package org.example.Model;
public class Book {
    private static int idCounter = 0;
    private final int id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private boolean isBorrowed;

    public int getId() {
        return id;
    }


    public Book(String title, String author,String genre, int year) {
        this.id = ++idCounter;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isBorrowed = false;
    }



    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                (isBorrowed ? " (Borrowed)" : "") +
                '}';
    }

}