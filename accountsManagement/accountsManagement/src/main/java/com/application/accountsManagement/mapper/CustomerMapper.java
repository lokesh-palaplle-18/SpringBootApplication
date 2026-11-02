
package com.application.accountsManagement.mapper;

import com.application.accountsManagement.entity.Customer;
import com.application.accountsManagement.model.AccountDto;
import com.application.accountsManagement.model.CustomerDto;

public final class CustomerMapper {

    public static Customer customerDtoToCustomerEntity(
            CustomerDto dto) {

        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
    }

    public static CustomerDto customerEntityToCustomerDto(
            Customer customer) {

        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .accountsModel(AccountDto.builder().build())
                .build();
    }


}
