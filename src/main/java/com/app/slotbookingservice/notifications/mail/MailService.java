package com.app.slotbookingservice.notifications.mail;

import com.app.slotbookingservice.booking.dto.BookingDetailsDto;
import com.app.slotbookingservice.utlis.AppConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendBookingConfirmation(final BookingDetailsDto bookingDetails) {
        log.info("Sending booking confirmation mail to : {}", bookingDetails.getUserEmail());
        Context context = new Context();
        context.setVariable("userName", bookingDetails.getUserName());
        context.setVariable("userEmail", bookingDetails.getUserEmail());
        context.setVariable("phoneNumber", bookingDetails.getPhoneNumber());
        context.setVariable("resumeLink", bookingDetails.getResumeLink());
        context.setVariable("targetRole", bookingDetails.getTargetRole());
        context.setVariable("selectedService", bookingDetails.getSelectedService());
        context.setVariable("date", bookingDetails.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        context.setVariable("time", bookingDetails.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        context.setVariable("additionalNote", bookingDetails.getAdditionalNote());
        context.setVariable("bookingId", bookingDetails.getBookingId());

        String body = templateEngine.process(AppConstants.BOOKING_EMAIL_HTML, context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(bookingDetails.getUserEmail());
            helper.setSubject("Booking Confirmation - " + bookingDetails.getBookingId());
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(AppConstants.FAILED_TO_SEND_EMAIL, e);
        }
    }

}