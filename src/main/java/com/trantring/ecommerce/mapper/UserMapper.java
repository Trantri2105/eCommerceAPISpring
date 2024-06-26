package com.trantring.ecommerce.mapper;

import com.trantring.ecommerce.dto.response.UserPageResponseDTO;
import com.trantring.ecommerce.dto.response.UserResponseDTO;
import com.trantring.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO userToUserResponseDTO(User user);

    List<UserResponseDTO> userPageToUserResponseDTOList(Page<User> userPage);

    @Mapping(target = "pageNumber", source = "userPage.number")
    @Mapping(target = "pageSize", source = "userPage.size")
    @Mapping(target = "users", source = "userPage")
    UserPageResponseDTO userPageToUserPageResponseDTO(Page<User> userPage);
}
