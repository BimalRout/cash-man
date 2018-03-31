package com.bkr.atm.service;

import java.math.BigDecimal;

public interface OperationHistoryService {
    void saveBalanceOperation(String cardNumber);
    void saveWithdrawalOperation(String cardNumber, BigDecimal withdrawalAmount);
	void saveAddOperation(String number, BigDecimal totalBalance);
}
