package com.demo.auth.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@MapperConfig
@SpringMapperConfig(
        conversionServiceAdapterPackage = "com.demo.auth.config",
        conversionServiceAdapterClassName = "UserRegisterResponseConversionAdapter"
)
public interface UserRegisterResponseMapperConfig { }
