package com.sax.views.nhanvien.doncho;

import com.sax.dtos.AccountDTO;
import com.sax.views.components.table.CellNameRender;
import com.sax.views.nhanvien.cart.CartModel;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import lombok.Data;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Data
public class DonChoView {
    private JCheckBox chkDelete;
    private int id;
    private String tenKhachHang;
    private CartModel cartModel;

    public DonChoView(int id, String tenKhachHang, CartModel cartModel) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.cartModel = cartModel;
    }

    public Object[] toObject() {
        return new Object[]{chkDelete, id, tenKhachHang, cartModel};
    }
}
