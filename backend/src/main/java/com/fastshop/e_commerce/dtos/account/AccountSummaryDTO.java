package com.fastshop.e_commerce.dtos.account;

import com.fastshop.e_commerce.models.AccountBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSummaryDTO {
    private Long id;
    private Long UserId;

    public AccountSummaryDTO(AccountBO account) {
        this.id = account.getId();
        this.UserId = account.getUser().getId();
    }
}
