package com.bkr.atm.exception;

public class WithdrawNotEnoughMoneyException extends WithdrawRulesConflictException {

    public WithdrawNotEnoughMoneyException() {
        super();
    }

    public WithdrawNotEnoughMoneyException(String message) {
        super(message);
    }

    public WithdrawNotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithdrawNotEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}
