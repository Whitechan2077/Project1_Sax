package com.sax.views.nhanvien.dialog;

import com.sax.Application;
import com.sax.views.quanly.views.panes.DonHangPane;

import javax.swing.*;
import java.awt.event.*;

public class KhachHangNVDialog extends JDialog {
    private JPanel contentPane;

    public KhachHangNVDialog() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(Application.app);
    }
}
