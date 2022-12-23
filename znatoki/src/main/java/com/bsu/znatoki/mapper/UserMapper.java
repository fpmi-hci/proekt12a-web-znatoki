package com.bsu.znatoki.mapper;

import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setRoles(userDto.getRoles());
        user.setActivated(userDto.isActivated());
        return user;
    }

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setActivated(user.isActivated());
        return userDto;
    }
}
