package com.bkr.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkr.atm.domain.Operation;
import com.bkr.atm.domain.OperationCode;
import com.bkr.atm.repository.OperationRepo;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OperationHistoryServiceImpl extends BaseService implements OperationHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(OperationHistoryServiceImpl.class);

    private OperationRepo operationRepo;

    @Autowired
    public OperationHistoryServiceImpl(OperationRepo operationRepo) {
        this.operationRepo = operationRepo;
    }

    @Override
    public void saveBalanceOperation(String cardNumber) {
        logger.info("Saving balance operation");
        Operation operation = new Operation(cardNumber, new Date(), OperationCode.BALANCE);
        operationRepo.save(operation);
    }
    
    @Override
    public void saveWithdrawalOperation(String cardNumber, BigDecimal withdrawalAmount) {
        logger.info("Saving withdrawal operation");
        Operation operation = new Operation(cardNumber, new Date(), OperationCode.WITHDRAWAL, withdrawalAmount);
        operationRepo.save(operation);
    }

	@Override
	public void saveAddOperation(String cardNumber, BigDecimal totalBalance) {
		 logger.info("Saving Add operation");
	     Operation operation = new Operation(cardNumber, new Date(), OperationCode.ADD, totalBalance);
	     operationRepo.save(operation);
		
	}
}
