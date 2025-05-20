package com.app.slotbookingservice.booking.service;

import com.app.slotbookingservice.booking.dto.BookingDetailsDto;

import java.util.List;

public interface BookingService {

    BookingDetailsDto bookASlot(BookingDetailsDto bookingDetailsDto) throws Exception;

    List<BookingDetailsDto> getBookings() throws Exception;

    List<BookingDetailsDto> getBookingsByEmail(String email, boolean upcoming) throws Exception;

    BookingDetailsDto getBookingsByBookingId(String id) throws Exception;
}
