package com.sax.services;

import com.sax.dtos.SachDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ISachService extends ICrudServices<SachDTO,Integer>{
    List<SachDTO> getAllSachInOrNotInCTKM();
    List<SachDTO> getAllSachNotInCTKM();
    List<SachDTO> getAllSachByIdDanhMuc(Integer id);
    List<SachDTO> getAllCtkSachInAllAvailablePromote(Pageable pageable);
    List<SachDTO> getAllCtkSachNotInAllAvailablePromote(Pageable pageable);
    int  getToTalPageCtkSachNotInAllAvailablePromote(Pageable pageable);
    int  getToTalPageCtkSachInAllAvailablePromote(Pageable pageable);
    SachDTO getByBarCode(String barcode);
    List<SachDTO> getAllSachByKeyWord(String kw);
    List<SachDTO> getAllAvailableSachByKeyWord(String kw);
}
