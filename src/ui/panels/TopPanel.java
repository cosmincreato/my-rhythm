package ui.panels;

import ui.Colors;
import ui.Header;
import ui.UserAddedListener;
import users.User;
import users.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel {
    public UserAddedListener listener;
    private UserService userService;
    private Header header;
    private JButton addUserButton;

    public TopPanel(UserAddedListener listener, UserService userService) {
        // Initializare
        this.listener = listener;
        this.userService = userService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cram header-ul
        header = new Header("MyRhythm", 40);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cream butonul de ADD
        addUserButton = new JButton("Add User");
        addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Atunci cand apasam pe butoane, trebuie sa actualizam UsersPanel
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User("Test", "123");
                userService.addUser(user);
                // Trimitem mai departe informatia ca sa actualizam UI
                listener.onUserAdded();
            }
        });

        // Adaugam header-ul
        add(header);
        add(Box.createVerticalStrut(10));

        // Adaugam butonul de ADD
        add(addUserButton);
        add(Box.createVerticalStrut(10));
    }
}
