package com.bkr.atm.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.bkr.atm.service.AccountService;


@Component
public class FailedLoginListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FailedLoginListener.class);

    @Autowired
    private AccountService accountService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String number = (String) event.getAuthentication().getPrincipal();
        Integer failedLoginAttempts = accountService.incrementFailedLoginAttempts(number);
        logger.info(String.format("PIN Entering failed (%s). Failed attempts incremented, now: %d", number, failedLoginAttempts));
    }
}