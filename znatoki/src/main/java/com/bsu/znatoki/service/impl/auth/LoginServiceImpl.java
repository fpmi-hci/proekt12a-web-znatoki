package com.bsu.znatoki.service.impl.auth;

import com.bsu.znatoki.dto.LoginDto;
import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.exception.UserNotActivatedException;
import com.bsu.znatoki.service.LoginService;
import com.bsu.znatoki.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void authorize(LoginDto loginDto) {
        log.info(String.format("TRYING AUTHORIZE WITH EMAIL %s AND PASSWORD %s",
                loginDto.getEmail(), loginDto.getPassword()));
        UserDto userDto = userService.findByEmail(loginDto.getEmail());

        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            throw new NotFoundEntityException("Incorrect credentials!");
        }
        if (!userDto.isActivated()) {
            throw new UserNotActivatedException("User not activated!");
        }
    }
}