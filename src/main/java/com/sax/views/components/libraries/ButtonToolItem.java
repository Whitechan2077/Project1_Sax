package com.sax.views.components.libraries;

import com.sax.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonToolItem extends JButton {

    public ButtonToolItem(String icon, String iconContrast) {
        setIcon(ImageUtils.readSVG(icon));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(ImageUtils.readSVG(iconContrast));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(ImageUtils.readSVG(icon));
            }
        });
    }
}
