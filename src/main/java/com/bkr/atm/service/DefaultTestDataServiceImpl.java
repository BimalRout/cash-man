package com.bkr.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.repository.AccountRepo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultTestDataServiceImpl extends BaseService implements DefaultTestDataService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTestDataServiceImpl.class);

    private AccountRepo accountsRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultTestDataServiceImpl(AccountRepo creditCardRepo, PasswordEncoder passwordEncoder) {
        this.accountsRepo = creditCardRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates default test data if there is no test data in the DB
     */
    @Override
    public void createDefaultTestDataIfNeeded() {
        if (accountsRepo.count() == 0L) {
            logger.info("Application Ready: Creating default test data for credit cards");
            List<Accounts> defaultTestCreditCards = Arrays.asList(
                    new Accounts("1111-2222-3333-4444", passwordEncoder.encode("1111"),10,5, new BigDecimal("10000.00")),
                    new Accounts("5555-6666-7777-8888", passwordEncoder.encode("5555"),5,10, new BigDecimal("10.00")),
                    new Accounts("1234-5678-1234-5678", passwordEncoder.encode("1234"),4,6, new BigDecimal("2000.00")),
                    new Accounts("1111-1111-1111-1111", passwordEncoder.encode("1111"),4,8, new BigDecimal("3500.00")));
            accountsRepo.save(defaultTestCreditCards);
        } else {
            logger.info("Application Ready: Not creating default test data for credit cards, because there are already some in DB");
        }
    }

}
