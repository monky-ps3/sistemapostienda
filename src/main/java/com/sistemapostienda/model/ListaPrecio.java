/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa una lista de precios.
 *
 * Una lista puede tener alcance:
 *
 * GLOBAL    -> Se utiliza en todas las sucursales.
 * SUCURSAL  -> Pertenece a una sucursal específica.
 *
 * Ejemplos:
 *
 * Público General
 * Mayoreo
 * Distribuidor
 * Público Centro
 * Público Norte
 */
public class ListaPrecio {

    private Long id;

    private String codigo;

    private String nombre;

    private String descripcion;

    private Integer prioridad;

    private Boolean esPredeterminada;

    /**
     * GLOBAL o SUCURSAL
     */
    private String alcance;

    /**
     * Puede ser NULL cuando la lista es GLOBAL.
     */
    private Long sucursalId;

    private Boolean activa;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================================================
    // CONSTRUCTORES
    // =========================================================

    /**
     * Constructor vacío.
     *
     * Necesario para crear el objeto y posteriormente
     * asignar sus propiedades mediante setters.
     */
    public ListaPrecio() {
    }

    /**
     * Constructor para crear una lista de precios.
     */
    public ListaPrecio(
            String codigo,
            String nombre,
            String descripcion,
            Integer prioridad,
            Boolean esPredeterminada,
            String alcance,
            Long sucursalId,
            Boolean activa) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.esPredeterminada = esPredeterminada;
        this.alcance = alcance;
        this.sucursalId = sucursalId;
        this.activa = activa;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getEsPredeterminada() {
        return esPredeterminada;
    }

    public void setEsPredeterminada(Boolean esPredeterminada) {
        this.esPredeterminada = esPredeterminada;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
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
     * Indica si la lista es global.
     */
    public boolean esGlobal() {
        return "GLOBAL".equalsIgnoreCase(this.alcance);
    }

    /**
     * Indica si la lista pertenece a una sucursal.
     */
    public boolean esPorSucursal() {
        return "SUCURSAL".equalsIgnoreCase(this.alcance);
    }

    /**
     * Representación del objeto.
     */
    @Override
    public String toString() {
        return "ListaPrecio{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", esPredeterminada=" + esPredeterminada +
                ", alcance='" + alcance + '\'' +
                ", sucursalId=" + sucursalId +
                ", activa=" + activa +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}