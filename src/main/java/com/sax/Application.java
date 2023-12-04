package com.sax;

import com.formdev.flatlaf.FlatLightLaf;
import com.sax.utils.JdbcConnection;
import com.sax.utils.MsgBox;
import com.sax.utils.Session;
import com.sax.views.LoginView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Application extends JFrame {
    public static Application app;

    public static void main(String[] args) {
        if (Session.createDefaultConfigFile()) {
            try {
                JdbcConnection.getConnection();
                JdbcConnection.getConnection().close();
                System.setProperty("apple.awt.application.name", "SaX Management");
                SwingUtilities.invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        UIManager.put("Component.focusWidth", 1);
                        UIManager.put("TabbedPane.selectedBackground", Color.white);
                        UIManager.put("Button.arc", 10);
                        UIManager.put("Component.arc", 10);
                        UIManager.put("ProgressBar.arc", 10);
                        UIManager.put("TextComponent.arc", 10);
                        UIManager.put("Component.focusColor", Color.decode("#ea6c20"));
                        UIManager.put("Component.borderColor", Color.decode("#D7DAE3"));
                        UIManager.put("ScrollBar.track", Color.white);
                    } catch (UnsupportedLookAndFeelException e) {
                        throw new RuntimeException(e);
                    }
                    app = new Application();
                    app.setContentPane(new LoginView());
                    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    app.pack();
                    app.setLocationRelativeTo(null);
                    app.setVisible(true);
                });
            } catch (SQLException e) {
                MsgBox.alert(null, "Không thể kết nối CSDL, vui lòng kiểm tra lại file " + Session.isConfigFileCreated() + "!");
                System.exit(0);
                throw new RuntimeException(e);
            }
        } else {
            MsgBox.alert(null, "Đã tạo file config.yaml tại đường đẫn " + Session.isConfigFileCreated() + ", vui lòng cấu hình file và chạy lại chương trình!");
            System.exit(0);
        }
    }
}
