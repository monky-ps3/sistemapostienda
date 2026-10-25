/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidades para el manejo de fechas y horas.
 *
 * @author monky
 * 
 */
public final class DateUtils {

    /**
     * Formato: 31/12/2026
     */
    public static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Formato: 31/12/2026 18:30:45
     */
    public static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private DateUtils() {
    }

    /**
     * Obtiene la fecha actual.
     */
    public static LocalDate hoy() {

        return LocalDate.now();

    }

    /**
     * Obtiene la fecha y hora actual.
     */
    public static LocalDateTime ahora() {

        return LocalDateTime.now();

    }

    /**
     * Obtiene únicamente la hora actual.
     */
    public static LocalTime horaActual() {

        return LocalTime.now();

    }

    /**
     * Convierte LocalDate a String.
     */
    public static String formatear(LocalDate fecha) {

        if (fecha == null) {
            return "";
        }

        return fecha.format(FORMATO_FECHA);

    }

    /**
     * Convierte LocalDateTime a String.
     */
    public static String formatear(LocalDateTime fechaHora) {

        if (fechaHora == null) {
            return "";
        }

        return fechaHora.format(FORMATO_FECHA_HORA);

    }

    /**
     * Convierte String a LocalDate.
     */
    public static LocalDate parseFecha(String fecha) {

        try {

            return LocalDate.parse(
                    fecha,
                    FORMATO_FECHA
            );

        } catch (DateTimeParseException e) {

            return null;

        }

    }

    /**
     * Convierte String a LocalDateTime.
     */
    public static LocalDateTime parseFechaHora(String fechaHora) {

        try {

            return LocalDateTime.parse(
                    fechaHora,
                    FORMATO_FECHA_HORA
            );

        } catch (DateTimeParseException e) {

            return null;

        }

    }

    /**
     * Primer día del mes.
     */
    public static LocalDate primerDiaMes() {

        return LocalDate.now()
                .withDayOfMonth(1);

    }

    /**
     * Último día del mes.
     */
    public static LocalDate ultimoDiaMes() {

        return LocalDate.now()
                .withDayOfMonth(
                        LocalDate.now().lengthOfMonth()
                );

    }

    /**
     * Suma días.
     */
    public static LocalDate sumarDias(
            LocalDate fecha,
            int dias) {

        return fecha.plusDays(dias);

    }

    /**
     * Resta días.
     */
    public static LocalDate restarDias(
            LocalDate fecha,
            int dias) {

        return fecha.minusDays(dias);

    }

    /**
     * Diferencia en días entre dos fechas.
     */
    public static long diasEntre(
            LocalDate inicio,
            LocalDate fin) {

        return java.time.temporal.ChronoUnit.DAYS
                .between(inicio, fin);

    }

    /**
     * Verifica si dos fechas son iguales.
     */
    public static boolean esMismoDia(
            LocalDate fecha1,
            LocalDate fecha2) {

        return fecha1 != null
                && fecha1.equals(fecha2);

    }

    /**
     * Calcula la edad.
     */
    public static int calcularEdad(
            LocalDate fechaNacimiento) {

        if (fechaNacimiento == null) {

            return 0;

        }

        return Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears();

    }

}