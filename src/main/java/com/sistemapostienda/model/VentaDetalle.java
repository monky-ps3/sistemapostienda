/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

/**
 *
 * @author monky
 */



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VentaDetalle {

    private Long id;
    private Long ventaId;
    private Long productoId;
    private Long presentacionId;
    private Long inventarioId;

    private String nombreProducto;
    private String nombrePresentacion;

    private BigDecimal cantidad;
    private BigDecimal factorConversion;
    private BigDecimal precioUnitario;
    private BigDecimal costoUnitario;
    private BigDecimal descuento;
    private BigDecimal porcentajeImpuesto;
    private BigDecimal importeImpuesto;
    private BigDecimal subtotal;
    private BigDecimal total;
    private BigDecimal utilidad;

    private String lote;
    private LocalDate fechaCaducidad;
    private LocalDateTime createdAt;

    public VentaDetalle() {
    }

    public VentaDetalle(
            Long id,
            Long ventaId,
            Long productoId,
            Long presentacionId,
            Long inventarioId,
            String nombreProducto,
            String nombrePresentacion,
            BigDecimal cantidad,
            BigDecimal factorConversion,
            BigDecimal precioUnitario,
            BigDecimal costoUnitario,
            BigDecimal descuento,
            BigDecimal porcentajeImpuesto,
            BigDecimal importeImpuesto,
            BigDecimal subtotal,
            BigDecimal total,
            BigDecimal utilidad,
            String lote,
            LocalDate fechaCaducidad,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.presentacionId = presentacionId;
        this.inventarioId = inventarioId;
        this.nombreProducto = nombreProducto;
        this.nombrePresentacion = nombrePresentacion;
        this.cantidad = cantidad;
        this.factorConversion = factorConversion;
        this.precioUnitario = precioUnitario;
        this.costoUnitario = costoUnitario;
        this.descuento = descuento;
        this.porcentajeImpuesto = porcentajeImpuesto;
        this.importeImpuesto = importeImpuesto;
        this.subtotal = subtotal;
        this.total = total;
        this.utilidad = utilidad;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
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

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
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

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
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
