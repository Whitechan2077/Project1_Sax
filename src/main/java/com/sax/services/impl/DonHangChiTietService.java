package com.sax.services.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.ChiTietDonHangDTO;
import com.sax.dtos.DonHangDTO;
import com.sax.entities.DonHang;
import com.sax.repositories.IDonHangChiTietRepository;
import com.sax.services.IDonHangChiTetService;
import com.sax.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DonHangChiTietService implements IDonHangChiTetService {
    @Autowired
    private IDonHangChiTietRepository repository;

    @Override
    public List<ChiTietDonHangDTO> getAll() {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(), ChiTietDonHangDTO.class);
    }

    @Override
    public List<ChiTietDonHangDTO> getAllByIds(List<Integer> ids) throws SQLServerException {
        return null;
    }

    @Override
    public ChiTietDonHangDTO getById(Integer id) {
        return DTOUtils.getInstance()
                .converter(repository.findById(id)
                        .orElseThrow(()
                                -> new NoSuchElementException("Khong tim thay")), ChiTietDonHangDTO.class);
    }
    @Override
    public ChiTietDonHangDTO insert(ChiTietDonHangDTO e) throws SQLServerException {
        return null;
    }


    @Override
    public void update(ChiTietDonHangDTO e) throws SQLServerException {

    }

    @Override
    public void delete(Integer id) throws SQLServerException {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(Set<Integer> ids) throws SQLServerException {

    }

    @Override
    public int getTotalPage(int amount) {
        return repository.findAll(Pageable.ofSize(amount)).getTotalPages();
    }

    @Override
    public List<ChiTietDonHangDTO> getPage(Pageable page) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(page).stream().toList(), ChiTietDonHangDTO.class);
    }

    @Override
    public List<ChiTietDonHangDTO> searchByKeyword(String keyword) {
        return null;
    }


    @Override
    public List<ChiTietDonHangDTO> getAllByDonHang(DonHangDTO donHangDTO) {
        return DTOUtils.getInstance()
                .convertToDTOList(repository.findAllByDonHang(DTOUtils.getInstance().
                        converter(donHangDTO, DonHang.class)),ChiTietDonHangDTO.class);
    }
}
