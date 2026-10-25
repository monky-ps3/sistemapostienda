/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de la tabla kardex.
 *
 * Representa un movimiento de inventario.
 *
 * Las cantidades de entrada, salida y existencia se manejan
 * siempre en la unidad base del producto.
 *
 * La presentación indica la forma en que se realizó la operación
 * cuando esta información está disponible.
 */
public class Kardex {

    private Long id;
    private Long sucursalId;
    private Long productoId;
    private Long presentacionId;
    private Long inventarioId;
    private Long usuarioId;
    private String tipoMovimiento;
    private Long referenciaId;
    private String referencia;
    private String lote;
    private LocalDate fechaCaducidad;
    private BigDecimal entrada;
    private BigDecimal salida;
    private BigDecimal existenciaAnterior;
    private BigDecimal existenciaActual;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
    private String observaciones;
    private LocalDateTime createdAt;

    /**
     * Constructor vacío.
     */
    public Kardex() {
    }

    /**
     * Constructor principal.
     */
    public Kardex(
            Long sucursalId,
            Long productoId,
            Long presentacionId,
            Long inventarioId,
            Long usuarioId,
            String tipoMovimiento,
            Long referenciaId,
            String referencia,
            String lote,
            LocalDate fechaCaducidad,
            BigDecimal entrada,
            BigDecimal salida,
            BigDecimal existenciaAnterior,
            BigDecimal existenciaActual,
            BigDecimal costoUnitario,
            BigDecimal costoTotal,
            String observaciones
    ) {
        this.sucursalId = sucursalId;
        this.productoId = productoId;
        this.presentacionId = presentacionId;
        this.inventarioId = inventarioId;
        this.usuarioId = usuarioId;
        this.tipoMovimiento = tipoMovimiento;
        this.referenciaId = referenciaId;
        this.referencia = referencia;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.entrada = entrada;
        this.salida = salida;
        this.existenciaAnterior = existenciaAnterior;
        this.existenciaActual = existenciaActual;
        this.costoUnitario = costoUnitario;
        this.costoTotal = costoTotal;
        this.observaciones = observaciones;
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

    public Long getPresentacionId() {
        return presentacionId;
    }

    public void setPresentacionId(Long presentacionId) {
        this.presentacionId = presentacionId;
    }

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    public BigDecimal getSalida() {
        return salida;
    }

    public void setSalida(BigDecimal salida) {
        this.salida = salida;
    }

    public BigDecimal getExistenciaAnterior() {
        return existenciaAnterior;
    }

    public void setExistenciaAnterior(
            BigDecimal existenciaAnterior
    ) {
        this.existenciaAnterior = existenciaAnterior;
    }

    public BigDecimal getExistenciaActual() {
        return existenciaActual;
    }

    public void setExistenciaActual(
            BigDecimal existenciaActual
    ) {
        this.existenciaActual = existenciaActual;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(
            BigDecimal costoUnitario
    ) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}