package com.app.slotbookingservice.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderDto {

    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;

    @Positive(message = "Amount must be greater than 0")
    private int amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter uppercase code like 'INR'")
    private String currency;

    @NotBlank(message = "Receipt ID is required")
    private String receiptId;
}
