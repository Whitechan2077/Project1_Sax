package com.sax.services;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.CtkmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ICtkmService extends ICrudServices<CtkmDTO,Integer> {
    List<CtkmDTO> getAllAvailable(Pageable page);
    List<CtkmDTO> getAllNotAvailable(Pageable pageable);
    int getAllNotAvailableToTalPage(Pageable pageable);
    int getAllAvailableToTalPage(Pageable pageable);

}
