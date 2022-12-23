package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.UserDto;

public interface UserService {
    UserDto findByEmail(String email);

    int findIdByEmail(String email);

    UserDto save(UserDto userDto);

    boolean existsByEmail(String email);

    void setActivatedById(int id, boolean activated);
}
