package com.rdi.offweb3.data.models;

import com.rdi.offweb3.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.rdi.offweb3.enums.Role.USER;

@Entity
@Setter
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = USER;

    private LocalDateTime dateRegistered;

    @PrePersist
    public void setDateRegistered() {
        dateRegistered = LocalDateTime.now();
    }
}
