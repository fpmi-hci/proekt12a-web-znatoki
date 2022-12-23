package com.bsu.znatoki.service;

import javax.mail.MessagingException;

public interface MailSenderService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
}
