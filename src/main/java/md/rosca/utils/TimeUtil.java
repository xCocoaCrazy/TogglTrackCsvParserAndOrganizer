package md.rosca.utils;

public class TimeUtil {
    public static long addTime(String time) {
        return parseTimeToSeconds(time);
    }

    // Helper method to parse HH:MM:SS to seconds
    public static long parseTimeToSeconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return hours * 3600L + minutes * 60L + seconds;
    }

    // Method to get total time in HH:MM:SS format
    public static String getTotalTime(long totalSeconds) {
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long hours = totalMinutes / 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
