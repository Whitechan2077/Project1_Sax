package com.sax.repositories;

import com.sax.entities.Ctkm;
import com.sax.entities.CtkmSach;
import com.sax.entities.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICtkmSachRepository extends JpaRepository<CtkmSach,Integer> {
    List<CtkmSach> findAllByCtkm(Ctkm ctkm);
    @Query("select cts from CtkmSach cts WHERE CAST(cts.id AS string) like %:keyword% " +
            "or cts.sach.tenSach LIKE %:keyword% AND cts.ctkm=:ctkm")
    List<CtkmSach> searchAllSachInCtkm(@Param("keyword") String keyword,@Param("ctkm") Ctkm ctkm);

    @Query("SELECT e FROM CtkmSach e WHERE CAST(e.id AS string) like %:keyword% OR e.sach.tenSach LIKE %:keyword% OR e.ctkm.tenSuKien LIKE %:keyword%")
    List<CtkmSach> findAllByKeyword(@Param("keyword") String keyword);

    List<CtkmSach> findAllByIdKM(@Param("id") Integer id);
}
