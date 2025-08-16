package xyz.thaumazein.forumhub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import xyz.thaumazein.forumhub.dtos.RegisterUserRequest;
import xyz.thaumazein.forumhub.dtos.UpdateUserRequest;
import xyz.thaumazein.forumhub.dtos.UserDto;
import xyz.thaumazein.forumhub.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(RegisterUserRequest dto);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
