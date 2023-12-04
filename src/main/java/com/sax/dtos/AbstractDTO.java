package com.sax.dtos;

import lombok.Data;

@Data
public abstract class AbstractDTO {
    protected int id;

    public AbstractDTO(int id) {
        this.id = id;
    }

    public AbstractDTO() {
    }
}
