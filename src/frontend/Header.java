package frontend;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private final String fontName = "Helvetica Neue";
    private final int fontSize;
    private JLabel label;

    public Header(String text, int fontSize) {
        this.fontSize = fontSize;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Colors.BACKGROUND.getColor());

        label = new JLabel();
        label.setText(text);
        label.setForeground(Colors.PRIMARY.getColor());
        label.setBackground(Colors.BACKGROUND.getColor());
        label.setFont(new Font(this.fontName,Font.BOLD, this.fontSize));

        add(label);
    }
}
