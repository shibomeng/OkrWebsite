package com.example.okrwebsite.exception;

public class EmployeeAlreadyExistException extends RuntimeException {
    public EmployeeAlreadyExistException() {
        throw new RuntimeException();
    }

    public EmployeeAlreadyExistException(final String errMsg) {
        throw new RuntimeException(errMsg);
    }

    public EmployeeAlreadyExistException(final String errMsg, final Exception e) {
        throw new RuntimeException(errMsg, e);
    }
}
