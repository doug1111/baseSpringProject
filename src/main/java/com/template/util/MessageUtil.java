package com.template.util;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 多语言资源工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class MessageUtil extends ResourceBundleMessageSource {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource source) {
        messageSource = source;
    }

    public MessageUtil() {
        super();
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String pvsKey) {
        try {
            return messageSource.getMessage(pvsKey, null, LocaleContextHolder.getLocale());
        }
        catch (Exception e) {
            return pvsKey;
        }
    }
}
