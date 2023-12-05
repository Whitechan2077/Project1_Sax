package com.sax.views.quanly.views.panes;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.services.IKhachHangService;
import com.sax.services.impl.KhachHangService;
import com.sax.utils.ContextUtils;
import com.sax.utils.MsgBox;
import com.sax.utils.Session;
import com.sax.views.components.ListPageNumber;
import com.sax.views.components.Loading;
import com.sax.views.components.Search;
import com.sax.views.quanly.viewmodel.KhachHangViewObject;
import com.sax.views.quanly.viewmodel.SachViewObject;
import com.sax.views.quanly.views.dialogs.KhachHangDialog;
import com.sax.views.components.libraries.ButtonToolItem;
import com.sax.views.components.libraries.RoundPanel;
import com.sax.views.components.table.CustomHeaderTableCellRenderer;
import com.sax.views.components.table.CustomTableCellEditor;
import com.sax.views.components.table.CustomTableCellRender;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXTable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class KhachHangPane extends JPanel {
    private JXTable table;
    private JPanel bg;
    private JPanel khachHangPanel;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdit;
    private JCheckBox cbkSelectedAll;
    private JPanel phanTrangPane;
    private JComboBox cboHienThi;
    private JList listPage;
    private Search timKiem;
    private IKhachHangService khachHangService = ContextUtils.getBean(KhachHangService.class);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Set tempIdSet = new HashSet();
    private List<JCheckBox> listCbk = new ArrayList<>();
    private Loading loading = new Loading();

    private DefaultListModel listPageModel = new DefaultListModel();
    private int size = 14;
    private Pageable pageable = PageRequest.of(0, 14);
    private Timer timer;

    public KhachHangPane() {
        initComponent();
        btnAdd.addActionListener((e) -> add());
        btnEdit.addActionListener((e) -> update());
        btnDel.addActionListener((e) -> delete());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) update();
            }
        });
        listPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPageDisplay();
            }
        });
        cboHienThi.addActionListener((e) -> selectSizeDisplay());
        timKiem.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timer.restart();
            }
        });
        cbkSelectedAll.addActionListener((e) -> Session.chonTatCa(cbkSelectedAll, table, listCbk, tempIdSet));
    }

    public void initComponent() {
        ((DefaultTableModel) table.getModel()).setColumnIdentifiers(new String[]{"", "Mã khách hàng", "Họ và tên", "Điểm tích luỹ", "Số điện thoại", "Giới tính", "Ngày thêm"});
        new Worker(0).execute();
        loading.setVisible(true);
        timer = new Timer(300, e -> {
            searchByKeyword();
            timer.stop();
        });
    }

    public void fillTable(List<AbstractViewObject> list) {
        Session.fillTable(list, table, cbkSelectedAll, executorService, tempIdSet, listCbk);
    }

    private void add() {
        KhachHangDialog khachHangDialog = new KhachHangDialog();
        khachHangDialog.parentPane = this;
        khachHangDialog.lblTitle.setText("Thêm mới khách hàng");
        khachHangDialog.pageable = (listPageModel.getSize() > 0) ? PageRequest.of(listPageModel.getSize() - 1, 14) : PageRequest.of(listPageModel.getSize(), 14);
        khachHangDialog.setVisible(true);
        table.clearSelection();
    }

    private void update() {
        if (table.getSelectedRow() >= 0) {
            KhachHangDialog khachHangDialog = new KhachHangDialog();
            khachHangDialog.parentPane = this;
            khachHangDialog.id = (int) table.getValueAt(table.getSelectedRow(), 1);
            khachHangDialog.pageable = pageable;
            khachHangDialog.fillForm();
            khachHangDialog.setVisible(true);
        } else MsgBox.alert(this, "Vui lòng chọn một khách hàng!");
    }

    private void delete() {
        if (!tempIdSet.isEmpty()) {
            boolean check = MsgBox.confirm(null, "Bạn có muốn xoá " + tempIdSet.size() + " khách hàng này không?");
            if (check) {
                try {
                    khachHangService.deleteAll(tempIdSet);
                    cbkSelectedAll.setSelected(false);
                    fillTable(khachHangService.getPage(pageable).stream().map(KhachHangViewObject::new).collect(Collectors.toList()));
                    fillListPage(pageable.getPageNumber());
                    loading.dispose();
                } catch (Exception e) {
                    MsgBox.alert(this, e.getMessage());
                }
            }
        } else MsgBox.alert(this, "Vui lòng tick vào ít nhất một khách hàng!");
    }

    public void searchByKeyword() {
        String keyword = timKiem.txtSearch.getText();
        if (!keyword.isEmpty()) {
            fillTable(khachHangService.searchByKeyword(keyword).stream().map(KhachHangViewObject::new).collect(Collectors.toList()));
            phanTrangPane.setVisible(false);
        } else {
            fillTable(khachHangService.getAll().stream().map(KhachHangViewObject::new).collect(Collectors.toList()));
            phanTrangPane.setVisible(true);
        }
    }

    public void fillListPage(int value) {
        Session.fillListPage(value, listPageModel, khachHangService, 14, listPage);
    }

    public void selectPageDisplay() {
        if (listPage.getSelectedValue() instanceof Integer) {
            int page = Integer.parseInt(listPage.getSelectedValue().toString()) - 1;
            pageable = PageRequest.of(page, size);
            new Worker(page).execute();
            loading.setVisible(true);
        }
    }

    public void selectSizeDisplay() {
        size = Integer.parseInt(cboHienThi.getSelectedItem().toString());
        pageable = PageRequest.of(0, size);
        new Worker(pageable.getPageNumber()).execute();
        loading.setVisible(true);
    }

    private void createUIComponents() {
        khachHangPanel = this;
        bg = new RoundPanel(10);
        btnAdd = new ButtonToolItem("add.svg", "add.svg");
        btnDel = new ButtonToolItem("trash-c.svg", "trash-c.svg");
        btnEdit = new ButtonToolItem("pencil.svg", "pencil.svg");

        listPage = new ListPageNumber();
    }

    class Worker extends SwingWorker<List<AbstractViewObject>, Integer> {
        int page;

        public Worker(int page) {
            this.page = page;
        }

        @Override
        protected List<AbstractViewObject> doInBackground() {
            return khachHangService.getPage(pageable).stream().map(KhachHangViewObject::new).collect(Collectors.toList());
        }

        @Override
        protected void done() {
            try {
                fillTable(get());
                fillListPage(page);
                loading.dispose();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
