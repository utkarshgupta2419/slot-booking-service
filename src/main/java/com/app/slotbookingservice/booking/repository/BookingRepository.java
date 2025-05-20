package com.app.slotbookingservice.booking.repository;

import com.app.slotbookingservice.booking.entity.BookingDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<BookingDetails, ObjectId> {

    List<BookingDetails> findAllByUserEmail(String email);

    @Query("""
            SELECT b FROM BookingDetails b
            WHERE (b.date > :today OR (b.date = :today AND b.time > :now))
            AND b.user.email = :email
            """)
    List<BookingDetails> findUpcomingByUserEmail(@Param("today") LocalDate today,
                                                 @Param("now") LocalTime now,
                                                 @Param("email") String email);
}
