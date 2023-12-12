package main.java.com.uefs.libraria.services;

import java.time.LocalDateTime;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class TimeService {
    private static LocalDateTime currentLocalDateTime;

    public static LocalDateTime getCurrentLocalDateTime() {
        return TimeService.currentLocalDateTime;
    }

    public static void setCurrentLocalDateTime(LocalDateTime time) {
        TimeService.currentLocalDateTime = time;
    }
}
