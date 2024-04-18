package com.rdi.offweb3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailRequest {

    @Email
    private String email;

    @NotNull
    private String otpCode;
}
