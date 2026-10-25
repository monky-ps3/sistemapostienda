/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */


import com.sistemapostienda.model.Permiso;
import com.sistemapostienda.model.Rol;
import com.sistemapostienda.model.RolPermiso;
import com.sistemapostienda.service.RolPermisoService;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de administrar
 * la relación entre Roles y Permisos.
 *
 * Controller
 *      │
 *      ▼
 * RolPermisoService
 *      │
 *      ▼
 * RolPermisoDAO
 *
 * ============================================================
 */
public class RolPermisoController {

    private final RolPermisoService rolPermisoService;

    /**
     * Constructor.
     */
    public RolPermisoController() {

        this.rolPermisoService = new RolPermisoService();

    }

    /**
     * Asigna un permiso a un rol.
     *
     * @param rolPermiso Relación rol-permiso.
     *
     * @return true si fue asignado.
     */
    public boolean asignarPermiso(RolPermiso rolPermiso) {

        return rolPermisoService.asignarPermiso(
                rolPermiso
        );

    }

    /**
     * Quita un permiso asignado a un rol.
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si fue eliminado.
     */
    public boolean quitarPermiso(Long rolId,
                                 Long permisoId) {

        return rolPermisoService.quitarPermiso(
                rolId,
                permisoId
        );

    }

    /**
     * Obtiene todos los permisos
     * pertenecientes a un rol.
     *
     * @param rolId ID del rol.
     *
     * @return Lista de permisos.
     */
    public List<Permiso> obtenerPermisosPorRol(Long rolId) {

        return rolPermisoService.obtenerPermisosPorRol(
                rolId
        );

    }

    /**
     * Obtiene todos los roles
     * que poseen un permiso.
     *
     * @param permisoId ID del permiso.
     *
     * @return Lista de roles.
     */
    public List<Rol> obtenerRolesPorPermiso(Long permisoId) {

        return rolPermisoService.obtenerRolesPorPermiso(
                permisoId
        );

    }

    /**
     * Verifica si un permiso
     * ya fue asignado a un rol.
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si existe.
     */
    public boolean existeRelacion(Long rolId,
                                  Long permisoId) {

        return rolPermisoService.existeRelacion(
                rolId,
                permisoId
        );

    }

    /**
     * Elimina todos los permisos
     * asignados a un rol.
     *
     * @param rolId ID del rol.
     *
     * @return true si fueron eliminados.
     */
    public boolean eliminarTodosPorRol(Long rolId) {

        return rolPermisoService.eliminarTodosPorRol(
                rolId
        );

    }

}