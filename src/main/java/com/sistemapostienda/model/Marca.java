/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * ============================================================
 *
 * @author monky
 *
 * Modelo que representa una marca de productos.
 *
 * Una marca identifica el fabricante o proveedor comercial de un producto.
 *
 * Ejemplos: Coca-Cola Pepsi Bimbo Gamesa Sabritas
 *
 * ============================================================
 */
public class Marca {

    /**
     * Identificador único.
     */
    private Long id;

    /**
     * Nombre de la marca.
     */
    private String nombre;
    /**
     * Código interno.
     *
     * Ejemplo: COC BIM SAB
     */
    private String codigo;

    /**
     * Descripción de la marca.
     */
    private String descripcion;

    /**
     * Estado de la marca.
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

    /*
     * ============================================================
     * CONSTRUCTORES
     * ============================================================
     */
    public Marca() {

    }

    public Marca(Long id,
            String nombre,
            String codigo,
            String descripcion,
            Boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    /*
     * ============================================================
     * GETTERS & SETTERS
     * ============================================================
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    /*
     * ============================================================
     * MÉTODOS
     * ============================================================
     */
    /**
     * Devuelve el nombre de la marca. Muy útil para mostrarla directamente en
     * JComboBox, JTable y listas.
     */
    @Override
    public String toString() {

        return nombre;

    }

}
