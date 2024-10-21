package org.example;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
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
            LibraryController controller = new LibraryController();

            LibraryView view = new LibraryView(controller);

            // Initialize the view (sets up the GUI)
            view.init();

            // Start the application
            view.showMainMenu();

            // Close the screen and terminal when done
            screen.stopScreen();
            terminal.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
