package com.sax.services;

import com.sax.dtos.AccountDTO;


public interface IAccountService extends ICrudServices<AccountDTO,Integer>{
    AccountDTO getByUsername(String username);
    AccountDTO getByEmail(String email);
    void updateUsernamePassword(AccountDTO accountDTO);
    void createAccount(AccountDTO accountDTO);
}
