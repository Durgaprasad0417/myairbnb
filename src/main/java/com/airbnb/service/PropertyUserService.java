package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyOwnerDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;

public interface PropertyUserService {
    PropertyUser createUser(PropertyUserDto dto);

    String verifyLogin(LoginDto loginDto);

    PropertyUser createOwner(PropertyOwnerDto dto1);
}
