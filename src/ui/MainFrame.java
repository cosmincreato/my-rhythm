package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final Dimension dimension = new Dimension(1024, 600);
    private final String title = "MyRhythm";
    private Header header;

    public MainFrame() {
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(this.title);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Colors.BACKGROUND.getColor());

        header = new Header(title);
        panel.add(header);

        add(panel, BorderLayout.NORTH);
        setLayout(new FlowLayout());


        setVisible(true);
    }
}
