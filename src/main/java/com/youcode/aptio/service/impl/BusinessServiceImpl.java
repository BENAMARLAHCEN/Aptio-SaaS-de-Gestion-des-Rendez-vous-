package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.business.BusinessRequest;
import com.youcode.aptio.dto.business.BusinessResponse;
import com.youcode.aptio.dto.business.BusinessUpdateRequest;
import com.youcode.aptio.exception.BusinessAlreadyExistsException;
import com.youcode.aptio.exception.ResourceNotFoundException;
import com.youcode.aptio.exception.UnauthorizedAccessException;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.Role;
import com.youcode.aptio.model.User;
import com.youcode.aptio.model.enums.SubscriptionPlan;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.RoleRepository;
import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.service.BusinessService;
import com.youcode.aptio.util.TimezoneUtils;
import com.youcode.aptio.util.mapper.BusinessMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BusinessMapper businessMapper;

    @Override
    @Transactional
    public BusinessResponse createBusiness(BusinessRequest request) {
        // Validate business name uniqueness
        if (businessRepository.existsByName(request.getName())) {
            throw new BusinessAlreadyExistsException("Business name already exists");
        }

        // Validate timezone
        if (!TimezoneUtils.isValidTimezone(request.getTimezone())) {
            throw new IllegalArgumentException("Invalid timezone");
        }

        // Create and save business
        Business business = businessMapper.toBusiness(request);
        business.setPlan(SubscriptionPlan.FREE);

        business.onCreate();
        return businessMapper.toBusinessResponse(businessRepository.save(business));
    }

    @Override
    @Transactional
    public BusinessResponse updateBusiness(Long id, BusinessUpdateRequest request) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRole().getName().equals("ROLE_ADMIN");
        boolean isOwner = business.getOwner() != null && business.getOwner().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new UnauthorizedAccessException("You don't have permission to update this business");
        }

        if (!business.getName().equals(request.getName()) &&
                businessRepository.existsByName(request.getName())) {
            throw new BusinessAlreadyExistsException("Business name already exists");
        }

        if (!TimezoneUtils.isValidTimezone(request.getTimezone())) {
            throw new IllegalArgumentException("Invalid timezone");
        }

        businessMapper.updateBusinessFromRequest(request, business);

        business.onUpdate();
        return businessMapper.toBusinessResponse(businessRepository.save(business));
    }

    @Override
    @Transactional
    public void deleteBusiness(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        // Reset owner's role to USER if this was their only business
        if (business.getOwner() != null) {
            User owner = business.getOwner();
            List<Business> ownerBusinesses = businessRepository.findByOwner(owner);
            if (ownerBusinesses.size() == 1) { // This was their only business
                Role userRole = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new ResourceNotFoundException("User role not found"));
                owner.setRole(userRole);
                userRepository.save(owner);
            }
        }

        businessRepository.delete(business);
    }

    @Override
    @Transactional
    public BusinessResponse assignOwner(Long businessId, Long userId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get or create BUSINESS_OWNER role
        Role businessOwnerRole = roleRepository.findByName("ROLE_BUSINESS_OWNER")
                .orElseThrow(() -> new ResourceNotFoundException("Business owner role not found"));

        // Set user's role to BUSINESS_OWNER
        user.setRole(businessOwnerRole);
        userRepository.save(user);

        // Assign owner to business
        business.setOwner(user);
        return businessMapper.toBusinessResponse(businessRepository.save(business));
    }

    @Override
    public BusinessResponse getBusinessById(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        // Check authorization
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRole().getName().equals("ROLE_ADMIN");
        boolean isOwner = business.getOwner() != null && business.getOwner().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new UnauthorizedAccessException("You don't have permission to view this business");
        }

        return businessMapper.toBusinessResponse(business);
    }

    @Override
    public List<BusinessResponse> getAllBusinesses() {
        return businessRepository.findAll().stream()
                .map(businessMapper::toBusinessResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BusinessResponse> getMyBusinesses() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return businessRepository.findByOwner(currentUser).stream()
                .map(businessMapper::toBusinessResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessResponse getBusinessByName(String name) {
        return businessRepository.findByNameIgnoreCase(name)
                .map(businessMapper::toBusinessResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
    }

    @Override
    public List<BusinessResponse> searchBusinesses(String query) {
        return businessRepository.searchBusinesses(query).stream()
                .map(businessMapper::toBusinessResponse)
                .collect(Collectors.toList());
    }
}