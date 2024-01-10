package org.landry.demomapperstruct.execptions;

public enum CodeError {

    EMPLOYEE_NOT_FOUND(1000),
    ROLE_NOT_FOUND(2000);

    private int code;

    CodeError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
