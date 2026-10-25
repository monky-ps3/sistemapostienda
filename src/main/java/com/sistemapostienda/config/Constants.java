/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.config;

/**
 *
 * @author monky

 * Constantes globales del sistema.
 *
 *
 */
import java.math.BigDecimal;

public final class Constants {

    /**
     * Constructor privado para evitar instancias.
     */
    private Constants() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada.");
    }

    /*==================================================
     * INFORMACIÓN DEL SISTEMA
     *==================================================*/
    public static final String NOMBRE_SISTEMA = "SistemaPOSTienda";
    public static final String VERSION = "1.0.0";
    public static final String EMPRESA = "SistemaPOSTienda";

    /*==================================================
     * MONEDA
     *==================================================*/
    public static final String MONEDA_MXN = "MXN";
    public static final String SIMBOLO_MONEDA = "$";

    /*==================================================
     * IVA
     *==================================================*/
    public static final BigDecimal IVA = new BigDecimal("16.00");

    /*==================================================
     * CLIENTE GENERAL
     *==================================================*/
    public static final Long CLIENTE_GENERAL_ID = 1L;
    public static final String CLIENTE_GENERAL = "PÚBLICO EN GENERAL";

    /*==================================================
     * ESTADOS
     *==================================================*/
    public static final boolean ACTIVO = true;
    public static final boolean INACTIVO = false;

    /*==================================================
     * TIPOS DE MOVIMIENTO (KARDEX)
     *==================================================*/
    public static final String MOV_COMPRA = "COMPRA";
    public static final String MOV_VENTA = "VENTA";
    public static final String MOV_AJUSTE = "AJUSTE";
    public static final String MOV_DEVOLUCION = "DEVOLUCION";
    public static final String MOV_TRASPASO = "TRASPASO";

    /*==================================================
     * FORMAS DE PAGO
     *==================================================*/
    public static final String PAGO_EFECTIVO = "EFECTIVO";
    public static final String PAGO_TARJETA = "TARJETA";
    public static final String PAGO_TRANSFERENCIA = "TRANSFERENCIA";
    public static final String PAGO_CHEQUE = "CHEQUE";
    public static final String PAGO_VALE = "VALE";
    public static final String PAGO_CREDITO = "CREDITO";

    /*==================================================
     * ROLES
     *==================================================*/
    public static final String ROL_ADMINISTRADOR = "Administrador";
    public static final String ROL_SUPERVISOR = "Supervisor";
    public static final String ROL_CAJERO = "Cajero";
    public static final String ROL_BODEGA = "Bodega";

}
