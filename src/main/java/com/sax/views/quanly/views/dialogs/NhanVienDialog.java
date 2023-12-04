package com.sax.views.quanly.views.dialogs;

import com.sax.Application;
import com.sax.dtos.AccountDTO;
import com.sax.services.IAccountService;
import com.sax.services.impl.AccountService;
import com.sax.utils.*;
import com.sax.views.components.libraries.ButtonToolItem;
import com.sax.views.quanly.viewmodel.NhanVienViewObject;
import com.sax.views.quanly.views.panes.NhanVienPane;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NhanVienDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtSdt;
    private JTextField txtEmail;
    private JButton btnSave;
    private JPanel contentPane;
    private JRadioButton rdoNu;
    private JRadioButton rdoNam;
    private JRadioButton rdoNhanVien;
    private JRadioButton rdoQuanLy;
    private JButton btnImg;
    private IAccountService accountService = ContextUtils.getBean(AccountService.class);
    private JPanel pnImage;
    private String image;
    private JRadioButton rdoDL;
    private JRadioButton rdoDN;

    @Getter
    @Setter
    private JPanel panelRole;

    private @Setter JLabel lblTenView;
    private @Setter JPanel avatar;

    public JLabel lblTitle;
    public int id;
    public NhanVienPane parentPane;
    public Pageable pageable;

    public NhanVienDialog() {
        initComponent();

        btnSave.addActionListener((e) -> update());
        btnImg.addActionListener(e -> image = ImageUtils.openImageFile(pnImage));
    }

    private void initComponent() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(parentPane);
    }

    public void fillForm() {
        if (id > 0) {
            AccountDTO accountDTO = accountService.getById(id);
            txtEmail.setText(accountDTO.getEmail());
            txtName.setText(accountDTO.getTenNhanVien());
            txtSdt.setText(accountDTO.getSdt());
            if (accountDTO.isVaiTro()) rdoQuanLy.setSelected(true);
            else rdoNhanVien.setSelected(true);
            if (accountDTO.isGioiTinh()) rdoNam.setSelected(true);
            else rdoNu.setSelected(true);
            image = "images/" + accountDTO.getAnh();
            pnImage.add(ImageUtils.getCircleImage(accountDTO.getAnh(), 200, 20, null, 0));
        }
    }

    private void update() {
        AccountDTO dto = readForm();
        if (dto != null) {
            try {
                dto.setId(id);
                accountService.update(dto);
                if (Session.accountid.getId() == id)
                {
                    AccountDTO ac = accountService.getById(id);
                    lblTenView.setText(ac.getTenNhanVien());
                    avatar.removeAll();
                    avatar.add(ImageUtils.getCircleImage(ac.getAnh(), 30,20,null,0));
                    avatar.revalidate();
                }
                if (parentPane != null)
                    parentPane.fillTable(accountService.getPage(pageable).stream().map(NhanVienViewObject::new).collect(Collectors.toList()));
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                MsgBox.alert(this, "Có lỗi! " + ex.getMessage());
            }
        }
    }

    private AccountDTO readForm() {
        AccountDTO accountDTO = new AccountDTO();
        String ten = txtName.getText().trim();
        accountDTO.setTenNhanVien(ten);
        String sdt = txtSdt.getText().trim();
        accountDTO.setSdt(sdt);
        String email = txtEmail.getText().trim();
        accountDTO.setEmail(email);
        boolean gioiTinh = rdoNam.isSelected() ? true : false;
        accountDTO.setGioiTinh(gioiTinh);
        boolean vaiTro = rdoQuanLy.isSelected() ? true : false;
        accountDTO.setVaiTro(vaiTro);
        boolean trangThai = rdoDL.isSelected() ? true : false;
        accountDTO.setTrangThai(trangThai);
        accountDTO.setAnh(image);

        return accountDTO;
    }

    private void createUIComponents() {
        btnImg = new ButtonToolItem("image-c.svg", "image-c.svg");
    }
}
