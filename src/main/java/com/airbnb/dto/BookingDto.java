package com.airbnb.dto;

import lombok.Data;

@Data
public class BookingDto {
    private long bookingId;

    private  String guestName;

    private int price;
    private int totalPrice;
}
