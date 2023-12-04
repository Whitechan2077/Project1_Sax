package com.sax.utils;

import java.text.DecimalFormat;

public class CurrencyConvert {
    public static String parseString(long price) {
        return new DecimalFormat("#,###").format(price).replace(",", ".") + "đ";
    }
    public static String parseString(String price) {
        return new DecimalFormat("#,###").format(price).replace(",", ".") + "đ";
    }

    public static long parseLong(String price) {
        return Long.parseLong(price.replace(".","").replace("đ",""));
    }
}
