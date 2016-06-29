package ru.rzncenter.webcore.service;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Сервис отправки email сообщений
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    JavaMailSender mailSender;

    @Value("${mailserver.email}")
    String mailFrom;

    @Autowired
    VelocityEngine velocityEngine;

    @Override
    public boolean sendMail(String to, String subject, String tpl, Map<String, Object> params) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, tpl, "utf-8", params);
            helper.setText(body, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            logger.error("Send mail error", e);
            return false;
        }
    }
}
