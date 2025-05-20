package com.app.slotbookingservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private String userName;
    private String email;
    private Long phoneNumber;

}
