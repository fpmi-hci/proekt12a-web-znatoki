package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.LoginDto;

public interface LoginService {
    void authorize(LoginDto loginDto);
}
