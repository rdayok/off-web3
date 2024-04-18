package com.rdi.offweb3.services;

import com.rdi.offweb3.data.models.Otp;
import com.rdi.offweb3.data.repositories.OtpRepository;
import com.rdi.offweb3.dto.EmailRequest;
import com.rdi.offweb3.dto.Recipient;
import com.rdi.offweb3.exceptions.OtpCodeIsNotValidException;
import com.rdi.offweb3.exceptions.OtpDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class OptServiceImp implements OptService{

    private final OtpRepository otpRepository;

    private final MailService mailService;

    @Override
    public void generateOtp(String userEmail, String userFirstName ) {
        String sixDigitOtpCode = generateSixDigitOtpCode();
        Otp otp = new Otp();
        otp.setEmail(userEmail);
        otp.setOtpCode(sixDigitOtpCode);
        emailOtpCode(userEmail, otp.getOtpCode(), userFirstName);
        otpRepository.save(otp);
    }

    private void emailOtpCode(String userEmail, String otpCode, String userFirstName) {
        Recipient recipient = new Recipient();
        recipient.setEmail(userEmail);
        recipient.setName(userFirstName);
        List<Recipient> recipients = List.of(
                recipient
        );
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipients(recipients);
        emailRequest.setSubject("User email verification");
        emailRequest.setHtmlContent(
                String.format(
                        "<p>Welcome to OffWeb3. \n Here is your confirmation code %s",
                        otpCode
                )
        );
        mailService.sendMail(emailRequest);
    }

    @Override
    public void validateOtpCode(String email, String givenOtpCode) {
        Otp foundOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new OtpDoesNotExistException("This otp does not exist"));
        String otpCode = foundOtp.getOtpCode();
        if(!Objects.equals(otpCode, givenOtpCode))
            throw new OtpCodeIsNotValidException(String.format("The otp code %s is not valid", givenOtpCode));
    }

    private String generateSixDigitOtpCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) builder.append(secureRandom.nextInt(10));
        return builder.toString();
    }
}
