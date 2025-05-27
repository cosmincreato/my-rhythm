package frontend.frames;

import frontend.Colors;
import frontend.panels.main.PerformersPanel;
import frontend.panels.main.SongsPanel;
import frontend.panels.main.TopPanel;
import frontend.panels.main.UsersPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Initializare
        Dimension dimension = new Dimension(1024, 600);
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyRhythm");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());
        setLayout(new BorderLayout());

        TopPanel topPanel = new TopPanel();

        UsersPanel usersPanel = new UsersPanel();
        PerformersPanel performersPanel = new PerformersPanel();
        SongsPanel songsPanel = new SongsPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(Colors.PRIMARY.getColor());

        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Performers", performersPanel);
        tabbedPane.addTab("Songs", songsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
