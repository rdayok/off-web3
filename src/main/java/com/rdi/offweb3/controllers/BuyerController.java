package com.rdi.offweb3.controllers;

import com.rdi.offweb3.dto.UserRegisterRequest;
import com.rdi.offweb3.dto.UserRegisterResponse;
import com.rdi.offweb3.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class BuyerController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRegisterResponse> registerBuyer(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.status(CREATED).body(userService.register(userRegisterRequest));
    }
}
