package com.sax.services;

import com.sax.dtos.CtkmDTO;
import com.sax.dtos.CtkmSachDTO;
import com.sax.dtos.SachDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICtkmSachService extends ICrudServices<CtkmSachDTO,Integer>{
    List<CtkmSachDTO> getAllSachInCtkm(CtkmDTO ctkmDTO);
    List<CtkmSachDTO> getAllSachByIdCtkm(Integer id);
   List<CtkmSachDTO> insetAll(List<CtkmSachDTO> e);
   List<CtkmSachDTO> searchAllSachInCtkm(String kw,CtkmDTO ctkmDTO);
}
