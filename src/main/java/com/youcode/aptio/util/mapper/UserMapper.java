package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.auth.RegisterRequest;
import com.youcode.aptio.dto.user.CreateUserRequest;
import com.youcode.aptio.dto.user.UpdateUserRequest;
import com.youcode.aptio.dto.user.UserResponse;
import com.youcode.aptio.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "role", source = "role.name")
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    User toUser(UpdateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);

    @Mapping(target = "role", ignore = true)
    User toUser(CreateUserRequest request);
}