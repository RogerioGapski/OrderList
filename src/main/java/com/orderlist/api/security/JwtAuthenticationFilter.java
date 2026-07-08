package com.orderlist.api.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orderlist.api.exceptions.customs.CustomAuthenticationException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.orderlist.api.exceptions.customs.CustomAuthenticationException.AuthErrorCode;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthenticationEntry entryPoint;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

    String token = extractToken(request);

    try {
        if(token == null)
            throw new CustomAuthenticationException(AuthErrorCode.TOKEN_MISSING);

        String userId = jwtService.validateToken(token);
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    } catch (TokenExpiredException e) {
        entryPoint.commence(
                request, response, new  CustomAuthenticationException(AuthErrorCode.TOKEN_EXPIRED));
    } catch (JWTVerificationException e) {
        entryPoint.commence(
                request, response, new CustomAuthenticationException(AuthErrorCode.TOKEN_INVALID));
    } catch (CustomAuthenticationException e) {
        entryPoint.commence(
                request, response, e);
    }
}

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
