package com.chisimdi.enrollment_service.jwutils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwUtil jwUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Authenticated=request.getHeader("Authorization");
        if(Authenticated!=null&&Authenticated.startsWith("Bearer ")){
            String token=Authenticated.substring(7);
if(jwUtil.isTokenValid(token)){
            String username=jwUtil.extractUsername(token);
            int userId=jwUtil.extractUserId(token);
            String role= jwUtil.extractRoles(token);

            CustomUserPrincipal customUserPrincipal=new CustomUserPrincipal(userId,role,username);
            UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(customUserPrincipal,null, List.of(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(auth);}
        }
        filterChain.doFilter(request,response);


    }
}
