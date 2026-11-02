
package com.application.accountsManagement.mapper;

import com.application.accountsManagement.entity.Accounts;
import com.application.accountsManagement.model.AccountDto;
import com.application.accountsManagement.model.CustomerDto;

public final class AccountMapper {

    private AccountMapper() {}

    public static Accounts customerDtoToAccountEntity(
            CustomerDto dto) {

        return Accounts.builder()
                .accountType(dto.getAccountsModel().getAccountType())
                .branchAddress(dto.getAccountsModel().getBranchAddress())
                .build();
    }

    public static AccountDto accountEntityToAccountDto(
            Accounts account) {

        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }
}
