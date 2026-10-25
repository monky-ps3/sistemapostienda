/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Utilidades para operaciones matemáticas y monetarias.
 *
 * Esta clase centraliza todos los cálculos numéricos utilizados
 * por el sistema.

 * Autor : @author monky
 *
 * ============================================================
 */
public final class NumberUtils {

    /**
     * Cantidad de decimales utilizada por todo el sistema.
     */
    public static final int SCALE = 2;

    /**
     * Constantes.
     */
    public static final BigDecimal CERO = BigDecimal.ZERO;
    public static final BigDecimal UNO = BigDecimal.ONE;
    public static final BigDecimal CIEN = BigDecimal.valueOf(100);

    /**
     * Locale México.
     */
    public static final Locale LOCALE_MX =
            new Locale("es", "MX");

    /**
     * Formato moneda.
     */
    private static final NumberFormat FORMATO_MONEDA =
            NumberFormat.getCurrencyInstance(LOCALE_MX);

    /**
     * Formato decimal.
     */
    private static final DecimalFormat FORMATO_DECIMAL =
            new DecimalFormat("#,##0.00");

    /**
     * Constructor privado.
     */
    private NumberUtils() {

    }

    //==========================================================
    // CONVERSIONES
    //==========================================================

    /**
     * Convierte null a BigDecimal.ZERO.
     */
    public static BigDecimal ceroSiNull(BigDecimal valor) {

        return valor == null
                ? CERO
                : valor;

    }

    /**
     * Convierte String a BigDecimal.
     */
    public static BigDecimal parse(String texto) {

        try {

            if (texto == null || texto.trim().isEmpty()) {

                return CERO;

            }

            texto = texto.replace(",", "");

            return new BigDecimal(texto);

        } catch (Exception e) {

            return CERO;

        }

    }

    /**
     * Convierte int a BigDecimal.
     */
    public static BigDecimal valueOf(int numero) {

        return BigDecimal.valueOf(numero);

    }

    /**
     * Convierte long a BigDecimal.
     */
    public static BigDecimal valueOf(long numero) {

        return BigDecimal.valueOf(numero);

    }

    /**
     * Convierte double a BigDecimal.
     */
    public static BigDecimal valueOf(double numero) {

        return BigDecimal.valueOf(numero);

    }

    //==========================================================
    // FORMATOS
    //==========================================================

    /**
     * Devuelve un importe con formato moneda.
     */
    public static String formatearMoneda(BigDecimal importe) {

        return FORMATO_MONEDA.format(
                ceroSiNull(importe)
        );

    }

    /**
     * Devuelve un decimal formateado.
     */
    public static String formatearDecimal(BigDecimal numero) {

        return FORMATO_DECIMAL.format(
                redondear(numero)
        );

    }

    /**
     * Devuelve el número sin separadores.
     */
    public static String formatearPlano(BigDecimal numero) {

        return redondear(numero)
                .toPlainString();

    }

    //==========================================================
    // REDONDEOS
    //==========================================================

    /**
     * Redondea usando HALF_UP.
     */
    public static BigDecimal redondear(BigDecimal numero) {

        return ceroSiNull(numero)
                .setScale(
                        SCALE,
                        RoundingMode.HALF_UP
                );

    }

    /**
     * Redondea hacia arriba.
     */
    public static BigDecimal redondearArriba(BigDecimal numero) {

        return ceroSiNull(numero)
                .setScale(
                        SCALE,
                        RoundingMode.UP
                );

    }

    /**
     * Redondea hacia abajo.
     */
    public static BigDecimal redondearAbajo(BigDecimal numero) {

        return ceroSiNull(numero)
                .setScale(
                        SCALE,
                        RoundingMode.DOWN
                );

    }

    //==========================================================
    // OPERACIONES MATEMÁTICAS
    //==========================================================

    /**
     * Suma dos números.
     */
    public static BigDecimal sumar(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .add(ceroSiNull(b));

    }

    /**
     * Resta dos números.
     */
    public static BigDecimal restar(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .subtract(ceroSiNull(b));

    }

    /**
     * Multiplica dos números.
     */
    public static BigDecimal multiplicar(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .multiply(ceroSiNull(b))
                .setScale(
                        SCALE,
                        RoundingMode.HALF_UP
                );

    }

