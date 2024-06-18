package com.airbnb.dto;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import lombok.Data;

@Data
public class ReviewDto {
    private long id;
    private String content;

//    private PropertyUser propertyUser;

//    private Property property;
}
