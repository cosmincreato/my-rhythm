package ui.frames;

import ui.Colors;
import ui.UserAddedListener;
import ui.UserRemovedListener;
import ui.panels.ProfilePanel;
import users.User;
import users.UserService;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame implements UserRemovedListener {
    private UserService userService;
    private final Dimension dimension = new Dimension(400, 400);
    private ProfilePanel profilePanel;
    User user;
    UserRemovedListener listener;

    public ProfileFrame(int id, UserService userService, UserRemovedListener listener) {
        // Initializare
        this.userService = userService;
        this.user = this.userService.findUser(id);
        this.listener = listener;
        setSize(dimension);
        setResizable(false);
        setTitle(user.getUsername() + "'s Profile");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());
        setLayout(new BorderLayout());

        profilePanel = new ProfilePanel(this, userService, user);

        add(profilePanel, BorderLayout.NORTH);
        setVisible(true);
    }

    // Cand in ProfilePanel se sterge un utilizator
    @Override
    public void onUserRemoved() {
        setVisible(false);
        dispose();
        if (listener != null)
            listener.onUserRemoved();
    }
}
