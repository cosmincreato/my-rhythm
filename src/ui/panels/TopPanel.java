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
        addUserButton = new JButton("Create New User");
        addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Atunci cand apasam pe butoane, trebuie sa actualizam UsersPanel
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameField = new JTextField();
                JTextField passwordField = new JPasswordField();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Create New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText().trim();
                    String password = passwordField.getText();

                    if (!username.isEmpty() && !password.isEmpty()) {
                        boolean strong = false;
                        char[] pass = password.toCharArray();
                        for (char character : pass)
                            if (Character.isDigit(character))
                                strong = true;
                        if (password.length() < 8)
                            strong = false;
                        if (strong) {
                            User user = new User(username, password);
                            userService.addUser(user);
                            listener.onUserAdded(); // update the UI
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Password should contain at least one digit and be at least 8 characters long.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username and password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
