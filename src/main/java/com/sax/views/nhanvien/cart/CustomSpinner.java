package com.sax.views.nhanvien.cart;

import javax.swing.*;
import java.awt.*;

public class CustomSpinner extends JSpinner {
    public CustomSpinner() {
        setValue(1);
        ((SpinnerNumberModel)getModel()).setMinimum(1);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
