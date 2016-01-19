package ru.rzncenter.webcore.service;

import java.util.Map;

/**
 * Created by anton on 22.06.15.
 */
public interface MailService {

    public boolean sendMail(String to, String subject, String tpl, Map<String, Object> params);

}
