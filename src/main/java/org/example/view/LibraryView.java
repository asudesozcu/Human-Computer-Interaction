package org.example.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import org.example.Controller.BookController;
import org.example.Controller.UserController;
import org.example.Model.Book;
import org.example.Model.User;

public class LibraryView {
    private final Screen screen;
    private final BookController bookController;
    private final UserController userController;
    private final WindowBasedTextGUI gui;

    public LibraryView(Screen screen, BookController libraryController, UserController userController) {
        this.screen = screen;
        this.bookController = libraryController;
        this.userController = userController;
        this.gui = new MultiWindowTextGUI(screen);
    }

    public void displayMainMenu() {
        BasicWindow window = new BasicWindow("Library System - Management Menu");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Select Management Option:"));

        panel.addComponent(new Button("1. Manage Books", this::displayBookManagementMenu));
        panel.addComponent(new Button("2. Manage Users", () -> new UserView(screen, userController).displayUserManagementMenu()));
        panel.addComponent(new Button("3. Borrow Book", this::borrowBook));
        panel.addComponent(new Button("4. Return Book", this::returnBook));
        panel.addComponent(new Button("5. Exit", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void displayBookManagementMenu() {
        BasicWindow window = new BasicWindow("Book Management");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Button("1. Add Book", this::displayAddBookForm));
        panel.addComponent(new Button("2. List Books", this::displayListBooks));
        panel.addComponent(new Button("3. Edit Book", this::displayEditBookForm));
        panel.addComponent(new Button("4. Remove Book", this::displayRemoveBookForm));
        panel.addComponent(new Button("Back", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }


    private void borrowBook() {
        BasicWindow window = new BasicWindow("Borrow Book");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox userNameInput = new TextBox(new TerminalSize(20, 1));
        TextBox bookIdInput = new TextBox(new TerminalSize(20, 1));

        panel.addComponent(new Label("Enter Username:"));
        panel.addComponent(userNameInput);
        panel.addComponent(new Label("Enter Book ID:"));
        panel.addComponent(bookIdInput);

        panel.addComponent(new Button("Borrow", () -> {
            String userName = userNameInput.getText();
            try {
                int bookId = Integer.parseInt(bookIdInput.getText());

                User user = userController.findUserByName(userName);
                Book book = bookController.findBookById(bookId).orElse(null);

                if (user != null && book != null && userController.borrowBookForUser(userName, book)) {
                    showSuccessMessage("Book borrowed successfully!");
                } else {
                    showErrorMessage("Failed to borrow book. It may already be borrowed or user/book is invalid.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid Book ID entered.");
            }
            gui.removeWindow(window);
        }));
        panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
    private void returnBook() {
        BasicWindow window = new BasicWindow("Return Book");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox userNameInput = new TextBox(new TerminalSize(20, 1));
        TextBox bookIdInput = new TextBox(new TerminalSize(20, 1));

        panel.addComponent(new Label("Enter Username:"));
        panel.addComponent(userNameInput);
        panel.addComponent(new Label("Enter Book ID:"));
        panel.addComponent(bookIdInput);

        panel.addComponent(new Button("Return", () -> {
            String userName = userNameInput.getText();
            try {
                int bookId = Integer.parseInt(bookIdInput.getText());

                User user = userController.findUserByName(userName);
                Book book = bookController.findBookById(bookId).orElse(null);

                if (user != null && book != null ) {
                    if (bookController.returnBook(bookId)) {
                        userController.returnBookForUser(user.getName(), book.getId());
                        showSuccessMessage("Book returned successfully!");
                    } else {
                        showErrorMessage("Failed to return book.");
                    }
                } else {
                    showErrorMessage("User has not borrowed this book or invalid user/book.");
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid Book ID entered.");
            }
            gui.removeWindow(window);
        }));
        panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void displayAddBookForm() {
        BasicWindow window = new BasicWindow("Add Book");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox titleInput = new TextBox(new TerminalSize(20, 1));
        TextBox authorInput = new TextBox(new TerminalSize(20, 1));
        TextBox genreInput = new TextBox(new TerminalSize(20, 1));
        TextBox yearInput = new TextBox(new TerminalSize(20, 1));

        panel.addComponent(new Label("Title:"));
        panel.addComponent(titleInput);
        panel.addComponent(new Label("Author:"));
        panel.addComponent(authorInput);
        panel.addComponent(new Label("Genre:"));
        panel.addComponent(genreInput);
        panel.addComponent(new Label("Year:"));
        panel.addComponent(yearInput);

        panel.addComponent(new Button("Save", () -> {
            bookController.addBook(new Book(titleInput.getText(), authorInput.getText(), genreInput.getText(), Integer.parseInt(yearInput.getText())));
            gui.removeWindow(window);
        }));
        panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void displayListBooks() {
        BasicWindow window = new BasicWindow("List of Books");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        if (bookController.getAllBooks().isEmpty()) {
            panel.addComponent(new Label("No books available."));
        } else {
            for (Book book : bookController.getAllBooks()) {
                panel.addComponent(new Label(book.toString()));
            }
        }

        panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void displayEditBookForm() {
        BasicWindow window = new BasicWindow("Edit Book");

        if (bookController.getAllBooks().isEmpty()) {
            showErrorMessage("No books available to edit.");
            return;
        }

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        for (int i = 0; i < bookController.getAllBooks().size(); i++) {
            Book book = bookController.getAllBooks().get(i);
            final int index = i;
            panel.addComponent(new Button(book.getTitle(), () -> {
                gui.removeWindow(window);
                showEditBookDetails(index);
            }));
        }

        panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
    private void displayRemoveBookForm() {
        BasicWindow window = new BasicWindow("Remove Book");

        if (bookController.getAllBooks().isEmpty()) {
            showErrorMessage("No books available to remove.");
            return;
        }

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        for (Book book : bookController.getAllBooks()) {
            final int bookId = book.getId();  // Assuming getId() method exists
            panel.addComponent(new Button(book.getTitle(), () -> {
                bookController.removeBook(bookId);
                gui.removeWindow(window);
                displayRemoveBookForm();  // Refresh the list after deletion
            }));
        }

        panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
    private void showEditBookDetails(int index) {
        BasicWindow window = new BasicWindow("Edit Book");

        Book book = bookController.getAllBooks().get(index);
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox titleInput = new TextBox(new TerminalSize(20, 1), book.getTitle());
        TextBox authorInput = new TextBox(new TerminalSize(20, 1), book.getAuthor());
        TextBox genreInput = new TextBox(new TerminalSize(20, 1), book.getGenre());
        TextBox yearInput = new TextBox(new TerminalSize(20, 1), String.valueOf(book.getYear()));

        panel.addComponent(new Label("Title:"));
        panel.addComponent(titleInput);
        panel.addComponent(new Label("Author:"));
        panel.addComponent(authorInput);
        panel.addComponent(new Label("Genre:"));
        panel.addComponent(genreInput);
        panel.addComponent(new Label("Year:"));
        panel.addComponent(yearInput);

        panel.addComponent(new Button("Save", () -> {
            bookController.updateBook(book.getId(), titleInput.getText(), authorInput.getText(), genreInput.getText(), Integer.parseInt(yearInput.getText()));
            gui.removeWindow(window);
        }));
        panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }


    private void showErrorMessage(String message) {
        MessageDialog.showMessageDialog(gui, "Error", message, MessageDialogButton.OK);
    }

    private void showSuccessMessage(String message) {
        MessageDialog.showMessageDialog(gui, "Success", message, MessageDialogButton.OK);
    }

}
