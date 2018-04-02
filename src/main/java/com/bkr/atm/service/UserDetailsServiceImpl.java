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
    public UserDetailsServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Accounts loadUserByUsername(String number) {
        Accounts account = accountRepo.findOne(number);
        if (account == null) {
            throw new UsernameNotFoundException("Not a valid account");
        }
        return account;
    }
}
