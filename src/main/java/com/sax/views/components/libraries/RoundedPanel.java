package com.sax.views.components.libraries;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    public RoundedPanel(int radius, Color color, int thickness) {
        putClientProperty(FlatClientProperties.STYLE, "arc:" + radius);
        setBorder(new FlatLineBorder(
                new Insets(0, 0, 0, 0),
                color,
                thickness,
                radius));
    }
}
