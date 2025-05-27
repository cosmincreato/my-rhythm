package frontend.panels.main;

import backend.Artist;
import backend.Band;
import backend.Performer;
import backend.User;
import backend.services.PerformerService;
import backend.services.UserService;
import frontend.Colors;
import frontend.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PerformersPanel extends JPanel {
    private Header header;
    private JButton addPerformerButton;

    public PerformersPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu artisti
        header = new Header("Performers", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cream butonul de ADD
        addPerformerButton = new JButton("Add New Performer");
        addPerformerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Atunci cand apasam pe butoane, trebuie sa actualizam UsersPanel
        addPerformerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField membersField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                String[] performerTypes = { "Artist", "Band" };
                JComboBox<String> typeDropdown = new JComboBox<>(performerTypes);
                panel.add(new JLabel("Type:"));
                panel.add(typeDropdown);
                JLabel membersLabel = new JLabel("Members (separated by ,):");
                panel.add(membersLabel);
                panel.add(membersField);
                membersLabel.setVisible(false);
                membersField.setVisible(false);

                typeDropdown.addActionListener(ev -> {
                    String selected = (String) typeDropdown.getSelectedItem();
                    if ("Band".equals(selected)) {
                        membersLabel.setVisible(true);
                        membersField.setVisible(true);
                    } else {
                        membersLabel.setVisible(false);
                        membersField.setVisible(false);
                    }
                });

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Add New Performer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String performerType = (String) typeDropdown.getSelectedItem();
                    String[] members = membersField.getText().split(",");
                    for (int i = 0; i != members.length; ++i)
                        members[i] = members[i].trim();

                    if (!name.isEmpty()) {
                        Performer performer;
                        if ("Artist".equalsIgnoreCase(performerType))
                            performer = new Artist(name);
                        else {
                            ArrayList<Artist> bandMembers = new ArrayList<Artist>();
                            for (String member : members) {
                                bandMembers.add(new Artist(member));
                            }
                            performer = new Band(name, bandMembers);
                        }
                        PerformerService.getInstance().addPerformer(performer);
                        update();
                    } else {
                        JOptionPane.showMessageDialog(null, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Adaugam lista de artisti in panel
        update();
    }

    public void update() {
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Adaugam header-ul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Adaugam butonul de ADD
        add(addPerformerButton);
        add(Box.createVerticalStrut(10));

        // Cream un panel pentru lista de artisti actualizata
        JPanel performerListPanel = new JPanel();
        performerListPanel.setLayout(new BoxLayout(performerListPanel, BoxLayout.Y_AXIS));
        performerListPanel.setBackground(Colors.BACKGROUND.getColor());

        for (Performer performer : PerformerService.getInstance().getPerformers()) {

            JLabel performerLabel = new JLabel(performer.toString());
            performerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            performerLabel.setForeground(Color.WHITE);

            performerListPanel.add(performerLabel);
            performerListPanel.add(Box.createVerticalStrut(5));
        }

        // Adaugam scroll la panel
        JScrollPane scrollPane = new JScrollPane(performerListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());

        // Adaugam panel-ul de scroll in panel-ul cu artisti
        add(scrollPane);

        // Afisam din nou panel-ul
        revalidate();
        repaint();
    }
}
