package frontend.frames;

import backend.services.PerformerService;
import backend.services.SongService;
import frontend.Colors;
import frontend.UserAddedListener;
import frontend.panels.TopPanel;
import frontend.panels.UsersPanel;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements UserAddedListener {
    private final Dimension dimension = new Dimension(1024, 600);
    private final TopPanel topPanel;
    private final UsersPanel usersPanel;

    public MainFrame() {
        // Initializare
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyRhythm");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());
        setLayout(new BorderLayout());

        topPanel = new TopPanel(this);
        usersPanel = new UsersPanel();

        add(topPanel, BorderLayout.NORTH);
        add(usersPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Cand in TopPanel se adauga un utilizator
    @Override
    public void onUserAdded() {
        usersPanel.update();
    }
}
