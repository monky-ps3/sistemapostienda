/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de la tabla compras_detalle.
 *
 * Representa cada producto incluido dentro de una compra.
 */
public class CompraDetalle {

    private Long id;
    private Long compraId;
    private Long productoId;
    private Long presentacionId;
    private BigDecimal cantidad;
    private BigDecimal factorConversion;
    private BigDecimal costoUnitario;
    private BigDecimal descuento;
    private BigDecimal porcentajeImpuesto;
    private BigDecimal importeImpuesto;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String lote;
    private LocalDate fechaCaducidad;
    private LocalDateTime createdAt;

    public CompraDetalle() {
    }

    public CompraDetalle(
            Long compraId,
            Long productoId,
            Long presentacionId,
            BigDecimal cantidad,
            BigDecimal factorConversion,
            BigDecimal costoUnitario,
            BigDecimal descuento,
            BigDecimal porcentajeImpuesto,
            BigDecimal importeImpuesto,
            BigDecimal subtotal,
            BigDecimal total,
            String lote,
            LocalDate fechaCaducidad
    ) {
        this.compraId = compraId;
        this.productoId = productoId;
        this.presentacionId = presentacionId;
        this.cantidad = cantidad;
        this.factorConversion = factorConversion;
        this.costoUnitario = costoUnitario;
        this.descuento = descuento;
        this.porcentajeImpuesto = porcentajeImpuesto;
        this.importeImpuesto = importeImpuesto;
        this.subtotal = subtotal;
        this.total = total;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(BigDecimal factorConversion) {
        this.factorConversion = factorConversion;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPorcentajeImpuesto() {
        return porcentajeImpuesto;
    }

    public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto) {
        this.porcentajeImpuesto = porcentajeImpuesto;
    }

    public BigDecimal getImporteImpuesto() {
        return importeImpuesto;
    }

    public void setImporteImpuesto(BigDecimal importeImpuesto) {
        this.importeImpuesto = importeImpuesto;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
