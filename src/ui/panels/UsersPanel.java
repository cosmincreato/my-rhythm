package ui.panels;

import music.PerformerService;
import ui.Colors;
import ui.Header;
import ui.frames.ProfileFrame;
import users.User;
import users.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsersPanel extends JPanel {
    private UserService userService;
    private PerformerService performerService;
    private Header header;

    public UsersPanel(UserService userService, PerformerService performerService) {
        // Initializare
        this.userService = userService;
        this.performerService = performerService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu utilizatori
        header = new Header("Users", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adaugam header-ul in panel
        add(header);
        add(Box.createVerticalStrut(10));
    }

    public void update() {
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Readaugam header-ul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Cream un panel pentru lista de utilizatori actualizata
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Colors.BACKGROUND.getColor());

        for (User user : userService.getUsers()) {
            JLabel userLabel = new JLabel(user.toString());
            userLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            userLabel.setForeground(Color.WHITE);

            userLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new ProfileFrame(user.getId(), userService, performerService,  () -> {update();});
                }
            });

            userListPanel.add(userLabel);
            userListPanel.add(Box.createVerticalStrut(5));
        }

        // Adaugam scroll la panel
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());

        // Adaugam panel-ul de scroll in panel-ul cu utilizatori
        add(scrollPane);

        // Afisam din nou panel-ul
        revalidate();
        repaint();
    }
}
