package com.uefs.libraria.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class TimeService {
    private static LocalDateTime currentLocalDateTime;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        TimeService.formatter = formatter;
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return TimeService.currentLocalDateTime;
    }

    public static void setCurrentLocalDateTime(LocalDateTime time) {
        TimeService.currentLocalDateTime = time;
    }
}
