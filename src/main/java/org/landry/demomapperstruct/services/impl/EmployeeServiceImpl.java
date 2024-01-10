package org.landry.demomapperstruct.services.impl;

import lombok.RequiredArgsConstructor;
import org.landry.demomapperstruct.dao.EmployeeRepository;
import org.landry.demomapperstruct.dao.RoleRepository;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeAlreadyExistException;
import org.landry.demomapperstruct.error.EmployeeErrorException;
import org.landry.demomapperstruct.error.EmployeeNotFoundException;
import org.landry.demomapperstruct.mappers.EmployeeMapper;
import org.landry.demomapperstruct.model.Employee;
import org.landry.demomapperstruct.model.Role;
import org.landry.demomapperstruct.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.landry.demomapperstruct.utils.Utils.EMPLOYEE_ALREADY_EXIST_IN_DATABASE;
import static org.landry.demomapperstruct.utils.Utils.EMPLOYEE_NOT_EXIST;
import static org.landry.demomapperstruct.utils.Utils.EMPLOYEE_OR_ROLE_NOT_EXIST;
import static org.landry.demomapperstruct.utils.Utils.ERROR_UNKNOWM;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RoleRepository roleRepository;


    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExistException {

        if (Objects.nonNull(employeeDto) && Objects.nonNull(employeeDto.getEmail())) {
            Optional<Employee> employee = employeeRepository.findEmployeeByEmail(employeeDto.getEmail());

            if (employee.isPresent()) {
                throw new EmployeeAlreadyExistException(EMPLOYEE_ALREADY_EXIST_IN_DATABASE);
            }

        }
        Employee employee = employeeRepository.save(employeeMapper.mapEmployeeDtoToEmployee(employeeDto));
        return employeeMapper.mapEmployeeToEmployeeDto(employee);
    }


    @Override
    public List<EmployeeDto> getListEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::mapEmployeeToEmployeeDto).toList();
    }

    @Override
    public EmployeeDto findEmployeeById(Long Id) throws EmployeeNotFoundException {
        return employeeMapper.mapEmployeeToEmployeeDto(employeeRepository.findById(Id)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST)));
    }

    @Override
    public EmployeeDto findEmployeeByName(String name) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findEmployeeByName(name);
        return employee.map(employeeMapper::mapEmployeeToEmployeeDto)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST));

    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws EmployeeNotFoundException, EmployeeErrorException {

        if (Objects.nonNull(employeeDto) && Objects.nonNull(employeeDto.getEmail())) {
            Optional<Employee> employee = employeeRepository.findEmployeeByEmail(employeeDto.getEmail());
            if (employee.isEmpty()) {
                throw new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST);
            }
            employee.get().setEmail(employeeMapper.mapEmployeeDtoToEmployee(employeeDto).getEmail());
            employee.get().setName(employeeMapper.mapEmployeeDtoToEmployee(employeeDto).getName());
            employee.get().setRoleList(employeeMapper.mapEmployeeDtoToEmployee(employeeDto).getRoleList());
            return employeeMapper.mapEmployeeToEmployeeDto(employeeRepository.save(employee.get()));

        }

        throw new EmployeeErrorException(ERROR_UNKNOWM);
    }

    @Override
    public void deleteEmployee(Long Id) throws EmployeeNotFoundException {
        employeeRepository.findById(Id)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST));

    }

    @Override
    public void addRoleToEmployee(String email, RoleDto roleDto) throws EmployeeNotFoundException, EmployeeErrorException {
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);
        Optional<Role> role = roleRepository.findRoleByName(roleDto.getName());

        if (employee.isEmpty() || role.isEmpty()) {
            throw new EmployeeNotFoundException(EMPLOYEE_OR_ROLE_NOT_EXIST);
        }

        List<Role> roles = new ArrayList<>(employee.map(Employee::getRoleList)
                .stream().flatMap(Collection::stream).toList());
        roles.add(role.orElse(null));
        employee.get().setRoleList(roles);
        employeeRepository.save(employee.get());
    }

}
