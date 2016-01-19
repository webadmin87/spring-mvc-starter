package ru.rzncenter.webcore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.rzncenter.webcore.web.Translate;

import java.util.Locale;

/**
 * Перевод сообщений
 */
@Component
public class TranslateImpl implements Translate {

    @Autowired
    MessageSource msg;

    @Value("#{props['app.locale']}")
    String locale;

    @Override
    public String t(String word) {

        return msg.getMessage(word, new Object[0], new Locale(locale));

    }

    @Override
    public String t(String word, Object[] params) {

        return msg.getMessage(word, params, new Locale(locale));

    }

}
