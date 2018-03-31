package com.bkr.atm.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.service.AccountService;


@Component
public class SuccessLoginListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SuccessLoginListener.class);

    @Autowired
    private AccountService accountService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Accounts accounts = (Accounts) event.getAuthentication().getPrincipal();
        Integer failedLoginAttempts = accountService.resetFailedLoginAttempts(accounts.getNumber());
        logger.info(String.format("PIN Entering succeeded (%s). Failed attempts reset, now: %d", accounts.getNumber(), failedLoginAttempts));
    }
}