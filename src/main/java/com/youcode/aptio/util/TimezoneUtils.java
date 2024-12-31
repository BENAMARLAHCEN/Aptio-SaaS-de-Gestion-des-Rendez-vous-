package com.youcode.aptio.util;

import java.time.ZoneId;

public class TimezoneUtils {

    /**
     * Validates if a given timezone is valid.
     *
     * @param timezone the timezone string (e.g., 'America/New_York')
     * @return true if valid, false otherwise
     */
    public static boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
