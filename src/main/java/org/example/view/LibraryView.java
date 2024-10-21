package org.example.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import org.example.Controller.LibraryController;

import java.io.IOException;

public class LibraryView {
    private LibraryController controller;
    private MultiWindowTextGUI gui;
    private BasicWindow window;
    private Panel mainPanel;

    public LibraryView(LibraryController controller) {
        this.controller = controller;
    }

    public void setController(LibraryController controller) {
        this.controller = controller;
    }

    // Initialize the terminal and GUI
    public void init(Screen screen) throws IOException {
        gui = new MultiWindowTextGUI(screen);
        window = new BasicWindow("Library Management System");
        mainPanel = new Panel();

        window.setComponent(mainPanel);
    }

    // Display the main menu
    public void showMainMenu() throws IOException {
        mainPanel.removeAllComponents();

        // Add menu options
        mainPanel.addComponent(new Label("Library Management System"));
        mainPanel.addComponent(new EmptySpace()); // Empty space for better formatting

        // "View Books" option
        mainPanel.addComponent(new Button("1. View Books", () -> {
            try {
                controller.displayBooks();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // "Add Book" option
        mainPanel.addComponent(new Button("2. Add Book", () -> {
            try {
                displayAddBook();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // "Exit" option
        mainPanel.addComponent(new Button("3. Exit", window::close));

        // Wait for user to close the window
        gui.addWindowAndWait(window); // Block and wait for window interaction
    }

    // Display list of books
    public void displayBooks(String books) throws IOException {
        mainPanel.removeAllComponents();
        mainPanel.addComponent(new Label("Books:"));
        mainPanel.addComponent(new Label(books.isEmpty() ? "No books available" : books));

        // Add a back button to go to the main menu
        mainPanel.addComponent(new Button("Back", () -> {
            try {
                showMainMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        gui.updateScreen();
    }

    // Display input prompt to add a book
    public void displayAddBook() throws IOException {
        mainPanel.removeAllComponents();
        mainPanel.addComponent(new Label("Enter book title:"));
        TextBox titleInput = new TextBox();
        mainPanel.addComponent(titleInput);

        mainPanel.addComponent(new Label("Enter author name:"));
        TextBox authorInput = new TextBox();
        mainPanel.addComponent(authorInput);

        // "Add Book" button to submit the input
        mainPanel.addComponent(new Button("Add Book", () -> {
            controller.addBook(titleInput.getText(), authorInput.getText(), " ");
            try {
                showMainMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        gui.updateScreen();
    }
}
