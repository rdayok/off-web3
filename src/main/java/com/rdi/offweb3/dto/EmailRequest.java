package com.rdi.offweb3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class EmailRequest{

    private final Sender sender;

    @JsonProperty("to")
    private List<Recipient> recipients;

    private String subject;

    private String htmlContent;

    public EmailRequest() {
        this.sender = new Sender("OffWeb3.inc","OffWeb3e@gmail.com");
    }

}
