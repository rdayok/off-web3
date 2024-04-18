package com.rdi.offweb3.services;

import com.rdi.offweb3.config.MailConfig;
import com.rdi.offweb3.dto.EmailRequest;
import com.rdi.offweb3.dto.EmailResponse;
import com.rdi.offweb3.exceptions.SendEmailNotSuccessfulException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final MailConfig mailconfig;

    @Override
    public void sendMail(EmailRequest emailRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.set("accept", APPLICATION_JSON_VALUE);
        httpHeaders.set("api-key", mailconfig.getBrevoApiKey());
        HttpEntity<EmailRequest> requestHttpEntity = new RequestEntity<>(
                emailRequest,httpHeaders,POST, URI.create("")
        );
        ResponseEntity<EmailResponse> responseEntity =
                restTemplate.postForEntity(mailconfig.getBrevoUrl(),requestHttpEntity,EmailResponse.class);
        var emailResponse = responseEntity.getBody();
        assert emailResponse != null;
        emailResponse.setStatusCode(responseEntity.getStatusCode().value());
        boolean isSendEmailNotSuccessful = emailResponse.getStatusCode() != 201;
        if(isSendEmailNotSuccessful)
            throw new SendEmailNotSuccessfulException(
                    String.format("Sending email to %s was not successful", emailRequest.getRecipients().toString())
            );
    }


}
