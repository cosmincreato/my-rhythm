package ui;

import music.PerformerService;
import music.SongService;
import ui.frames.MainFrame;
import users.UserService;

import javax.swing.*;

public class UILauncher {

    public static void launch(UserService userService, PerformerService performerService, SongService songService) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame(userService, performerService, songService);
        });
    }

}
