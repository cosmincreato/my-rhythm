package frontend.panels.profile;

import backend.Song;
import backend.User;
import backend.services.SongService;
import backend.services.UserService;
import frontend.Colors;
import frontend.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PlaylistsPanel extends JPanel {
    private JButton addPlaylistButton;
    private Header header;
    private User user;

    public PlaylistsPanel(User user) {
        this.user = user;

        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu playlisturi
        header = new Header("Playlists", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        addPlaylistButton = new JButton("Create Playlist");
        addPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPlaylistButton.addActionListener(this::onAddPlaylistClicked);

        update();
    }
    private void onAddPlaylistClicked(ActionEvent e) {
        ArrayList<Song> songs = SongService.getInstance().getSongs();

        if (songs.size() != 0) {
            Song selected = (Song) JOptionPane.showInputDialog(
                    this,
                    "Select a Song:",
                    "Choose Song",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    songs.toArray(),
                    songs.get(0)
            );

            if (selected != null) {
                UserService.getInstance().addFavoriteSong(user, selected);
                update();
            }
        }
    }

    public void update() {
        ArrayList<Song> favoriteSongs = UserService.getInstance().getFavoriteSongs(user);
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Readaugam header-ul, butonul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));
        add(addPlaylistButton);

        // Cream un panel pentru lista de artisti actualizata
        JPanel songLabelPanel = new JPanel();
        songLabelPanel.setLayout(new BoxLayout(songLabelPanel, BoxLayout.Y_AXIS));
        songLabelPanel.setBackground(Colors.BACKGROUND.getColor());

        for (Song song : favoriteSongs) {
            JLabel performerLabel = new JLabel(song.toString());
            performerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            performerLabel.setForeground(Color.WHITE);

            songLabelPanel.add(performerLabel);
            songLabelPanel.add(Box.createVerticalStrut(5));
        }

        // Adaugam scroll la panel
        JScrollPane scrollPane = new JScrollPane(songLabelPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());
        add(scrollPane);

        // Afisam din nou panel-ul
        revalidate();
        repaint();
    }
}

