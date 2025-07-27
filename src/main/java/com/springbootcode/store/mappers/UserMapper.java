package com.springbootcode.store.mappers;

import com.springbootcode.store.dto.RegisterUserDto;
import com.springbootcode.store.dto.UpdateUserRequest;
import com.springbootcode.store.dto.UserDto;
import com.springbootcode.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toEntity(RegisterUserDto registerUserDto);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
