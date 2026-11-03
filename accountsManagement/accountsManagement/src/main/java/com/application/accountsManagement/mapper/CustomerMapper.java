
package com.application.accountsManagement.mapper;

import com.application.accountsManagement.entity.Customer;
import com.application.accountsManagement.model.CustomerDto;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    // DTO to Entity - CREATE
    public static Customer customerDtoToCustomerEntity(
            CustomerDto dto) {

        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
    }

    // Entity to DTO - READ
    public static CustomerDto customerEntityToCustomerDto(
            Customer customer) {

        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }

    // Update existing Entity - UPDATE
    public static void updateCustomerEntity(
            CustomerDto dto, Customer customer) {

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
    }
}
