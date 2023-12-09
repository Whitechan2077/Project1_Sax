package com.sax.utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.imgscalr.Scalr;
import org.jdesktop.swingx.JXImageView;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;

public class ImageUtils {
    private static final String ABSOLUTE_PATH = ImageUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public static File getFile(String url) {
        return new File(ABSOLUTE_PATH + "/images/" + url);
    }

    public static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            if (path == null) return ImageIO.read(ImageUtils.class.getResourceAsStream("/images/no-image.png"));
            image = ImageIO.read(new File("images/" + path));
        } catch (IOException e) {
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException ex) {
                try {
                    image = ImageIO.read(ImageUtils.class.getResourceAsStream("/images/no-image.png"));
                } catch (IOException exc) {
                    exc.getStackTrace();
                }
            }
        }
        return image;
    }

    public static FlatSVGIcon readSVG(String path) {
        try {
            if (path == null || path.equals("")) return null;
            return new FlatSVGIcon(ImageUtils.class.getResourceAsStream("/icons/" + path));
        } catch (IOException e) {
            return null;
        }
    }

    public static JLayeredPane getCircleImage(String url, int size, int radius, Color borderColor, int borderSize) {
        BufferedImage icon = readImage(url);

        //Crop
        int w = Math.min(icon.getWidth(), icon.getHeight());
        int h = Math.max(icon.getWidth(), icon.getHeight());

        int x = 0;
        int y = 0;
        if ((double) icon.getWidth() / icon.getHeight() < 1)
            y = (h - w) / 2;
        if ((double) icon.getWidth() / icon.getHeight() > 1)
            x = (h - w) / 2;

        BufferedImage jpgImg = new BufferedImage(icon.getWidth(), icon.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImg.createGraphics().drawImage(icon, 0, 0, Color.WHITE, null);

        BufferedImage resize = Scalr.crop(jpgImg, x, y, w, w);

        //Resize
        if (size <= 50 && h > 200)
            resize = Scalr.resize(resize, Scalr.Method.BALANCED, 200);

        BufferedImage circle = makeCircleImage(resize, radius);
        JLayeredPane layeredPane = new JLayeredPane();
        JXImageView jxv = new JXImageView();
        jxv.setDragEnabled(false);
        JPanel border = new JPanel();

        jxv.setImage((Image) circle);
        jxv.setScale((double) (size - borderSize) / resize.getHeight());

        border.setOpaque(false);
        border.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), borderColor, borderSize, 999));

        layeredPane.add(jxv, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(border, JLayeredPane.PALETTE_LAYER);
        jxv.setBounds((int) (borderSize / 2), (int) (borderSize / 2), size - (int) (borderSize / 2), size - (int) (borderSize / 2));
        border.setBounds(0, 0, size, size);
        layeredPane.setPreferredSize(new Dimension(size, size));

        layeredPane.setBackground(Color.red);
        return layeredPane;
    }

    public static BufferedImage makeCircleImage(BufferedImage master, int radius) {
        int diameter = Math.min(master.getWidth(), master.getHeight());
        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillRoundRect(0, 0, diameter - 1, diameter - 1, radius, radius);
        g2d.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - master.getWidth()) / 2;
        int y = (diameter - master.getHeight()) / 2;
        g2d.drawImage(master, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);

        return masked;
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage convertMatToBufferedImage(Mat frame) {
        int width = frame.width();
        int height = frame.height();
        int channels = frame.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        frame.get(0, 0, sourcePixels);
        BufferedImage bufferedImage = null;
        bufferedImage =  width > 0 && height > 0 ? new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR) :  new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return bufferedImage;
    }

    public static String openImageFile(JPanel pane) {
        FileDialog fileChooser = new FileDialog((Frame) null);
        fileChooser.setVisible(true);
        File[] selectedFile = fileChooser.getFiles();
        File image = null;
        if (selectedFile.length > 0) {
            image = selectedFile[0];
            pane.removeAll();
            try {
                pane.add(getCircleImage(image.getPath(), 200, 20, null, 0));
            } catch (NullPointerException e) {
                return null;
            }
            pane.revalidate();
        }
        return (image != null) ? image.getAbsolutePath() : null;
    }

    public static void saveBufferImageToRaster(BufferedImage image, String nameFile) {
        try {
            ImageIO.write(image, "png", new File("images/" + nameFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveBufferImageToPdf(BufferedImage image, String nameFile) {
        try {
            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream("images/" + nameFile));
            document.open();

            float pdfWidth = PageSize.A6.getWidth();
            float pdfHeight = PageSize.A6.getHeight();

            float imageWidth = image.getWidth();
            float imageHeight = image.getHeight();

            float scale = Math.min(pdfWidth / imageWidth, pdfHeight / imageHeight);

            float x = (pdfWidth - imageWidth * scale) / 2;
            float y = pdfHeight - imageHeight * scale;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(imageBytes);
            pdfImage.scaleToFit(imageWidth * scale, imageHeight * scale);
            pdfImage.setAbsolutePosition(x, y);
            document.add(pdfImage);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String saveImage(File selectedFile) throws IOException {
        byte[] imageData;
        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
            imageData = new byte[(int) selectedFile.length()];
            fileInputStream.read(imageData);
        }
        String imagePath = "images/" + selectedFile.getName();

        try (FileOutputStream fileOutputStream = new FileOutputStream(imagePath)) {
            fileOutputStream.write(imageData);
        }

        return selectedFile.getName();
    }
}
