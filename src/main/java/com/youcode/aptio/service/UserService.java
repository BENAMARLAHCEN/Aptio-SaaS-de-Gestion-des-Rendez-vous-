package com.youcode.aptio.service;

import com.youcode.aptio.dto.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    // Admin operations
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getUserById(Long id);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    UserResponse changeUserRole(Long id, String role);
    UserResponse blockUser(Long id);
    UserResponse unblockUser(Long id);
    Page<UserResponse> searchUsers(String query, Pageable pageable);

    // User profile operations
    UserResponse getCurrentUserProfile();
    UserResponse updateCurrentUserProfile(UpdateProfileRequest request);
    void changePassword(ChangePasswordRequest request);
}