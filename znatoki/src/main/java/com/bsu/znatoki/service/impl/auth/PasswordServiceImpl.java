package com.bsu.znatoki.service.impl.auth;

import com.bsu.znatoki.dto.PasswordResetDto;
import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.redis.RedisService;
import com.bsu.znatoki.service.MailSenderService;
import com.bsu.znatoki.exception.IncorrectEmailDataException;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.service.PasswordService;
import com.bsu.znatoki.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private static final String RESET_TOPIC = "News agency password reset";
    private static final int TIMEOUT_HOURS = 24;

    private final RedisService redisService;
    private final UserService userService;
    private final MailSenderService mailSender;
    private final PasswordEncoder passwordEncoder;

    public void reset(String email) {
        log.info("PASSWORD RESET: CHECKING IF USER EXISTS, GENERATING HASH AND SENDING EMAIL");
        if (!userExists(email)) {
            throw new NotFoundEntityException(String.format("User with email %s doesnt exist!", email));
        }
        String uuidHash = UUID.randomUUID().toString();
        redisService.add(uuidHash, email, TIMEOUT_HOURS, TimeUnit.HOURS);
        sendConfirmMessage(email, uuidHash);
    }

    public void confirmReset(PasswordResetDto resetDto) {
        log.info("PASSWORD RESET CONFIRMATION...");
        if (!redisService.contains(resetDto.getHash())) {
            throw new NotFoundEntityException(String.format("It's no password-update request with hash %s",
                    resetDto.getHash()));
        }
        String email = String.valueOf(redisService.retrieve(resetDto.getHash()));
        UserDto userDto = userService.findByEmail(email);
        userDto.setPassword(cryptPassword(resetDto.getPassword()));
        userService.save(userDto);
    }

    private boolean userExists(String email) {
        return userService.existsByEmail(email);
    }

    private void sendConfirmMessage(String email, String hash) {
        log.info("SENDING PASSWORD RESET CONFIRMATION TO " + email);
        try {
            mailSender.sendEmail(email, RESET_TOPIC,hash);
        } catch (MessagingException e) {
            throw new IncorrectEmailDataException(e);
        }
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
