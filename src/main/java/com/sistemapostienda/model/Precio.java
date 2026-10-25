/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa el precio de una presentación dentro de una lista de precios.
 *
 * Ejemplo:
 *
 * Presentación: Coca-Cola Caja 24
 *
 * Lista: Público General
 *
 * Precio: $400.00
 */
public class Precio {

    private Long id;

    /**
     * Presentación a la que pertenece el precio.
     */
    private Long presentacionId;

    /**
     * Lista de precios a la que pertenece.
     */
    private Long listaPrecioId;

    /**
     * Precio de venta.
     */
    private BigDecimal precio;

    /**
     * Estado del precio.
     */
    private Boolean activo;

    /**
     * Fecha de creación.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización.
     */
    private LocalDateTime updatedAt;

    // =========================================================
    // CONSTRUCTORES
    // =========================================================
    /**
     * Constructor vacío.
     */
    public Precio() {
    }

    /**
     * Constructor para crear un precio.
     */
    public Precio(
            Long presentacionId,
            Long listaPrecioId,
            BigDecimal precio,
            Boolean activo) {

        this.presentacionId = presentacionId;
        this.listaPrecioId = listaPrecioId;
        this.precio = precio;
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

    public Long getPresentacionId() {
        return presentacionId;
    }

    public void setPresentacionId(Long presentacionId) {
        this.presentacionId = presentacionId;
    }

    public Long getListaPrecioId() {
        return listaPrecioId;
    }

    public void setListaPrecioId(Long listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================
    /**
     * Indica si el precio está activo.
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Indica si el precio tiene una presentación válida.
     */
    public boolean tienePresentacion() {
        return this.presentacionId != null
                && this.presentacionId > 0;
    }

    /**
     * Indica si el precio tiene una lista válida.
     */
    public boolean tieneListaPrecio() {
        return this.listaPrecioId != null
                && this.listaPrecioId > 0;
    }

    /**
     * Representación del objeto.
     */
    @Override
    public String toString() {
        return "Precio{"
                + "id=" + id
                + ", presentacionId=" + presentacionId
                + ", listaPrecioId=" + listaPrecioId
                + ", precio=" + precio
                + ", activo=" + activo
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
