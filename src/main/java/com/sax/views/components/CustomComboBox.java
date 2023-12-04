package com.sax.views.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JComboBox {

    public CustomComboBox(int radius) {
        putClientProperty(FlatClientProperties.STYLE, "buttonBackground:#00000000");
        setBackground(new Color(0,0,0,0));
        setBorder(new FlatLineBorder(new Insets(4, 4, 4, 4), Color.decode("#D7DAE3"), 1, radius));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
