package com.rdi.offweb3.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailConfig {

    @Value("${mail.api.key}")
    private String brevoApiKey;

    @Value("${brevo.mail.url}")
    private String brevoUrl;
}
