/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de la tabla inventario.
 *
 * Representa la existencia de un producto dentro de una sucursal.
 *
 * La existencia se almacena siempre en la unidad base del producto.
 * Las presentaciones y sus factores de conversión se manejan
 * en las operaciones de compra y venta.
 */
public class Inventario {

    private Long id;
    private Long sucursalId;
    private Long productoId;
    private String lote;
    private LocalDate fechaCaducidad;
    private BigDecimal existencia;
    private BigDecimal existenciaReservada;
    private BigDecimal existenciaMinima;
    private BigDecimal existenciaMaxima;
    private BigDecimal ultimoCosto;
    private BigDecimal costoPromedio;
    private String ubicacion;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor vacío.
     */
    public Inventario() {
    }

    /**
     * Constructor principal.
     */
    public Inventario(
            Long sucursalId,
            Long productoId,
            String lote,
            LocalDate fechaCaducidad,
            BigDecimal existencia,
            BigDecimal existenciaReservada,
            BigDecimal existenciaMinima,
            BigDecimal existenciaMaxima,
            BigDecimal ultimoCosto,
            BigDecimal costoPromedio,
            String ubicacion,
            Boolean activo
    ) {
        this.sucursalId = sucursalId;
        this.productoId = productoId;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.existencia = existencia;
        this.existenciaReservada = existenciaReservada;
        this.existenciaMinima = existenciaMinima;
        this.existenciaMaxima = existenciaMaxima;
        this.ultimoCosto = ultimoCosto;
        this.costoPromedio = costoPromedio;
        this.ubicacion = ubicacion;
        this.activo = activo;
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigDecimal getExistencia() {
        return existencia;
    }

    public void setExistencia(BigDecimal existencia) {
        this.existencia = existencia;
    }

    public BigDecimal getExistenciaReservada() {
        return existenciaReservada;
    }

    public void setExistenciaReservada(BigDecimal existenciaReservada) {
        this.existenciaReservada = existenciaReservada;
    }

    public BigDecimal getExistenciaMinima() {
        return existenciaMinima;
    }

    public void setExistenciaMinima(BigDecimal existenciaMinima) {
        this.existenciaMinima = existenciaMinima;
    }

    public BigDecimal getExistenciaMaxima() {
        return existenciaMaxima;
    }

    public void setExistenciaMaxima(BigDecimal existenciaMaxima) {
        this.existenciaMaxima = existenciaMaxima;
    }

    public BigDecimal getUltimoCosto() {
        return ultimoCosto;
    }

    public void setUltimoCosto(BigDecimal ultimoCosto) {
        this.ultimoCosto = ultimoCosto;
    }

    public BigDecimal getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(BigDecimal costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
