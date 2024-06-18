package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyOwnerDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PropertyUserServiceImpl implements PropertyUserService {
    private  PropertyUserRepository repository;
    private JWTService jwtService;

    @Override
    public PropertyUser createUser(PropertyUserDto dto) {
        PropertyUser propertyUser = new PropertyUser();
        propertyUser.setFirstName(dto.getFirstName());
        propertyUser.setLastName(dto.getLastName());
        propertyUser.setUsername(dto.getUsername());
        propertyUser.setEmail(dto.getEmail());
        propertyUser.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        propertyUser.setUserRole("ROLE_USER");
        return repository.save(propertyUser);
    }

    @Override
    public PropertyUser createOwner(PropertyOwnerDto dto1) {
        PropertyUser propertyUser = new PropertyUser();
        propertyUser.setFirstName(dto1.getFirstName());
        propertyUser.setLastName(dto1.getLastName());
        propertyUser.setUsername(dto1.getUsername());
        propertyUser.setEmail(dto1.getEmail());
        propertyUser.setPassword(BCrypt.hashpw(dto1.getPassword(),BCrypt.gensalt(10)));
        propertyUser.setUserRole("ROLE_OWNER");
        return repository.save(propertyUser);
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> byUsername = repository.findByUsername(loginDto.getUsername());
        if(byUsername.isPresent()){
            PropertyUser propertyUser = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
               return jwtService.generateToken(propertyUser);
            }

        }
        return null;

    }


}
