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

public class SesionCaja {

    private Long id;
    private String folio;
    private Long cajaId;
    private Long usuarioId;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private BigDecimal fondoInicial;
    private String turno;
    private String estado;
    private String observaciones;

    public SesionCaja() {
    }

    public SesionCaja(
            Long folioId,
            String folio,
            Long cajaId,
            Long usuarioId,
            LocalDateTime fechaApertura,
            LocalDateTime fechaCierre,
            BigDecimal fondoInicial,
            String turno,
            String estado,
            String observaciones) {

        this.id = folioId;
        this.folio = folio;
        this.cajaId = cajaId;
        this.usuarioId = usuarioId;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.fondoInicial = fondoInicial;
        this.turno = turno;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public BigDecimal getFondoInicial() {
        return fondoInicial;
    }

    public void setFondoInicial(BigDecimal fondoInicial) {
        this.fondoInicial = fondoInicial;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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
}
