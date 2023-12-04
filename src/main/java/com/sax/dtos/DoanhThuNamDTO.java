package com.sax.dtos;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoanhThuNamDTO extends AbstractThongKe{
    private int thang;


    public DoanhThuNamDTO(Long tongTien, Long chiPhi, int thang) {
        super(tongTien, chiPhi);
        this.thang = thang;
    }
}
