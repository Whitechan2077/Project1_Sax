package com.sax.views.components;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTextArea extends JTextArea {
    public CustomTextArea() {
        setBorder(new FlatLineBorder(new Insets(5, 8, 5, 8), Color.decode("#d7dae3"), 1, 10));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new FlatLineBorder(new Insets(5, 8, 5, 8), Color.decode("#ea6c20"), 2, 10));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new FlatLineBorder(new Insets(5, 8, 5, 8), Color.decode("#d7dae3"), 1, 10));
            }
        });
    }
}
