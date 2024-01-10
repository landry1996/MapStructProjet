package org.landry.demomapperstruct.enums;

public enum Code {
    NOT_FOUND(404),
    INTERNAL_SERVE_ERROR(500),
    ALREADY_EXIST(208);
    public final int value;
    Code(int value) {
        this.value = value;
    }
}
