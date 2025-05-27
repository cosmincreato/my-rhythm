package frontend.panels.main;

import backend.Genre;
import backend.Performer;
import backend.Song;
import backend.User;
import backend.services.PerformerService;
import backend.services.SongService;
import backend.services.UserService;
import frontend.Colors;
import frontend.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongsPanel extends JPanel {
    private Header header;
    private JButton addSongButton;

    public SongsPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu melodii
        header = new Header("Songs", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cream butonul de ADD
        addSongButton = new JButton("Add New Song");
        addSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Atunci cand apasam pe butoane, trebuie sa actualizam SongsPanel
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JComboBox<Genre> genreComboBox = new JComboBox<>(Genre.values());
                JComboBox<Performer> performerComboBox = new JComboBox<>();
                JTextField durationField = new JTextField();

                for (Performer performer : PerformerService.getInstance().getPerformers()) {
                    performerComboBox.addItem(performer);
                }

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Genre:"));
                panel.add(genreComboBox);
                panel.add(new JLabel("Performer:"));
                panel.add(performerComboBox);
                panel.add(new JLabel("Duration (seconds):"));
                panel.add(durationField);

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Add New Song", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String duration = durationField.getText().trim();

                    if (name.isEmpty() || duration.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields must be filled", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        int durationInSeconds = Integer.parseInt(duration);
                        Genre genre = (Genre) genreComboBox.getSelectedItem();
                        Performer performer = (Performer) performerComboBox.getSelectedItem();

                        Song song = new Song(performer, name, genre, durationInSeconds);
                        SongService.getInstance().addSong(song);
                        update();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Duration must be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Adaugam lista de utilizatori in panel
        update();
    }



    public void update() {
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Adaugam header-ul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Adaugam butonul de ADD
        add(addSongButton);
        add(Box.createVerticalStrut(10));

        // Cream un panel pentru lista de melodii actualizata
        JPanel songListPanel = new JPanel();
        songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));
        songListPanel.setBackground(Colors.BACKGROUND.getColor());

        for (Song song : SongService.getInstance().getSongs()) {
            JLabel performerLabel = new JLabel(song.toString());
            performerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            performerLabel.setForeground(Color.WHITE);

            songListPanel.add(performerLabel);
            songListPanel.add(Box.createVerticalStrut(5));
        }

        // Adaugam scroll la panel
        JScrollPane scrollPane = new JScrollPane(songListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());

        // Adaugam panel-ul de scroll in panel-ul cu melodii
        add(scrollPane);

        // Afisam din nou panel-ul
        revalidate();
        repaint();
    }
}

