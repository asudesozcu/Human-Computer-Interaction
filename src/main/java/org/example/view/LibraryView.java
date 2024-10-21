package org.example.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.example.Controller.LibraryController;

import java.io.IOException;

// The View class to display the text interface
public class LibraryView {
    private final LibraryController controller;
    private MultiWindowTextGUI gui;
    private BasicWindow window;
    private Panel mainPanel;

    public LibraryView(LibraryController controller) {
        this.controller = controller;
    }

    // Initialize the terminal and GUI
    public void init() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        gui = new MultiWindowTextGUI(screen);
        window = new BasicWindow("Library Management System");
        mainPanel = new Panel();

        window.setComponent(mainPanel);
        gui.addWindow(window);
    }

    // Display the main menu
    public void showMainMenu() throws IOException {
        mainPanel.removeAllComponents();

        // Add menu options
        mainPanel.addComponent(new Label("1. View Books"));
        mainPanel.addComponent(new Label("2. Add Book"));
        mainPanel.addComponent(new Label("3. Exit"));

        // Input for user choice
        TextBox inputBox = new TextBox();
        mainPanel.addComponent(inputBox);
        mainPanel.addComponent(new Button("Submit", () -> {
            char choice = inputBox.getText().charAt(0);
            try {
                handleInput(choice);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        gui.updateScreen();
    }

    // Handle user input
    private void handleInput(Character choice) throws IOException {
        switch (choice) {
            case '1':
                controller.displayBooks();
                break;
            case '2':
                try {
                    displayAddBook();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case '3':
                window.close();
                break;
            default:
                showMainMenu();
        }
    }

    // Display list of books
    public void displayBooks(String books) throws IOException {
        mainPanel.removeAllComponents();
        mainPanel.addComponent(new Label("Books:"));
        mainPanel.addComponent(new Label(books.isEmpty() ? "No books available" : books));

        // Add a back button to go to the main menu
     //   mainPanel.addComponent(new Button("Back", this::showMainMenu));
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

        mainPanel.addComponent(new Button("Add Book", () -> {
            controller.addBook(titleInput.getText(), authorInput.getText()," ");
            try {
                showMainMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        gui.updateScreen();
    }
}
