package com.sax.views.nhanvien.cart.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import com.sax.views.nhanvien.cart.scroll.ScrollBarCustomUI;

public class TableCustom {

    public static void apply(JScrollPane scroll) {
        JTable table = (JTable) scroll.getViewport().getComponent(0);
        table.getTableHeader().setReorderingAllowed(false);
//        table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(table));
//        table.setRowHeight(80);
        table.setDefaultRenderer(Object.class, new TextAreaCellRenderer());
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setForeground(new Color(51, 51, 51));
        table.setSelectionForeground(new Color(51, 51, 51));
        scroll.setBorder(new LineBorder(new Color(220, 220, 220)));
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                grphcs.setColor(new Color(220, 220, 220));
                grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                grphcs.dispose();
            }
        };
        panel.setBackground(new Color(250, 250, 250));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUI(new ScrollBarCustomUI());
        scroll.getHorizontalScrollBar().setUI(new ScrollBarCustomUI());
    }

    public static enum TableType {
        MULTI_LINE, DEFAULT
    }
}
