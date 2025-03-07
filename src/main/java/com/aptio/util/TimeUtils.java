package com.aptio.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {

    /**
     * Checks if two time ranges overlap
     *
     * @param start1 Start time of first range
     * @param end1 End time of first range
     * @param start2 Start time of second range
     * @param end2 End time of second range
     * @return true if the time ranges overlap, false otherwise
     */
    public static boolean doTimeRangesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isBefore(end2) && end1.isAfter(start2)) ||
                start1.equals(start2) || end1.equals(end2);
    }

    /**
     * Formats a time string in HH:MM format
     *
     * @param time The LocalTime to format
     * @return A string representation of the time in HH:MM format
     */
    public static String formatTime(LocalTime time) {
        if (time == null) return "";
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    /**
     * Parses a time string in HH:MM format
     *
     * @param timeStr The time string to parse
     * @return A LocalTime object representing the parsed time
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return null;

        String[] parts = timeStr.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Time string must be in HH:MM format");
        }

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return LocalTime.of(hour, minute);
    }

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}