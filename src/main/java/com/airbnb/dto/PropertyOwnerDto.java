package com.airbnb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyOwnerDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String userRole;

}