package com.app.slotbookingservice.auth.service;

import com.app.slotbookingservice.auth.dto.AuthResponse;
import com.app.slotbookingservice.user.dto.UserLoginDto;
import com.app.slotbookingservice.user.dto.UserSignUpDto;
import com.app.slotbookingservice.user.entity.User;
import com.app.slotbookingservice.user.repository.UserRepository;
import com.app.slotbookingservice.exception.CustomExceptions.UserRegistrationException;
import com.app.slotbookingservice.utlis.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void registerUser(final UserSignUpDto userSignUpDto) throws Exception {
        log.info("Registering User...");
        if (userRepository.existsByEmail(userSignUpDto.getEmail()))
            throw new UserRegistrationException(AppConstants.USER_ALREADY_EXIST_MSG);
        try {
            User user = userSignUpDtoToUser(userSignUpDto);
            userRepository.save(user);
        } catch (final Exception e) {
            throw new UserRegistrationException(AppConstants.USER_REGISTER_FAILURE_MSG);
        }
    }

    @Override
    public AuthResponse validateUser(final UserLoginDto userLoginDto) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
        );
        User user = (User) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);
        //TO STORE ON CLIENT SIDE
        return AuthResponse.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .token(token)
                .build();
    }

    @Override
    public void blacklistToken(final String token) {
        log.info("Blacklisting token on logout...");
        jwtService.blackListToken(token);
    }

    @Override
    public void verifyAuthDetails(final String token) throws Exception {
        jwtService.validateToken(token.substring(7));
    }

    /**
     * For simplicity I have written mapping logic here only
     */
    private User userSignUpDtoToUser(final UserSignUpDto userSignUpDto) throws Exception {
        return User.builder()
                .name(userSignUpDto.getName())
                .email(userSignUpDto.getEmail())
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .build();
    }

}
