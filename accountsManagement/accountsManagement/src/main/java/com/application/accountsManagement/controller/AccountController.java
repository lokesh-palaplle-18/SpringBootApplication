
package com.application.accountsManagement.controller;

import com.application.accountsManagement.model.CustomerDto;
import com.application.accountsManagement.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/api")

public class AccountController {

    @Autowired
    private AccountService accountsService;

    // 1. CREATE
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(
           @Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Created account successfully");
    }

    // 2. READ

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


    // 3. UPDATE
    @PutMapping("/update/{mobileNumber}")
    public ResponseEntity<String> updateAccount(
            @PathVariable String mobileNumber,
           @Valid @RequestBody CustomerDto customerDto) {

        Boolean updated = accountsService
                .updateAccount(mobileNumber, customerDto);

        if (updated) {
            return ResponseEntity.ok("Account updated successfully");
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Customer or account not found");
    }

    // 4. DELETE

    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable
            @NotBlank(message = "Mobile number is required")
            @Pattern(
                    regexp = "^[6-9][0-9]{9}$",
                    message = "Enter a valid 10-digit Indian mobile number"
            )
            String mobileNumber) {

        boolean deleted = accountsService.deleteAccount(mobileNumber);

        if (deleted) {
            return ResponseEntity.ok("Account deleted successfully");
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Customer not found");
    }

}

