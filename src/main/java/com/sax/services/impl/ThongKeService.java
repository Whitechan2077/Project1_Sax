package com.sax.services.impl;

import com.sax.dtos.DoanhThuNamDTO;
import com.sax.dtos.DoanhThuNgayDTO;
import com.sax.repositories.IDonHangRepository;
import com.sax.services.IThongKeService;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ThongKeService implements IThongKeService {
    @Autowired
    IDonHangRepository repository;
    @Transactional
    @Override
    public List<DoanhThuNgayDTO> getAllTongTienTheoThang(int month, int year) {
        List<DoanhThuNgayDTO> result = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusDays(1);
        while (!startDate.isAfter(endDate)) {
            int day = startDate.getDayOfMonth();
            DoanhThuNgayDTO dto = new DoanhThuNgayDTO(repository.findDailyRevenueForDate(day, month, year),
                    repository.findDailyExpenseForDate(day, month, year), day);
            dto.setChiPhi(Optional.ofNullable(dto.getChiPhi()).orElse(0L));
            dto.setTongTien(Optional.ofNullable(dto.getTongTien()).orElse(0L));
            result.add(dto);
            startDate = startDate.plusDays(1);
        }
        return result;
    }
    @Transactional
    @Override
    public List<DoanhThuNamDTO> getAllTongTienTheoNam(int year) {
        List<DoanhThuNamDTO> result = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Long expense = repository.findDailyExpenseForMonth(i, year);
            Long revenue = repository.findDailyRevenueForMonth(i, year);
            DoanhThuNamDTO doanhThuNamDTO = new DoanhThuNamDTO(revenue, expense, i);
            doanhThuNamDTO.setChiPhi(Optional.ofNullable(doanhThuNamDTO.getChiPhi()).orElse(0L));
            doanhThuNamDTO.setTongTien(Optional.ofNullable(doanhThuNamDTO.getTongTien()).orElse(0L));
            result.add(doanhThuNamDTO);
        }
        return result;
    }

    @Override
    public Integer countDonHangByNhanVien(Integer id) {
        return null;
    }

    @Override
    public Integer countAllDonhang() {
        return null;
    }
}
