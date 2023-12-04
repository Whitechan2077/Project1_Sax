package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends AbstractDTO implements Serializable  {
    private String username;
    private String password;
    private boolean vaiTro;
    private String tenNhanVien;
    private String anh;
    private String sdt;
    private String email;
    private LocalDateTime ngayDangKi;
    private boolean gioiTinh;
    private Boolean trangThai;
}
