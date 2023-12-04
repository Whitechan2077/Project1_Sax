package com.sax.views.quanly.views.dialogs;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.sax.utils.ImageUtils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class CameraDialog extends JDialog {
    private JPanel contentPane;
    private JLabel camera;
    private JComboBox cboCamera;
    private Thread thread;
    private VideoCapture videoCapture;

    public CameraDialog(JTextField txtBarcode) {
        setContentPane(contentPane);
        setModal(true);
        setSize(new Dimension(640, 360));
        setLocationRelativeTo(null);

        nu.pattern.OpenCV.loadLocally();
        videoCapture = new VideoCapture(0);
        Mat frame = new Mat();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedImage bufferedImage;
                while (true) {
                    videoCapture.read(frame);
                    bufferedImage = ImageUtils.convertMatToBufferedImage(frame);

                    Image icon = bufferedImage.getScaledInstance(640, 360, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(icon);
                    camera.setIcon(imageIcon);
                    try {
                        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        Reader reader = new MultiFormatReader();
                        Result result = reader.decode(bitmap);

                        if (result != null) {
                            if (txtBarcode != null)
                            txtBarcode.setText(result.getText());
                            dispose();
                        }
                    } catch (NotFoundException | ChecksumException | IllegalArgumentException | FormatException e) {

                    }
                }
            }
        });
        thread.start();
    }
}
