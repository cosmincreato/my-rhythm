package frontend.panels.main;

import frontend.Colors;
import frontend.Header;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul
        Header header = new Header("MyRhythm", 40);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Adaugam header-ul
        add(header);
        add(Box.createVerticalStrut(10));
    }
}
