package org.landry.demomapperstruct.mappers;

import org.landry.demomapperstruct.dto.RoleDto;
import org.landry.demomapperstruct.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role mapRoleDtoToRole(RoleDto roleDto);

    RoleDto mapRoleToRoleDto(Role role);
}
