package com.demo.auth.mapper;

import com.demo.auth.dto.request.RegisterRequest;
import com.demo.auth.dto.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = RegisterMapperConfig.class)
public interface RegisterMapper extends Converter<RegisterRequest, RegisterResponse> {
    @Override
    @Nullable
    @Mapping(target = "avatar", constant = "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small_2x/default-avatar-icon-of-social-media-user-vector.jpg")
    RegisterResponse convert(@Nullable RegisterRequest source);
}