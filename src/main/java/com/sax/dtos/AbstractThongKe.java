package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbstractThongKe {
    protected Long tongTien;
    protected Long chiPhi;
    protected Long loiNhuan;

    public AbstractThongKe(Long tongTien, Long chiPhi) {
        this.tongTien = tongTien;
        this.chiPhi = chiPhi;
    }

    public Long tinhLoiNhuan(){
        return this.loiNhuan= tongTien - chiPhi;
    }
}
