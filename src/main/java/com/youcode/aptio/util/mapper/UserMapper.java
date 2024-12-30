package com.youcode.aptio.util.mapper;

import com.youcode.aptio.dto.auth.RegisterRequest;
import com.youcode.aptio.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest registerRequest);
}
