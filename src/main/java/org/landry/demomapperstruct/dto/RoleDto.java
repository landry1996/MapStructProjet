package org.landry.demomapperstruct.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;

    public RoleDto(String rolename) {
    }
}