    /**
     * Multiplica por entero.
     */
    public static BigDecimal multiplicar(
            BigDecimal a,
            int cantidad) {

        return multiplicar(
                a,
                BigDecimal.valueOf(cantidad)
        );

    }

    /**
     * Multiplica por long.
     */
    public static BigDecimal multiplicar(
            BigDecimal a,
            long cantidad) {

        return multiplicar(
                a,
                BigDecimal.valueOf(cantidad)
        );

    }

    /**
     * Divide dos números.
     */
    public static BigDecimal dividir(
            BigDecimal a,
            BigDecimal b) {

        if (b == null || b.compareTo(CERO) == 0) {

            return CERO;

        }

        return ceroSiNull(a)
                .divide(
                        b,
                        SCALE,
                        RoundingMode.HALF_UP
                );

    }

    //==========================================================
    // COMPARACIONES
    //==========================================================

    /**
     * Verifica si es cero.
     */
    public static boolean esCero(BigDecimal numero) {

        return ceroSiNull(numero)
                .compareTo(CERO) == 0;

    }

    /**
     * Verifica si es positivo.
     */
    public static boolean esPositivo(BigDecimal numero) {

        return ceroSiNull(numero)
                .compareTo(CERO) > 0;

    }

    /**
     * Verifica si es negativo.
     */
    public static boolean esNegativo(BigDecimal numero) {

        return ceroSiNull(numero)
                .compareTo(CERO) < 0;

    }

    /**
     * Mayor que.
     */
    public static boolean mayorQue(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .compareTo(ceroSiNull(b)) > 0;

    }

    /**
     * Menor que.
     */
    public static boolean menorQue(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .compareTo(ceroSiNull(b)) < 0;

    }

    /**
     * Son iguales.
     */
    public static boolean iguales(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .compareTo(ceroSiNull(b)) == 0;

    }

    /**
     * Devuelve el mayor valor.
     */
    public static BigDecimal max(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .max(ceroSiNull(b));

    }

    /**
     * Devuelve el menor valor.
     */
    public static BigDecimal min(
            BigDecimal a,
            BigDecimal b) {

        return ceroSiNull(a)
                .min(ceroSiNull(b));

    }
    
    //==========================================================
    // IVA
    //==========================================================

    /**
     * Calcula el IVA de un importe.
     *
     * Ejemplo:
     * Subtotal = 100
     * IVA = 16%
     * Resultado = 16
     */
    public static BigDecimal calcularIVA(
            BigDecimal subtotal,
            BigDecimal porcentajeIVA) {

        subtotal = ceroSiNull(subtotal);
        porcentajeIVA = ceroSiNull(porcentajeIVA);

        return subtotal.multiply(
                porcentajeIVA.divide(
                        CIEN,
                        6,
                        RoundingMode.HALF_UP))
                .setScale(
                        SCALE,
                        RoundingMode.HALF_UP);

    }

    /**
     * Obtiene el subtotal cuando el total ya incluye IVA.
     */
    public static BigDecimal calcularSubtotalDesdeTotal(
            BigDecimal total,
            BigDecimal porcentajeIVA) {

        total = ceroSiNull(total);
        porcentajeIVA = ceroSiNull(porcentajeIVA);

        BigDecimal divisor = UNO.add(
                porcentajeIVA.divide(
                        CIEN,
                        6,
                        RoundingMode.HALF_UP));

        return total.divide(
                divisor,
                SCALE,
                RoundingMode.HALF_UP);

    }

    /**
     * Calcula el total.
     */
    public static BigDecimal calcularTotal(
            BigDecimal subtotal,
            BigDecimal iva) {

        return sumar(subtotal, iva);

    }

    //==========================================================
    // CAMBIO
    //==========================================================

    /**
     * Calcula el cambio.
     */
    public static BigDecimal calcularCambio(
            BigDecimal recibido,
            BigDecimal total) {

        return restar(recibido, total);

    }

    /**
     * Verifica si el dinero recibido alcanza.
     */
    public static boolean alcanzaPago(
            BigDecimal recibido,
            BigDecimal total) {

        return ceroSiNull(recibido)
                .compareTo(ceroSiNull(total)) >= 0;

    }

    //==========================================================
    // DESCUENTOS
    //==========================================================

