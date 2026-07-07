package com.orderlist.api.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orderlist.api.exceptions.customs.CustomAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.orderlist.api.exceptions.customs.CustomAuthenticationException.AuthErrorCode;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthenticationEntry entryPoint;

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
