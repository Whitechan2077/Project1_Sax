package com.sax.views.components.table;


import com.sax.utils.ImageUtils;

import javax.swing.*;
import java.util.concurrent.ExecutorService;

public class CellNameRender extends JPanel {
    private JPanel content;
    private JLabel cellName;
    private JPanel avt;

    public CellNameRender(ExecutorService executorService, JTable tbl, String url, String name) {
        cellName.setText(name);
        executorService.submit(() -> {
            avt.add(ImageUtils.getCircleImage(url, 26, 20,null,0));
            tbl.repaint();
        });
    }

    private void createUIComponents() {
        content = this;
    }
}