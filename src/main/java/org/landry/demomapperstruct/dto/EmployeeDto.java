package org.landry.demomapperstruct.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;

    private List<RoleDto> roleDtoList = new ArrayList<>();
}
