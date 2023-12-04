package com.sax.views.quanly.views.dialogs;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.services.IAccountService;
import com.sax.services.impl.AccountService;
import com.sax.utils.ContextUtils;
import com.sax.utils.HashUtils;
import com.sax.utils.MsgBox;
import com.sax.views.quanly.viewmodel.NhanVienViewObject;
import com.sax.views.quanly.views.panes.NhanVienPane;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.util.stream.Collectors;

public class TaiKhoanDialog extends JDialog {
    private JTextField txtTK;
    private JPasswordField txtMK1;
    private JLabel lblEmail;
    private JButton btnSave;
    private JPasswordField txtMK2;
    private JPanel contentPane;
    private IAccountService accountService = ContextUtils.getBean(AccountService.class);

    public JLabel lblTitle;
    public NhanVienPane nhanVienPane;
    public Pageable pageable;
    public int id;

    public TaiKhoanDialog() {
        initComponent();
        btnSave.addActionListener((e) -> add());
    }

    private void initComponent() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(nhanVienPane);
    }

    public void fillForm()
    {
        AccountDTO accountDTO = accountService.getById(id);
        txtTK.setText(accountDTO.getUsername());
        txtMK1.setText(accountDTO.getPassword());
        txtMK2.setText(accountDTO.getPassword());
        pack();
    }

    private void add() {
        AccountDTO account = readForm();
        if (account != null) {
            try {
                if (id > 0)
                {
                    account.setId(id);
                    accountService.updateUsernamePassword(account);
                }
                else {
                    accountService.createAccount(account);
                    nhanVienPane.fillListPage(pageable.getPageNumber());
                }
                nhanVienPane.fillTable(accountService.getPage(pageable).stream().map(NhanVienViewObject::new).collect(Collectors.toList()));
                dispose();
            } catch (DataIntegrityViolationException e) {
                MsgBox.alert(nhanVienPane, "Có lỗi: " + e.getMessage());
            }
        }
    }

    private AccountDTO readForm() {
        AccountDTO accountDTO = new AccountDTO();
        try {
            String taiKhoan = txtTK.getText().trim();

            if (taiKhoan.isEmpty()) {
                JOptionPane.showMessageDialog(nhanVienPane, "Tài khoản không được để trống!");
                return null;
            }
            if (taiKhoan.length() < 6) {
                JOptionPane.showMessageDialog(nhanVienPane, "Tài khoản phải it nhất 6 ký tự!");
                return null;
            }
            accountDTO.setUsername(taiKhoan);

            String mk1 = new String(txtMK1.getText().trim());
            if (mk1.isEmpty()) {
                JOptionPane.showMessageDialog(nhanVienPane, "Mật khẩu không được để trống!");
                return null;
            }

            String mk2 = new String(txtMK2.getText().trim());
            if (mk2.isEmpty()) {
                JOptionPane.showMessageDialog(nhanVienPane, "Nhập lại mật khẩu không được để trống!");
                return null;
            }
            if (!mk1.equals(mk2)) {
                JOptionPane.showMessageDialog(nhanVienPane, "Nhập lại mật khẩu không khớp với mật khẩu!");
                return null;
            }
            accountDTO.setPassword(mk1);
            return accountDTO;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(nhanVienPane, e.getMessage());
            return null;
        }
    }
}
