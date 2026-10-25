/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */


import com.sistemapostienda.dao.PermisoDAO;
import com.sistemapostienda.model.Permiso;

import java.util.List;


/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Capa de servicio para la administración
 * de permisos del sistema.
 *
 * Se encarga de aplicar validaciones antes
 * de comunicarse con PermisoDAO.
 *
 * ============================================================
 */
public class PermisoService {
    private final PermisoDAO permisoDAO;
    /**Constructor. */
    public PermisoService() {
        this.permisoDAO = new PermisoDAO();
   }
    /**
     * Guarda un nuevo permiso.
     *
     * Validaciones:
     *
     * - La clave es obligatoria.
     * - El nombre es obligatorio.
     *
     * @param permiso Permiso a guardar.
     *
     * @return true si fue guardado.
     */
    public boolean guardar(Permiso permiso) {
        if (permiso == null) {
            return false;
        }
        if (permiso.getClave() == null ||
                permiso.getClave().trim().isEmpty()) {
            return false;
        }
        if (permiso.getNombre() == null ||
                permiso.getNombre().trim().isEmpty()) {
            return false;
        }

        if (permisoDAO.existeClave(
                permiso.getClave()
        )) {
            return false;
        }
        return permisoDAO.guardar(permiso);
    }
    /**
     * Actualiza un permiso existente.
     *
     * @param permiso Permiso actualizado.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Permiso permiso) {

       if (permiso == null) {
            return false;
        }
        if (permiso.getId() == null ||
                permiso.getId() <= 0) {
            return false;
        }
        return permisoDAO.actualizar(permiso);
    }

    /**
     * Elimina un permiso.
     *
     * @param id Identificador del permiso.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            return false;
       }
        return permisoDAO.eliminar(id);
    }
    /**
     * Busca permiso por ID.
     *
     * @param id Identificador.
     *
     * @return Permiso encontrado.
     */
    public Permiso buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return permisoDAO.buscarPorId(id);
    }

   /**
     * Busca permiso mediante su clave.
     *
     * Ejemplo:
     *
     * USUARIO_CREAR
     *
     * @param clave Clave del permiso.
     *
     * @return Permiso encontrado.
     */
    public Permiso buscarPorClave(String clave) {
        if (clave == null ||
                clave.trim().isEmpty()) {
            return null;
        }
        return permisoDAO.buscarPorClave(clave);

    }
    /**
     * Obtiene todos los permisos.
     *
     * @return Lista de permisos.
     */
    public List<Permiso> listarTodos() {
        return permisoDAO.listarTodos();
    }
    /**
     * Obtiene permisos activos.
     *
     * @return Lista de permisos activos.
     */
    public List<Permiso> listarActivos() {
        return permisoDAO.listarActivos();
    }
    /**
     * Verifica si existe una clave registrada.
     *
     * @param clave Clave del permiso.
     *
     * @return true si existe.
     */
    public boolean existeClave(String clave) {
        if (clave == null ||
                clave.trim().isEmpty()) {
            return false;
       }
       return permisoDAO.existeClave(clave);

    }


}