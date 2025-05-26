package frontend.frames;

import backend.services.PerformerService;
import frontend.Colors;
import frontend.UserRemovedListener;
import frontend.panels.PerformersPanel;
import frontend.panels.PlaylistsPanel;
import frontend.panels.ProfilePanel;
import frontend.panels.SongsPanel;
import backend.User;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame implements UserRemovedListener {
    private final Dimension dimension = new Dimension(1024, 600);
    private ProfilePanel profilePanel;
    private SongsPanel songsPanel;
    private PerformersPanel performersPanel;
    private PlaylistsPanel playlistsPanel;
    User user;
    UserRemovedListener listener;

    public ProfileFrame(int id, UserRemovedListener listener) {
        // Initializare
        this.user = UserService.getInstance().findUser(id);
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



        profilePanel = new ProfilePanel(this, user);
        songsPanel = new SongsPanel();
        performersPanel = new PerformersPanel(user);
        playlistsPanel = new PlaylistsPanel();

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
