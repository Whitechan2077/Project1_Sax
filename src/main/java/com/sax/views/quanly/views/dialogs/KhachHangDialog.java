package com.sax.views.quanly.views.dialogs;

import com.sax.dtos.DanhMucDTO;
import com.sax.dtos.KhachHangDTO;
import com.sax.dtos.SachDTO;
import com.sax.services.IKhachHangService;
import com.sax.services.impl.KhachHangService;
import com.sax.utils.ContextUtils;
import com.sax.utils.ImageUtils;
import com.sax.utils.MsgBox;
import com.sax.views.quanly.viewmodel.KhachHangViewObject;
import com.sax.views.quanly.viewmodel.SachViewObject;
import com.sax.views.quanly.views.panes.KhachHangPane;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KhachHangDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtSdt;
    private JTextField txtDiem;
    private JButton btnSave;
    private JPanel contentPanel;
    private JRadioButton rdoNu;
    private JRadioButton rdoNam;
    private IKhachHangService khachHangService;

    public JLabel lblTitle;
    public int id;
    public KhachHangPane parentPane;
    public Pageable pageable;

    public KhachHangDialog() {
        initComponent();

        btnSave.addActionListener((e) -> save());
    }

    private void initComponent() {
        setContentPane(contentPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        khachHangService = ContextUtils.getBean(KhachHangService.class);
    }

    public void fillForm() {
        if (id > 0) {
            KhachHangDTO dto = khachHangService.getById(id);
            lblTitle.setText("Cập nhật khách hàng " + dto.getId() + " - " + dto.getTenKhach());
            txtName.setText(dto.getTenKhach());
            txtSdt.setText(dto.getSdt());
            txtDiem.setText(String.valueOf(dto.getDiem()));
            if (dto.isGioiTinh()) rdoNam.setSelected(true);
            else rdoNu.setSelected(true);
        }
    }

    private void save() {
        KhachHangDTO dto = readForm();
        if (dto != null) {
            try {
                if (id > 0) {
                    dto.setId(id);
                    dto.setNgayThem( khachHangService.getById(id).getNgayThem());
                    khachHangService.update(dto);
                } else khachHangService.insert(dto);
                parentPane.fillTable(khachHangService.getPage(pageable).stream().map(KhachHangViewObject::new).collect(Collectors.toList()));
                dispose();
            } catch (Exception ex) {
                MsgBox.alert(this, "Có lỗi! " + ex.getMessage());
            }
        }
    }

    private KhachHangDTO readForm() {
        KhachHangDTO khachHangDTO = new KhachHangDTO();

        String ten = txtName.getText().trim();
        khachHangDTO.setTenKhach(ten);

        String sdt = txtSdt.getText().trim();
        try {
            Integer.parseInt(sdt);
        }
        catch (NumberFormatException ex)
        {
            MsgBox.alert(this, "Số điện thoại phải là số");
            return null;
        }
        khachHangDTO.setSdt(sdt);

        String diem = txtDiem.getText().trim();
        try {
            khachHangDTO.setDiem(Integer.parseInt(diem));
        } catch (NumberFormatException ex) {
            MsgBox.alert(this, "Điểm bán phải là số!");
        }
        boolean gt = (rdoNam.isSelected()) ? true : false;
        khachHangDTO.setGioiTinh(gt);
        return khachHangDTO;
    }

}
