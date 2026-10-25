/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Modelo que representa una unidad de medida.
 *
 * Las unidades de medida son utilizadas por los productos
 * para indicar cómo se compran, venden y controlan
 * dentro del inventario.
 *
 * Ejemplos:
 *
 * Pieza
 * Kilogramo
 * Litro
 * Metro
 * Caja
 *
 * ============================================================
 */
public class UnidadMedida {

    /*
     * ============================================================
     * ATRIBUTOS
     * ============================================================
     */

    /**
     * Identificador único.
     */
    private Long id;

    /**
     * Nombre de la unidad.
     */
    private String nombre;

    /**
     * Código interno.
     *
     * Ejemplo:
     * PZA
     * KG
     * LT
     */
    private String codigo;

    /**
     * Abreviatura utilizada
     * para mostrar en reportes
     * y documentos.
     */
    private String abreviatura;

    /**
     * Descripción.
     */
    private String descripcion;

    /**
     * Estado de la unidad.
     */
    private Boolean activo;

    /**
     * Fecha de creación.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de actualización.
     */
    private LocalDateTime updatedAt;

    /*
     * ============================================================
     * CONSTRUCTORES
     * ============================================================
     */

    public UnidadMedida() {

    }

    public UnidadMedida(Long id,
            String nombre,
            String codigo,
            String abreviatura,
            String descripcion,
            Boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.abreviatura = abreviatura;
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
     * Devuelve el nombre de la unidad.
     *
     * Muy útil para mostrar la unidad
     * directamente en JComboBox y JTable.
     */
    @Override
    public String toString() {

        return nombre;

    }

}