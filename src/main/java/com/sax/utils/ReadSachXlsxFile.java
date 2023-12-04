package com.sax.utils;

import com.sax.dtos.SachDTO;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReadSachXlsxFile {
    public static List<SachDTO> readExcelFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        List<SachDTO> sachDTOList = new ArrayList<>();
             Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                SachDTO sachDTO = new SachDTO();
                int cellIndex = 0;

                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            handleStringCell(cellIndex, cell, sachDTO);
                            break;
                        case NUMERIC:
                            handleNumericCell(cellIndex, cell, sachDTO);
                            break;
                        // Xử lý các kiểu dữ liệu khác nếu cần
                        case BOOLEAN:
                            handleBooleanCell(cellIndex,cell,sachDTO);
                            break;
                        default:
                            break;
                    }

                    cellIndex++;
                }
                sachDTOList.add(sachDTO);
            }
        fis.close();
        List<SachDTO> sachDTOS = sachDTOList.stream().filter(sachDTO -> sachDTO.getGiaBan() != null).collect(Collectors.toList());
        return readImage(workbook,sachDTOS);

    }
    private static void handleStringCell(int cellIndex, Cell cell, SachDTO sachDTO) {
        switch (cellIndex) {
            case 0:
                sachDTO.setBarCode(cell.getStringCellValue());
                break;
            case 1:
                sachDTO.setTenSach(cell.getStringCellValue());
                break;
            case 6:
                sachDTO.setHinhAnh(cell.getStringCellValue());
                break;
            case 8:
                sachDTO.setNxb(cell.getStringCellValue());
                break;
            default:
                break;
        }
    }

    private static void handleNumericCell(int cellIndex, Cell cell, SachDTO sachDTO) {
        switch (cellIndex) {
            case 2:
                sachDTO.setGiaBan((long) cell.getNumericCellValue());
                break;
            case 3:
                sachDTO.setSoLuong((int) cell.getNumericCellValue());
                break;
            case 4:
                Date dateCellValue = cell.getDateCellValue();
                LocalDateTime ngayThem = dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                sachDTO.setNgayThem(ngayThem);
                break;
            case 7:
//                sachDTO.setGiaNhap((long) cell.getNumericCellValue());
                break;
                default:
                break;

        }
    }
    private static void handleBooleanCell(int cellIndex, Cell cell, SachDTO sachDTO) {
        if (cellIndex == 5) {
            sachDTO.setTrangThai(cell.getBooleanCellValue());
        }
    }
    private static List<SachDTO> readImage(Workbook workbook,List<SachDTO> sachDTO){
        List<? extends PictureData> lst = workbook.getAllPictures();



        IntStream.range(0, lst.size()).forEach(i -> {
            byte[] img = lst.get(i).getData();
//            sachDTO.get(i).setImg(img);
        });
        return sachDTO;
    }
}
