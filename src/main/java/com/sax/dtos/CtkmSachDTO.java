package com.sax.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtkmSachDTO extends AbstractDTO {
    private SachDTO sach;
    private CtkmDTO ctkm;
    private Long giaTriGiam;
}
