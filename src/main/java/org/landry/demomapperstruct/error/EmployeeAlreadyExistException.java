package org.landry.demomapperstruct.error;

public class EmployeeAlreadyExistException extends Exception{
    public EmployeeAlreadyExistException(String message){
        super(message);
    }
}
