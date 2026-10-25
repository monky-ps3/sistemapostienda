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
import java.time.LocalDateTime;

public class Venta {

    private Long id;
    private Long sucursalId;
    private Long cajaId;
    private Long sesionCajaId;
    private Long clienteId;
    private Long usuarioId;
    private Long listaPrecioId;

    private String folio;
    private LocalDateTime fechaVenta;
    private String tipoVenta;

    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal impuesto;
    private BigDecimal total;
    private BigDecimal totalPagado;
    private BigDecimal cambio;

    private String estado;
    private String observaciones;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío
    public Venta() {
    }

    // Constructor completo
    public Venta(
            Long id,
            Long sucursalId,
            Long cajaId,
            Long sesionCajaId,
            Long clienteId,
            Long usuarioId,
            Long listaPrecioId,
            String folio,
            LocalDateTime fechaVenta,
            String tipoVenta,
            BigDecimal subtotal,
            BigDecimal descuento,
            BigDecimal impuesto,
            BigDecimal total,
            BigDecimal totalPagado,
            BigDecimal cambio,
            String estado,
            String observaciones,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.sucursalId = sucursalId;
        this.cajaId = cajaId;
        this.sesionCajaId = sesionCajaId;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.listaPrecioId = listaPrecioId;
        this.folio = folio;
        this.fechaVenta = fechaVenta;
        this.tipoVenta = tipoVenta;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.total = total;
        this.totalPagado = totalPagado;
        this.cambio = cambio;
        this.estado = estado;
        this.observaciones = observaciones;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
    }

    public Long getSesionCajaId() {
        return sesionCajaId;
    }

    public void setSesionCajaId(Long sesionCajaId) {
        this.sesionCajaId = sesionCajaId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getListaPrecioId() {
        return listaPrecioId;
    }

    public void setListaPrecioId(Long listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
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

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
