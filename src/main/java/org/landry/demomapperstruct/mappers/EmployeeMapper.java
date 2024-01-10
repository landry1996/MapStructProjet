package org.landry.demomapperstruct.mappers;

import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface EmployeeMapper {

    @Mapping(target = "roleList", source = "roleDtoList")
    Employee mapEmployeeDtoToEmployee(EmployeeDto employeeDto);

    @Mapping(target = "roleDtoList", source = "roleList")
    EmployeeDto mapEmployeeToEmployeeDto(Employee employee);

}
