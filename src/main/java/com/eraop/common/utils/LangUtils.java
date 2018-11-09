package com.eraop.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author weiyi
 */
@Component
public class LangUtils {
    private static MessageSource messageSource;
    @Resource
    private MessageSource _messageSource;

    @PostConstruct
    private void init() {
        messageSource = _messageSource;
    }

    public static String language(String key, Object[] args, String defaultValue) {
        return messageSource.getMessage(key, args, defaultValue, LocaleContextHolder.getLocale());
    }

    public static String language(String key, Object... args) {
        return language(key, args, null);
    }

}
