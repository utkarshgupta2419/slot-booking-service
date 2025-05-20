package com.app.slotbookingservice.booking.dto;

import com.app.slotbookingservice.booking.enums.ServiceType;
import com.app.slotbookingservice.booking.enums.TargetRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {

    private String paymentId;
    private String bookingId;
    private String userName;
    private String userEmail;
    private Long phoneNumber;

    private String resumeLink;
    private TargetRole targetRole;
    private String additionalNote;
    private ServiceType selectedService;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
}