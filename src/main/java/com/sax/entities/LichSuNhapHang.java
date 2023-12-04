package com.sax.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lich_su_nhap_hang", schema = "dbo", catalog = "SaX")
public class LichSuNhapHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "ngay_nhap",nullable = false)
    private LocalDateTime ngayNhap;
    @Basic
    @Column(name = "gia_nhap",nullable = false)
    private long giaNhap;
    @Basic
    @Column(name = "so_luong",nullable = false)
    private int soLuong;
    @Basic
    @Column(name = "id_sach",insertable = false, updatable = false)
    private int idSach;
    @ManyToOne
    @JoinColumn(name = "id_sach" ,referencedColumnName = "id",nullable = false)
    private Sach sach;
}
