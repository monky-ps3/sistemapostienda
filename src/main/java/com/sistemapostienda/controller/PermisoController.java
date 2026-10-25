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
import com.sistemapostienda.service.PermisoService;

import java.util.List;


/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de gestionar las operaciones
 * relacionadas con los permisos del sistema.
 *
 * Se comunica con:
 *
 * PermisoController
 *          |
 *          ↓
 * PermisoService
 *          |
 *          ↓
 * PermisoDAO
 *
 * ============================================================
 */
public class PermisoController {


    private final PermisoService permisoService;



    /**
     * Constructor.
     */
    public PermisoController() {

        this.permisoService = new PermisoService();

    }

    /**
     * Crea un nuevo permiso.
     *
     * @param permiso Permiso a registrar.
     *
     * @return true si fue creado correctamente.
     */
    public boolean crear(Permiso permiso) {


        return permisoService.guardar(permiso);

    }



    /**
     * Actualiza un permiso existente.
     *
     * @param permiso Permiso actualizado.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Permiso permiso) {


        return permisoService.actualizar(permiso);

    }



    /**
     * Elimina un permiso.
     *
     * @param id Identificador del permiso.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {


        return permisoService.eliminar(id);

    }



    /**
     * Busca un permiso mediante ID.
     *
     * @param id Identificador.
     *
     * @return Permiso encontrado.
     */
    public Permiso buscarPorId(Long id) {


        return permisoService.buscarPorId(id);

    }



    /**
     * Busca un permiso mediante su clave.
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


        return permisoService.buscarPorClave(clave);

    }



    /**
     * Lista todos los permisos.
     *
     * @return Lista completa.
     */
    public List<Permiso> listarTodos() {


        return permisoService.listarTodos();

    }



    /**
     * Lista únicamente permisos activos.
     *
     * @return Lista de permisos activos.
     */
    public List<Permiso> listarActivos() {


        return permisoService.listarActivos();

    }



    /**
     * Verifica si existe una clave.
     *
     * @param clave Clave a validar.
     *
     * @return true si existe.
     */
    public boolean existeClave(String clave) {


        return permisoService.existeClave(clave);

    }


}
