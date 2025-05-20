package com.app.slotbookingservice.auth.controller;

import com.app.slotbookingservice.user.dto.UserLoginDto;
import com.app.slotbookingservice.user.dto.UserSignUpDto;
import com.app.slotbookingservice.auth.service.AuthService;
import com.app.slotbookingservice.common.AbstractController;
import com.app.slotbookingservice.utlis.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends AbstractController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpDto userSignUpDto) throws Exception {
        authService.registerUser(userSignUpDto);
        return generateResponse(AppConstants.USER_REGISTER_SUCCESS_MSG, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto) throws Exception {
        return generateResponse(authService.validateUser(userLoginDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.blacklistToken(token);
        }
        SecurityContextHolder.clearContext();
        return generateResponse(AppConstants.LOG_OUT_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestHeader("Authorization") String authHeader) throws Exception {
        authService.verifyAuthDetails(authHeader);
        return generateResponse(true, HttpStatus.OK);
    }

}
