package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtkmDTO extends AbstractDTO {
    private String tenSuKien;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private boolean kieuGiamGia;

    @Override
    public String toString() {
        return tenSuKien;
    }
}
