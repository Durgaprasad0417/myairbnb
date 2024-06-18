package com.airbnb.controller;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyOwnerDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.PropertyUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/propertyUser")
@AllArgsConstructor
public class PropertyUserController {
    private PropertyUserService service;
    private PropertyUserRepository repository;

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@RequestBody PropertyUserDto dto) {

        PropertyUser user = service.createUser(dto);
        if (user != null) {
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("already a user", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/owner")
    public ResponseEntity<?> createUser(@RequestBody PropertyOwnerDto dto1) {

        PropertyUser owner = service.createOwner(dto1);
        if (owner != null) {
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("already a owner", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) {
        String token  = service.verifyLogin(loginDto);
        if (token!=null) {
            TokenResponse tokenResponse= new TokenResponse();
            tokenResponse.setToken(token);
           return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("user/password is incorrect", HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;
    }
}

