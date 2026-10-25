/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Utilidades para el manejo de cadenas de texto.
 *
 * Esta clase concentra operaciones comunes sobre String
 * utilizadas en todo el sistema.
 *
 * No contiene reglas de negocio.
 *
 * @author monky
 * Versión: 1.0.0
 * ============================================================
 */
public final class StringUtils {

    /**
     * Constructor privado.
     */
    private StringUtils() {

    }

    //==========================================================
    // VALIDACIONES
    //==========================================================

    /**
     * Verifica si una cadena es nula o vacía.
     *
     * @param texto Cadena a evaluar.
     * @return true si es nula o vacía.
     */
    public static boolean estaVacio(String texto) {

        return texto == null
                || texto.trim().isEmpty();

    }

    /**
     * Verifica si una cadena contiene información.
     *
     * @param texto Cadena.
     * @return true cuando contiene texto.
     */
    public static boolean noEstaVacio(String texto) {

        return !estaVacio(texto);

    }

    //==========================================================
    // ESPACIOS
    //==========================================================

    /**
     * Elimina espacios al inicio y al final.
     *
     * @param texto Cadena.
     * @return Cadena limpia.
     */
    public static String trim(String texto) {

        if (texto == null) {

            return "";

        }

        return texto.trim();

    }

    /**
     * Reemplaza múltiples espacios por uno solo.
     *
     * Ejemplo:
     *
     * Juan     Pérez
     *
     * Resultado:
     *
     * Juan Pérez
     */
    public static String quitarEspaciosDobles(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return texto.trim()
                .replaceAll("\\s+", " ");

    }

    /**
     * Elimina todos los espacios.
     *
     * Ejemplo:
     *
     * Coca Cola
     *
     * Resultado:
     *
     * CocaCola
     */
    public static String quitarEspacios(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return texto.replace(" ", "");

    }

    //==========================================================
    // MAYÚSCULAS Y MINÚSCULAS
    //==========================================================

    /**
     * Convierte una cadena a mayúsculas.
     */
    public static String mayusculas(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

           return texto.toUpperCase(Locale.ROOT);

    }

    /**
     * Convierte una cadena a minúsculas.
     */
    public static String minusculas(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

       return texto.toLowerCase(Locale.ROOT);

    }

    //==========================================================
    // CAPITALIZAR
    //==========================================================

    /**
     * Convierte:
     *
     * juan perez lopez
     *
     * En:
     *
     * Juan Perez Lopez
     */
    public static String capitalizar(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        texto = quitarEspaciosDobles(
        texto.toLowerCase(Locale.ROOT));

        StringBuilder resultado = new StringBuilder();

        String[] palabras = texto.split(" ");

        for (String palabra : palabras) {

            if (!palabra.isEmpty()) {

                resultado.append(
                        Character.toUpperCase(
                                palabra.charAt(0)));

                if (palabra.length() > 1) {

                    resultado.append(
                            palabra.substring(1));

                }

                resultado.append(" ");

            }

        }

        return resultado.toString().trim();

    }

    //==========================================================
    // COMPARACIONES
    //==========================================================

    /**
     * Compara ignorando mayúsculas.
     */
    public static boolean iguales(
            String texto1,
            String texto2) {

        if (texto1 == null && texto2 == null) {

            return true;

        }

        if (texto1 == null || texto2 == null) {

            return false;

        }

        return texto1.equalsIgnoreCase(texto2);

    }

    /**
     * Verifica si una cadena contiene otra.
     */
    public static boolean contiene(
            String texto,
            String buscar) {

        if (estaVacio(texto)
                || estaVacio(buscar)) {

            return false;

        }

        return texto.toLowerCase(Locale.ROOT)
        .contains(
                buscar.toLowerCase(Locale.ROOT));

    }

    //==========================================================
    // LONGITUD
    //==========================================================

    /**
     * Obtiene la longitud de una cadena.
     */
    public static int longitud(String texto) {

        if (texto == null) {

            return 0;

        }

        return texto.length();

    }

    /**
     * Verifica longitud mínima.
     */
    public static boolean longitudMinima(
            String texto,
            int longitud) {

        return texto != null
                && texto.length() >= longitud;

    }

    /**
     * Verifica longitud máxima.
     */
    public static boolean longitudMaxima(
            String texto,
            int longitud) {

        return texto != null
                && texto.length() <= longitud;

    }

    /**
     * Verifica si la longitud está dentro de un rango.
     */
    public static boolean longitudEntre(
            String texto,
            int minimo,
            int maximo) {

        if (texto == null) {

            return false;

        }

        int longitud = texto.length();

        return longitud >= minimo
                && longitud <= maximo;

    }


    //==========================================================
    // NORMALIZACIÓN
    //==========================================================

