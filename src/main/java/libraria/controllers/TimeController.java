package main.java.libraria.controllers;

import main.java.libraria.errors.NotEnoughPermissionException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeController {
    private static LocalDateTime currentLocalDateTime;
    private static LocalTime horarioAbertura;
    private static LocalTime horarioFechamento;

    public static LocalDateTime getCurrentLocalDateTime() {
        return currentLocalDateTime;
    }

    public static void setCurrentLocalDateTime(LocalDateTime time) {
        currentLocalDateTime = time;
    }

    public static LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public static void setHorarioAbertura(LocalTime horarioAbertura) throws NotEnoughPermissionException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        TimeController.horarioAbertura = horarioAbertura;
    }

    public static LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public static void setHorarioFechamento(LocalTime horarioFechamento) throws NotEnoughPermissionException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }
        TimeController.horarioFechamento = horarioFechamento;
    }
}
