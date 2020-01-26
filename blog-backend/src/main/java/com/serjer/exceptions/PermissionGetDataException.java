package com.serjer.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class PermissionGetDataException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
    public PermissionGetDataException(String message) {
        super(message);
    }
 
    public PermissionGetDataException(String message, Throwable cause) {
        super(message, cause);
    }
}