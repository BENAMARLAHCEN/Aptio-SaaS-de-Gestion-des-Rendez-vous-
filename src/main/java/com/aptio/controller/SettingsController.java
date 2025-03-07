// src/main/java/com/aptio/controller/SettingsController.java
package com.aptio.controller;

import com.aptio.model.BusinessSettings;
import com.aptio.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/business")
    public ResponseEntity<BusinessSettings> getBusinessSettings() {
        return ResponseEntity.ok(settingsService.getBusinessSettings());
    }

    @PutMapping("/business")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BusinessSettings> updateBusinessSettings(@RequestBody BusinessSettings settings) {
        return ResponseEntity.ok(settingsService.updateBusinessSettings(settings));
    }
}