    /**
     * Calcula un descuento porcentual.
     */
    public static BigDecimal calcularDescuentoPorcentaje(
            BigDecimal importe,
            BigDecimal porcentaje) {

        return importe.multiply(
                porcentaje.divide(
                        CIEN,
                        6,
                        RoundingMode.HALF_UP))
                .setScale(
                        SCALE,
                        RoundingMode.HALF_UP);

    }

    /**
     * Aplica un descuento en pesos.
     */
    public static BigDecimal aplicarDescuentoMonto(
            BigDecimal importe,
            BigDecimal descuento) {

        return restar(importe, descuento);

    }

    /**
     * Aplica un descuento porcentual.
     */
    public static BigDecimal aplicarDescuentoPorcentaje(
            BigDecimal importe,
            BigDecimal porcentaje) {

        return restar(
                importe,
                calcularDescuentoPorcentaje(
                        importe,
                        porcentaje));

    }

    //==========================================================
    // PORCENTAJES
    //==========================================================

    /**
     * Obtiene el porcentaje de una cantidad respecto a otra.
     */
    public static BigDecimal calcularPorcentaje(
            BigDecimal base,
            BigDecimal cantidad) {

        if (esCero(base)) {

            return CERO;

        }

        return cantidad.multiply(CIEN)
                .divide(
                        base,
                        SCALE,
                        RoundingMode.HALF_UP);

    }

    //==========================================================
    // UTILIDAD
    //==========================================================

    /**
     * Calcula la utilidad.
     */
    public static BigDecimal calcularUtilidad(
            BigDecimal costo,
            BigDecimal venta) {

        return restar(venta, costo);

    }

    /**
     * Calcula el margen de utilidad.
     */
    public static BigDecimal calcularMargen(
            BigDecimal costo,
            BigDecimal venta) {

        if (esCero(costo)) {

            return CERO;

        }

        return calcularUtilidad(costo, venta)
                .multiply(CIEN)
                .divide(
                        costo,
                        SCALE,
                        RoundingMode.HALF_UP);

    }

    //==========================================================
    // INVENTARIO
    //==========================================================

    /**
     * Calcula el importe.
     *
     * Cantidad × Precio
     */
    public static BigDecimal calcularImporte(
            BigDecimal cantidad,
            BigDecimal precio) {

        return multiplicar(
                cantidad,
                precio);

    }

    /**
     * Calcula el costo promedio.
     */
    public static BigDecimal calcularCostoPromedio(
            BigDecimal existencia,
            BigDecimal costoActual,
            BigDecimal nuevaCantidad,
            BigDecimal nuevoCosto) {

        BigDecimal totalActual =
                multiplicar(
                        existencia,
                        costoActual);

        BigDecimal totalNuevo =
                multiplicar(
                        nuevaCantidad,
                        nuevoCosto);

        BigDecimal totalExistencia =
                sumar(
                        existencia,
                        nuevaCantidad);

        if (esCero(totalExistencia)) {

            return CERO;

        }

        return dividir(
                sumar(
                        totalActual,
                        totalNuevo),
                totalExistencia);

    }

    //==========================================================
    // UTILIDADES GENERALES
    //==========================================================

    /**
     * Valor absoluto.
     */
    public static BigDecimal absoluto(
            BigDecimal numero) {

        return ceroSiNull(numero).abs();

    }

    /**
     * Devuelve el signo del número.
     *
     * -1 = negativo
     *  0 = cero
     *  1 = positivo
     */
    public static int signo(
            BigDecimal numero) {

        return ceroSiNull(numero).signum();

    }

    /**
     * Convierte porcentaje a decimal.
     *
     * 16 -> 0.16
     */
    public static BigDecimal porcentajeADecimal(
            BigDecimal porcentaje) {

        return ceroSiNull(porcentaje)
                .divide(
                        CIEN,
                        6,
                        RoundingMode.HALF_UP);

    }

    /**
     * Convierte decimal a porcentaje.
     *
     * 0.16 -> 16
     */
    public static BigDecimal decimalAPorcentaje(
            BigDecimal decimal) {

        return ceroSiNull(decimal)
                .multiply(CIEN)
                .setScale(
                        SCALE,
                        RoundingMode.HALF_UP);

    }

}


