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

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);


            // Create a terminal object
            Terminal terminal = new DefaultTerminalFactory().createTerminal(); //auto-detects swing or unix terminal at runtime

            // Create a screen to manage the terminal
        Screen screen = new TerminalScreen(terminal);
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            window.setComponent(contentPanel);
            gui.addWindowAndWait(window);


    }
}


//The Terminal layer is the lowest level available in Lanterna,
// giving you very precise control of what data is sent to the client.