package com.sax.views.nhanvien.dialog.hoadon.table;

import org.jdesktop.swingx.JXTextArea;

import java.awt.Color;
import java.awt.Component;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class TextAreaCellRenderer extends JXTextArea implements TableCellRenderer {
    private int rowHeight;

    public TextAreaCellRenderer() {
        setColumns(20);
        setWrapStyleWord(true);
        setLineWrap(true);
        setOpaque(true);
//        setBorder(new EmptyBorder(8, 10, 8, 10));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (column != 0) {
            JLabel l = new JLabel(value.toString());
            l.setBorder(new EmptyBorder(0, 10, 0, 10));
            return l;
        }
        setText(Objects.toString(value, ""));
        setFont(table.getFont());
        int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
        int textLength = this.getText().length();
        int lines = textLength / this.getColumnWidth();
        if (lines == 0) {
            lines = 1;
        }
        int height = fontHeight * lines;
        table.setRowHeight(row, height + 8);
        return this;
    }

}