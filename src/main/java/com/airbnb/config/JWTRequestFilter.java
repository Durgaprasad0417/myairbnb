package com.airbnb.config;

import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    //OncePerRequestFilter it is abstract class it has
    //inbuilt methods add that method
    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8,tokenHeader.length()-1);
            //upto here we r coping the Token it is coming
            System.out.println(token);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(username);
            if(opUser.isPresent()){
                PropertyUser propertyUser = opUser.get();
                //for server to understand who is the current user
                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(propertyUser,null, Collections.singleton(new SimpleGrantedAuthority(propertyUser.getUserRole())));
                //asnw
                authentication.setDetails(new WebAuthenticationDetails(request));//creating a session
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request,response);
    }

}
