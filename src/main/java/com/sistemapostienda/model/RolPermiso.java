/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * ============================================================
 * @author monky
 *
 * Modelo que representa la relación entre un Rol y un Permiso.
 *
 * Esta clase corresponde a la tabla rol_permiso.
 *
 * Un rol puede tener muchos permisos y un permiso puede
 * pertenecer a varios roles.
 *
 * ============================================================
 */
public class RolPermiso {

    private Long id;

    private Long rolId;

    private Long permisoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    /*
     * Relaciones
     */
    private Rol rol;

    private Permiso permiso;


    /**
     * Constructor vacío.
     */
    public RolPermiso() {

    }


    /**
     * Constructor completo.
     */
    public RolPermiso(
            Long id,
            Long rolId,
            Long permisoId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.rolId = rolId;
        this.permisoId = permisoId;
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


    public Long getRolId() {
        return rolId;
    }


    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }


    public Long getPermisoId() {
        return permisoId;
    }


    public void setPermisoId(Long permisoId) {
        this.permisoId = permisoId;
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


    public Rol getRol() {
        return rol;
    }


    public void setRol(Rol rol) {
        this.rol = rol;
    }


    public Permiso getPermiso() {
        return permiso;
    }


    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }


    //==========================================================
    // MÉTODOS
    //==========================================================

    @Override
    public String toString() {

        if (permiso != null) {
            return permiso.getNombre();
        }

        return "RolPermiso";

    }

}