package com.sax.views.components.menu;

import com.sax.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class CustomMenu extends JList<MenuModel> {
    private DefaultListModel listModel;

    public CustomMenu() {
        listModel = new DefaultListModel();
        setModel(listModel);
        listModel.addElement(new MenuModel("sp.svg", "sp-c.svg", "Sản phẩm"));
        listModel.addElement(new MenuModel("danhmuc.svg", "danhmuc-c.svg", "Danh mục"));
        listModel.addElement(new MenuModel("donhang.svg", "donhang-c.svg", "Đơn hàng"));
        listModel.addElement(new MenuModel("ctkm.svg", "ctkm-c.svg", "Khuyến mại"));
        listModel.addElement(new MenuModel("khachhang.svg", "khachhang-c.svg", "Khách hàng"));
        listModel.addElement(new MenuModel("tknv.svg", "tknv-c.svg", "TK nhân viên"));
        listModel.addElement(new MenuModel("thongke.svg", "thongke-c.svg", "Thống kê"));
        setSelectedIndex(0);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                MenuModel menuModel = (MenuModel) value;
                ItemMenu itemMenu = new ItemMenu(menuModel);
                if (isSelected) {
                    itemMenu.setIcon(ImageUtils.readSVG(menuModel.getIconContrast()));
                    itemMenu.setForeground(Color.decode("#EA6C20"));
                    itemMenu.setBackground(Color.decode("#f2f2f2"));
                }
                return itemMenu;
            }
        });
    }


}
