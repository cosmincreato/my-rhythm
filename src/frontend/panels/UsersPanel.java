package frontend.panels;

import backend.services.PerformerService;
import frontend.Colors;
import frontend.Header;
import frontend.frames.ProfileFrame;
import backend.User;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsersPanel extends JPanel {
    private Header header;

    public UsersPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu utilizatori
        header = new Header("Users", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adaugam lista de utilizatori in panel
        update();
    }

    public void update() {
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Adaugam header-ul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Cream un panel pentru lista de utilizatori actualizata
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Colors.BACKGROUND.getColor());

        for (User user : UserService.getInstance().getUsers()) {
            JLabel userLabel = new JLabel(user.toString());
            userLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            userLabel.setForeground(Color.WHITE);

            userLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new ProfileFrame(user.getId(),  () -> {update();});
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
