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

public class VentaPago {

    private Long id;
    private Long ventaId;
    private Long formaPagoId;
    private Long usuarioId;
    private Long cajaId;
    private Long sesionCajaId;

    private String tipoPago;
    private BigDecimal monto;
    private BigDecimal montoRecibido;
    private BigDecimal cambio;

    private String referencia;
    private String numeroOperacion;
    private LocalDateTime fechaPago;
    private String observaciones;
    private String estado;
    private LocalDateTime createdAt;

    // Constructor vacío
    public VentaPago() {
    }

    // Constructor completo
    public VentaPago(
            Long id,
            Long ventaId,
            Long formaPagoId,
            Long usuarioId,
            Long cajaId,
            Long sesionCajaId,
            String tipoPago,
            BigDecimal monto,
            BigDecimal montoRecibido,
            BigDecimal cambio,
            String referencia,
            String numeroOperacion,
            LocalDateTime fechaPago,
            String observaciones,
            String estado,
            LocalDateTime createdAt) {

        this.id = id;
        this.ventaId = ventaId;
        this.formaPagoId = formaPagoId;
        this.usuarioId = usuarioId;
        this.cajaId = cajaId;
        this.sesionCajaId = sesionCajaId;
        this.tipoPago = tipoPago;
        this.monto = monto;
        this.montoRecibido = montoRecibido;
        this.cambio = cambio;
        this.referencia = referencia;
        this.numeroOperacion = numeroOperacion;
        this.fechaPago = fechaPago;
        this.observaciones = observaciones;
        this.estado = estado;
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

    public Long getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(Long formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoRecibido() {
        return montoRecibido;
    }

    public void setMontoRecibido(BigDecimal montoRecibido) {
        this.montoRecibido = montoRecibido;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
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
}
