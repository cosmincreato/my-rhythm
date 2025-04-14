package ui.frames;

import music.PerformerService;
import ui.Colors;
import ui.UserAddedListener;
import ui.UserRemovedListener;
import ui.panels.PerformersPanel;
import ui.panels.PlaylistsPanel;
import ui.panels.ProfilePanel;
import ui.panels.SongsPanel;
import users.User;
import users.UserService;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame implements UserRemovedListener {
    private UserService userService;
    private PerformerService performerService;
    private final Dimension dimension = new Dimension(1024, 600);
    private ProfilePanel profilePanel;
    private SongsPanel songsPanel;
    private PerformersPanel performersPanel;
    private PlaylistsPanel playlistsPanel;
    User user;
    UserRemovedListener listener;

    public ProfileFrame(int id, UserService userService, PerformerService performerService, UserRemovedListener listener) {
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
        JPanel centerContainer = new JPanel();
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.setBackground(Colors.BACKGROUND.getColor());



        profilePanel = new ProfilePanel(this, userService, user);
        songsPanel = new SongsPanel(userService);
        performersPanel = new PerformersPanel(userService, performerService, user);
        playlistsPanel = new PlaylistsPanel(userService);

        centerContainer.add(songsPanel);
        centerContainer.add(Box.createVerticalStrut(10)); // spacing
        centerContainer.add(performersPanel);
        centerContainer.add(Box.createVerticalStrut(10)); // spacing
        centerContainer.add(playlistsPanel);

        add(profilePanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
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
