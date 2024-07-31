package com.fastshop.e_commerce.exceptions.common;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String msg) {
        super(msg);
    }
}
