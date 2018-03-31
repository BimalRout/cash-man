package com.bkr.atm.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.form.AddForm;
import com.bkr.atm.repository.AccountRepo;


@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepo accountRepo;
    private OperationHistoryService operationHistoryService;

    @Autowired
    public AccountServiceImpl(AccountRepo creditCardRepo, OperationHistoryService operationHistoryService) {
        this.accountRepo = creditCardRepo;
        this.operationHistoryService = operationHistoryService;
    }
    
    @Override
    public boolean checkAccount(String number) {
        Accounts creditCard = accountRepo.findOne(number);
        return creditCard != null && creditCard.isAccountNonLocked();
    }

    @Override
    public Integer incrementFailedLoginAttempts(String number) {
        Accounts accounts = accountRepo.findOne(number);
        accounts.incrementFailedLoginAttempts();
        Accounts savedCreditCard = accountRepo.save(accounts);
        return savedCreditCard.getFailedLoginAttempts();
    }

   
    @Override
    public Integer resetFailedLoginAttempts(String number) {
        Accounts accounts = accountRepo.findOne(number);
        accounts.resetFailedLoginAttempts();
        Accounts savedCreditCard = accountRepo.save(accounts);
        return savedCreditCard.getFailedLoginAttempts();
    }
    
    @Override
    public Accounts checkBalance(String number) {
        Accounts accounts = accountRepo.findOne(number);
        operationHistoryService.saveBalanceOperation(accounts.getNumber());
        return accounts;
    }
    @Override
    public Accounts withdraw(String number, BigDecimal withdrawalAmount) {
        Accounts accounts = accountRepo.findOne(number);
        accounts.withdraw(withdrawalAmount);
        Accounts savedCreditCard = accountRepo.save(accounts);
        operationHistoryService.saveWithdrawalOperation(savedCreditCard.getNumber(), withdrawalAmount);
        return savedCreditCard;
    }
    
    @Override
    public Accounts addBalance(String number, AddForm addForm) {
        Accounts accounts = accountRepo.findOne(number);
        accounts.setTwentyDenomNo(accounts.getTwentyDenomNo()+Integer.valueOf(addForm.getTwentyDenomes()));
        accounts.setFiftyDenomNo(accounts.getFiftyDenomNo()+Integer.valueOf(addForm.getFiftyDenomes()));
        
        BigDecimal amount = accounts.getAmount();
        BigDecimal twentyAmount =  new BigDecimal(Integer.valueOf(addForm.getTwentyDenomes())*20);
        BigDecimal fiftyAmount = new BigDecimal(Integer.valueOf(addForm.getFiftyDenomes())*50);
        BigDecimal totalBalance= amount.add(twentyAmount).add(fiftyAmount);
        accounts.setAmount(totalBalance);
        Accounts savedCreditCard = accountRepo.save(accounts);
        operationHistoryService.saveAddOperation(savedCreditCard.getNumber(), totalBalance);
        return savedCreditCard;
    }
    @Override
    public Integer getLoginAttemptsLeft(String number) {
        Accounts accounts = accountRepo.findOne(number);
        return Accounts.FAILED_LOGIN_ATTEMPTS_TO_BLOCK - accounts.getFailedLoginAttempts();
    }

}
