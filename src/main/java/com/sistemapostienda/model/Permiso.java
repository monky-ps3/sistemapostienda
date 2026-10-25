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
 * Modelo que representa un permiso del sistema.
 *
 * Un permiso define una acción que puede realizar un usuario. Los permisos son
 * asignados a los roles mediante la tabla rol_permiso.
 *
 * ============================================================
 */
public class Permiso {

    private Long id;

    private String clave;

    private String nombre;

    private String modulo;

    private String descripcion;

    private boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Constructor vacío.
     */
    public Permiso() {

    }

    /**
     * Constructor completo.
     */
    public Permiso(
            Long id,
            String clave,
            String nombre,
            String modulo,
            String descripcion,
            boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    //==========================================================
    // GETTERS Y SETTERS
    //==========================================================
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
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

    //==========================================================
    // MÉTODOS
    //==========================================================
    @Override
    public String toString() {
        return nombre;
    }

}
