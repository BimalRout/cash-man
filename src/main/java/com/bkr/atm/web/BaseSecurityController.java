package com.bkr.atm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.exception.AccountBlockedOrNotExistException;
import com.bkr.atm.service.AccountService;

@Controller
public abstract class BaseSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(BaseSecurityController.class);

    @Autowired
    private AccountService accountService;
  
    protected String getCardNumber() {
        return ((Accounts) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getNumber();
    }

    protected void validateCard(String number) {
        if (!accountService.checkAccount(number)) {
            logger.info(String.format("Validate card: Card %s is blocked or does not exist", number));
            throw new AccountBlockedOrNotExistException();
        }
    }
    @ExceptionHandler(AccountBlockedOrNotExistException.class)
    public String handleCardBlockedOrNotExist(Exception ex) {
        return "redirect:/errorBlockedOrNotExist";
    }

}
