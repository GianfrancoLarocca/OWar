package com.giancotsu.owar.exception;

public class UsernameOrEmailAlreadyExistsException extends RuntimeException{

    public UsernameOrEmailAlreadyExistsException(String message) {
        super(message);
    }
}
