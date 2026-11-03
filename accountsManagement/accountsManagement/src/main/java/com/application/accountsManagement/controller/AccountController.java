
package com.application.accountsManagement.controller;

import com.application.accountsManagement.constants.AccountConstants;
import com.application.accountsManagement.model.CustomerDto;
import com.application.accountsManagement.model.ResponseDto;
import com.application.accountsManagement.service.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/api")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountsService;

    // 1. CREATE ACCOUNT
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(
            @Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountConstants.ACCOUNT_CREATED,
                        AccountConstants.ACCOUNT_CREATED_MESSAGE
                ));
    }

    // 2. FETCH ACCOUNT
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> getAccount(
            @PathVariable
            @NotBlank(message = "Mobile number is required")
            @Pattern(
                    regexp = "^[6-9][0-9]{9}$",
                    message = "Enter a valid 10-digit Indian mobile number"
            )
            String mobileNumber) {

        CustomerDto customerDto =
                accountsService.getCustomerByMobileNumber(mobileNumber);

        return ResponseEntity.ok(customerDto);
    }

    // 3. UPDATE ACCOUNT
    @PutMapping("/update/{mobileNumber}")
    public ResponseEntity<ResponseDto> updateAccount(
            @PathVariable
            @NotBlank(message = "Mobile number is required")
            @Pattern(
                    regexp = "^[6-9][0-9]{9}$",
                    message = "Enter a valid 10-digit Indian mobile number"
            )
            String mobileNumber,
            @Valid @RequestBody CustomerDto customerDto) {

        accountsService.updateAccount(mobileNumber, customerDto);

        return ResponseEntity.ok(
                new ResponseDto(
                        AccountConstants.ACCOUNT_UPDATED,
                        AccountConstants.ACCOUNT_UPDATED_MESSAGE
                )
        );
    }

    // 4. DELETE ACCOUNT
    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(
            @PathVariable
            @NotBlank(message = "Mobile number is required")
            @Pattern(
                    regexp = "^[6-9][0-9]{9}$",
                    message = "Enter a valid 10-digit Indian mobile number"
            )
            String mobileNumber) {

        accountsService.deleteAccount(mobileNumber);

        return ResponseEntity.ok(
                new ResponseDto(
                        AccountConstants.ACCOUNT_DELETED,
                        AccountConstants.ACCOUNT_DELETED_MESSAGE
                )
        );
    }
}
