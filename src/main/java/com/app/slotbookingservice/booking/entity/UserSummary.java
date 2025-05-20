package com.app.slotbookingservice.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummary {

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("phone_number")
    private Long phoneNumber;

}
