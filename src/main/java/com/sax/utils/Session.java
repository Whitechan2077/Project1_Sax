package com.sax.utils;

import com.sax.dtos.AccountDTO;
import com.sax.services.ICrudServices;
import com.sax.views.components.table.CustomHeaderTableCellRenderer;
import com.sax.views.components.table.CustomTableCellEditor;
import com.sax.views.components.table.CustomTableCellRender;
import com.sax.views.quanly.viewmodel.AbstractViewObject;
import org.jdesktop.swingx.JXTable;
import org.springframework.data.domain.Pageable;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class Session {
    private static Timer timer;
    private static  int secondsRemaining ;

    public static AccountDTO accountid;
    public static String otp;

    public void logout() {
        accountid = null;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void chonTatCa(JCheckBox cbkSelectedAll, JTable table, List<JCheckBox> listCbk, Set tempIdSet) {
        if (cbkSelectedAll.isSelected()) {
            for (int i = 0; i < table.getRowCount(); i++) {
                listCbk.get(i).setSelected(true);
                tempIdSet.add((int) table.getValueAt(i, 1));
                table.repaint();
            }
        } else {
            for (int i = 0; i < table.getRowCount(); i++)
                listCbk.get(i).setSelected(false);
            tempIdSet.clear();
            table.repaint();
        }
    }

    public static void fillTable(List<AbstractViewObject> list, JXTable table, JCheckBox cbkSelectedAll, ExecutorService executorService, Set tempIdSet, List<JCheckBox> listCbk) {
        tempIdSet.clear();
        listCbk.clear();
        cbkSelectedAll.setSelected(false);
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        list.forEach(i -> ((DefaultTableModel) table.getModel()).addRow(i.toObject(executorService, table, tempIdSet, listCbk)));
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setDefaultRenderer(new CustomHeaderTableCellRenderer());
        table.getTableHeader().setEnabled(false);
        table.getTableHeader().setPreferredSize(new Dimension(-1, 28));
        table.getColumnModel().getColumn(0).setCellEditor(new CustomTableCellEditor(list));
        table.setDefaultRenderer(Object.class, new CustomTableCellRender(list, cbkSelectedAll));
        table.packAll();
    }

    public static void fillListPage(int value, DefaultListModel listPageModel, ICrudServices services, Pageable pageable, JList listPage) {
        listPageModel.clear();
        int totalPage = services.getTotalPage(pageable);

        if (totalPage < 10) {
            for (int i = 1; i <= totalPage; i++) {
                listPageModel.addElement(i);
            }
        } else {
            if (value < 4) {
                for (int i = 0; i < 8; i++) {
                    listPageModel.addElement(i + 1);
                }
                listPageModel.addElement("...");
                listPageModel.addElement(totalPage);

            } else if (totalPage - value >= 4) {
                for (int i = value - 3; i < value; i++) {
                    listPageModel.addElement(i < 0 ? value + i : i);
                }
                for (int i = value; i <= value + 3; i++) {
                    listPageModel.addElement(i);
                }
                listPageModel.addElement("...");
                listPageModel.addElement(totalPage);
            } else {
                for (int i = totalPage - 8; i <= totalPage; i++) {
                    listPageModel.addElement(i);
                }
            }
        }

        listPage.setModel(listPageModel);
        listPage.setSelectedValue(value + 1, false);
        listPage.repaint();
    }

    public static Map<String, String> getConfig() {
        Map<String, String> data = null;
        try {
            FileInputStream input = new FileInputStream("./config.yaml");
            Yaml yaml = new Yaml();
            data = yaml.load(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void startTimer(JButton jButton) {
        // Disable nút để ngăn chặn việc nhấn liên tục trong thời gian chờ
        disableSendButton(jButton);
         secondsRemaining = 60;
        timer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (secondsRemaining > 0) {
                    try {
                        jButton.setText(String.valueOf(secondsRemaining));
                        // Đợi 1 giây
                        Thread.sleep(1000);
                        secondsRemaining--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timer.stop();
                jButton.setText("Gửi otp");
            }
        }).start();
    }
    private static void enableSendButton(JButton jButton) {
        jButton.setEnabled(true);
    }

    private static void disableSendButton(JButton jButton) {
        jButton.setEnabled(false);
    }

}
