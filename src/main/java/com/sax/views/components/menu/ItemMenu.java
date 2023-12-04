package com.sax.views.components.menu;

import com.sax.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class ItemMenu extends JButton {

    public ItemMenu(MenuModel menuModel) {
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(new Font(".SF NS Text", 4, 14));
        setText(menuModel.getName());
        setIcon(ImageUtils.readSVG(menuModel.getIcon()));
        setForeground(Color.decode("#ffffff"));
        setBackground(Color.decode("#EA6C20"));
        setBorderPainted(false);
        setIconTextGap(10);
        setMargin(new Insets(0,5,0,5));
    }
}
