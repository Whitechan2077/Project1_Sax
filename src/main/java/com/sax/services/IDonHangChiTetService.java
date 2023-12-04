package com.sax.services;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.ChiTietDonHangDTO;
import com.sax.dtos.DonHangDTO;
import com.sax.entities.Sach;

import java.util.List;
import java.util.Set;

public interface IDonHangChiTetService extends ICrudServices<ChiTietDonHangDTO,Integer>{
    List<ChiTietDonHangDTO> getAllByDonHang(DonHangDTO donHangDTO);
}
