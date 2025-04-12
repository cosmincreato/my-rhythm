package ui;

import java.awt.*;

public enum Colors {
    BACKGROUND(new Color(12,11,14)),
    PRIMARY(new Color(142,125,190));

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
