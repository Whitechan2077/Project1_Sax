package com.sax.dtos;

import com.sax.entities.Account;
import com.sax.entities.ChiTietDonHang;
import com.sax.entities.KhachHang;
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
public class DonHangDTO extends AbstractDTO {
    private KhachHangDTO khach;
    private AccountDTO account;
    private Long tongTien;
    private LocalDateTime ngayTao;
    private Boolean pttt;
    private long chietKhau;
    private long tienHang;
    private List<ChiTietDonHangDTO> chiTietDonHangs;
}
