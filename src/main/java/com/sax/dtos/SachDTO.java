package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SachDTO extends AbstractDTO {
    private String barCode;
    private String tenSach;
    private Long giaBan;
    private Integer soLuong;
    private LocalDateTime ngayThem;
    private LocalDateTime ngaySua;
    private Boolean trangThai;
    private String hinhAnh;
    private String nxb;
    private Set<DanhMucDTO> setDanhMuc;
    private long giaGiam;

    public SachDTO(int id, String tenSach, Long giaBan, Integer soLuong, String hinhAnh) {
        super(id);
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public SachDTO(int id, String barCode, String tenSach, Long giaBan, Integer soLuong, Boolean trangThai, String hinhAnh, String nxb, Set<DanhMucDTO> setDanhMuc) {
        super(id);
        this.barCode = barCode;
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.nxb = nxb;
        this.setDanhMuc = setDanhMuc;
    }

    public SachDTO(String barCode, String tenSach, Long giaBan, Integer soLuong, LocalDateTime ngayThem, LocalDateTime ngaySua, Boolean trangThai, String hinhAnh, String nxb, Set<DanhMucDTO> setDanhMuc) {
        this.barCode = barCode;
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.ngayThem = ngayThem;
        this.ngaySua = ngaySua;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.nxb = nxb;
        this.setDanhMuc = setDanhMuc;
    }

    @Override
    public String toString() {
        if (id == 0) return "-Nhấn vào để chọn sách-";
        return id + " - " + tenSach;
    }
}
