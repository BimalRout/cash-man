package com.bkr.atm.exception;

public class AccountBlockedOrNotExistException extends RuntimeException {

    public AccountBlockedOrNotExistException() {
        super();
    }

    public AccountBlockedOrNotExistException(String message) {
        super(message);
    }

    public AccountBlockedOrNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountBlockedOrNotExistException(Throwable cause) {
        super(cause);
    }
}
