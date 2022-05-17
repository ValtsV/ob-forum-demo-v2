package com.valts.obforumdemov2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyFollowingException extends ResponseStatusException {

    public AlreadyFollowingException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
