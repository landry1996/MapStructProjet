package org.landry.demomapperstruct;

import lombok.RequiredArgsConstructor;
import org.landry.demomapperstruct.dao.RoleRepository;
import org.landry.demomapperstruct.dto.EmployeeDto;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.error.EmployeeAlreadyExistException;
import org.landry.demomapperstruct.mappers.RoleMapper;
import org.landry.demomapperstruct.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
//@RequiredArgsConstructor
public class DemoMapperstructApplication implements CommandLineRunner {

//    private final EmployeeService employeeService;
//    private final RoleRepository roleRepository;
//    private final RoleMapper roleMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoMapperstructApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<EmployeeDto> employeeDtos = new ArrayList<>(Arrays.asList(
//                EmployeeDto.builder().name("joel").email("joel@gmail.com").build(),
//                EmployeeDto.builder().name("landry").email("landry@gmail.com").build(),
//                EmployeeDto.builder().name("placide").email("placide@gmail.com").build(),
//                EmployeeDto.builder().name("idriss").email("idriss@gmail.com").build()
//        ));
//        for (EmployeeDto employeeDto:employeeDtos){
//            employeeService.addEmployee(employeeDto);
//        }
//
//        Set<RoleDto> roleDtoList = new HashSet<>(Set.of(
//                RoleDto.builder().name("Bandit").build(),
//                RoleDto.builder().name("Admin").build(),
//                RoleDto.builder().name("User").build(),
//                RoleDto.builder().name("Developer").build()
//        ));
//
//        roleRepository.deleteAll();
//        roleDtoList.forEach(roleDTO -> roleRepository.save(roleMapper.mapRoleDtoToRole(roleDTO)));
//
//        employeeService.addRoleToEmployee("joel@gmail.com", RoleDto.builder().name("Admin").build());

    }
}
