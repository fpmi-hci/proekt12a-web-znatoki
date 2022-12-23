package com.bsu.znatoki.service.impl.entitiy;

import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.entity.User;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.mapper.UserMapper;
import com.bsu.znatoki.service.UserService;
import com.bsu.znatoki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findByEmail(String email) throws NotFoundEntityException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> {
            throw new NotFoundEntityException(String.format("No user with email %s", email));
        });
        return userMapper.mapToDto(fetchLazyRoles(user));
    }

    @Override
    public int findIdByEmail(String email) {
        return userRepository.findIdByEmail(email).orElseThrow(() -> {
            throw new NotFoundEntityException(String.format("No user with email %s", email));
        });
    }

    @Override
    public UserDto save(UserDto userDto) {
        log.info("SAVING USER WITH EMAIL " + userDto.getEmail());
        User user = userMapper.mapToEntity(userDto);
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void setActivatedById(int id, boolean activated) {
        userRepository.setActivatedById(id, activated);
    }

    private User fetchLazyRoles(User user) {
        log.info("FETCHING LAZY ROLES ROLES OF USER WITH ID " + user.getId());
        user.getRoles().size();
        return user;
    }
}
