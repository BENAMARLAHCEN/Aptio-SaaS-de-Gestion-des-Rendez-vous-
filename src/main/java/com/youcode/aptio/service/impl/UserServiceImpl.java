package com.youcode.aptio.service.impl;

import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
}