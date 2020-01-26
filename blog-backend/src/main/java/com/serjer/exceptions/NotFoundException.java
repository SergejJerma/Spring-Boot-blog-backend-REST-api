package com.serjer.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
    public NotFoundException(String message) {
        super(message);
    }
 
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

