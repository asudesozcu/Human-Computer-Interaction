package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.Locale;

public class HelloWorld {

    public static void main(String[] args) {
        // Force the locale to English to avoid issues with color names
        Locale.setDefault(Locale.ENGLISH);

        try {
            // Create a single terminal and screen object using the same DefaultTerminalFactory
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();
            TerminalScreen screen = terminalFactory.createScreen();

            screen.startScreen();  // Start the screen

            // Create the GUI with a window to show "Hello World"
            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);

            // Create a basic window
            Window window = new BasicWindow("Hello World Window");

            // Create a panel for layout
            Panel contentPanel = new Panel();
            contentPanel.setPreferredSize(new TerminalSize(30, 10));

            // Add a label with "Hello World" text
            contentPanel.addComponent(new Label("Hello World"));

            // Add a button to exit the program
            contentPanel.addComponent(new Button("Exit", () -> {
                try {
                    window.close();
                    screen.stopScreen();
                    terminal.close();  // Close the terminal as well
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            // Set the panel in the window and display it
            window.setComponent(contentPanel);
            gui.addWindowAndWait(window);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}