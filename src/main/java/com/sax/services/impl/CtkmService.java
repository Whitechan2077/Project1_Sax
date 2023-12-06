package com.sax.services.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.CtkmDTO;
import com.sax.repositories.ICtkmRepository;
import com.sax.services.ICtkmService;
import com.sax.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CtkmService implements ICtkmService {
    @Autowired
    private ICtkmRepository repository;
    @Override
    public List<CtkmDTO> getAll() {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(), CtkmDTO.class);
    }

    @Override
    public List<CtkmDTO> getAllByIds(List<Integer> ids) throws SQLServerException {
        return null;
    }

    @Override
    public CtkmDTO getById(Integer id) {
        return DTOUtils
                .getInstance()
                .converter(repository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Khong tim thay")), CtkmDTO.class);
    }

    @Override
    public CtkmDTO insert(CtkmDTO e) throws SQLServerException
    {
        if (e.getNgayBatDau().isBefore(e.getNgayKetThuc())){
          if(LocalDateTime.now().isBefore(e.getNgayBatDau())){
              return DTOUtils.getInstance().converter(repository
                      .save(DTOUtils.getInstance()
                              .converter(e, com.sax.entities.Ctkm.class)), CtkmDTO.class);
          }
          else throw new RuntimeException("Ngày lên lich phải lớn hơn ngày hiện tại");
        }
        else throw new RuntimeException("Ngày bắt đầu phải lớn hơn ngày kết thúc");
    }

    @Override
    public void update(CtkmDTO e) throws SQLServerException
    {
        if (e.getNgayBatDau().isBefore(e.getNgayKetThuc())){
            if(LocalDateTime.now().isBefore(e.getNgayBatDau())){
                 DTOUtils.getInstance().converter(repository
                        .save(DTOUtils.getInstance()
                                .converter(e, com.sax.entities.Ctkm.class)), CtkmDTO.class);
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLServerException
    {
        repository.deleteById(id);
    }
    @Override
    @Transactional
    public void deleteAll(Set<Integer> ids) throws SQLServerException {
        repository.deleteAllById(ids);
    }

    @Override
    public int getTotalPage(int amount) {
        return repository.findAll(Pageable.ofSize(amount)).getTotalPages();
    }

    @Override
    public List<CtkmDTO> getPage(Pageable page) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(page).stream().toList(), CtkmDTO.class);
    }

    @Override
    public List<CtkmDTO> searchByKeyword(String keyword) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAllByKeyword(keyword), CtkmDTO.class);
    }


    @Override
    public List<CtkmDTO> getAllAvailable(Pageable page) {
        return DTOUtils.getInstance()
                .convertToDTOList(repository.findAvailablePromote(page)
                        .getContent(),CtkmDTO.class);
    }

    @Override
    public List<CtkmDTO> getAllNotAvailable(Pageable pageable) {
        return DTOUtils.getInstance()
                .convertToDTOList(repository.findNotAvailablePromote(pageable)
                        .getContent(),CtkmDTO.class);    }

    @Override
    public int getAllNotAvailableToTalPage(Pageable pageable) {
       return repository.findNotAvailablePromote(pageable).getTotalPages();
    }

    @Override
    public int getAllAvailableToTalPage(Pageable pageable) {
        return repository.findAvailablePromote(pageable).getTotalPages();
    }
}
