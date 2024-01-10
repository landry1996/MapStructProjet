package org.landry.demomapperstruct.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.landry.demomapperstruct.model.Employee;
import org.landry.demomapperstruct.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class RoleRepositoryTest {

   /* @BeforeEach
    void setUp() {
    }*/
    @Autowired
    private RoleRepository repository;

    @Test
    void tesFindRoleByName() {
        //given
     Role role = Role.builder()
             .name("Developer")
             .build();
        repository.save(role);
        //when

        Role sechRole = repository.findRoleByName("Developer").orElse(null);

        //then

        Assertions.assertThat(sechRole).isNotNull();
    }

    @Test
    void tesFindRoleByName_then_return_null() {
        //given
        Role role = Role.builder()
                .name("Developer")
                .build();
        repository.save(role);
        //when

        Role sechRole = repository.findRoleByName("Admin").orElse(null);

        //then

        Assertions.assertThat(sechRole);
    }

}