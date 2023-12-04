package com.sax.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.services.IAccountService;
import com.sax.services.impl.AccountService;
import com.sax.utils.*;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuenMatKhauDialog extends JDialog {
    private JPanel contentPane;
    private JTextField txtOTP;
    private JPanel otp;
    private JTextField txtEmail;
    private JPanel email;
    private JPanel cardPane;
    private JButton btnSendOTP;
    private JButton btnSubmit;
    private JPasswordField txtPass;
    private JPasswordField txtPassA;
    private IAccountService accountService;
    private   AccountDTO accountDTO;
    private Timer timer;
    private int secondsRemaining;


    public QuenMatKhauDialog() {
        initComponent();
        accountService = ContextUtils.getBean(AccountService.class);
        timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSendButton();
            }
        });
    }
    private void initComponent() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
       btnSendOTP.setCursor(new Cursor(Cursor.HAND_CURSOR));
       btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
       btnSubmit.addActionListener(e -> changePass());
        btnSendOTP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra xem đã hết thời gian chưa
                if (!timer.isRunning()) {
                    // Gửi OTP và bắt đầu đếm ngược
                    accountDTO = sendOtp();
                    startTimer();
                } else {
                    // Thông báo người dùng chờ cho đến khi hết thời gian
                    JOptionPane.showMessageDialog(null, "Vui lòng chờ đến khi hết thời gian trước khi gửi lại OTP.");
                }
            }
        });
    }
    private void startTimer() {
        // Disable nút để ngăn chặn việc nhấn liên tục trong thời gian chờ
        disableSendButton();
        secondsRemaining = 60;
        timer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (secondsRemaining > 0) {
                    try {
                        btnSendOTP.setText(String.valueOf(secondsRemaining));
                        // Đợi 1 giây
                        Thread.sleep(1000);
                        secondsRemaining--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timer.stop();
                btnSendOTP.setText("Gửi otp");
            }
        }).start();
    }
    private void enableSendButton() {
        btnSendOTP.setEnabled(true);
    }

    private void disableSendButton() {
        btnSendOTP.setEnabled(false);
    }
    private void changePass(){
        String pass  = new String(txtPassA.getPassword());
        String rePass = new String(txtPass.getPassword());
        String otp = txtOTP.getText();
        if (!pass.trim().isEmpty() || !rePass.trim().isEmpty() || !otp.trim().isEmpty()||!txtEmail.getText().isEmpty())
        {
            if (accountDTO!= null){
                    if (pass.equals(rePass)){
                        if (otp.equals(Session.otp)) {
                            accountDTO.setPassword(rePass);
                            accountService.updateUsernamePassword(accountDTO);
                            MsgBox.alert(null, "Đổi mật khẩu thành công");
                            this.dispose();
                        }
                        else MsgBox.alert(null,"Sai otp");
                    }
                else MsgBox.alert(null,"Không trùng mật khẩu");
            }
            else MsgBox.alert(null,"Vui lòng gửi otp");
        }
        else MsgBox.alert(null,"Vui lòng nhập đủ thông tin");
    }
    private AccountDTO sendOtp(){
        try {
            if (!txtEmail.getText().trim().isEmpty())
            {
                if (Session.isValidEmail(txtEmail.getText())){
                    accountDTO = accountService.getByEmail(txtEmail.getText());
                }
                else{
                    accountDTO = accountService.getByUsername(txtEmail.getText());
                }
                MailService.sendEmail(accountDTO.getEmail());
                MsgBox.alert(null,"Mail đã được gửi tới email :"+accountDTO.getEmail());
                System.out.println(Session.otp);
            }
            else MsgBox.alert(null,"Vui lòng nhập email");
        }catch (IllegalArgumentException e){
            accountDTO = null;
            MsgBox.alert(null,"Không tồn tài khoản");
        } catch (MessagingException e) {
            MsgBox.alert(null,"Mail ko hợp lệ");
        }
        return accountDTO;
    }
}
