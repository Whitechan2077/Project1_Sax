package com.sax.views.components;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;

public class ComboBoxSearch extends JComboBox {
    public ComboBoxSearch() {
        AutoCompleteDecorator.decorate(this);
    }
}
