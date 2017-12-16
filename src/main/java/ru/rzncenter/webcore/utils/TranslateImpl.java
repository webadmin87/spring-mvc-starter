package ru.rzncenter.webcore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Перевод сообщений
 */
@Component
public class TranslateImpl implements Translate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateImpl.class);

    private final MessageSource msg;
    private final String locale;

    @Autowired
    public TranslateImpl(MessageSource msg, @Value("${app.locale}") String locale) {
        this.msg = msg;
        this.locale = locale;
    }

    @Override
    public String t(String word) {
        try {
            return msg.getMessage(word, new Object[0], new Locale(locale));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String t(String word, Object[] params) {
        try {
            return msg.getMessage(word, params, new Locale(locale));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}
