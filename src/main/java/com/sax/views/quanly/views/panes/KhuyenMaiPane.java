package com.sax.views.quanly.views.panes;

import com.formdev.flatlaf.FlatClientProperties;
import com.sax.dtos.CtkmDTO;
import com.sax.services.ICtkmSachService;
import com.sax.services.ICtkmService;
import com.sax.services.impl.CtkmService;
import com.sax.utils.ContextUtils;
import com.sax.utils.MsgBox;
import com.sax.utils.Session;
import com.sax.views.components.ListPageNumber;
import com.sax.views.components.Loading;
import com.sax.views.components.Search;
import com.sax.views.components.libraries.ButtonToolItem;
import com.sax.views.components.libraries.RoundPanel;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import com.sax.views.quanly.viewmodel.CtkmSachViewObject;
import com.sax.views.quanly.viewmodel.CtkmViewObject;
import com.sax.views.quanly.views.dialogs.CtkmDialog;
import com.sax.views.quanly.views.dialogs.CtkmSachDialog;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXTable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class KhuyenMaiPane extends JPanel {
    private JXTable tableCTKM;
    private JXTable tableSP;
    private JPanel bg;
    private JPanel bg2;
    private JScrollPane kmScroll;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnEdit;
    private JCheckBox cbkSelectedAllCTKM;
    private JPanel phanTrangPane;
    private JList listPageKM;
    private JComboBox cboHienThi;
    private Search timKiemCTKM;
    private Search timKiemSP;
    private JPanel khuyenMaiPane;
    private JComboBox cboCTKM;
    private JButton btnAddSachTo;
    private JCheckBox cbkSelectedAllSP;
    private JButton btnXoaSP;
    private JButton btnSuaSP;
    private JButton addCtkmSach;
    private ICtkmSachService ctkmSachService = ContextUtils.getBean(ICtkmSachService.class);
    private ICtkmService ctkmService = ContextUtils.getBean(CtkmService.class);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Set tempIdSetCTKM = new HashSet();
    private Set tempIdSetSP = new HashSet();
    private List<JCheckBox> listCbkCTKM = new ArrayList<>();
    private List<JCheckBox> listCbkSP = new ArrayList<>();
    private Loading loading = new Loading();

    private DefaultListModel listPageModelKM = new DefaultListModel();
    private int size = 14;
    private Pageable pageableKM = PageRequest.of(0, 14);
    private Timer timerKM;
    private Timer timerSP;

    public KhuyenMaiPane() {
        initComponent();
        btnAdd.addActionListener((e) -> addKM());
        btnEdit.addActionListener((e) -> updateKM());
        btnDel.addActionListener((e) -> deleteKM());
        cbkSelectedAllCTKM.addActionListener((e) -> Session.chonTatCa(cbkSelectedAllCTKM, tableCTKM, listCbkCTKM, tempIdSetCTKM));
        tableCTKM.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) updateKM();
            }
        });
        listPageKM.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timerKM.restart();
            }
        });
        cboHienThi.addActionListener((e) -> selectSizeDisplay());
        timKiemCTKM.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchByKeywordKM();
            }
        });

        btnAddSachTo.addActionListener((e) -> addSP());
        btnSuaSP.addActionListener((e) -> updateSP());
        btnXoaSP.addActionListener((e) -> deleteSP());
        cboCTKM.addActionListener((e) -> locSPCtkm());
        tableSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) updateSP();
            }
        });
        cbkSelectedAllSP.addActionListener((e) -> Session.chonTatCa(cbkSelectedAllSP, tableSP, listCbkSP, tempIdSetSP));
        timKiemSP.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timerSP.restart();
            }
        });
    }

    public void initComponent() {
        kmScroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "track:#F2F2F2");
        ((DefaultTableModel) tableCTKM.getModel()).setColumnIdentifiers(new String[]{"", "Mã sự kiện", "Tên sự kiện", "Ngày bắt đầu", "Ngày kết thúc", "Giảm theo", "Trạng thái"});
        ((DefaultTableModel) tableSP.getModel()).setColumnIdentifiers(new String[]{"", "Id", "Tên sách", "Tên sự kiện", "Ngày bắt đầu", "Ngày kết thúc", "Giá trị giảm", "Trạng thái"});
        new WorkerKM(0).execute();
        new WorkerSP(0).execute();
        loading.setVisible(true);
        fillListPage(0);
        timerKM = new Timer(300, e -> {
            searchByKeywordKM();
            timerKM.stop();
        });
        timerSP = new Timer(300, e -> {
            searchByKeywordSP();
            timerSP.stop();
        });
        fillCboCtkm();
    }

    public void fillTableKM(List<AbstractViewObject> list) {
        Session.fillTable(list, tableCTKM, cbkSelectedAllCTKM, executorService, tempIdSetCTKM, listCbkCTKM);
    }

    private void addKM() {
        CtkmDialog ctkmDialog = new CtkmDialog();
        ctkmDialog.parentPane = this;
        ctkmDialog.lblTitle.setText("Thêm mới chương trình khuyến mại");
        ctkmDialog.pageable = PageRequest.of(listPageModelKM.getSize() - 1, 14);
        ctkmDialog.setVisible(true);
        tableCTKM.clearSelection();
    }

    private void updateKM() {
        if (tableCTKM.getSelectedRow() >= 0) {
            if (tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đang diễn ra")) {
                MsgBox.alert(this, "Sự kiện đang diễn ra, bạn không thể chỉnh sửa!");
                return;
            }
            if (tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đã kết thúc")) {
                MsgBox.alert(this, "Sự kiện đã kết thúc, bạn không thể chỉnh sửa!");
                return;
            }
            executorService.submit(() -> {
                CtkmDialog ctkmDialog = new CtkmDialog();
                ctkmDialog.parentPane = this;
                ctkmDialog.id = (int) tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 1);
                ctkmDialog.pageable = pageableKM;
                ctkmDialog.fillForm();
                ctkmDialog.setVisible(true);
                tableCTKM.clearSelection();
            });
        } else MsgBox.alert(this, "Vui lòng chọn một chương trình khuyến mại!");
    }

    private void deleteKM() {
        if (!tempIdSetCTKM.isEmpty()) {
            boolean check = MsgBox.confirm(this, "Bạn có muốn xoá " + tempIdSetCTKM.size() + " chương trình khuyến mại này không?");
            if (check) {
                executorService.submit(() -> {
                    try {
                        ctkmService.deleteAll(tempIdSetCTKM);
                        tableCTKM.clearSelection();
                        tempIdSetCTKM.clear();
                        fillTableKM(ctkmService.getAll().stream().map(CtkmViewObject::new).collect(Collectors.toList()));
                    } catch (Exception e) {
                        MsgBox.alert(this, "Có sản phẩm trong chương trình khuyến mại!, bạn không thể xoá!");
                    }
                });
            }
        } else MsgBox.alert(this, "Vui lòng tick vào ít nhất một chương trình khuyến mại!");
    }

    public void searchByKeywordKM() {
        String keyword = timKiemCTKM.txtSearch.getText();
        if (!keyword.isEmpty()) {
            fillTableKM(ctkmService.searchByKeyword(keyword).stream().map(CtkmViewObject::new).collect(Collectors.toList()));
            phanTrangPane.setVisible(false);
        } else {
            fillTableKM(ctkmService.getAll().stream().map(CtkmViewObject::new).collect(Collectors.toList()));
            phanTrangPane.setVisible(true);
        }
    }

    public void fillListPage(int value) {
        Session.fillListPage(value, listPageModelKM, ctkmSachService, 14, listPageKM);
    }

    public void selectPageDisplay() {
        if (listPageKM.getSelectedValue() instanceof Integer) {
            int page = Integer.parseInt(listPageKM.getSelectedValue().toString()) - 1;
            pageableKM = PageRequest.of(page, size);
            new WorkerKM(page).execute();
            loading.setVisible(true);
        }
    }

    public void selectSizeDisplay() {
        size = Integer.parseInt(cboHienThi.getSelectedItem().toString());
        pageableKM = PageRequest.of(0, size);
        new WorkerKM(pageableKM.getPageNumber()).execute();
        loading.setVisible(true);
    }

    //Table CTKM_Sach
    public void fillCboCtkm() {
        cboCTKM.addItem("-Tất cả-");
        ctkmService.getAll().forEach(i -> cboCTKM.addItem(i));
    }

    public void fillTableSP(List<AbstractViewObject> list) {
        Session.fillTable(list, tableSP, cbkSelectedAllSP, executorService, tempIdSetSP, listCbkSP);
    }

    public void addSP() {
        if (tableCTKM.getSelectedRow() >= 0) {
            if (tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đã kết thúc")) {
                MsgBox.alert(this, "Sự kiện đã kết thúc, bạn không thể thêm sản phẩm!");
                return;
            }
            if (!tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đã kết thúc")) {
                CtkmSachDialog ctkmSachDialog = new CtkmSachDialog();
                ctkmSachDialog.khuyenMaiPane = this;
                ctkmSachDialog.ctkmDTO = ctkmService.getById((Integer) tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 1));
                ctkmSachDialog.fillForm();
                ctkmSachDialog.setVisible(true);
            } else MsgBox.alert(this, "Sự kiện đã kết thúc!");
        } else MsgBox.alert(this, "Vui lòng chọn 1 sự kiện khuyến mại!");
    }

    private void updateSP() {
        if (tableCTKM.getSelectedRow() >= 0) {
            if (tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đang diễn ra")) {
                MsgBox.alert(this, "Sự kiện đang diễn ra, bạn không thể chỉnh sửa!");
                return;
            }
            if (tableCTKM.getValueAt(tableCTKM.getSelectedRow(), 6).toString().equals("Đã kết thúc")) {
                MsgBox.alert(this, "Sự kiện đã kết thúc, bạn không thể chỉnh sửa!");
                return;
            }
            executorService.submit(() -> {

            });
        } else MsgBox.alert(this, "Vui lòng chọn một sản phẩm trong chương trình khuyến mại!");
    }

    private void deleteSP() {
        if (!tempIdSetSP.isEmpty()) {
            boolean check = MsgBox.confirm(this, "Bạn có muốn xoá " + tempIdSetSP.size() + " sản phẩm trong chương trình khuyến mại này không?");
            if (check) {
                executorService.submit(() -> {
                    try {
                        ctkmSachService.deleteAll(tempIdSetSP);
                        tableSP.clearSelection();
                        tempIdSetSP.clear();
                        fillTableSP(ctkmSachService.getAll().stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
                    } catch (Exception e) {
                        MsgBox.alert(this, e.getMessage());
                    }
                });
            }
        } else MsgBox.alert(this, "Vui lòng tick vào ít nhất một sản phẩm trong chương trình khuyến mại!");
    }

    public void locSPCtkm() {
        if (cboCTKM.getSelectedItem() instanceof String)
            fillTableSP(ctkmSachService.getPage(pageableKM).stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
        else {
            CtkmDTO ctkmDTO = (CtkmDTO) cboCTKM.getSelectedItem();
            fillTableSP(ctkmSachService.getAllSachInCtkm(ctkmDTO).stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
        }
    }

    public void searchByKeywordSP() {
        String keyword = timKiemSP.txtSearch.getText();
        if (!keyword.isEmpty())
            fillTableSP(ctkmSachService.searchByKeyword(keyword).stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
        else {
            if (cboCTKM.getSelectedItem() instanceof String)
                fillTableSP(ctkmSachService.getAll().stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
            else
                fillTableSP(ctkmSachService.getAllSachInCtkm((CtkmDTO) cboCTKM.getSelectedItem()).stream().map(CtkmSachViewObject::new).collect(Collectors.toList()));
        }
    }


    private void createUIComponents() {
        khuyenMaiPane = this;
        bg = new RoundPanel(10);
        bg2 = new RoundPanel(10);
        btnAdd = new ButtonToolItem("add.svg", "add.svg");
        btnAddSachTo = new ButtonToolItem("add-c.svg", "add-c.svg");
        btnDel = new ButtonToolItem("trash-c.svg", "trash-c.svg");
        btnEdit = new ButtonToolItem("pencil.svg", "pencil.svg");
        btnXoaSP = new ButtonToolItem("trash-c.svg", "trash-c.svg");
        btnSuaSP = new ButtonToolItem("pencil.svg", "pencil.svg");

        listPageKM = new ListPageNumber();
    }

    class WorkerKM extends SwingWorker<List<AbstractViewObject>, Integer> {
        int page;

        public WorkerKM(int page) {
            this.page = page;
        }

        @Override
        protected List<AbstractViewObject> doInBackground() {
            return ctkmService.getPage(pageableKM).stream().map(CtkmViewObject::new).collect(Collectors.toList());
        }

        @Override
        protected void done() {
            try {
                fillTableKM(get());
                fillListPage(page);
                loading.dispose();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class WorkerSP extends SwingWorker<List<AbstractViewObject>, Integer> {

        int page;

        public WorkerSP(int page) {
            this.page = page;
        }

        @Override
        protected List<AbstractViewObject> doInBackground() {
            return ctkmSachService.getAll().stream().map(CtkmSachViewObject::new).collect(Collectors.toList());
        }

        @Override
        protected void done() {
            try {
                fillTableSP(get());
                fillListPage(page);
                loading.dispose();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
