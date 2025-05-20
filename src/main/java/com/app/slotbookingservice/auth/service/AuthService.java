package com.app.slotbookingservice.auth.service;

import com.app.slotbookingservice.auth.dto.AuthResponse;
import com.app.slotbookingservice.user.dto.UserLoginDto;
import com.app.slotbookingservice.user.dto.UserSignUpDto;

public interface AuthService {

    void registerUser(UserSignUpDto userSignUpDto) throws Exception;

    AuthResponse validateUser(UserLoginDto userLoginDto) throws Exception;

    void blacklistToken(String token);

    void verifyAuthDetails(String authHeader) throws Exception;

}
