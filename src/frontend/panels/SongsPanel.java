package frontend.panels;

import frontend.Colors;
import frontend.Header;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;

public class SongsPanel extends JPanel {
    private Header header;

    public SongsPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu melodii preferate
        header = new Header("Favorite Songs", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adaugam header-ul in panel
        add(header);
        add(Box.createVerticalStrut(10));
    }

}
