
package com.application.accountsManagement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotBlank(message = "Customer name should not me null or empty")
    @Size(min =5,max = 40,message = "customer name length should in between 5 -40 characters")
    private String name;



    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Enter a valid 10-digit Indian mobile number"
    )
    private String mobileNumber;


    @NotBlank(message = "email is required")
    @Email(message = "enter a valid email address")
    private String email;

    @Valid
    @NotNull(message = "account details are required")
    private AccountDto accountsModel;
}

