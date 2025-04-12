package ui;

import javax.swing.*;

public class UILauncher {
    private static final UILauncher instance = new UILauncher();
    private MainFrame mainFrame;
    private boolean init = false;

    private UILauncher() {
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame();
            init = true;
        });
    }

    public static UILauncher getInstance() {
        return instance;
    }

    public MainFrame getMainFrame() {
        while (!init) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return mainFrame;
    }
}
