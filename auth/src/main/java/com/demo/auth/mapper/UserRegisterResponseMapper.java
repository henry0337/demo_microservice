package com.demo.auth.mapper;

import com.demo.auth.dto.response.RegisterResponse;
import com.demo.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(uses = UserRegisterResponseMapperConfig.class)
public interface UserRegisterResponseMapper extends Converter<RegisterResponse, User> {

    @Override
    @Nullable
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isCredentialsExpired", ignore = true)
    @Mapping(target = "isAccountLocked", ignore = true)
    @Mapping(target = "isAccountExpired", ignore = true)
    @Mapping(target = "canAuthenticate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User convert(@Nullable RegisterResponse source);
}
