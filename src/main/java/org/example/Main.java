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


            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            Screen screen = new TerminalScreen(terminal);
            screen.startScreen(); 

            // Initialize the controller and view
            LibraryView view = new LibraryView(null);
            LibraryController controller = new LibraryController(view);
            view.setController(controller);

            //  GUI
            view.init(screen);
            view.showMainMenu();

            // Close
            screen.stopScreen();
            terminal.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
