package com.app.slotbookingservice.booking.service;

import com.app.slotbookingservice.booking.dto.BookingDetailsDto;
import com.app.slotbookingservice.booking.entity.BookingDetails;
import com.app.slotbookingservice.booking.repository.BookingRepository;
import com.app.slotbookingservice.exception.CustomExceptions.BookingRegistrationException;
import com.app.slotbookingservice.exception.CustomExceptions.RazorpayPaymentVerificationException;
import com.app.slotbookingservice.notifications.mail.MailService;
import com.app.slotbookingservice.payment.enums.PaymentStatus;
import com.app.slotbookingservice.payment.entity.PaymentHistory;
import com.app.slotbookingservice.utlis.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final MailService mailService;
    private final MongoTemplate mongoTemplate;

    @Override
    public BookingDetailsDto bookASlot(final BookingDetailsDto bookingDetailsDto) throws Exception {
        try {
            verifyPaymentDetails(bookingDetailsDto);
            BookingDetails bookingDetails = bookingMapper.toBookingDetails(bookingDetailsDto);
            BookingDetails persistedBookingDetails = bookingRepository.save(bookingDetails);
            bookingDetailsDto.setBookingId(persistedBookingDetails.getId().toString());
            mailService.sendBookingConfirmation(bookingDetailsDto);
            return bookingDetailsDto;
        } catch (final Exception e) {
            throw new BookingRegistrationException(e.getMessage());
        }
    }

    @Override
    public List<BookingDetailsDto> getBookings() throws Exception {
        List<BookingDetails> allBookings = bookingRepository.findAll();
        return bookingMapper.toBookingDetailsDtoList(allBookings);
    }

    @Override
    public List<BookingDetailsDto> getBookingsByEmail(final String email, final boolean upcoming) throws Exception {
        log.info("Fetching bookings for email: {} & upcoming -> {}", email, upcoming);
        List<BookingDetails> allBookingsByEmail = upcoming
                ? bookingRepository.findUpcomingByUserEmail(LocalDate.now(), LocalTime.now().withSecond(0).withNano(0), email)
                : bookingRepository.findAllByUserEmail(email);
        return bookingMapper.toBookingDetailsDtoList(allBookingsByEmail);
    }

    @Override
    public BookingDetailsDto getBookingsByBookingId(final String id) throws Exception {
        BookingDetails bookingDetails = bookingRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException(AppConstants.BOOKING_FETCH_FAILURE_MSG));
        return bookingMapper.toBookingDetailsDto(bookingDetails);
    }

    private void verifyPaymentDetails(final BookingDetailsDto bookingDetails) {
        log.info("Verifying Payment details for booking a slot...");
        Query query = new Query(Criteria.where("booking_id").is(bookingDetails.getBookingId())
                .and("payment_status").is(PaymentStatus.COMPLETED));
        boolean exists = mongoTemplate.exists(query, PaymentHistory.class);
        if (exists) return;
        throw new RazorpayPaymentVerificationException(AppConstants.RAZORPAY_PAYMENT_VERIFICATION_EXC_MSG);
    }

}
