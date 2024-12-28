package com.youcode.aptiov2.service.impl;

import com.youcode.aptiov2.repository.UserRepository;
import com.youcode.aptiov2.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
