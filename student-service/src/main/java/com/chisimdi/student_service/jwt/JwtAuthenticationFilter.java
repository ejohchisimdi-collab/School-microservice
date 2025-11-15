package com.chisimdi.student_service.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwUtil jwUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");

        if(authHeader!=null&&authHeader.startsWith("Bearer ")){
          String token=  authHeader.substring(7);
            if(jwUtil.isTokenValid(token)){
                String userName= jwUtil.extractUsername(token);
                int userId=jwUtil.extractUserId(token);
                String role=jwUtil.extractRoles(token);

                CustomUserPrincipal customUserPrincipal=new CustomUserPrincipal(userId,role,userName);
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(customUserPrincipal,null, List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}
