package com.bkr.atm.exception;

public class WithdrawRulesConflictException extends RuntimeException {

    public WithdrawRulesConflictException() {
        super();
    }

    public WithdrawRulesConflictException(String message) {
        super(message);
    }

    public WithdrawRulesConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithdrawRulesConflictException(Throwable cause) {
        super(cause);
    }
}
