package frontend.panels.playlist;

import backend.Playlist;
import backend.Song;
import backend.User;
import backend.UserNotFoundException;
import backend.services.PlaylistService;
import backend.services.SongService;
import backend.services.UserService;
import frontend.Colors;
import frontend.Header;
import frontend.UserRemovedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaylistPanel extends JPanel {
    private Header header;
    private JButton addSongButton;
    private Playlist playlist;

    public PlaylistPanel(Playlist playlist) {
        this.playlist = playlist;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cram header-ul
        header = new Header(playlist.getName(), 40);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Cream butonul de DELETE
        addSongButton = new JButton("Add Song To Playlist");
        addSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        addSongButton.addActionListener(this::onAddSongClicked);

        update();
    }

    private void onAddSongClicked(ActionEvent e) {
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
                PlaylistService.getInstance().addSongToPlaylist(playlist, selected);
                update();
            }
        }
    }

    private void update() {
        ArrayList<Song> playlistSongs = PlaylistService.getInstance().getPlaylistSongs(playlist);
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Adaugam header-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Adaugam butonul de ADD
        add(addSongButton);
        add(Box.createVerticalStrut(20));

        // Cream un panel pentru lista de melodii actualizata
        JPanel songLabelPanel = new JPanel();
        songLabelPanel.setLayout(new BoxLayout(songLabelPanel, BoxLayout.Y_AXIS));
        songLabelPanel.setBackground(Colors.BACKGROUND.getColor());

        for (Song song : playlistSongs) {
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
