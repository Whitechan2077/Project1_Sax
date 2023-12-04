package com.sax.views.components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomHeaderTableCellRenderer<T> extends DefaultTableCellRenderer {
    private JLabel l;
    private JLabel icon;
    private int indexSort;

    public CustomHeaderTableCellRenderer() {
        this.indexSort = indexSort;
        setHorizontalAlignment(JLabel.LEADING);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(247, 247, 247)));
        l = new JLabel();
        l.setBackground(new Color(0, 0, 0, 0));
        l.setText("  " + value.toString() + "  ");
        l.setFont(new Font(".SF NS Text", 1, 13));
        l.setForeground(Color.decode("#727272"));
        l.setAlignmentY(Component.TOP_ALIGNMENT);
        p.add(l);
        if (indexSort == column) {
            icon = new JLabel();
            icon.setText("  ");
            icon.setAlignmentY(Component.TOP_ALIGNMENT);
            p.add(icon);
        }
        return p;
    }
}
