package com.app.slotbookingservice.user.service;

import com.app.slotbookingservice.user.repository.UserRepository;
import com.app.slotbookingservice.utlis.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
         return userRepository.findByEmail(username)
                 .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));
    }

}
