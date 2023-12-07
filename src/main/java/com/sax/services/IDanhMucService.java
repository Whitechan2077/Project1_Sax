package com.sax.services;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.DanhMucDTO;

import java.util.List;
import java.util.Set;

public interface IDanhMucService extends ICrudServices<DanhMucDTO,Integer>{
    List<DanhMucDTO> getAllDanhMucCha();

    void deleteAllDanhMucSach(Set<Integer> ids);

    List<DanhMucDTO> getAllDanhMucForUpdate(int id);

}
