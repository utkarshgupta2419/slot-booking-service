package com.app.slotbookingservice.booking.entity;

import com.app.slotbookingservice.booking.enums.ServiceType;
import com.app.slotbookingservice.booking.enums.TargetRole;
import com.app.slotbookingservice.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "BookingDetails")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails extends AbstractEntity {

    @Field(name = "user")
    private UserSummary user;
    @Field(name = "resume_link")
    private String resumeLink;
    @Field(name = "target_role")
    private TargetRole targetRole;
    @Field(name = "additional_note")
    private String additionalNote;
    @Field(name = "selected_service")
    private ServiceType selectedService;
    @Field(name = "date")
    private LocalDate date;
    @Field(name = "time")
    private LocalTime time;

}
