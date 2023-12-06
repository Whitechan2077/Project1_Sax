package com.sax.services.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.DonHangDTO;
import com.sax.dtos.KhachHangDTO;
import com.sax.entities.KhachHang;
import com.sax.repositories.IDonHangRepository;
import com.sax.repositories.IKhachHangRepository;
import com.sax.services.IKhachHangService;
import com.sax.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class KhachHangService implements IKhachHangService {
    @Autowired
    private IKhachHangRepository repository;
    @Autowired
    IDonHangRepository donHangRepository;

    @Override
    public List<KhachHangDTO> getAll() {
        return DTOUtils.getInstance().convertToDTOList(repository.findAll(),KhachHangDTO.class);
    }

    @Override
    public List<KhachHangDTO> getAllByIds(List<Integer> ids) throws SQLServerException {
        return null;
    }

    @Override
    public KhachHangDTO getById(Integer id) {
        return DTOUtils.getInstance()
                .converter(repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ko tim thay")),KhachHangDTO.class);
    }

    @Override
    public KhachHangDTO insert(KhachHangDTO e) throws SQLServerException {
        KhachHang khachHang = DTOUtils.getInstance().converter(e,KhachHang.class);
        khachHang.setNgayThem(LocalDateTime.now());
        return DTOUtils.getInstance().converter(repository.save(khachHang),KhachHangDTO.class);
    }

    @Override
    public void update(KhachHangDTO e) throws SQLServerException {
        repository.save(DTOUtils.getInstance().converter(e,KhachHang.class));
    }


    @Override
    public void delete(Integer id) throws SQLServerException {
        repository.deleteById(id);
    }


    @Override
    public void deleteAll(Set<Integer> ids) throws SQLServerException {
        boolean check = true;
        StringBuilder name = new StringBuilder("Sách");
        for (Integer x : ids) {
            KhachHang e = repository.findRelative(x);
            if (e == null)repository.deleteById(x);
            else {
                name.append(" ").append(e.getTenKhach()).append(", ");
                check = false;
            }
        }
        if (!check) throw new DataIntegrityViolationException(name + " .Không thể xoá, do khách đã mua hàng!");
    }

    @Override
    public int getTotalPage(int amount) {
        return repository.findAll(Pageable.ofSize(amount)).getTotalPages();
    }

    @Override
    public List<KhachHangDTO> getPage(Pageable page) {
        return DTOUtils.getInstance()
                .convertToDTOList(repository.findAll(page)
                        .stream()
                        .toList(), KhachHangDTO.class);
    }

    @Override
    public List<KhachHangDTO> searchByKeyword(String keyword) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAllByKeyword(keyword),KhachHangDTO.class);
    }


    @Override
    public void addPoint(DonHangDTO donHangDTO) {
        KhachHang khachHang = DTOUtils.getInstance().converter(donHangDTO.getKhach(), KhachHang.class);
        if (donHangDTO.getTongTien()>200000&&khachHang.getDiem()>-1) khachHang.setDiem(khachHang.getDiem()+1);
        repository.save(khachHang);
    }

    @Override
    public void usePoint(DonHangDTO donHangDTO, int point) {
            KhachHang khachHang = DTOUtils.getInstance().converter(donHangDTO.getKhach(), KhachHang.class);
            if (khachHang.getDiem()!=-1)  khachHang.setDiem(point);
            repository.save(khachHang);
    }
}
