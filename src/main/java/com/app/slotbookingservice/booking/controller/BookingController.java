package com.app.slotbookingservice.booking.controller;

import com.app.slotbookingservice.booking.dto.BookingDetailsDto;
import com.app.slotbookingservice.booking.service.BookingService;
import com.app.slotbookingservice.common.AbstractController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingController extends AbstractController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> bookASlot(@Valid @RequestBody BookingDetailsDto bookingDetailsDto) throws Exception {
        return generateResponse(bookingService.bookASlot(bookingDetailsDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listBookings() throws Exception {
        return generateResponse(bookingService.getBookings(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getBookingsForAUser(@PathVariable("email") String email,
                                                 @RequestParam(value = "upcoming",
                                                         required = false, defaultValue = "true") boolean upcoming) throws Exception {
        return generateResponse(bookingService.getBookingsByEmail(email, upcoming), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") String id) throws Exception {
        return generateResponse(bookingService.getBookingsByBookingId(id), HttpStatus.OK);
    }

}
