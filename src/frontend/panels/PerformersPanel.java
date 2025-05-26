package frontend.panels;

import backend.Performer;
import backend.services.PerformerService;
import frontend.Colors;
import frontend.Header;
import backend.User;
import backend.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Set;

public class PerformersPanel extends JPanel {
    private JButton addPerformerButton;
    private Header header;
    private User user;

    public PerformersPanel(User user) {
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.BACKGROUND.getColor());

        header = new Header("Favorite Artists", 20);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        addPerformerButton = new JButton("Add Favorite Artist");
        addPerformerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPerformerButton.addActionListener(this::onAddPerformerClicked);

        update();
    }

    private void onAddPerformerClicked(ActionEvent e) {
        ArrayList<Performer> performers = PerformerService.getInstance().getPerformers();

        Performer selected = (Performer) JOptionPane.showInputDialog(
                this,
                "Select an Artist:",
                "Choose Artist",
                JOptionPane.PLAIN_MESSAGE,
                null,
                performers.toArray(),
                performers.get(0)
        );

        if (selected != null) {
            user.addFavoritePerformer(selected);
            update();
        }
    }

        public void update() {
            Set<Performer> favoritePerformers = user.getFavoritePerformers();
            // Stergem content-ul din panel-ul invechit
            removeAll();

            // Readaugam header-ul, butonul si spacing-ul
            add(header);
            add(Box.createVerticalStrut(10));
            add(addPerformerButton);

            // Cream un panel pentru lista de artisti actualizata
            JPanel performerLabelPanel = new JPanel();
            performerLabelPanel.setLayout(new BoxLayout(performerLabelPanel, BoxLayout.Y_AXIS));
            performerLabelPanel.setBackground(Colors.BACKGROUND.getColor());

            for (Performer performer : favoritePerformers) {
                JLabel performerLabel = new JLabel(performer.toString());
                performerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
                performerLabel.setForeground(Color.WHITE);

                performerLabelPanel.add(performerLabel);
                performerLabelPanel.add(Box.createVerticalStrut(5));
            }

            // Adaugam scroll la panel
            JScrollPane scrollPane = new JScrollPane(performerLabelPanel);
            scrollPane.setBorder(null);
            scrollPane.getViewport().setBackground(Colors.BACKGROUND.getColor());
            add(scrollPane);

            // Afisam din nou panel-ul
            revalidate();
            repaint();
        }
}
