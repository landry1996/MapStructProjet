package org.landry.demomapperstruct.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.model.Employee;
import org.landry.demomapperstruct.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeMapperImpl.class,RoleMapperImpl.class})
//@SpringJUnitConfig(classes = {EmployeeMapperImpl.class, RoleMapperImpl.class})
class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Test
    void testMapEmployeeDtoToEmployee() {
        //given
        RoleDto roleDto = RoleDto.builder().name("developer").build();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleDtoList(Collections.singletonList(roleDto)).build();


        //when
        Employee employee = employeeMapper.mapEmployeeDtoToEmployee(employeeDto);


        //then
        Assertions.assertThat(employee.getName()).isEqualTo(employeeDto.getName());
        Assertions.assertThat(employee.getEmail()).isEqualTo(employeeDto.getEmail());
        Assertions.assertThat(employee.getRoleList().get(0).getName()).isEqualTo(employeeDto.getRoleDtoList().get(0).getName());


    }

    @Test
    void testMapEmployeeToEmployeeDto() {
        //given
        Role role = Role.builder().name("Admin").build();
        Employee employee = Employee.builder()
                .name("Joel").email("joel@gmail.com")
                .roleList(Collections.singletonList(role))
                .build();
        //when

        EmployeeDto employeeDto = employeeMapper.mapEmployeeToEmployeeDto(employee);

        //then
        Assertions.assertThat(employeeDto.getName()).isEqualTo(employee.getName());
        Assertions.assertThat(employeeDto.getEmail()).isEqualTo(employee.getEmail());
        Assertions.assertThat(employeeDto.getRoleDtoList().get(0).getName()).isEqualTo(employee.getRoleList().get(0).getName());
    }
}