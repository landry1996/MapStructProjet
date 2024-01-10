package org.landry.demomapperstruct.dao;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.landry.demomapperstruct.model.Employee;
import org.landry.demomapperstruct.model.Role;
import org.landry.demomapperstruct.services.EmployeeService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import  org.assertj.core.api.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void when_i_call_find_employee_by_name_with_name_then_return_employee(){
        //given
        Role role = Role.builder().name("developer").build();
        Employee employee = Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build();
        employeeRepository.save(employee);

        //when
        Employee searchEmployee = employeeRepository.findEmployeeByName("landry").orElse(null);

        //then
        Assertions.assertThat(searchEmployee).isNotNull();

    }

    @Test
    void when_i_call_find_employee_by_name_with_email_then_return_null(){
        //given
        Role role = Role.builder().name("developer").build();
        Employee employee = Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build();
        employeeRepository.save(employee);

        //when
        Employee searchEmployee = employeeRepository.findEmployeeByName("placide").orElse(null);

        //then
        Assertions.assertThat(searchEmployee);

    }

    @Test
    void when_i_call_find_employee_by_email_with_email_then_return_employee() {
        //given
        Role role = Role.builder().name("developer").build();
        Employee employee = Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build();
        employeeRepository.save(employee);

        //when
        Employee searchEmployee = employeeRepository.findEmployeeByEmail("landry@gmail.com").orElse(null);

        //then
        Assertions.assertThat(searchEmployee).isNotNull();
        Assertions.assertThat(searchEmployee.getName()).isNotNull();
    }

    @Test
    void when_i_call_find_employee_by_email_with_email_then_return_null() {
        //given
        Role role = Role.builder().name("developer").build();
        Employee employee = Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build();
        employeeRepository.save(employee);

        //when
        Employee searchEmployee = employeeRepository.findEmployeeByEmail("placide@gmail.com").orElse(null);

        //then
        Assertions.assertThat(searchEmployee);
    }

    @Test
    void when_i_save_employee_with_employee_then_return_employee() {
        //given
        Role role = Role.builder().name("developer").build();
        Employee employee = Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build();

        //when
        Employee saveEmployee = employeeRepository.save(employee);

        //then
        Assertions.assertThat(saveEmployee).isNotNull();
    }

    @Test
    void when_i_call_find_all_then_return_employees() {
        //given
        employeeRepository.saveAll(getAllEmployees());

        //when
        List<Employee> employeeList = employeeRepository.findAll();

        //then
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }



    private List<Employee> getAllEmployees(){
        Role role = Role.builder().name("developer").build();
       return new ArrayList<>(Arrays.asList(Employee.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleList(Collections.singletonList(role)).build(),Employee.builder()
                .name("placide")
                .email("placide@gmail.com")
                .roleList(Collections.singletonList(role)).build()));

    }

}