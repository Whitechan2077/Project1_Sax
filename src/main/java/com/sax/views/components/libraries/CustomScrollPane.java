package com.sax.views.components.libraries;

import javax.swing.*;

public class CustomScrollPane extends JScrollPane {
    public CustomScrollPane() {
        setVerticalScrollBar(new com.sax.views.components.libraries.ScrollBar());
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
