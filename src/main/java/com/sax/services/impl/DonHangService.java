package com.sax.services.impl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.CtkmDTO;
import com.sax.dtos.DonHangDTO;
import com.sax.dtos.LichSuNhapHangDTO;
import com.sax.entities.*;
import com.sax.repositories.IDonHangChiTietRepository;
import com.sax.repositories.IDonHangRepository;
import com.sax.repositories.ISachRepository;
import com.sax.services.IDonHangService;
import com.sax.utils.DTOUtils;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class DonHangService implements IDonHangService {
    @Autowired
    private IDonHangRepository repository;
    @Autowired
    private IDonHangChiTietRepository donHangChiTietRepository;
    @Autowired
    private ISachRepository sachRepository;


    @Override
    public List<DonHangDTO> getAll() {
        return DTOUtils
                .getInstance()
                .convertToDTOList(repository.findAll(), DonHangDTO.class);
    }

    @Override
    public List<DonHangDTO> getAllByIds(List<Integer> ids) throws SQLServerException {
        return null;
    }

    @Override
    public DonHangDTO getById(Integer id) {
        return DTOUtils
                .getInstance()
                .converter(repository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Khong tim thay")), DonHangDTO.class);
    }

    @Transactional
    @Override
    public DonHangDTO insert(DonHangDTO e) throws SQLServerException {
        DonHang donHang = DTOUtils.getInstance().converter(e, DonHang.class);
        KhachHang khachHang = DTOUtils.getInstance().converter(e.getKhach(), KhachHang.class);
        Account account = DTOUtils.getInstance().converter(e.getAccount(), Account.class);

        donHang.setNgayTao(LocalDateTime.now());
        donHang.setKhachHangByIdKhach(khachHang);
        donHang.setAccount(account);
        DonHang save = null;
        List<ChiTietDonHang> chiTietDonHang = DTOUtils
                .getInstance()
                .convertToDTOList(e.getChiTietDonHangs(), ChiTietDonHang.class);

        List<Sach> sachList = e.getChiTietDonHangs()
                .stream()
                .map(chiTietDonHangDTO -> DTOUtils.getInstance()
                        .converter(chiTietDonHangDTO.getSach(), Sach.class))
                .toList();
        for (int i = 0; i < chiTietDonHang.size(); i++) {
            ChiTietDonHang chiTietDonHang1 = chiTietDonHang.get(i);
            Sach sach = sachList.get(i);
            if (chiTietDonHang1.getSoLuong()<=sach.getSoLuong()){
                save = repository.save(donHang);
                sach.setSoLuong(sach.getSoLuong() - chiTietDonHang1.getSoLuong());
                if (sach.getSoLuong() <= 0) {
                    sach.setTrangThai(false);
                }
                sachRepository.save(sach);
                chiTietDonHang1.setDonHang(save);
                chiTietDonHang1.setSach(sach);
            }
        }
            try {
                donHangChiTietRepository.saveAll(chiTietDonHang);
            }catch (Exception ex){
                throw new InvalidDataAccessApiUsageException("Vượt quá số lượng hàng còn lại");
            }
        return DTOUtils.getInstance().converter(repository.findById(save.getId()).get(), DonHangDTO.class);
    }

    @Override
    public void update(DonHangDTO e) throws SQLServerException {
        repository.save(DTOUtils.getInstance().converter(e, DonHang.class));
    }

    @Override
    public void delete(Integer id) throws SQLServerException {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(Set<Integer> ids) throws SQLServerException {
        repository.deleteAllById(ids);
    }

    @Override
    public int getTotalPage(Pageable page) {
        return repository.findAll(page).getTotalPages();
    }

    @Override
    public List<DonHangDTO> getPage(Pageable page) {
        return DTOUtils
                .getInstance()
                .convertToDTOList(repository.findAll(page)
                        .stream().toList(), DonHangDTO.class);
    }
    @Override
    public List<DonHangDTO> searchByKeyword(String keyword) {
        return DTOUtils.getInstance().convertToDTOList(repository.findAllByKeyword(keyword), DonHangDTO.class);
    }
}