    /**
     * Elimina acentos y caracteres especiales.
     *
     * Ejemplo:
     * José Álvarez -> Jose Alvarez
     */
    public static String quitarAcentos(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        String normalizado = Normalizer.normalize(
                texto,
                Normalizer.Form.NFD);

        return normalizado.replaceAll("\\p{M}", "");

    }

    /**
     * Normaliza una cadena para búsquedas.
     *
     * Convierte a:
     * - Sin acentos
     * - Mayúsculas
     * - Sin espacios dobles
     */
    public static String normalizar(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        texto = quitarAcentos(texto);
        texto = quitarEspaciosDobles(texto);

        return texto.toUpperCase(java.util.Locale.ROOT);

    }

    //==========================================================
    // FILTROS
    //==========================================================

    /**
     * Conserva únicamente números.
     */
    public static String soloNumeros(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return texto.replaceAll("[^0-9]", "");

    }

    /**
     * Conserva únicamente letras.
     */
    public static String soloLetras(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return quitarAcentos(texto)
                .replaceAll("[^A-Za-z]", "");

    }

    /**
     * Conserva únicamente letras y números.
     */
    public static String soloLetrasYNumeros(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return quitarAcentos(texto)
                .replaceAll("[^A-Za-z0-9]", "");

    }

    //==========================================================
    // INICIALES
    //==========================================================

    /**
     * Obtiene las iniciales.
     *
     * Juan Pérez López
     *
     * Resultado:
     *
     * JPL
     */
    public static String obtenerIniciales(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        StringBuilder sb = new StringBuilder();

        String[] palabras =
                quitarEspaciosDobles(texto).split(" ");

        for (String palabra : palabras) {

            if (!palabra.isEmpty()) {

                sb.append(
                        Character.toUpperCase(
                                palabra.charAt(0)));

            }

        }

        return sb.toString();

    }

    //==========================================================
    // RELLENO
    //==========================================================

    /**
     * Rellena por la izquierda.
     */
    public static String rellenarIzquierda(
            String texto,
            int longitud,
            char caracter) {

        texto = texto == null ? "" : texto;

        if (texto.length() >= longitud) {

            return texto;

        }

        StringBuilder sb = new StringBuilder();

        while (sb.length() < longitud - texto.length()) {

            sb.append(caracter);

        }

        return sb.append(texto).toString();

    }

    /**
     * Rellena por la derecha.
     */
    public static String rellenarDerecha(
            String texto,
            int longitud,
            char caracter) {

        texto = texto == null ? "" : texto;

        StringBuilder sb = new StringBuilder(texto);

        while (sb.length() < longitud) {

            sb.append(caracter);

        }

        return sb.toString();

    }

    //==========================================================
    // TEXTO
    //==========================================================

    /**
     * Repite una cadena.
     */
    public static String repetir(
            String texto,
            int veces) {

        if (estaVacio(texto) || veces <= 0) {

            return "";

        }

        return texto.repeat(veces);

    }

    /**
     * Trunca una cadena.
     */
    public static String truncar(
            String texto,
            int longitud) {

        if (estaVacio(texto)) {

            return "";

        }

        if (texto.length() <= longitud) {

            return texto;

        }

        if (longitud <= 3) {

            return texto.substring(0, longitud);

        }

        return texto.substring(0, longitud - 3) + "...";

    }

    /**
     * Invierte una cadena.
     */
    public static String invertir(String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return new StringBuilder(texto)
                .reverse()
                .toString();

    }

    //==========================================================
    // CARACTERES
    //==========================================================

    /**
     * Elimina caracteres especiales.
     */
    public static String eliminarCaracteresEspeciales(
            String texto) {

        if (estaVacio(texto)) {

            return "";

        }

        return quitarAcentos(texto)
                .replaceAll("[^A-Za-z0-9 ]", "");

    }

    /**
     * Verifica si la cadena es numérica.
     */
    public static boolean esNumerico(String texto) {

        if (estaVacio(texto)) {

            return false;

        }

        return texto.matches("\\d+");

    }

    /**
     * Verifica si inicia con un prefijo.
     */
    public static boolean empiezaCon(
            String texto,
            String prefijo) {

        if (texto == null || prefijo == null) {

            return false;

        }

        return texto.startsWith(prefijo);

    }

    /**
     * Verifica si termina con un sufijo.
     */
    public static boolean terminaCon(
            String texto,
            String sufijo) {

        if (texto == null || sufijo == null) {

            return false;

        }

        return texto.endsWith(sufijo);

    }

    /**
     * Cuenta las ocurrencias de una subcadena.
     */
    public static int contarOcurrencias(
            String texto,
            String buscar) {

        if (estaVacio(texto)
                || estaVacio(buscar)) {

            return 0;

        }

        int contador = 0;
        int indice = 0;

        while ((indice = texto.indexOf(buscar, indice)) != -1) {

            contador++;
            indice += buscar.length();

        }

        return contador;

    }

}
