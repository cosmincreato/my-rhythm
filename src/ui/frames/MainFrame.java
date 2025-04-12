package ui.frames;

import ui.Colors;
import ui.UserAddedListener;
import ui.panels.TopPanel;
import ui.panels.UsersPanel;
import users.UserService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements UserAddedListener {
    private final UserService userService;
    private final Dimension dimension = new Dimension(1024, 600);
    private final TopPanel topPanel;
    private final UsersPanel usersPanel;

    public MainFrame(UserService userService) {
        // Initializare
        this.userService = userService;
        setSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyRhythm");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());
        setLayout(new BorderLayout());

        topPanel = new TopPanel(this, userService);
        usersPanel = new UsersPanel(userService);

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
