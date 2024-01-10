package org.landry.demomapperstruct.error;

public class EmployeeNotFoundException extends  Exception{
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
