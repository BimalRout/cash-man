package com.bkr.atm.exception;

public class WithdrawZeroAmountException extends WithdrawRulesConflictException {

    public WithdrawZeroAmountException() {
        super();
    }

    public WithdrawZeroAmountException(String message) {
        super(message);
    }

    public WithdrawZeroAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithdrawZeroAmountException(Throwable cause) {
        super(cause);
    }
}
