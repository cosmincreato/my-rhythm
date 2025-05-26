package frontend.panels;

import frontend.Colors;
import frontend.Header;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;

public class PlaylistsPanel extends JPanel {
    private Header header;

    public PlaylistsPanel() {
        // Initializare
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
