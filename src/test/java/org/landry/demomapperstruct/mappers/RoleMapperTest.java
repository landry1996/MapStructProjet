package org.landry.demomapperstruct.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EmployeeMapperImpl.class,RoleMapperImpl.class})

class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void mapRoleDtoToRole() {

        //given

        RoleDto roleDto = RoleDto.builder()
                .name("Developer")
                .build();
        //when
        Role role = roleMapper.mapRoleDtoToRole(roleDto);

        //then
        Assertions.assertEquals(role.getName(), roleDto.getName());
    }

    @Test
    void mapRoleToRoleDto() {
        //given
        Role role = Role.builder().name("chauffeur").build();

        //when
        RoleDto roleDto = roleMapper.mapRoleToRoleDto(role);

        //then
        Assertions.assertEquals(roleDto.getName(),role.getName());
    }
}