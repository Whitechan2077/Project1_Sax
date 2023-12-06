package com.sax.utils;

import com.jgoodies.common.collect.ArrayListModel;
import com.sax.views.nhanvien.cart.CartModel;

import javax.swing.*;
import java.util.List;

public class Cart {
    private static List<CartModel> list;

    static {
        list = new ArrayListModel<>();
    }

    public static List<CartModel> getCart() {
        return list;
    }

    public static void tinhTien(JTable table, JLabel lblTienHang, JLabel lblChietKhau, JLabel lblTPT, JCheckBox chkDiem) {
        if (table.getRowCount() == 0) {
            lblTienHang.setText("0đ");
            lblChietKhau.setText("0đ");
            lblTPT.setText("0đ");
        } else {
            long tienHang = 0;
            for (int i = 0; i < table.getRowCount(); i++) {
                long donGia = Long.parseLong(table.getValueAt(i, 1).toString());
                int soLuong = Integer.parseInt(((JSpinner) table.getValueAt(i, 2)).getValue().toString());
                tienHang += donGia * soLuong;
            }
            lblTienHang.setText(CurrencyConverter.parseString(tienHang));
            if (chkDiem.isSelected()) {
                lblChietKhau.setText("-" + CurrencyConverter.parseString(Integer.valueOf(chkDiem.getText()) * 1000));
            } else {
                lblChietKhau.setText("-" + CurrencyConverter.parseString(0));
            }
            long km = CurrencyConverter.parseLong(lblChietKhau.getText().replace("-", ""));
            lblTPT.setText(CurrencyConverter.parseString(tienHang - km));
            if (tienHang < km) {
                lblChietKhau.setText("-" + CurrencyConverter.parseString(tienHang));
                lblTPT.setText("0đ");

            }
//            chkDiem.setText(
//                    (Integer.parseInt(chkDiem.getText()) - CurrencyConvert.parseLong(lblChietKhau.getText())) / 1000 + ""
//            );
        }
    }
}
