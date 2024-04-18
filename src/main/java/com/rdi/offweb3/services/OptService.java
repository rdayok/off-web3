package com.rdi.offweb3.services;

public interface OptService {

    void generateOtp(String userEmail, String firstName);

    void validateOtpCode(String email, String otpCode);
}
