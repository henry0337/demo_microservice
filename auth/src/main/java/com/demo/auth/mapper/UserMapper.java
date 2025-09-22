package com.demo.auth.mapper;

import com.demo.auth.dto.request.RegisterRequest;
import com.demo.auth.dto.response.RegisterResponse;
import com.demo.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author <a href="https://github.com/henry0337">Moineau</a>, <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Mapper
public interface UserMapper {
    RegisterResponse fromRegisterRequestToItsResponse(RegisterRequest request);

    @Mapping(target = "avatar",
            constant = "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small_2x/default-avatar-icon-of-social-media-user-vector.jpg")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isCredentialsExpired", ignore = true)
    @Mapping(target = "isAccountLocked", ignore = true)
    @Mapping(target = "isAccountExpired", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User fromRegisterResponseToUser(RegisterResponse response);
}
