package com.app.slotbookingservice.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    public <T> ResponseEntity<T> generateResponse(final T body, final HttpStatus httpStatusCode) {
        return new ResponseEntity<>(body, httpStatusCode);
    }


}
