package frontend.panels.profile;

import frontend.Colors;
import frontend.Header;
import frontend.UserRemovedListener;
import backend.User;
import backend.UserNotFoundException;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePanel extends JPanel {
    public UserRemovedListener listener;
    private Header header;
    private JButton removeUserButton;
    private User user;

    public ProfilePanel(UserRemovedListener listener, User user) {
        this.listener = listener;
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        // Cram header-ul
        header = new Header(user.getUsername(), 40);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Cream butonul de DELETE
        removeUserButton = new JButton("Remove User");
        removeUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserService.getInstance().removeUser(user);
                }
                catch (UserNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                // Trimitem mai departe informatia ca sa actualizam UI
                listener.onUserRemoved();
            }
        });

        // Adaugam header-ul
        add(header);
        add(Box.createVerticalStrut(10));


        // Adaugam butonul de DELETE
        add(removeUserButton);
        add(Box.createVerticalStrut(20));
    }
}
