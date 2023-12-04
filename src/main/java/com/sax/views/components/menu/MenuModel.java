package com.sax.views.components.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Data
public class MenuModel {
    private String icon;
    private String iconContrast;
    private String name;

    public MenuModel(String icon, String iconContrast, String name) {
        this.icon = icon;
        this.iconContrast = iconContrast;
        this.name = name;
    }
}
