package com.fastshop.e_commerce.exceptions.service;

public class InvalidEmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidEmailException(String msg) {
        super(msg);
    }

}
