package com.sax.services.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.LichSuNhapHangDTO;
import com.sax.dtos.SachDTO;
import com.sax.entities.LichSuNhapHang;
import com.sax.entities.Sach;
import com.sax.repositories.ILichSuNhapHangRepository;
import com.sax.repositories.ISachRepository;
import com.sax.services.ILichSuNhapHangService;
import com.sax.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class LichSuNhapHangService implements ILichSuNhapHangService {
    @Autowired
    private ILichSuNhapHangRepository repository;
    @Autowired
    private SachService sachService;
    @Autowired
    private ISachRepository sachRepository;

    @Override
    public List<LichSuNhapHangDTO> getAll() {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(), LichSuNhapHangDTO.class);
    }

    @Override
    public List<LichSuNhapHangDTO> getAllByIds(List<Integer> ids) throws SQLServerException {
        return DTOUtils.getInstance().convertToDTOList(repository.findAllById(ids), LichSuNhapHangDTO.class);
    }


    @Override
    public LichSuNhapHangDTO getById(Integer id) {
        return DTOUtils.getInstance()
                .converter(repository.findById(id)
                        .orElseThrow(()
                                -> new NoSuchElementException("Khong tin thay")), LichSuNhapHangDTO.class);
    }

    @Override
    public LichSuNhapHangDTO insert(LichSuNhapHangDTO e) throws SQLServerException {
      if(e.getSoLuong()>0){
          SachDTO dto = e.getSach();
          dto.setTrangThai(true);
          sachService.update(dto);
          e.getSach().setTrangThai(true);
          sachService.update(e.getSach());
          return DTOUtils.getInstance()
                  .converter(repository.save(DTOUtils.getInstance()
                          .converter(e, LichSuNhapHang.class)), LichSuNhapHangDTO.class);
      }
      else throw new RuntimeException("Số lượng phải lớn hơn 0");
    }

    @Override
    public void update(LichSuNhapHangDTO e) throws SQLServerException {
       if (e.getSoLuong()>0){
           SachDTO dto = e.getSach();
           LichSuNhapHang lichSuNhapHang = repository.findById(e.getId()).orElseThrow();
           int soLuongCapNhat = e.getSoLuong() - lichSuNhapHang.getSoLuong();
           lichSuNhapHang.setSoLuong(e.getSoLuong());
           dto.setTrangThai(true);
           dto.setSoLuong(dto.getSoLuong()+soLuongCapNhat);
           sachService.update(dto);
           e.getSach().setTrangThai(true);
           sachService.update(e.getSach());
           repository.save(lichSuNhapHang);
       }else throw new RuntimeException("Số lượng phải lớn hơn 0");
    }

    @Override
    public void delete(Integer id) throws SQLServerException {
        LichSuNhapHang lichSuNhapHang = repository.findById(id).orElseThrow();
        Sach sach = lichSuNhapHang.getSach();
        sach.setSoLuong(sach.getSoLuong()-lichSuNhapHang.getSoLuong());
        sachRepository.save(sach);
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(Set<Integer> ids) throws SQLServerException {
        repository.deleteAllById(ids);
    }

    @Override
    public int getTotalPage(int amount) {
        return repository.findAll(Pageable.ofSize(amount)).getTotalPages();
    }

    @Override
    public List<LichSuNhapHangDTO> getPage(Pageable page) {
        return DTOUtils.getInstance().convertToDTOList(repository
                .findAll(page).stream().toList(), LichSuNhapHangDTO.class);
    }

    @Override
    public List<LichSuNhapHangDTO> searchByKeyword(String keyword) {
        return null;
    }



    @Override
    public List<LichSuNhapHangDTO> getAllByIdSach(Integer id) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAllByIdSach(id), LichSuNhapHangDTO.class);
    }
}
