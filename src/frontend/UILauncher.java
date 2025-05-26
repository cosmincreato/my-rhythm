package frontend;

import backend.services.PerformerService;
import backend.services.SongService;
import frontend.frames.MainFrame;
import backend.services.UserService;

import javax.swing.*;

public class UILauncher {

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }

}
