package de.icp.match.request.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GermanDateFormatter {

    public static String localDateTimeToGermanDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter germanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm", Locale.GERMAN);
        return localDateTime.format(germanDateFormatter);
    }
}
