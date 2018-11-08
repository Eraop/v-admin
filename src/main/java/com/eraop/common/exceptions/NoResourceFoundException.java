package com.eraop.common.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author weiyi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoResourceFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoResourceFoundException() {
        super("NOT FOUND");
    }

    public NoResourceFoundException(String resourceName) {
        super(resourceName);
    }
}