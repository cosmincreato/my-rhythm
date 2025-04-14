package ui.panels;

import ui.Colors;
import ui.Header;
import users.UserService;

import javax.swing.*;
import java.awt.*;

public class PlaylistsPanel extends JPanel {
    private UserService userService;
    private Header header;

    public PlaylistsPanel(UserService userService) {
        // Initializare
        this.userService = userService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu melodii preferate
        header = new Header("Playlists", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adaugam header-ul in panel
        add(header);
        add(Box.createVerticalStrut(10));
    }
}
