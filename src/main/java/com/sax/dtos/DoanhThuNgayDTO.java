package com.sax.dtos;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoanhThuNgayDTO extends AbstractThongKe{
    private int ngay;

    public DoanhThuNgayDTO(Long tongTien, Long chiPhi, int ngay) {
        super(tongTien, chiPhi);
        this.ngay = ngay;
    }
}
