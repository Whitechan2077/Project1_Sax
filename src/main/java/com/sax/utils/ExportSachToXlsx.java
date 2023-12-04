package com.sax.utils;

import com.sax.dtos.SachDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportSachToXlsx {
    public static void exportToExcel(List<SachDTO> sachList, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sach Data");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Barcode", "Tên sách", "Giá bán", "Số lượng", "Ngày thêm", "Ngày sửa", "Trạng thái", "Hình ảnh", "Giá nhập", "NXB"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        // Đổ dữ liệu từ danh sách DTO vào tệp Excel
        int rowNum = 1;
        for (SachDTO sach : sachList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(sach.getBarCode());
            row.createCell(1).setCellValue(sach.getTenSach());
            row.createCell(2).setCellValue(sach.getGiaBan());
            row.createCell(3).setCellValue(sach.getSoLuong());
            row.createCell(4).setCellValue(formatLocalDateTime(sach.getNgayThem()));
            row.createCell(5).setCellValue(formatLocalDateTime(sach.getNgaySua()));
            row.createCell(6).setCellValue(sach.getTrangThai());
            row.createCell(7).setCellValue(sach.getHinhAnh());
//            row.createCell(8).setCellValue(sach.getGiaNhap());
            row.createCell(9).setCellValue(sach.getNxb());
        }

        // Ghi tệp Excel
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        workbook.close();
    }
    private static String formatLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
