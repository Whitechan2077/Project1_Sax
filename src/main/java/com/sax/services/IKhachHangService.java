package com.sax.services;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.DonHangDTO;
import com.sax.dtos.KhachHangDTO;

import java.util.List;
import java.util.Set;

public interface IKhachHangService extends ICrudServices<KhachHangDTO,Integer> {
    void addPoint(DonHangDTO donHangDTO);
    void usePoint(DonHangDTO donHangDTO,int point);
}
