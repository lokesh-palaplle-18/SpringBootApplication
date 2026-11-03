
package com.application.accountsManagement.mapper;

import com.application.accountsManagement.entity.Accounts;
import com.application.accountsManagement.model.AccountDto;
import com.application.accountsManagement.model.CustomerDto;

public final class AccountMapper {

    private AccountMapper() {
    }

    // DTO to Entity - CREATE
    public static Accounts customerDtoToAccountEntity(
            CustomerDto dto) {

        return Accounts.builder()
                .accountType(dto.getAccountsModel().getAccountType())
                .branchAddress(dto.getAccountsModel().getBranchAddress())
                .build();
    }

    // Entity to DTO - READ
    public static AccountDto accountEntityToAccountDto(
            Accounts account) {

        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }

    // Update existing Entity - UPDATE
    public static void updateAccountEntity(
            AccountDto dto, Accounts account) {

        account.setAccountType(dto.getAccountType());
        account.setBranchAddress(dto.getBranchAddress());
    }
}
