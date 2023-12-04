package com.sax.views.components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ListPageNumber extends JList<Object> {

    public ListPageNumber() {
//        setLayoutOrientation(JList.HORIZONTAL_WRAP);
        setVisibleRowCount(1);
        setFixedCellWidth(24);
        setFixedCellHeight(24);

        setCellRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JPanel p = new JPanel();
                JLabel l = new JLabel();
                p.setLayout(new GridBagLayout());
                p.setBorder(new EmptyBorder(2, 2, 2, 2));
                p.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
                if (isSelected && !value.toString().equals("...")) {
                    p.setBackground(Color.decode("#EA6C20"));
                    l.setForeground(Color.WHITE);
                } else {
                    p.setOpaque(false);
                    l.setForeground(Color.decode("#727272"));
                }
                l.setText(value.toString());
                l.setFont(new Font(".SF NS Text", 4,12));
                p.add(l);

                return p;
            }
        });
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
