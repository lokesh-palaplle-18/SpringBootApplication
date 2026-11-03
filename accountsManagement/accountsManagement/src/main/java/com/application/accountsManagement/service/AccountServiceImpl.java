
package com.application.accountsManagement.service;

import com.application.accountsManagement.entity.Accounts;
import com.application.accountsManagement.entity.Customer;
import com.application.accountsManagement.exceptions.CustomerAlreadyExistsException;
import com.application.accountsManagement.exceptions.ResourceNotFoundException;
import com.application.accountsManagement.mapper.AccountMapper;
import com.application.accountsManagement.mapper.CustomerMapper;
import com.application.accountsManagement.model.AccountDto;
import com.application.accountsManagement.model.CustomerDto;
import com.application.accountsManagement.repository.AccountRepository;
import com.application.accountsManagement.repository.CustomerRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    // 1. CREATE ACCOUNT
    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {

        if (customerRepository.findByMobileNumber(
                customerDto.getMobileNumber()).isPresent()) {

            throw new CustomerAlreadyExistsException(
                    "Customer already registered with given mobileNumber "
                            + customerDto.getMobileNumber());
        }

        Customer customer =
                CustomerMapper.customerDtoToCustomerEntity(customerDto);

        Customer savedCustomer = customerRepository.save(customer);

        long randomAccNumber =
                1000000000L + new Random().nextInt(900000000);

        Accounts accounts =
                AccountMapper.customerDtoToAccountEntity(customerDto);

        accounts.setAccountNumber(randomAccNumber);
        accounts.setCustomerId(savedCustomer.getCustomerId());

        accountRepository.save(accounts);
    }

    // 2. FETCH ACCOUNT
    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber
                ));

        Accounts account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "customerId",
                        customer.getCustomerId().toString()
                ));

        AccountDto accountDto =
                AccountMapper.accountEntityToAccountDto(account);

        CustomerDto customerDtoResponse =
                CustomerMapper.customerEntityToCustomerDto(customer);

        customerDtoResponse.setAccountsModel(accountDto);

        return customerDtoResponse;
    }

    // 3. UPDATE ACCOUNT
    @Override
    @Transactional
    public Boolean updateAccount(
            String mobileNumber, CustomerDto customerDto) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber
                ));

        Accounts account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "customerId",
                        customer.getCustomerId().toString()
                ));

        // Prevent duplicate mobile numbers
        if (!mobileNumber.equals(customerDto.getMobileNumber())
                && customerRepository
                .findByMobileNumber(customerDto.getMobileNumber())
                .isPresent()) {

            throw new CustomerAlreadyExistsException(
                    "Mobile number already registered: "
                            + customerDto.getMobileNumber()
            );
        }

        // Update existing entities without changing their IDs
        CustomerMapper.updateCustomerEntity(customerDto, customer);

        AccountMapper.updateAccountEntity(
                customerDto.getAccountsModel(),
                account
        );

        customerRepository.save(customer);
        accountRepository.save(account);

        return true;
    }

    // 4. DELETE ACCOUNT
    @Override
    @Transactional
    public Boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber
                ));

        accountRepository.findByCustomerId(customer.getCustomerId())
                .ifPresent(accountRepository::delete);

        customerRepository.delete(customer);

        return true;
    }
}
