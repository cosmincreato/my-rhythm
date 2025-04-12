package ui;

import ui.frames.MainFrame;
import users.UserService;

import javax.swing.*;

public class UILauncher {

    public static void launch(UserService userService) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame(userService);
        });
    }

}
