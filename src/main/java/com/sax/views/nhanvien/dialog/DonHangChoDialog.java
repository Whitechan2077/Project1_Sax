package com.sax.views.nhanvien.dialog;

import com.sax.Application;
import com.sax.dtos.DonHangDTO;
import com.sax.utils.Cart;
import com.sax.utils.Session;
import com.sax.views.components.ListPageNumber;
import com.sax.views.components.Loading;
import com.sax.views.components.Search;
import com.sax.views.components.libraries.ButtonToolItem;
import com.sax.views.components.libraries.RoundPanel;
import com.sax.views.components.table.CustomHeaderTableCellRenderer;
import com.sax.views.components.table.CustomTableCellEditor;
import com.sax.views.components.table.CustomTableCellRender;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import com.sax.views.quanly.viewmodel.DonHangViewObject;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DonHangChoDialog extends JDialog {
    private JXTable table;
    private JPanel donHangChoPane;
    private JPanel bg;
    private JPanel phanTrangPane;
    private JComboBox cboHienThi;
    private JList listPage;
    private Search timKiem;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdit;
    private Set tempIdSet = new HashSet();
    private List<JCheckBox> listCbk = new ArrayList<>();

    public DonHangChoDialog() {
        initComponent();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                }
            }
        });
    }

    private void initComponent()
    {
        setContentPane(donHangChoPane);
        setModal(true);
        setLocationRelativeTo(Application.app);

        ((DefaultTableModel) table.getModel()).setColumnIdentifiers(new String[]{"", "ID", "Tên khách hàng", "Tiền hàng", "Chiết khấu", "Tổng tiền", "Phương thức thanh toán"});
        fillTable(Session.listDonCho.stream().map(DonHangViewObject::new).collect(Collectors.toList()));
    }

    private void fillTable(List<AbstractViewObject> list)
    {
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        list.forEach(i -> ((DefaultTableModel) table.getModel()).addRow(i.toObject()));
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setDefaultRenderer(new CustomHeaderTableCellRenderer());
        table.getTableHeader().setEnabled(false);
        table.getTableHeader().setPreferredSize(new Dimension(-1, 28));
        table.getColumnModel().getColumn(0).setCellEditor(new CustomTableCellEditor(list));
        table.setDefaultRenderer(Object.class, new CustomTableCellRender(list));
        table.packAll();
        pack();
        table.getColumns().forEach(TableColumn::sizeWidthToFit);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void sentDonTamToDonHang() {
        if (table.getRowCount() >= 0) {
            int id = (int) table.getValueAt(table.getRowCount(), 1);
            DonHangDTO donHangDTO = Session.listDonCho.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
            if (donHangDTO != null) {

            }
        }
    }

    private void createUIComponents() {
        bg = new RoundPanel(10);
        btnAdd = new ButtonToolItem("add.svg", "add.svg");
        btnDel = new ButtonToolItem("trash-c.svg", "trash-c.svg");
        btnEdit = new ButtonToolItem("pencil.svg", "pencil.svg");

        listPage = new ListPageNumber();
    }
}
