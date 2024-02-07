package de.hysky.skyblocker.utils;

import java.time.Duration;
import java.util.Formatter;

public class FormatterUtil {

    private static final Formatter FORMATTER = new Formatter();

    /**
     * Format duration as a string that looks like "DdHhMminSs".
     * Examples:
     * 30 -> 30s
     * 93600 -> 1d2h
     *
     * @param duration seconds
     * @return formatted string
     */
    public static String formatDuration(long duration) {
        Duration d = Duration.ofSeconds(duration);
        long days = d.toDays();
        d = d.minusDays(days);
        long hours = d.toHours();
        d = d.minusHours(hours);
        long minutes = d.toMinutes();
        d = d.minusMinutes(minutes);
        long seconds = d.toSeconds();
        return String.join("",
                days == 0 ? "" : days + "d",
                hours == 0 ? "" : hours + "h",
                minutes == 0 ? "" : minutes + "min",
                seconds == 0 ? "" : seconds + "s"
        );
    }

    /**
     * Formats the given number by adding commas for every three digits.
     *
     * @param number the number to be formatted
     * @return the formatted string
     */
    public static String formatNumber(long number) {
        return new Formatter().format("%,d", number).toString();
    }

}
