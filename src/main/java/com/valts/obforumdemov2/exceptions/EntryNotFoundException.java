package com.valts.obforumdemov2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntryNotFoundException extends ResponseStatusException {
    public EntryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
