package org.example.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import org.example.Controller.UserController;
import org.example.Model.User;

public class UserView {
    private final Screen screen;
    private final UserController controller;
    private final WindowBasedTextGUI gui;

    public UserView(Screen screen, UserController controller) {
        this.screen = screen;
        this.controller = controller;
        this.gui = new MultiWindowTextGUI(screen);
    }

    // Main menu for user management
    public void displayUserManagementMenu() {
        BasicWindow window = new BasicWindow("User Management Menu");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Choose an option:"));
        panel.addComponent(new Button("1. Add User", this::displayAddUserForm));
        panel.addComponent(new Button("2. List Users", this::displayListUsers));
        panel.addComponent(new Button("3. Edit User", this::displayEditUserForm));
        panel.addComponent(new Button("4. Remove User", this::displayRemoveUserForm));
        panel.addComponent(new Button("5. Back to Main Menu", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void displayRemoveUserForm() {
        BasicWindow window = new BasicWindow("Remove User");

        if (controller.getAllUsers().isEmpty()) {
            showErrorMessage("No users available to remove.");
            return;
        }

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Inner method to populate and refresh the list of users
        populateUserList(panel, window);

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    private void populateUserList(Panel panel, Window window) {
        panel.removeAllComponents(); // Clear previous components

        for (User user : controller.getAllUsers()) {
            final int userId = user.getId();
            panel.addComponent(new Button(user.getName(), () -> {
                if (controller.removeUser(userId)) {
                    showErrorMessage("User deleted successfully.");
                    populateUserList(panel, window); // Refresh list after deletion
                } else {
                    showErrorMessage("Failed to delete user.");
                }
            }));
        }

        panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
    }


    // Utility to show error message dialogs
    private void showErrorMessage(String message) {
        MessageDialog.showMessageDialog(gui, "Error", message);
    }
private void displayAddUserForm() {
    BasicWindow window = new BasicWindow("Add User");

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

    TextBox usernameInput = new TextBox(new TerminalSize(20, 1));
    TextBox emailInput = new TextBox(new TerminalSize(20, 1));

    panel.addComponent(new Label("Username:"));
    panel.addComponent(usernameInput);
    panel.addComponent(new Label("Email:"));
    panel.addComponent(emailInput);

    panel.addComponent(new Button("Save", () -> {
        controller.addUser(usernameInput.getText(), emailInput.getText());
        gui.removeWindow(window);
    }));
    panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

    window.setComponent(panel);
    gui.addWindowAndWait(window);
}

private void displayListUsers() {
    BasicWindow window = new BasicWindow("List of Users");

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

    if (controller.getAllUsers().isEmpty()) {
        panel.addComponent(new Label("No users available."));
    } else {
        for (User user : controller.getAllUsers()) {
            System.out.println(user.getId());
            panel.addComponent(new Label("ID:"+ user.getId()+" Name: "+ user.getName()+" Mail: " +user.getEmail()));
        }
    }

    panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
    window.setComponent(panel);
    gui.addWindowAndWait(window);
}

private void displayEditUserForm() {
    BasicWindow window = new BasicWindow("Edit User");

    if (controller.getAllUsers().isEmpty()) {
        showErrorMessage("No users available to edit.");
        return;
    }

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

    for (int i = 0; i < controller.getAllUsers().size(); i++) {
        User user = controller.getAllUsers().get(i);
        final int index = i;
        panel.addComponent(new Button(user.getName(), () -> {
            gui.removeWindow(window);
            showEditUserDetails(index);
        }));
    }

    panel.addComponent(new Button("Back", () -> gui.removeWindow(window)));
    window.setComponent(panel);
    gui.addWindowAndWait(window);
}


private void showEditUserDetails(int index) {
    BasicWindow window = new BasicWindow("Edit User");

    User user = controller.getAllUsers().get(index);
    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

    TextBox usernameInput = new TextBox(new TerminalSize(20, 1), user.getName());
    TextBox emailInput = new TextBox(new TerminalSize(20, 1), user.getEmail());

    panel.addComponent(new Label("Username:"));
    panel.addComponent(usernameInput);
    panel.addComponent(new Label("Email:"));
    panel.addComponent(emailInput);

    panel.addComponent(new Button("Save", () -> {
        controller.updateUser(user.getId(), usernameInput.getText(), emailInput.getText());
        gui.removeWindow(window);
    }));
    panel.addComponent(new Button("Cancel", () -> gui.removeWindow(window)));

    window.setComponent(panel);
    gui.addWindowAndWait(window);
}
}