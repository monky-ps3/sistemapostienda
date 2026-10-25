/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.utils;




import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para validar datos.
 *
 * Contiene métodos reutilizables para validar
 * texto, números, correo, RFC, teléfono, etc.
 *
 * @author monky
 */
public final class Validator {

    private Validator() {
    }

    /**
     * Verifica si un texto está vacío.
     */
    public static boolean esVacio(String texto) {

        return texto == null || texto.trim().isEmpty();

    }

    /**
     * Verifica longitud máxima.
     */
    public static boolean longitudMaxima(String texto, int maximo) {

        if (texto == null) {
            return true;
        }

        return texto.length() <= maximo;

    }

    /**
     * Verifica longitud mínima.
     */
    public static boolean longitudMinima(String texto, int minimo) {

        if (texto == null) {
            return false;
        }

        return texto.trim().length() >= minimo;

    }

    /**
     * Valida correo electrónico.
     */
    public static boolean esCorreo(String correo) {

        if (esVacio(correo)) {
            return false;
        }

        String regex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern.matches(regex, correo);

    }

    /**
     * Valida un número entero.
     */
    public static boolean esEntero(String valor) {

        try {

            Integer.parseInt(valor);

            return true;

        } catch (NumberFormatException e) {

            return false;

        }

    }

    /**
     * Valida un número decimal.
     */
    public static boolean esDecimal(String valor) {

        try {

            new BigDecimal(valor);

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    /**
     * Valida un número positivo.
     */
    public static boolean esPositivo(String valor) {

        if (!esDecimal(valor)) {
            return false;
        }

        return new BigDecimal(valor)
                .compareTo(BigDecimal.ZERO) > 0;

    }

    /**
     * Valida un porcentaje.
     */
    public static boolean esPorcentaje(String valor) {

        if (!esDecimal(valor)) {
            return false;
        }

        BigDecimal numero = new BigDecimal(valor);

        return numero.compareTo(BigDecimal.ZERO) >= 0
                && numero.compareTo(new BigDecimal("100")) <= 0;

    }

    /**
     * Valida teléfono mexicano de 10 dígitos.
     */
    public static boolean esTelefono(String telefono) {

        if (esVacio(telefono)) {
            return false;
        }

        return telefono.matches("\\d{10}");

    }

    /**
     * Valida RFC mexicano.
     */
    public static boolean esRFC(String rfc) {

        if (esVacio(rfc)) {
            return false;
        }

        return rfc.toUpperCase().matches(
                "^([A-ZÑ&]{3,4})\\d{6}[A-Z0-9]{3}$"
        );

    }

    /**
     * Valida código de barras (EAN13 aproximado).
     */
    public static boolean esCodigoBarras(String codigo) {

        if (esVacio(codigo)) {
            return false;
        }

        return codigo.matches("\\d{8}|\\d{12}|\\d{13}");

    }

    /**
     * Valida si contiene únicamente letras y espacios.
     */
    public static boolean soloLetras(String texto) {

        if (esVacio(texto)) {
            return false;
        }

        return texto.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+");

    }

    /**
     * Valida si contiene únicamente letras y números.
     */
    public static boolean alfanumerico(String texto) {

        if (esVacio(texto)) {
            return false;
        }

        return texto.matches("[A-Za-z0-9\\-_. ]+");

    }

}