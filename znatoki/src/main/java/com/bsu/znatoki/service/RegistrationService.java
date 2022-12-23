package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.UserDto;

public interface RegistrationService {
    void register(UserDto userDto);

    void confirmAndCreate(String hash);
}
