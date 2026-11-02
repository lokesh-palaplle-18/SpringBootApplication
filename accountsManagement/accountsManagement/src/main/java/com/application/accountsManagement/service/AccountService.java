
package com.application.accountsManagement.service;

import com.application.accountsManagement.model.CustomerDto;

public interface AccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto getCustomerByMobileNumber(String mobileNumber);

    Boolean updateAccount(String mobileNumber, CustomerDto customerDto);

    Boolean deleteAccount(String mobileNumber);
}
