package com.sax.utils;

import com.sax.configs.JpaConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class ContextUtils {
    private static  AnnotationConfigApplicationContext context;

    static {
        context = new AnnotationConfigApplicationContext(JpaConfig.class);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
