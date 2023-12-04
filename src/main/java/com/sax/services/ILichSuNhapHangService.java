package com.sax.services;

import com.sax.dtos.LichSuNhapHangDTO;

import java.util.List;

public interface ILichSuNhapHangService extends ICrudServices<LichSuNhapHangDTO, Integer> {
    public List<LichSuNhapHangDTO> getAllByIdSach(Integer id);
}
