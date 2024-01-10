package org.landry.demomapperstruct.services;

import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeAlreadyExistException;
import org.landry.demomapperstruct.error.EmployeeErrorException;
import org.landry.demomapperstruct.error.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExistException;

    List<EmployeeDto> getListEmployees();

    EmployeeDto findEmployeeById(Long Id) throws EmployeeNotFoundException;

    EmployeeDto findEmployeeByName(String name) throws EmployeeNotFoundException;

    EmployeeDto updateEmployee(EmployeeDto employeeDto) throws EmployeeNotFoundException, EmployeeErrorException;

    void deleteEmployee(Long Id) throws EmployeeErrorException, EmployeeNotFoundException;

    void addRoleToEmployee(String email, RoleDto roleDto) throws EmployeeNotFoundException, EmployeeErrorException;
}
