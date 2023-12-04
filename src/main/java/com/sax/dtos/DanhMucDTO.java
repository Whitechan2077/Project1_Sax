package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class DanhMucDTO extends AbstractDTO {
    private String tenDanhMuc;
    private DanhMucDTO danhMucCha;
    private String ghiChu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DanhMucDTO that = (DanhMucDTO) o;
        return id == that.id;
    }
    @Override
    public String toString() {
        return tenDanhMuc;
    }
}
