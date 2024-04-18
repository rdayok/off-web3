package com.rdi.offweb3.services;

import com.rdi.offweb3.config.security.services.JwtService;
import com.rdi.offweb3.data.models.User;
import com.rdi.offweb3.data.repositories.UserRepository;
import com.rdi.offweb3.dto.UserRegisterRequest;
import com.rdi.offweb3.dto.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ECommerceUserService implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        User registeredUser = userRepository.save(user);
        String token = jwtService.generateAccessToken(registeredUser.getEmail());

        return modelMapper.map(registeredUser, UserRegisterResponse.class);
    }

    @Override
    public User getUserBy(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() ->
                new RuntimeException(String.format("The user with email %s does not exist", username)));
    }
}
