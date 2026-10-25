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

public class Caja {

    private Long id;
    private Long sucursalId;
    private String nombre;
    private String descripcion;
    private boolean activa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Caja() {
    }

    public Caja(
            Long id,
            Long sucursalId,
            String nombre,
            String descripcion,
            boolean activa,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.sucursalId = sucursalId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
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
}
