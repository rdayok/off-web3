package com.rdi.offweb3.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Otp {

        @Id
        @UuidGenerator
        private String id;

        private String email;

        private String otpCode;

        private LocalDateTime createdAt=LocalDateTime.now();

        private LocalDateTime confirmedAt;





}
