package frontend.frames;

import backend.Playlist;
import backend.services.PlaylistService;
import frontend.Colors;
import frontend.panels.playlist.PlaylistPanel;

import javax.swing.*;
import java.awt.*;

public class PlaylistFrame extends JFrame {
    private final Dimension dimension = new Dimension(1024, 600);
    private PlaylistPanel playlistPanel;
    Playlist playlist;

    public PlaylistFrame(int playlistId) {
        this.playlist = PlaylistService.getInstance().findPlaylist(playlistId);

        setSize(dimension);
        setResizable(false);
        setTitle(playlist.getName() + " (" + playlist.getUser().getUsername() + "'s Playlist)");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colors.BACKGROUND.getColor());
        setLayout(new BorderLayout());
        JPanel centerContainer = new JPanel();
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.setBackground(Colors.BACKGROUND.getColor());

        playlistPanel = new PlaylistPanel(playlist);

        add(playlistPanel, BorderLayout.NORTH);
        setVisible(true);
    }
}
