package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private PdfService pdfService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, PdfService pdfService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
    }
//    @PostMapping("/createBooking")
//    public ResponseEntity<Booking> addBooking(
//            @RequestBody Booking booking,
//            @AuthenticationPrincipal PropertyUser user) {
//        booking.setPropertyUser(user);
//        Property property = booking.getProperty();
//        Long propertyId = property.getId();
//        Property completePropertyInfo = propertyRepository.findById(propertyId).get();
//        Booking createdBooking = bookingRepository.save(booking);
//        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    //INSTEAD OF ALL THESE WE JUST SUPPLY {propertyId} THAT IS ENOUGH
    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<?> addBooking(
            @RequestBody Booking booking,
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable long propertyId) {
        booking.setPropertyUser(user);//settingup user
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        int propertyNightlyPrice = property.getNightlyPrice();
        int totalNights = booking.getTotalNights();
        int totalPrice = propertyNightlyPrice * totalNights;
        booking.setProperty(property);//for which property
        booking.setTotalPrice(totalPrice);//setting up rate

        Booking createdBooking = bookingRepository.save(booking);
        BookingDto dto = new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propertyNightlyPrice);
        dto.setTotalPrice(createdBooking.getTotalPrice());
        //Create PDF with Booking Confirmation
        boolean b = pdfService.generatePdf("F://jan//" + "booking-confirmation-id" + createdBooking.getId() + ".pdf", dto);
        if (b) {// upload your file into bucket
            System.out.println("F://jan//" + "booking-confirmation-id" + createdBooking.getId() + ".pdf");
        }else {
            return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
