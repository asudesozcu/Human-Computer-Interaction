package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
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

import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class HelloWorld {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();// terminal for UI
            Terminal terminal = terminalFactory.createTerminal();//reading writing from terminal
            TerminalScreen screen = terminalFactory.createScreen(); // renders uı and user interaction
            screen.startScreen();


            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);

            Window window = new BasicWindow("Hello World Window");

            //panel for layout
            Panel contentPanel = new Panel();
            contentPanel.addComponent(new Label("Hello World"));
            contentPanel.addComponent(new Button("Exit", () -> {
                try {
                    window.close();
                    screen.stopScreen();
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

              window.setComponent(contentPanel); // add panel to window
            gui.addWindowAndWait(window); //show the window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}