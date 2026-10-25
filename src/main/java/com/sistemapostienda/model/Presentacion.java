/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ============================================================ SistemaPOSTienda
 *
 * Modelo que representa una presentación de producto.
 *
 * Una presentación pertenece a un producto.
 *
 * Ejemplos:
 *
 * Coca-Cola ├── Pieza 600 ml ├── Paquete 6 piezas └── Caja 24 piezas
 *
 * Azúcar ├── 1/2 kg ├── 1 kg ├── 2 kg └── Bulto 20 kg
 *
 * ============================================================
 */
public class Presentacion {

    /*
     * ============================================================
     * ATRIBUTOS
     * ============================================================
     */
    /**
     * Identificador de la presentación.
     */
    private Long id;

    /**
     * ID del producto al que pertenece.
     */
    private Long productoId;

    /**
     * Nombre de la presentación.
     *
     * Ejemplo: "Pieza" "Caja" "Paquete" "1 kg"
     */
    private String nombre;

    /**
     * Abreviatura de la presentación.
     *
     * Ejemplo: "pz" "cja" "paq" "kg"
     */
    private String abreviatura;

    /**
     * Factor de conversión respecto a la unidad base del producto.
     *
     * Ejemplo:
     *
     * Pieza = 1.000 Caja 24 = 24.000 Medio kilo = 0.500
     */
    private BigDecimal factorConversion;

    /**
     * Indica si es la presentación predeterminada del producto.
     */
    private Boolean esPredeterminada;

    /**
     * Indica si la presentación puede utilizarse para ventas.
     */
    private Boolean permiteVenta;

    /**
     * Indica si la presentación está activa.
     */
    private Boolean activa;

    /**
     * Orden de visualización.
     */
    private Integer orden;

    /**
     * Observaciones adicionales.
     */
    private String observaciones;

    /**
     * Fecha de creación.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de actualización.
     */
    private LocalDateTime updatedAt;

    /*
     * ============================================================
     * CONSTRUCTOR VACÍO
     * ============================================================
     */
    public Presentacion() {

    }

    /*
     * ============================================================
     * CONSTRUCTOR COMPLETO
     * ============================================================
     */
    public Presentacion(
            Long id,
            Long productoId,
            String nombre,
            String abreviatura,
            BigDecimal factorConversion,
            Boolean esPredeterminada,
            Boolean permiteVenta,
            Boolean activa,
            Integer orden,
            String observaciones,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.productoId = productoId;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.factorConversion = factorConversion;
        this.esPredeterminada = esPredeterminada;
        this.permiteVenta = permiteVenta;
        this.activa = activa;
        this.orden = orden;
        this.observaciones = observaciones;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.updatedAt = updatedAt;
    }

    /*
     * ============================================================
     * GETTERS Y SETTERS
     * ============================================================
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(
            BigDecimal factorConversion) {

        this.factorConversion = factorConversion;
    }

    public Boolean getEsPredeterminada() {
        return esPredeterminada;
    }

    public void setEsPredeterminada(
            Boolean esPredeterminada) {

        this.esPredeterminada = esPredeterminada;
    }

    public Boolean getPermiteVenta() {
        return permiteVenta;
    }

    public void setPermiteVenta(
            Boolean permiteVenta) {

        this.permiteVenta = permiteVenta;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(
            String observaciones) {

        this.observaciones = observaciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }

    /*
     * ============================================================
     * TOSTRING
     * ============================================================
     */
    @Override
    public String toString() {

        return nombre;

    }

}
