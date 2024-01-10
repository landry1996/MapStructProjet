package org.landry.demomapperstruct.execptions;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {


    @Getter
    private CodeError codeError;


    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message, Throwable cause, CodeError codeError) {
        super(message, cause);
        this.codeError = codeError;
    }

    public EntityNotFoundException(String message, CodeError codeError) {
        super(message);
        this.codeError = codeError;
    }
}
