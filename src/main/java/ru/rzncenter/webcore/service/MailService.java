package ru.rzncenter.webcore.service;

import java.util.Map;

/**
 * Интерфейс компонента для отправки почты
 */
public interface MailService {

    boolean sendMail(String to, String subject, String tpl, Map<String, Object> params);

}
