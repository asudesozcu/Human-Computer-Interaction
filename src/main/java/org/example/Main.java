package org.example;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import org.example.Controller.LibraryController;
import org.example.view.LibraryView;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ENGLISH);

            // Create a terminal object
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            // Create a screen to manage the terminal
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen(); // Start the screen

            // Initialize the controller and view
            LibraryView view = new LibraryView(null); // Pass null initially, we will update it

            // Initialize the controller with the view
            LibraryController controller = new LibraryController(view);

            // Now set the controller inside the view
            view.setController(controller);

            // Initialize the view (sets up the GUI)
            view.init(screen);

            // Show the main menu and block until the window is closed
            view.showMainMenu();

            // Close the screen and terminal when done
            screen.stopScreen();
            terminal.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
