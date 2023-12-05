package com.sax.repositories;

import com.sax.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISachRepository extends JpaRepository<Sach, Integer> {
    @Query("SELECT s FROM Sach s where s.id" +
            " not in (select ct.idSach FROM " +
            "CtkmSach ct " +
            "JOIN Ctkm ctkm on ct.idKM = ctkm.id " +
            "WHERE CURRENT_TIMESTAMP " +
            "< ctkm.ngayKetThuc)")
    List<Sach> findAllSachNotInCTKM();

    @Query("SELECT s FROM Sach s " +
            "LEFT JOIN CtkmSach ctkmSach ON s.id = ctkmSach.idSach " +
            "LEFT JOIN ctkmSach.ctkm giamGia " +
            "WHERE (ctkmSach.idSach IS NULL " +
            "OR CURRENT_TIMESTAMP BETWEEN giamGia.ngayBatDau AND giamGia.ngayKetThuc)")
    List<Sach> findAllSachInOrNotInCTKM();

    Optional<Sach> findByBarCode(String barCode);
    
    @Query("SELECT e FROM Sach e WHERE CAST(e.id AS string) like %:keyword% OR e.tenSach LIKE %:keyword%")
    List<Sach> findAllByKeyword(@Param("keyword") String keyword);

    @Query("select cts.sach from CtkmSach cts " +
            "where CURRENT_TIMESTAMP" +
            " BETWEEN cts.ctkm.ngayBatDau " +
            "AND cts.ctkm.ngayKetThuc ")
    Page<Sach> findAllCtkmSachInAllAvailablePromote(Pageable pageable);

    @Query("select cts.sach from CtkmSach cts " +
            "where CURRENT_TIMESTAMP > cts.ctkm.ngayKetThuc OR CURRENT_TIMESTAMP < cts.ctkm.ngayBatDau ")
    Page<Sach> findAllCtkmSachNotAllAvailablePromote(Pageable pageable);
    List<Sach> findAllByTrangThai(boolean trangThai);
    @Query("SELECT s FROM Sach s JOIN s.CtkmSach JOIN s.chiTietDonHangs where s.id=:id")
    Sach findRelative(@Param("id")int id);
}
