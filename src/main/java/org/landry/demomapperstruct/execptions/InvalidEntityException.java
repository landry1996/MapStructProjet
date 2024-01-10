package org.landry.demomapperstruct.execptions;

import lombok.Getter;

import java.util.List;

public class InvalidEntityException extends RuntimeException {


    @Getter
    private CodeError codeError;
    @Getter
    private List<String> errors;

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(String message, Throwable cause, CodeError codeError) {
        super(message, cause);
        this.codeError = codeError;
    }

    public InvalidEntityException(String message, CodeError codeError) {
        super(message);
        this.codeError = codeError;
    }

    public InvalidEntityException(String message, CodeError codeError, List<String> errors) {
        super(message);
        this.codeError = codeError;
        this.errors = errors;
    }
}
