package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDTO extends AbstractDTO {
    private String tenKhach;
    private String sdt;
    private Integer diem;
    private LocalDateTime ngayThem;
    private boolean gioiTinh;

    public KhachHangDTO(String tenKhach, String sdt, Integer diem, boolean gioiTinh) {
        this.tenKhach = tenKhach;
        this.sdt = sdt;
        this.diem = diem;
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return id + " - " + tenKhach;
    }
}
