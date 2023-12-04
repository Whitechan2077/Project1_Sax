package com.sax.services;

import com.sax.dtos.DoanhThuNamDTO;
import com.sax.dtos.DoanhThuNgayDTO;

import java.util.List;

public interface IThongKeService {
    List<DoanhThuNgayDTO>  getAllTongTienTheoThang(int month, int year);
    List<DoanhThuNamDTO> getAllTongTienTheoNam(int year);
    Integer countDonHangByNhanVien(Integer id);
    Integer countAllDonhang();
}
