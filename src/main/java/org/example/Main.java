package org.example;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import org.example.Controller.BookController;
import org.example.view.LibraryView;

import java.io.IOException;
import java.util.Locale;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.example.Controller.BookController;
import org.example.Controller.UserController;
import org.example.view.LibraryView;


public class Main {

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ENGLISH);

            // Create a terminal and screen
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            TerminalScreen screen = new TerminalScreen(terminal);
            screen.startScreen();


            // Initialize controllers
            BookController libraryController = new BookController();
            UserController userController = new UserController();

            // Create and display the main view
            LibraryView libraryView = new LibraryView(screen, libraryController, userController);
            libraryView.displayMainMenu();

            // Stop the screen
            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
