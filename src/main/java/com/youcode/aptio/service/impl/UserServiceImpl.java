package com.youcode.aptio.service.impl;

import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
