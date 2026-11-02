
package com.application.accountsManagement.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {


    @PositiveOrZero(message = "Account number cannot be negative")
    private Long accountNumber;

    @NotBlank(message = "Account type is required")
    @Pattern(
            regexp = "^(Savings|Current)$",
            message = "Account type must be Savings or Current"
    )
    private String accountType;

    @NotBlank(message = "Branch address is required")
    private String branchAddress;

}
