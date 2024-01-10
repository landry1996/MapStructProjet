package org.landry.demomapperstruct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeErrorException;
import org.landry.demomapperstruct.error.EmployeeNotFoundException;
import org.landry.demomapperstruct.services.EmployeeService;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeDto employeeDto;

    @BeforeEach
    void initialize() {
        employeeDto = EmployeeDto.builder()
                .name("landry")
                .email("landry@gmail.com")
                .roleDtoList(Collections.singletonList(RoleDto.builder().name("developer").build())).build();
    }

    @Test
    void testCreatedEmployee() throws Exception {
        //given
        given(employeeService.addEmployee(ArgumentMatchers.any())).willAnswer(employeeDto -> employeeDto.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(employeeDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));


    }

    @Test
    void testGetAllEmployeeList() throws Exception {
        //given
        when(employeeService.getListEmployees()).thenReturn(Collections.singletonList(employeeDto));

        //when
        ResultActions response = mockMvc.perform(get("/api/listEmployees")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Collections.singletonList(employeeDto).size()));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        //given
        Long employeeId = 1L;
        Consumer<Long> consumerDeleteEmployee = (Id) -> {
            try {
                employeeService.deleteEmployee(Id);
            } catch (EmployeeErrorException e) {
                throw new RuntimeException(e);
            } catch (EmployeeNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
        consumerDeleteEmployee.accept(employeeId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/delete/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    void testUpdateEmployee() throws Exception {
        //given
        when(employeeService.updateEmployee(employeeDto)).thenReturn(employeeDto);

        //when
        ResultActions response = mockMvc.perform(patch("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void testGetEmployeeByName() throws Exception {
        //given
        String employeeName = "landry";
        when(employeeService.findEmployeeByName(employeeName)).thenReturn(employeeDto);

        //when
        ResultActions response = mockMvc.perform(get("/api/employeeByName/" + employeeName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(employeeDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));


    }

    @Test
    void testGetEmployeeById() throws Exception {
        //given
        Long employeeID = 1L;
        when(employeeService.findEmployeeById(employeeID)).thenReturn(employeeDto);

        //when
        ResultActions response = mockMvc.perform(get("/api/employeeById/" + employeeID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(employeeDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));


    }

    @Test
    void testAddRoleToEmployee() throws Exception {
        //given
        String employeeEmail = "landry@gmail.com";
        RoleDto roleDto = RoleDto.builder().name("Developer").build();

        BiConsumer<String, RoleDto> consumer = (email, role) -> {
            try {
                employeeService.addRoleToEmployee(email, role);
            } catch (EmployeeNotFoundException e) {
                throw new RuntimeException(e);
            } catch (EmployeeErrorException e) {
                throw new RuntimeException(e);
            }
        };

        consumer.accept(employeeEmail, roleDto);

        //when
        ResultActions response = mockMvc.perform(patch("/api/employee/" + employeeEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}