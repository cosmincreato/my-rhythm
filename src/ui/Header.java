package ui;

import javax.swing.*;
import java.awt.*;

public class Header extends JTextArea {

    public Header(String text) {
        setText(text);
        setForeground(Colors.PRIMARY.getColor());
        setBackground(Colors.BACKGROUND.getColor());
        setFont(new Font("Helvetica Neue",Font.BOLD, 48));
    }
}
