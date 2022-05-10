package com.valts.obforumdemov2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectUserException extends ResponseStatusException {
    public IncorrectUserException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
