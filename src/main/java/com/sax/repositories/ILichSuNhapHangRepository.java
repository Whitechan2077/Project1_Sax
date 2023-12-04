package com.sax.repositories;

import com.sax.dtos.LichSuNhapHangDTO;
import com.sax.entities.LichSuNhapHang;
import com.sax.entities.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ILichSuNhapHangRepository extends JpaRepository<LichSuNhapHang, Integer> {
    public List<LichSuNhapHang> findAllByIdSach(@Param("id") Integer id);

    @Query("SELECT e FROM LichSuNhapHang e WHERE CAST(e.ngayNhap AS string) like %:keyword% OR cast(e.giaNhap as string) LIKE %:keyword%")
    List<LichSuNhapHang> findAllByKeyword(@Param("keyword") String keyword);
}
