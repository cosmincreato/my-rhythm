package ui.panels;

import ui.Colors;
import ui.Header;
import users.UserService;

import javax.swing.*;
import java.awt.*;

public class SongsPanel extends JPanel {
    private UserService userService;
    private Header header;

    public SongsPanel(UserService userService) {
        // Initializare
        this.userService = userService;
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
