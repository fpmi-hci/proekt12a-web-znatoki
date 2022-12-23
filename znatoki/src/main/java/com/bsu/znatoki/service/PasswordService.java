package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.PasswordResetDto;

public interface PasswordService {
    void reset(String email);

    void confirmReset(PasswordResetDto resetDto);
}
