package frontend.panels.main;

import frontend.Colors;
import frontend.Header;
import frontend.frames.ProfileFrame;
import backend.User;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsersPanel extends JPanel {
    private Header header;
    private JLabel infoLabel;
    private JButton addUserButton;


    public UsersPanel() {
        // Initializare
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cream header-ul pentru panel-ul cu utilizatori
        header = new Header("Users", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoLabel = new JLabel("Click on a user's name to open their profile.");
        infoLabel.setForeground(Colors.PRIMARY.getColor());
        infoLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 13));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cream butonul de ADD
        addUserButton = new JButton("Add New User");
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
                        null, panel, "Add New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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
                            UserService.getInstance().addUser(user);
                            update();
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

        // Adaugam lista de utilizatori in panel
        update();
    }

    public void update() {
        // Stergem content-ul din panel-ul invechit
        removeAll();

        // Adaugam header-ul si spacing-ul
        add(header);
        add(Box.createVerticalStrut(10));

        add(infoLabel);
        add(Box.createVerticalStrut(10));

        // Adaugam butonul de ADD
        add(addUserButton);
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
