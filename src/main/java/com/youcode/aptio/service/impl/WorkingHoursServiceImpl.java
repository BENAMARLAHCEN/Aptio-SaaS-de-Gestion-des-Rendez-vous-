package com.youcode.aptio.service.impl;

import com.youcode.aptio.dto.workingHours.BusinessHoursResponse;
import com.youcode.aptio.dto.workingHours.WorkingHoursRequest;
import com.youcode.aptio.dto.workingHours.WorkingHoursResponse;
import com.youcode.aptio.exception.InvalidWorkingHoursException;
import com.youcode.aptio.exception.ResourceNotFoundException;
import com.youcode.aptio.exception.UnauthorizedAccessException;
import com.youcode.aptio.model.Business;
import com.youcode.aptio.model.User;
import com.youcode.aptio.model.WorkingHours;
import com.youcode.aptio.repository.BusinessRepository;
import com.youcode.aptio.repository.UserRepository;
import com.youcode.aptio.repository.WorkingHoursRepository;
import com.youcode.aptio.service.WorkingHoursService;
import com.youcode.aptio.util.mapper.WorkingHoursMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final WorkingHoursRepository workingHoursRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final WorkingHoursMapper workingHoursMapper;

    @Override
    @Transactional
    public WorkingHoursResponse createWorkingHours(Long businessId, WorkingHoursRequest request) {
        Business business = verifyBusinessAndOwnership(businessId);
        validateWorkingHours(request);

        // Check if working hours already exist for this day
        if (workingHoursRepository.existsByBusinessIdAndDayOfWeek(businessId, request.getDayOfWeek())) {
            throw new InvalidWorkingHoursException("Working hours already exist for this day");
        }

        WorkingHours workingHours = workingHoursMapper.toWorkingHours(request);
        workingHours.setBusiness(business);

        return workingHoursMapper.toWorkingHoursResponse(workingHoursRepository.save(workingHours));
    }

    @Override
    @Transactional
    public WorkingHoursResponse updateWorkingHours(Long workingHoursId, WorkingHoursRequest request) {
        WorkingHours workingHours = workingHoursRepository.findById(workingHoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Working hours not found"));

        verifyBusinessAndOwnership(workingHours.getBusiness().getId());
        validateWorkingHours(request);

        workingHoursMapper.updateWorkingHoursFromRequest(request, workingHours);
        return workingHoursMapper.toWorkingHoursResponse(workingHoursRepository.save(workingHours));
    }

    @Override
    @Transactional
    public void deleteWorkingHours(Long workingHoursId) {
        WorkingHours workingHours = workingHoursRepository.findById(workingHoursId)
                .orElseThrow(() -> new ResourceNotFoundException("Working hours not found"));

        verifyBusinessAndOwnership(workingHours.getBusiness().getId());
        workingHoursRepository.delete(workingHours);
    }

    @Override
    public List<WorkingHoursResponse> getWorkingHoursByBusiness(Long businessId) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business not found");
        }

        return workingHoursRepository.findByBusinessId(businessId).stream()
                .map(workingHoursMapper::toWorkingHoursResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessHoursResponse getBusinessSchedule(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        List<WorkingHours> allWorkingHours = workingHoursRepository.findByBusinessId(businessId);

        // Group working hours by day
        Map<String, List<WorkingHoursResponse>> weeklySchedule = allWorkingHours.stream()
                .map(workingHoursMapper::toWorkingHoursResponse)
                .collect(Collectors.groupingBy(wh -> wh.getDayOfWeek().toString()));

        // Find closed days
        List<String> closedDays = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            if (!weeklySchedule.containsKey(day.toString())) {
                closedDays.add(day.toString());
            }
        }

        // Check if business is open 24/7
        boolean isOpen24Hours = allWorkingHours.stream()
                .allMatch(wh -> wh.getStartTime().equals(LocalTime.MIN) && wh.getEndTime().equals(LocalTime.MAX));

        return BusinessHoursResponse.builder()
                .businessId(business.getId())
                .businessName(business.getName())
                .weeklySchedule(weeklySchedule)
                .isOpen24Hours(isOpen24Hours)
                .closedDays(closedDays)
                .build();
    }

    @Override
    public WorkingHoursResponse getWorkingHoursByDay(Long businessId, DayOfWeek dayOfWeek) {
        return workingHoursRepository.findByBusinessIdAndDayOfWeek(businessId, dayOfWeek)
                .map(workingHoursMapper::toWorkingHoursResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Working hours not found for the specified day"));
    }

    @Override
    public boolean isBusinessOpenNow(Long businessId) {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();

        return workingHoursRepository.findByBusinessIdAndDayOfWeek(businessId, currentDay)
                .map(workingHours -> {
                    // Check if business is open 24 hours
                    if (workingHours.getStartTime().equals(LocalTime.MIN) &&
                            workingHours.getEndTime().equals(LocalTime.MAX)) {
                        return true;
                    }

                    LocalTime currentTime = now.toLocalTime();
                    LocalTime startTime = workingHours.getStartTime();
                    LocalTime endTime = workingHours.getEndTime();

                    // Handle normal hours
                    if (endTime.isAfter(startTime)) {
                        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
                    }
                    // Handle hours that span midnight
                    else {
                        return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
                    }
                })
                .orElse(false);
    }

    private Business verifyBusinessAndOwnership(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Allow access for ADMIN role
        if (currentUser.getRole().getName().equals("ROLE_ADMIN")) {
            return business;
        }

        // Check if the current user is the business owner
        if (business.getOwner() == null || !business.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You don't have permission to manage working hours for this business");
        }

        return business;
    }

    private void validateWorkingHours(WorkingHoursRequest request) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new InvalidWorkingHoursException("Start time and end time must be provided");
        }

        if (request.getDayOfWeek() == null) {
            throw new InvalidWorkingHoursException("Day of week must be provided");
        }

        // Allow equal times for 24-hour operation
        if (!request.getStartTime().equals(request.getEndTime()) &&
                request.getStartTime().isAfter(request.getEndTime())) {
            throw new InvalidWorkingHoursException("Start time must be before or equal to end time");
        }
    }
}