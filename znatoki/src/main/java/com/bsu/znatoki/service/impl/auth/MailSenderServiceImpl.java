package com.bsu.znatoki.service.impl.auth;

import com.bsu.znatoki.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final Properties mailProperties;
    private static final String USER_PROP_NAME = "user";
    private static final String PASSWORD_PROP_NAME = "password";

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Session session = Session.getInstance(mailProperties, getAuthenticator());
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailProperties.getProperty(USER_PROP_NAME)));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }

    private Authenticator getAuthenticator() {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getProperty(USER_PROP_NAME),
                        mailProperties.getProperty(PASSWORD_PROP_NAME));
            }
        };
    }
}
