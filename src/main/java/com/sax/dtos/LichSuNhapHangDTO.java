package com.sax.dtos;

import lombok.*;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LichSuNhapHangDTO extends AbstractDTO {
    SachDTO sach;
    private long giaNhap;
    private LocalDateTime ngayNhap;
    private Integer soLuong;
}
