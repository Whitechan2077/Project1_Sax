package com.sax.views.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.sax.utils.ImageUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Search extends JPanel {
    private JPanel p;
    private JLabel lblIcon;
    public JTextField txtSearch;

    public Search() {
        lblIcon.setIcon(ImageUtils.readSVG("search.svg"));
        p.setBorder(new FlatLineBorder(new Insets(0, 10, 0, 10), Color.decode("#D7DAE3"), 1, 10));
        txtSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khoá...");
    }


    private void createUIComponents() {
        p = this;
    }
}
