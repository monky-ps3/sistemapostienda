/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

/**
 *
 * @author monky
 */

import java.time.LocalDateTime;

public class FormaPago {

    private Long id;
    private String clave;
    private String nombre;
    private String descripcion;
    private boolean requiereReferencia;
    private boolean permiteCambio;
    private boolean permitePagoParcial;
    private boolean activo;
    private int orden;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FormaPago() {
    }

    public FormaPago(
            Long id,
            String clave,
            String nombre,
            String descripcion,
            boolean requiereReferencia,
            boolean permiteCambio,
            boolean permitePagoParcial,
            boolean activo,
            int orden,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requiereReferencia = requiereReferencia;
        this.permiteCambio = permiteCambio;
        this.permitePagoParcial = permitePagoParcial;
        this.activo = activo;
        this.orden = orden;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isRequiereReferencia() {
        return requiereReferencia;
    }

    public void setRequiereReferencia(boolean requiereReferencia) {
        this.requiereReferencia = requiereReferencia;
    }

    public boolean isPermiteCambio() {
        return permiteCambio;
    }

    public void setPermiteCambio(boolean permiteCambio) {
        this.permiteCambio = permiteCambio;
    }

    public boolean isPermitePagoParcial() {
        return permitePagoParcial;
    }

    public void setPermitePagoParcial(boolean permitePagoParcial) {
        this.permitePagoParcial = permitePagoParcial;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
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