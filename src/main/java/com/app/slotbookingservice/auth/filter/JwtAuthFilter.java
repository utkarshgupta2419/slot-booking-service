package com.app.slotbookingservice.auth.filter;

import com.app.slotbookingservice.auth.service.JwtService;
import com.app.slotbookingservice.user.service.CustomUserDetailsService;
import com.app.slotbookingservice.utlis.AppConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsAny(request.getServletPath(), "/api/v1/auth/login", "/api/v1/auth/sign-up")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        if (jwtService.isBlackListedToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(AppConstants.INVALID_TOKEN);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
                && !request.getServletPath().equals("/api/v1/auth/logout")) {
            if (jwtService.isTokenValid(jwt, username) ) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
