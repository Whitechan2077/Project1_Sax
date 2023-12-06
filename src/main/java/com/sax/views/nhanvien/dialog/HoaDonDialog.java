package com.sax.views.nhanvien.dialog;

import com.sax.dtos.DonHangDTO;
import com.sax.utils.CurrencyConverter;
import com.sax.utils.ImageUtils;
import com.sax.views.components.libraries.ButtonToolItem;
import org.jdesktop.swingx.JXTable;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoaDonDialog extends JDialog {
    private JPanel contentPane;
    private JLabel lblSdt;
    private JLabel lblTenKH;
    private JXTable table;
    private JLabel lblTPT;
    private JLabel lblTienHang;
    private JLabel lblMNV;
    private JLabel lblSHD;
    private JPanel sp;
    private JLabel lblNgayTao;
    private JButton btnSubmit;
    private JButton btnClose;
    private JLabel lblChietKhau;
    private JLabel lblLogo;
    private DefaultTableModel tableModel;
    private DecimalFormat decimalFormat;

    public HoaDonDialog(Component parent, DonHangDTO donHangDTO, boolean check) throws FileNotFoundException {
        table = new JXTable();
        tableModel = (DefaultTableModel) table.getModel();
        decimalFormat = new DecimalFormat();

        lblTenKH.setText(donHangDTO.getKhach().getTenKhach().toUpperCase());
        lblSdt.setText("Số điện thoại: " + donHangDTO.getKhach().getSdt());

        lblMNV.setText("Nhân viên " + donHangDTO.getAccount().getId() + " " + donHangDTO.getAccount().getTenNhanVien());
        lblSHD.setText("Hoá đơn #" + donHangDTO.getId());

        table.setVisibleRowCount(donHangDTO.getChiTietDonHangs().size());
        String[] columnNames = {"Sản phẩm", "SL", "Giá bán", "Giá giảm", "Thành tiền"};
        tableModel.setDataVector(donHangDTO.getChiTietDonHangs().stream().map(i -> new Object[]{
                i.getSach().getTenSach(),
                i.getSoLuong(),
                CurrencyConverter.parseString(i.getGiaBan()),
                "-" + CurrencyConverter.parseString(i.getGiaGiam()),
                CurrencyConverter.parseString((i.getGiaBan() - i.getGiaGiam()) * i.getSoLuong())
        }).toArray(Object[][]::new), columnNames);
        table.packAll();
        table.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        lblNgayTao.setText("Ngày tạo: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " - "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        lblTienHang.setText(CurrencyConverter.parseString(donHangDTO.getTienHang()));
        lblChietKhau.setText("-" + CurrencyConverter.parseString(donHangDTO.getChietKhau()));
        lblTPT.setText(CurrencyConverter.parseString(donHangDTO.getTongTien()));
        lblLogo.setIcon(new ImageIcon(ImageUtils.readImage("logo-com.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        sp.add(scrollPane);

        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(parent);
        setFocusableWindowState(true);
        setAlwaysOnTop(true);

        if (check) {
            BufferedImage hoadon = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = (Graphics2D) hoadon.getGraphics();
            contentPane.paint(g2D);
            g2D.translate(0, this.getHeight());
            contentPane.paint(g2D);
            ImageUtils.saveBufferImageToFile(hoadon, "invoices/" + donHangDTO.getId() + ".png");
        }

        btnSubmit.addActionListener((e) -> {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("images/invoices/" + donHangDTO.getId() + ".png");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            DocFlavor myFormat = DocFlavor.INPUT_STREAM.PNG;

            Doc myDoc = new SimpleDoc(fileInputStream, myFormat, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(new Copies(1));
            aset.add(OrientationRequested.REVERSE_PORTRAIT);
            aset.add(Sides.ONE_SIDED);

            PrintService[] services = PrintServiceLookup.lookupPrintServices(myFormat, aset);
            if (services.length != 0) {
                DocPrintJob printJob = services[0].createPrintJob();
                try {
                    printJob.print(myDoc, aset);
                } catch (PrintException pe) {
                    throw new RuntimeException(pe);
                }
            }
            dispose();
        });

        btnClose.addActionListener((e) -> dispose());
    }


    private void createUIComponents() {
        btnSubmit = new ButtonToolItem("ctkm.svg", "ctkm.svg");
        btnClose = new ButtonToolItem("x-c.svg", "x-c.svg");
    }
}
