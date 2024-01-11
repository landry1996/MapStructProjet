package org.landry.demomapperstruct.services.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.landry.demomapperstruct.dao.EmployeeRepository;
import org.landry.demomapperstruct.dao.RoleRepository;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeAlreadyExistException;
import org.landry.demomapperstruct.error.EmployeeErrorException;
import org.landry.demomapperstruct.error.EmployeeNotFoundException;
import org.landry.demomapperstruct.mappers.EmployeeMapper;
import org.landry.demomapperstruct.mappers.EmployeeMapperImpl;
import org.landry.demomapperstruct.mappers.RoleMapper;
import org.landry.demomapperstruct.model.Employee;
import org.landry.demomapperstruct.model.Role;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.landry.demomapperstruct.utils.Utils.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration(classes = {EmployeeServiceImpl.class, EmployeeMapperImpl.class})
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private RoleMapper roleMapper;


    @Test
    void testAddEmployee() throws EmployeeAlreadyExistException {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder().email("fadila@gmail.com").name("fadila").build();
        Employee employee = employeeMapper.mapEmployeeDtoToEmployee(employeeDto);

        Mockito.when(employeeRepository.findEmployeeByEmail(employeeDto.getEmail())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
        Mockito.when(employeeMapper.mapEmployeeToEmployeeDto(employee)).thenReturn(employeeDto);

        //when
        EmployeeDto saveEmployeeDto = employeeService.addEmployee(employeeDto);
        //then
        Assertions.assertThat(saveEmployeeDto).isNotNull();

    }

    @Test
    void testAddEmployeeAlreadyExist() throws EmployeeAlreadyExistException {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder().email("fadila@gmail.com").name("fadila").build();
        Employee existingEmployee = Employee.builder().email("fadila@gmail.com").name("fadila").build();

        //when
        Mockito.when(employeeMapper.mapEmployeeDtoToEmployee(employeeDto)).thenReturn(existingEmployee);
        Mockito.when(employeeMapper.mapEmployeeToEmployeeDto(existingEmployee)).thenReturn(employeeDto);
        Mockito.when(employeeRepository.findEmployeeByEmail(employeeDto.getEmail()))
                .thenReturn(Optional.ofNullable(existingEmployee));


        //then
        Assertions.assertThatThrownBy(() -> employeeService.addEmployee(employeeDto))
                .isInstanceOf(EmployeeAlreadyExistException.class)
                .hasMessage(EMPLOYEE_ALREADY_EXIST_IN_DATABASE);


    }

    @Test
    void testGetListEmployees() {
        //given
        List<Employee> employees = getListEmployees();
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        //when

        List<EmployeeDto> employeeDtos = employeeService.getListEmployees();

        //then
        Assertions.assertThat(employeeDtos).isNotNull();
        Assertions.assertThat(employeeDtos.size()).isEqualTo(2);


    }


    @Test
    void testReturnEmptyListEmployees() {

        //given
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<EmployeeDto> employeeDtos = employeeService.getListEmployees();

        //then
        Assertions.assertThat(employeeDtos).isEmpty();
    }

    @Test
    void testEmployeeFindById() throws EmployeeNotFoundException {
        //given
        Long Id = 1L;
        Employee employee = getEmployee(Id);
        EmployeeDto employeeDto = getEmployeeDto(Id);
        Mockito.when(employeeRepository.findById(Id)).thenReturn(Optional.of(employee));
        Mockito.when(employeeMapper.mapEmployeeToEmployeeDto(employee)).thenReturn(employeeDto);

        //when
        EmployeeDto foundEmployeeDto = employeeService.findEmployeeById(Id);

        //then
        Assertions.assertThat(foundEmployeeDto).isNotNull();
        Assertions.assertThat(foundEmployeeDto.getName()).isNotNull();
        Assertions.assertThat(foundEmployeeDto.getEmail()).isNotNull();
        Assertions.assertThat(foundEmployeeDto.getRoleDtoList()).isNotNull();

    }

    @Test
    void testGetEmployeeByIdThenThrowEmployeeNotFoundException() throws EmployeeNotFoundException {
        //given
        Long Id = 1L;

        //when
        Mockito.when(employeeRepository.findById(Id)).thenReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy( () -> employeeService.findEmployeeById(Id))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_NOT_EXIST);

    }

    @Test
    void testfindEmployeeByName() throws EmployeeNotFoundException {
        //given
        String name = "romeo";
        Employee employee = getEmployee(name);
        EmployeeDto employeeDto = getEmployeeDto(name);
        Mockito.when(employeeRepository.findEmployeeByName(name)).thenReturn(Optional.of(employee));
        Mockito.when(employeeMapper.mapEmployeeToEmployeeDto(employee)).thenReturn(employeeDto);

        //when
        EmployeeDto foundEmployeeDto = employeeService.findEmployeeByName(name);

        //then
        Assertions.assertThat(foundEmployeeDto).isNotNull();

    }


    @Test
    void testGetEmployeeByNameNotFound() throws EmployeeNotFoundException {

        //given
        String userName = "bernard";

        //when
        Mockito.when(employeeRepository.findEmployeeByName(userName)).thenReturn(Optional.empty());

        //then

        Assertions.assertThatThrownBy( () -> employeeService.findEmployeeByName(userName))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_NOT_EXIST);

    }

    @Test
    void testUpdateEmployee() throws EmployeeErrorException, EmployeeNotFoundException {

        //given
        Employee employee = getEmployeeUpdated();
        EmployeeDto employeeDto = getEmployeeDtoUpdated();

        Mockito.when(employeeRepository.findEmployeeByEmail("loic@gmail.com")).thenReturn(Optional.of(employee));
        Mockito.when(employeeMapper.mapEmployeeDtoToEmployee(employeeDto)).thenReturn(employee);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.when(employeeMapper.mapEmployeeToEmployeeDto(employee)).thenReturn(employeeDto);

        //when
        EmployeeDto updateEmployeeDto = employeeService.updateEmployee(employeeDto);

        //then
        Assertions.assertThat(1L).isEqualTo(updateEmployeeDto.getId());
        Assertions.assertThat("loic").isEqualTo(updateEmployeeDto.getName());
        Assertions.assertThat("loic@gmail.com").isEqualTo(updateEmployeeDto.getEmail());

    }

    @Test
    void testUpdateEmployeeAndGetEmployeeNotFoundException() throws EmployeeErrorException, EmployeeNotFoundException {

        //given
        EmployeeDto employeeDto = getEmployeeDtoUpdated();

        //when
        Mockito.when(employeeRepository.findEmployeeByEmail("loic@gmail.com")).thenReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy( () -> employeeService.updateEmployee(employeeDto))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_NOT_EXIST);


    }

    @Test
    void testUpdateEmployeeAndGetEmployeeErrorException() throws EmployeeErrorException, EmployeeNotFoundException {

        //given
        EmployeeDto employeeDto = null;

        //when
        //not get value for this step

        //then
        Assertions.assertThatThrownBy( () -> employeeService.updateEmployee(employeeDto))
                .isInstanceOf(EmployeeErrorException.class)
                .hasMessage(ERROR_UNKNOWM);


    }


    @Test
    void testDeleteEmployee() {

        //given
        Long Id = 1L;
        Employee employee = Employee.builder().id(Id).name("landry").email("landry@gmail.com").build();
        Mockito.when(employeeRepository.findById(Id)).thenReturn(Optional.of(employee));

        //when
        Consumer<Long> consumer = (Long employeeId) -> {
            try {
                employeeService.deleteEmployee(employeeId);
            } catch (EmployeeNotFoundException e) {
                throw new RuntimeException(e);
            }
        };

        //then
        consumer.accept(Id);

    }


    @Test
    void testDeleteEmployeeThenReturnEmployeeNotFoundException() {

        //given
        Long Id = 1L;
        Mockito.when(employeeRepository.findById(Id)).thenReturn(Optional.empty());

        //when
        //not step get

        //then
        Assertions.assertThatThrownBy( () -> employeeService.deleteEmployee(Id))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_NOT_EXIST);

    }


    @Test
    void TestToDeleteEmployeeWhenIdNotFound() {

        //given
        Long Id = 2L;
        Mockito.when(employeeRepository.existsById(Id)).thenReturn(false);
        //when
        //not step get in

        //then
        Assertions.assertThatThrownBy( () -> employeeService.deleteEmployee(Id))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_NOT_EXIST);



    }

    @Test
    void testAddRoleToEmployee() {

        //given
        String name = "landry";
        String email = "landry@gmail.com";
        String roleName = "developer";

        Employee employeeWithOutRole = employeeWithOutRole(name,email);

        Employee employeeWithRole = employeeWithRole(name,email);

        Mockito.when(employeeRepository.findEmployeeByEmail(email)).thenReturn(Optional.of(employeeWithOutRole));
        Mockito.when(roleRepository.findRoleByName(roleName)).thenReturn(Optional.of(role()));
        Mockito.when(employeeRepository.save(employeeWithRole)).thenReturn(employeeWithRole);

        //when

        //when
        BiConsumer<String, RoleDto> biConsumer = (String eValue, RoleDto rDto) -> {
            try {
                employeeService.addRoleToEmployee(eValue, rDto);
            } catch (EmployeeNotFoundException e) {
                throw new RuntimeException(e);
            } catch (EmployeeErrorException e) {
                throw new RuntimeException(e);
            }
        };




        //then
        biConsumer.accept(email, RoleDto.builder().name(roleName).build());


    }

    @Test
    void testAddRoleToEmployeeThenThrowEmployeeNotFoundException() throws EmployeeErrorException, EmployeeNotFoundException {

        //given
        String email = "landry@gmail.com";
        String roleName = "developer";

        Mockito.when(employeeRepository.findEmployeeByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findRoleByName(roleName)).thenReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy( () -> employeeService
                .addRoleToEmployee(email, RoleDto.builder().name("developer").build()))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage(EMPLOYEE_OR_ROLE_NOT_EXIST);

    }

    private List<Employee> getListEmployees() {
        return Arrays.asList(
                Employee.builder()
                        .name("willy")
                        .email("willy@gmail.com")
                        .roleList(Collections.singletonList(Role.builder().name("Developpeur").build()))
                        .build(),
                Employee.builder()
                        .name("brunel")
                        .email("brunel@gmail.com")
                        .roleList(Collections.singletonList(Role.builder().name("eleveur").build()))
                        .build());

    }
    private Employee employeeWithOutRole(String name, String email){
        return Employee.builder()
                .name(name)
                .email(email)
                .roleList(Collections.emptyList())
                .build();
    }
    private Employee employeeWithRole(String name, String email){

        return Employee.builder()
                .name(name)
                .email(email)
                .roleList(Collections.singletonList(role()))
                .build();
    }
    private Role role(){
        return Role.builder().name("Developer").build();
    }



    private Employee getEmployeeUpdated() {
        return Employee.builder()
                .id(1L)
                .name("loic")
                .email("loic@gmail.com")
                .roleList(Collections.singletonList(Role.builder().name("developer").build()))
                .build();
    }

    private EmployeeDto getEmployeeDtoUpdated() {
        return EmployeeDto.builder()
                .id(1L)
                .name("loic")
                .email("loic@gmail.com")
                .roleDtoList(Collections.singletonList(RoleDto.builder().name("developer").build()))
                .build();
    }

    private Employee getEmployee(String employeeName) {
        return Employee.builder()
                .name(employeeName)
                .email("romeo@gmail.com")
                .roleList(Collections.singletonList(Role.builder().name("Senior Manager").build()))
                .build();
    }

    private EmployeeDto getEmployeeDto(String employeeName) {
        return EmployeeDto.builder()
                .name(employeeName)
                .email("romeogmail.com")
                .roleDtoList(Collections.singletonList(RoleDto.builder().name("Senior Manager").build()))
                .build();
    }

    private Employee getEmployee(Long Id) {
        return Employee.builder()
                .id(Id)
                .name("loic")
                .email("loic@gmail.com")
                .roleList(Collections.singletonList(Role.builder().name("Chief Project").build()))
                .build();
    }

    private EmployeeDto getEmployeeDto(Long Id) {
        return EmployeeDto.builder()
                .id(Id)
                .name("loic")
                .email("loic@gmail.com")
                .roleDtoList(Collections.singletonList(RoleDto.builder().name("Chief Project").build()))
                .build();
    }

}