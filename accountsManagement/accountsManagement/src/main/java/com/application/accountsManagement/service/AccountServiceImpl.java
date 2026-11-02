
package com.application.accountsManagement.service;

import com.application.accountsManagement.entity.Accounts;
import com.application.accountsManagement.entity.Customer;
import com.application.accountsManagement.exceptions.CustomerAlreadyExistsException;
import com.application.accountsManagement.mapper.AccountMapper;
import com.application.accountsManagement.mapper.CustomerMapper;
import com.application.accountsManagement.model.AccountDto;
import com.application.accountsManagement.model.CustomerDto;
import com.application.accountsManagement.repository.AccountRepository;
import com.application.accountsManagement.repository.CustomerRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    // 1. CREATE
    @Override
//    @Transactional
    public void createAccount(CustomerDto customerDto) {

        if (customerRepository.findByMobileNumber(
                customerDto.getMobileNumber()).isPresent()) {

            throw new CustomerAlreadyExistsException(
                    "Customer already registered with given mobileNumber "
                            + customerDto.getMobileNumber());
        }

        if (customerDto.getAccountsModel() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account details are required");
        }

        Customer customer = CustomerMapper.customerDtoToCustomerEntity(customerDto);


        Customer savedCustomer =
                customerRepository.save(customer);

        long randomAccNumber =
                1000000000L + new Random().nextInt(900000000);

        Accounts accounts = AccountMapper.customerDtoToAccountEntity(customerDto);
        accounts.setAccountNumber(randomAccNumber);
        accounts.setCustomerId(savedCustomer.getCustomerId());

        accountRepository.save(accounts);
    }

    // 2. READ
    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"));

        Accounts account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        AccountDto accountDto = AccountMapper.accountEntityToAccountDto(account);

        CustomerDto customerDtoResponse = CustomerMapper.customerEntityToCustomerDto(customer);

        return customerDtoResponse;

    }

    // 3. UPDATE
    @Override
    @Transactional
    public Boolean updateAccount(
            String mobileNumber, CustomerDto customerDto) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElse(null);

        if (customer == null) {
            return false;
        }

        Accounts account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElse(null);

        if (account == null) {
            return false;
        }

        if (customerDto.getAccountsModel() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account details are required");
        }

        // Prevent duplicate mobile number during update
        if (!mobileNumber.equals(customerDto.getMobileNumber())
                && customerRepository
                .findByMobileNumber(customerDto.getMobileNumber())
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Mobile number already registered");
        }

        Customer customer1 = CustomerMapper.customerDtoToCustomerEntity(customerDto);
        customerRepository.save(customer1);
        Accounts accounts = AccountMapper.customerDtoToAccountEntity(customerDto);

        accountRepository.save(accounts);

        return true;
    }

    // 4. DELETE
    @Override
    @Transactional
    public Boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElse(null);

        if (customer == null) {
            return false;
        }

        accountRepository.findByCustomerId(customer.getCustomerId())
                .ifPresent(accountRepository::delete);

        customerRepository.delete(customer);

        return true;
    }
}
