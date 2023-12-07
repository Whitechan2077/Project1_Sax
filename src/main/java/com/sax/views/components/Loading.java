package com.sax.views.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.sax.Application;
import com.sax.utils.ImageUtils;
import com.sax.views.components.libraries.PanelShadow;
import com.sax.views.components.libraries.RoundPanel;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Loading extends JDialog {
    private JPanel okee;
    private JPanel contentPane;

    public Loading(Component parent) {
        okee.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), Color.decode("#a7a7a7"), 1, 10));
        setUndecorated(true);
        getRootPane().setBackground(new Color(0, 0, 0, 0));
        getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
        setBackground(new Color(0, 0, 0, 0));
        getRootPane().setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(parent);
        setAlwaysOnTop(true);
    }

    private void createUIComponents() {
        contentPane = new PanelShadow(10);
    }
}
