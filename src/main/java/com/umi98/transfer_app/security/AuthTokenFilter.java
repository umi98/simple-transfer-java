package com.umi98.transfer_app.security;

import com.umi98.transfer_app.service.AppUserService;
import com.umi98.transfer_app.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AppUserService appUserService;

    private Set<String> blackList = new HashSet<>();

    public void blacklistToken(String token) {
        blackList.add(token);
    }
    public Boolean isTokenBlacklisted(String token) {
        return blackList.contains(token);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String headerAuth = request.getHeader("Authorization");
            String token = null;
            Boolean isTokenBlacklisted = null;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                token = headerAuth.substring(7);
                isTokenBlacklisted = isTokenBlacklisted(token);
            }
            if (token != null && jwtUtil.verifyJwtToken(token) && !isTokenBlacklisted) {
                Map<String, String> userInfo = jwtUtil.getUserInfoByToken(token);
                UserDetails user = appUserService.loadUserByUserId(userInfo.get("userId"));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user auth: {}", e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
