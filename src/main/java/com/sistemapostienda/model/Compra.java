/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de la tabla compras.
 *
 * Representa la cabecera de una compra.
 */
public class Compra {

    private Long id;

    private Long sucursalId;

    private Long proveedorId;

    private Long usuarioId;

    private String folio;

    private String factura;

    private String referencia;

    private LocalDateTime fechaCompra;

    private String tipoCompra;

    private BigDecimal subtotal;

    private BigDecimal descuento;

    private BigDecimal impuesto;

    private BigDecimal total;

    private String observaciones;

    private String estado;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================================================
    // CONSTRUCTORES
    // =========================================================

    public Compra() {
    }

    /**
     * Constructor para crear una compra nueva.
     */
    public Compra(
            Long sucursalId,
            Long proveedorId,
            Long usuarioId,
            String folio,
            String factura,
            String referencia,
            LocalDateTime fechaCompra,
            String tipoCompra,
            BigDecimal subtotal,
            BigDecimal descuento,
            BigDecimal impuesto,
            BigDecimal total,
            String observaciones,
            String estado
    ) {
        this.sucursalId = sucursalId;
        this.proveedorId = proveedorId;
        this.usuarioId = usuarioId;
        this.folio = folio;
        this.factura = factura;
        this.referencia = referencia;
        this.fechaCompra = fechaCompra;
        this.tipoCompra = tipoCompra;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.total = total;
        this.observaciones = observaciones;
        this.estado = estado;
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

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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