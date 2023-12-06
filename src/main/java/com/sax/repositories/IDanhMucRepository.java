package com.sax.repositories;

import com.sax.entities.DanhMuc;
import com.sax.entities.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDanhMucRepository extends JpaRepository<DanhMuc,Integer> {
    public List<DanhMuc> findAllByDanhMucCha(DanhMuc danhMuc);

    @Query("SELECT e FROM DanhMuc e WHERE CAST(e.id AS string) like %:keyword% or e.tenDanhMuc LIKE %:keyword%")
    List<DanhMuc> findAllByKeyword(@Param("keyword") String keyword);
}
