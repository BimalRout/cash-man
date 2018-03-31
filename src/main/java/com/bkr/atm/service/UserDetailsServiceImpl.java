package com.bkr.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.repository.AccountRepo;

@Service
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService {

    private AccountRepo accountRepo;

    @Autowired
    public UserDetailsServiceImpl(AccountRepo creditCardRepo) {
        this.accountRepo = creditCardRepo;
    }

    @Override
    public Accounts loadUserByUsername(String number) {
        Accounts creditCard = accountRepo.findOne(number);
        if (creditCard == null) {
            throw new UsernameNotFoundException("Credit Card not found");
        }
        return creditCard;
    }
}
