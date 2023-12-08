package com.sax.views.quanly.views.panes;

import com.sax.dtos.DoanhThuNamDTO;
import com.sax.dtos.DoanhThuNgayDTO;
import com.sax.services.IThongKeService;
import com.sax.services.impl.ThongKeService;
import com.sax.utils.ContextUtils;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;

public class ThongKePane extends JPanel {

    private JPanel contentPane;
    private JPanel contentTT;
    private JRadioButton rdoLoiNhuan;
    private JRadioButton rdoDoanhThu;
    private JRadioButton rdoChiPhi;
    private JComboBox cboThang;
    private JComboBox cboNamTT;
    private JComboBox cboNamTN;
    private JPanel contentTN;
    private JRadioButton rdoLoiNhuanTN;
    private JRadioButton rdoDoanhThuTN;
    private JRadioButton rdoChiPhiTN;
    private IThongKeService thongKeService = ContextUtils.getBean(ThongKeService.class);

    public ThongKePane() {
        cboThang.addActionListener((e) -> hienThiTheoThang());
        cboNamTT.addActionListener((e) -> hienThiTheoThang());
        rdoLoiNhuan.addActionListener((e) -> hienThiTheoThang());
        rdoChiPhi.addActionListener((e) -> hienThiTheoThang());
        rdoDoanhThu.addActionListener((e) -> hienThiTheoThang());

        cboNamTN.addActionListener((e) -> hienThiTheoNam());
        rdoLoiNhuanTN.addActionListener((e) -> hienThiTheoNam());
        rdoChiPhiTN.addActionListener((e) -> hienThiTheoNam());
        rdoDoanhThuTN.addActionListener((e) -> hienThiTheoNam());

        initComponent();
    }

    private void initComponent() {
        int thang = LocalDateTime.now().getMonthValue();
        int nam = LocalDateTime.now().getYear();
        cboThang.setSelectedItem(thang < 10 ? "0" + thang : String.valueOf(thang));
        cboNamTT.setSelectedItem(String.valueOf(nam));
        cboNamTN.setSelectedItem(String.valueOf(nam));
        hienThiTheoThang();
        hienThiTheoNam();
    }

    private void fillBieuDo(XYDataset dataset, JPanel content) {
        JFreeChart chart = createSplineChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.WHITE);

        content.removeAll();
        content.add(chartPanel);
        content.revalidate();
    }

    private JFreeChart createSplineChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Biểu đồ thống kê lợi nhuận, doanh thu và chi phí bán hàng",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(dataset.getItemCount(0));

        // Tạo định dạng tùy chỉnh cho giá trị trục y
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        NumberFormat format = new DecimalFormat("#,###", symbols);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setNumberFormatOverride(format);

        plot.setBackgroundPaint(Color.WHITE);
        SplineRenderer renderer = new SplineRenderer();
        renderer.setSeriesShapesVisible(0, true); // Hiển thị điểm dữ liệu cho series 0
        renderer.setSeriesLinesVisible(0, true); // Tắt đường nối giữa các điểm dữ liệu cho series 0
        renderer.setBaseItemLabelGenerator(new CustomXYItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setPrecision(1000);
        plot.setRenderer(renderer);

        return chart;
    }

    private void hienThiTheoThang() {
        int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
        int nam = Integer.parseInt(cboNamTT.getSelectedItem().toString());

        DefaultXYDataset dataset = new DefaultXYDataset();

        List<DoanhThuNgayDTO> list = thongKeService.getAllTongTienTheoThang(thang, nam);
        double[][] data = new double[2][list.size()];

        if (rdoLoiNhuan.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNgayDTO dto = list.get(i);
                data[0][i] = dto.getNgay();
                data[1][i] = dto.getTongTien() - dto.getChiPhi();
            }
        }
        if (rdoDoanhThu.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNgayDTO dto = list.get(i);
                data[0][i] = dto.getNgay();
                data[1][i] = dto.getTongTien();
            }
        }

        if (rdoChiPhi.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNgayDTO dto = list.get(i);
                data[0][i] = dto.getNgay();
                data[1][i] = dto.getChiPhi();
            }
        }

        dataset.addSeries("Biểu đồ thống kê " + thang + "/" + nam, data);

        fillBieuDo(dataset, contentTT);
    }

    private void hienThiTheoNam() {

        int nam = Integer.parseInt(cboNamTN.getSelectedItem().toString());

        DefaultXYDataset dataset = new DefaultXYDataset();

        List<DoanhThuNamDTO> list = thongKeService.getAllTongTienTheoNam(nam);
        double[][] data = new double[2][list.size()];

        if (rdoLoiNhuanTN.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNamDTO dto = list.get(i);
                data[0][i] = dto.getThang();
                data[1][i] = dto.getTongTien() - dto.getChiPhi();
            }
        }
        if (rdoDoanhThuTN.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNamDTO dto = list.get(i);
                data[0][i] = dto.getThang();
                data[1][i] = dto.getTongTien();
            }
        }

        if (rdoChiPhiTN.isSelected()) {
            for (int i = 0; i < list.size(); i++) {
                DoanhThuNamDTO dto = list.get(i);
                data[0][i] = dto.getThang();
                data[1][i] = dto.getChiPhi();
            }
        }

        dataset.addSeries("Biểu đồ thống kê năm" + nam, data);

        fillBieuDo(dataset, contentTN);
    }

    private void createUIComponents() {
        contentPane = this;
    }

    class CustomXYItemLabelGenerator extends StandardXYItemLabelGenerator {
        private DecimalFormat format;

        public CustomXYItemLabelGenerator() {
            super();
            format = new DecimalFormat("#,###");
        }

        @Override
        public String generateLabel(org.jfree.data.xy.XYDataset dataset, int series, int item) {
            Number y = dataset.getY(series, item);
            if (y != null) {
                double value = y.doubleValue();
                return format.format(value / 1000).replace(",", ".") + "k";
            }
            return super.generateLabel(dataset, series, item);
        }
    }

}


