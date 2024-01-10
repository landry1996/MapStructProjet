package org.landry.demomapperstruct.controller;

import lombok.RequiredArgsConstructor;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeAlreadyExistException;
import org.landry.demomapperstruct.error.EmployeeErrorException;
import org.landry.demomapperstruct.error.EmployeeNotFoundException;
import org.landry.demomapperstruct.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @PostMapping(path = "/employee")
    public ResponseEntity<EmployeeDto> createdEmployee(@RequestBody EmployeeDto employeeDto) throws EmployeeAlreadyExistException {
        EmployeeDto addEmployeeDto = employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(addEmployeeDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/listEmployees")
    public List<EmployeeDto> getAllEmployee() {
        List<EmployeeDto> employeeDtos = employeeService.getListEmployees();
        return employeeDtos;
    }

    @DeleteMapping(path = "/delete/{Id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long Id) throws EmployeeErrorException, EmployeeNotFoundException {
        employeeService.deleteEmployee(Id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping(path = "/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) throws EmployeeErrorException, EmployeeNotFoundException {
        EmployeeDto updateEmployeeDto = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok(updateEmployeeDto);
    }

    @GetMapping(path = "/employeeByName/{name}")
    public ResponseEntity<EmployeeDto> getEmployeeByName(@PathVariable String name) throws EmployeeNotFoundException {
        EmployeeDto employeeDto = employeeService.findEmployeeByName(name);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping(path = "/employeeById/{Id}")
    public ResponseEntity<EmployeeDto> getEmployeeByid(@PathVariable Long Id) throws EmployeeNotFoundException {
        EmployeeDto employeeDto = employeeService.findEmployeeById(Id);
        return ResponseEntity.ok(employeeDto);
    }

    @PatchMapping(path = "/employee/{email}")
    public ResponseEntity<Void> addRoleToEmployee(@PathVariable String email, @RequestBody RoleDto roleDto) throws EmployeeErrorException, EmployeeNotFoundException {
        employeeService.addRoleToEmployee(email, roleDto);
        return ResponseEntity.ok().build();
    }

}
