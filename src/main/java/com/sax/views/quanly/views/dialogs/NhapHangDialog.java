package com.sax.views.quanly.views.dialogs;

import com.sax.dtos.LichSuNhapHangDTO;
import com.sax.dtos.SachDTO;
import com.sax.services.ILichSuNhapHangService;
import com.sax.services.ISachService;
import com.sax.utils.ContextUtils;
import com.sax.utils.CurrencyConverter;
import com.sax.utils.MsgBox;
import com.sax.views.components.Search;
import com.sax.views.components.libraries.ButtonToolItem;
import com.sax.views.components.libraries.RoundPanel;
import com.sax.views.components.table.CellNameRender;
import com.sax.views.components.table.CustomHeaderTableCellRenderer;
import com.sax.views.components.table.CustomTableCellEditor;
import com.sax.views.components.table.CustomTableCellRender;
import com.sax.views.quanly.viewmodel.SachViewObject;
import com.sax.views.quanly.views.panes.SanPhamPane;
import lombok.Getter;
import lombok.Setter;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NhapHangDialog extends JDialog {
    private JPanel contentPane;
    private JFormattedTextField txtGiaNhap;
    private JXDatePicker dateNgayNhap;
    private JTextField txtSL;
    private JButton btnSave;
    private JPanel donHangPanel;
    private JPanel background;
    private JPanel bg;
    private JXTable tbl;
    private Search timKiem;
    private JCheckBox cbkSelectedAll;
    private JButton btnEdit;
    private ILichSuNhapHangService lichSuNhapHangService = ContextUtils.getBean(ILichSuNhapHangService.class);
    private ISachService sachService = ContextUtils.getBean(ISachService.class);

    public int id;
    public SanPhamPane parentPane;

    public NhapHangDialog() {
        btnSave.addActionListener(e -> save());
        initComponent();
    }

    private void initComponent() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        txtGiaNhap.setFormatterFactory(CurrencyConverter.getVnCurrency());
    }

    public void fillTable() {
        ((DefaultTableModel) tbl.getModel()).setDataVector(
                lichSuNhapHangService.getAllByIdSach(id).stream().map(i -> new Object[]{"", i.getNgayNhap(), i.getSoLuong(), CurrencyConverter.parseString(i.getGiaNhap())}).toArray(Object[][]::new),
                new String[]{"STT", "Ngày nhập sách", "Số lượng", "Giá nhập"}
        );
        tbl.setDefaultEditor(Object.class, null);
        tbl.getTableHeader().setDefaultRenderer(new CustomHeaderTableCellRenderer());
        tbl.getTableHeader().setEnabled(false);
        tbl.getTableHeader().setPreferredSize(new Dimension(-1, 28));
//        tbl.getColumnModel().getColumn(0).setCellEditor(new CustomTableCellEditor(list));
        tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel p = new JPanel();
                p.setLayout(new GridLayout());
                p.setBackground(new Color(0, 0, 0, 0));
                JLabel l = (value == null) ? new JLabel("") : new JLabel("  " + value + "  ");
                if (column == 0) {
                    l = new JLabel(String.valueOf(row + 1));
                    l.setHorizontalAlignment(SwingConstants.CENTER);
                }
                l.setFont(new Font(".SF NS Text", 4, 13));
                l.setForeground(Color.decode("#727272"));
                p.add(l);
                if (isSelected) {
                    if (column == 0)
                        p.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.decode("#EA6C20")));
                    else if (column == table.getColumnCount() - 1)
                        p.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.decode("#EA6C20")));
                    else p.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.decode("#EA6C20")));
                }
                return p;
            }
        });
        tbl.packAll();
    }

    private void save() {
        LichSuNhapHangDTO lichSuNH = readForm();
        if (lichSuNH != null) {
            try {
                SachDTO sachDTO = sachService.getById(id);
                sachDTO.setSoLuong(sachDTO.getSoLuong() + lichSuNH.getSoLuong());
                sachService.update(sachDTO);

                lichSuNH.setSach(sachDTO);
                lichSuNhapHangService.insert(lichSuNH);
                parentPane.fillTable(sachService.getPage(parentPane.getPageable()).stream().map(SachViewObject::new).collect(Collectors.toList()));
                dispose();
            } catch (Exception ex) {
                MsgBox.alert(this, ex.getMessage());
            }
        }
    }

    public LichSuNhapHangDTO readForm() {
        LichSuNhapHangDTO lichSuNhapHangDTO = new LichSuNhapHangDTO();

        Date ngayNhap = dateNgayNhap.getDate();
        if (ngayNhap == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày nhập hàng");
            return null;
        }

        String nhaNhap = txtGiaNhap.getText().trim();
        try {
            lichSuNhapHangDTO.setGiaNhap(CurrencyConverter.parseLong(nhaNhap));
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Số lượng phải là số!");
            return null;
        }

        String soLuong = txtSL.getText().trim();
        try {
            lichSuNhapHangDTO.setSoLuong(Integer.parseInt(soLuong));
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Số lượng phải là số!");
            return null;
        }

        lichSuNhapHangDTO.setNgayNhap(ngayNhap.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return lichSuNhapHangDTO;
    }

    private void createUIComponents() {
        bg = new RoundPanel(10);
        background = new RoundPanel(10);
        btnEdit = new ButtonToolItem("pencil.png", "pencil.png");
    }
}
