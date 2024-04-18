package com.rdi.offweb3.services;

import com.rdi.offweb3.data.models.User;
import com.rdi.offweb3.dto.UserRegisterRequest;
import com.rdi.offweb3.dto.UserRegisterResponse;

public interface UserService {

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    User getUserBy(String username);
}
