package com.rdi.offweb3.services;

import com.rdi.offweb3.dto.EmailRequest;

public interface MailService {

    void sendMail(EmailRequest emailRequest);
}
