/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */


import com.sistemapostienda.dao.RolPermisoDAO;
import com.sistemapostienda.model.Permiso;
import com.sistemapostienda.model.Rol;
import com.sistemapostienda.model.RolPermiso;

import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Servicio encargado de administrar la relación
 * entre Roles y Permisos.
 *
 * ============================================================
 */
public class RolPermisoService {

    private final RolPermisoDAO rolPermisoDAO;

    /**
     * Constructor.
     */
    public RolPermisoService() {

        this.rolPermisoDAO = new RolPermisoDAO();

    }

    /**
     * Asigna un permiso a un rol.
     *
     * @param rolPermiso Relación rol-permiso.
     *
     * @return true si fue asignado.
     */
    public boolean asignarPermiso(RolPermiso rolPermiso) {

        if (rolPermiso == null) {
            return false;
        }

        if (rolPermiso.getRolId() == null
                || rolPermiso.getRolId() <= 0) {
            return false;
        }

        if (rolPermiso.getPermisoId() == null
                || rolPermiso.getPermisoId() <= 0) {
            return false;
        }

        if (rolPermisoDAO.existeRelacion(
                rolPermiso.getRolId(),
                rolPermiso.getPermisoId())) {

            return false;
        }

        return rolPermisoDAO.asignarPermiso(
                rolPermiso.getRolId(),
                rolPermiso.getPermisoId()
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

        if (rolId == null || rolId <= 0) {
            return false;
        }

        if (permisoId == null || permisoId <= 0) {
            return false;
        }

        return rolPermisoDAO.quitarPermiso(
                rolId,
                permisoId
        );

    }

    /**
     * Obtiene todos los permisos
     * asignados a un rol.
     *
     * @param rolId ID del rol.
     *
     * @return Lista de permisos.
     */
    public List<Permiso> obtenerPermisosPorRol(Long rolId) {

        if (rolId == null || rolId <= 0) {
            return Collections.emptyList();
        }

        return rolPermisoDAO.obtenerPermisosPorRol(
                rolId
        );

    }

    /**
     * Obtiene todos los roles
     * que tienen un permiso.
     *
     * @param permisoId ID del permiso.
     *
     * @return Lista de roles.
     */
    public List<Rol> obtenerRolesPorPermiso(Long permisoId) {

        if (permisoId == null || permisoId <= 0) {
            return Collections.emptyList();
        }

        return rolPermisoDAO.obtenerRolesPorPermiso(
                permisoId
        );

    }

    /**
     * Verifica si ya existe
     * la relación rol-permiso.
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si existe.
     */
    public boolean existeRelacion(Long rolId,
                                  Long permisoId) {

        if (rolId == null || permisoId == null) {
            return false;
        }

        return rolPermisoDAO.existeRelacion(
                rolId,
                permisoId
        );

    }

    /**
     * Elimina todos los permisos
     * asignados a un rol.
     *
     * Se utiliza cuando se desea
     * volver a asignar completamente
     * los permisos de un rol.
     *
     * @param rolId ID del rol.
     *
     * @return true si fueron eliminados.
     */
    public boolean eliminarTodosPorRol(Long rolId) {

        if (rolId == null || rolId <= 0) {
            return false;
        }

        return rolPermisoDAO.eliminarTodosPorRol(
                rolId
        );

    }

}