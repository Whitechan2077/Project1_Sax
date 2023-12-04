package com.sax.views.components.table;


import com.sax.dtos.AbstractDTO;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import com.sax.views.quanly.viewmodel.SachViewObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomTableCellEditor extends DefaultCellEditor {
    private List<AbstractViewObject> list;

    public CustomTableCellEditor(List<AbstractViewObject> list) {
        super(new JCheckBox());
        this.list = list;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JPanel p = new JPanel();
        JCheckBox c = list.get(row).getCheckBoxDelete();
        p.setLayout(new GridBagLayout());
        p.add(c);
        p.setBackground(Color.decode("#F6FAFF"));
        p.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.decode("#EA6C20")));
        return p;
    }

}
