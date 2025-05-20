package com.app.slotbookingservice.booking.service;

import com.app.slotbookingservice.booking.dto.BookingDetailsDto;
import com.app.slotbookingservice.booking.entity.BookingDetails;
import com.app.slotbookingservice.booking.entity.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingMapper {

    public BookingDetailsDto toBookingDetailsDto(final BookingDetails bookingDetails) {
        if (bookingDetails == null) return null;

        return BookingDetailsDto.builder()
                .bookingId(bookingDetails.getId().toString())
                .userName(bookingDetails.getUser() != null ? bookingDetails.getUser().getName() : null)
                .userEmail(bookingDetails.getUser() != null ? bookingDetails.getUser().getEmail() : null)
                .phoneNumber(bookingDetails.getUser() != null ? bookingDetails.getUser().getPhoneNumber() : null)
                .resumeLink(bookingDetails.getResumeLink())
                .targetRole(bookingDetails.getTargetRole())
                .additionalNote(bookingDetails.getAdditionalNote())
                .selectedService(bookingDetails.getSelectedService())
                .date(bookingDetails.getDate())
                .time(bookingDetails.getTime())
                .build();
    }

    public BookingDetails toBookingDetails(final BookingDetailsDto bookingDetailsDto) {
        if (bookingDetailsDto == null)
            return null;

        UserSummary user = UserSummary.builder()
                .name(bookingDetailsDto.getUserName())
                .email(bookingDetailsDto.getUserEmail())
                .phoneNumber(bookingDetailsDto.getPhoneNumber())
                .build();

        return BookingDetails.builder()
                .user(user)
                .resumeLink(bookingDetailsDto.getResumeLink())
                .targetRole(bookingDetailsDto.getTargetRole())
                .additionalNote(bookingDetailsDto.getAdditionalNote())
                .selectedService(bookingDetailsDto.getSelectedService())
                .date(bookingDetailsDto.getDate())
                .time(bookingDetailsDto.getTime())
                .build();
    }

    public List<BookingDetailsDto> toBookingDetailsDtoList(final List<BookingDetails> bookingDetailsList) {
        if (bookingDetailsList == null)
            return null;

        return bookingDetailsList.stream().map(this::toBookingDetailsDto).toList();
    }

}
