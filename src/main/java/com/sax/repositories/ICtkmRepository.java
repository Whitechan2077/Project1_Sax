package com.sax.repositories;

import com.sax.entities.Ctkm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICtkmRepository extends JpaRepository<Ctkm, Integer> {
    @Query("SELECT e FROM Ctkm e WHERE CAST(e.id AS string) like %:keyword% or e.tenSuKien LIKE %:keyword%")
    List<Ctkm> findAllByKeyword(@Param("keyword") String keyword);

    @Query("SELECT ct FROM Ctkm ct WHERE CURRENT_TIMESTAMP > ct.ngayKetThuc OR CURRENT_TIMESTAMP < ct.ngayBatDau")
    Page<Ctkm> findNotAvailablePromote(Pageable pageable);

    @Query("SELECT ct FROM Ctkm ct WHERE current_timestamp BETWEEN ct.ngayBatDau AND ct.ngayKetThuc")
    Page<Ctkm> findAvailablePromote(Pageable pageable);
}
