package frontend.panels.profile;

import backend.Playlist;
import backend.User;
import backend.services.PlaylistService;
import frontend.Colors;
import frontend.Header;
import frontend.frames.PlaylistFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UserPlaylistsPanel extends JPanel {
    private JButton addPlaylistButton;
    private Header header;
    private User user;

    public UserPlaylistsPanel(User user) {
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
        JTextField nameField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Create New Playlist", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();

            if (!name.isEmpty()) {
                Playlist playlist = new Playlist(name, user);
                PlaylistService.getInstance().addPlaylist(playlist);
                update();
            } else {
                JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void update() {
        ArrayList<Playlist> playlists = PlaylistService.getInstance().getUserPlaylists(user);
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Readaugam header-ul, butonul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));
        add(addPlaylistButton);

        // Cream un panel pentru lista de playlist-uri actualizata
        JPanel playlistLabelPanel = new JPanel();
        playlistLabelPanel.setLayout(new BoxLayout(playlistLabelPanel, BoxLayout.Y_AXIS));
        playlistLabelPanel.setBackground(Colors.BACKGROUND.getColor());

        for (Playlist playlist : playlists) {
            JLabel playlistLabel = new JLabel(playlist.toString());
            playlistLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            playlistLabel.setForeground(Color.WHITE);

            playlistLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new PlaylistFrame(playlist.getId());
                }
            });

            playlistLabelPanel.add(playlistLabel);
            playlistLabelPanel.add(Box.createVerticalStrut(5));
        }

        // Adaugam scroll la panel
        JScrollPane scrollPane = new JScrollPane(playlistLabelPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());
        add(scrollPane);

        // Afisam din nou panel-ul
        revalidate();
        repaint();
    }
}

