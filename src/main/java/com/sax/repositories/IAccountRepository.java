package com.sax.repositories;

import com.sax.entities.Account;
import com.sax.entities.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Integer> {
    Account findByUsername(String username);
    @Query("SELECT e FROM Account e WHERE CAST(e.id AS string) like %:keyword% or e.tenNhanVien LIKE %:keyword%")
    List<Account> findAllByKeyword(@Param("keyword") String keyword);
    Account findByEmail(String email);
}
