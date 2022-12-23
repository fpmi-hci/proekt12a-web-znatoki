package com.bsu.znatoki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:mail.yml")
public class MailConfig {
    public static final String MAIL_HOST_PROP = "mail.smtp.host";
    public static final String MAIL_PORT_PROP = "mail.smtp.port";
    public static final String MAIL_AUTH_PROP = "mail.smtp.auth";
    public static final String MAIL_STARTTLS_PROP = "mail.smtp.starttls.enable";
    public static final String MAIL_USER_PROP = "user";
    public static final String MAIL_PASSWORD_PROP = "password";

    @Value("${host}")
    private String host;
    @Value("${port}")
    private String port;
    @Value("${auth}")
    private String auth;
    @Value("${enable}")
    private String enable;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    @Bean
    public Properties mailProperties() {
        Properties props = new Properties();
        props.put(MAIL_HOST_PROP, host);
        props.put(MAIL_PORT_PROP, port);
        props.put(MAIL_AUTH_PROP, auth);
        props.put(MAIL_STARTTLS_PROP, enable);
        props.put(MAIL_USER_PROP, user);
        props.put(MAIL_PASSWORD_PROP, password);
        return props;
    }
}
