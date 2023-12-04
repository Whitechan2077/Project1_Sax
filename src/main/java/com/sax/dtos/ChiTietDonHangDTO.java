package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangDTO extends AbstractDTO {
    private SachDTO sach;
    private long giaGiam;
    private long giaBan;
    private Integer soLuong;
    private String ghichu;
}
