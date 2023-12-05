package main.java.libraria.controllers;

import java.time.LocalDateTime;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class TimeController {
    private static LocalDateTime currentLocalDateTime;

    public static LocalDateTime getCurrentLocalDateTime() {
        return TimeController.currentLocalDateTime;
    }

    public static void setCurrentLocalDateTime(LocalDateTime time) {
        TimeController.currentLocalDateTime = time;
    }
}
