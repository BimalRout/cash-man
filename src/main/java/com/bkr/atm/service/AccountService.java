package com.bkr.atm.service;

import java.math.BigDecimal;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.form.AddForm;


public interface AccountService {

    boolean checkAccount(String number);
    Integer incrementFailedLoginAttempts(String number);
    Integer resetFailedLoginAttempts(String number);
    Accounts checkBalance(String number);
    Accounts withdraw(String number, BigDecimal amount);
    Integer getLoginAttemptsLeft(String number);
	Accounts addBalance(String number, AddForm addForm);
}
