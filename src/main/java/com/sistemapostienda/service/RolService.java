/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */

import com.sistemapostienda.dao.RolDAO;
import com.sistemapostienda.model.Rol;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Capa de servicio para la gestión de roles.
 *
 * Se encarga de aplicar reglas de negocio antes
 * de comunicarse con RolDAO.
 *
 * ============================================================
 */
public class RolService {


    private final RolDAO rolDAO;



    /**
     * Constructor.
     *
     * Inicializa el acceso al DAO.
     */
    public RolService() {

        this.rolDAO = new RolDAO();

    }



    /**
     * Guarda un nuevo rol.
     *
     * Validaciones realizadas:
     *
     * - El nombre no debe estar vacío.
     *
     * @param rol Rol a guardar.
     *
     * @return true si fue guardado correctamente.
     */
    public boolean guardar(Rol rol) {


        if (rol == null) {

            return false;

        }


        if (rol.getNombre() == null ||
                rol.getNombre().trim().isEmpty()) {


            return false;

        }


        return rolDAO.guardar(rol);

    }



    /**
     * Actualiza un rol existente.
     *
     * @param rol Rol con los cambios.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Rol rol) {


        if (rol == null) {

            return false;

        }


        if (rol.getId() == null ||
                rol.getId() <= 0) {


            return false;

        }


        return rolDAO.actualizar(rol);

    }



    /**
     * Elimina un rol.
     *
     * @param id Identificador del rol.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {


        if (id == null || id <= 0) {

            return false;

        }


        return rolDAO.eliminar(id);

    }



    /**
     * Busca un rol por ID.
     *
     * @param id Identificador.
     *
     * @return Rol encontrado.
     */
    public Rol buscarPorId(Long id) {


        if (id == null || id <= 0) {

            return null;

        }


        return rolDAO.buscarPorId(id);

    }



    /**
     * Busca un rol por nombre.
     *
     * @param nombre Nombre del rol.
     *
     * @return Rol encontrado.
     */
    public Rol buscarPorNombre(String nombre) {


        if (nombre == null ||
                nombre.trim().isEmpty()) {


            return null;

        }


        return rolDAO.buscarPorNombre(nombre);

    }



    /**
     * Obtiene todos los roles registrados.
     *
     * @return Lista de roles.
     */
    public List<Rol> listarTodos() {


        return rolDAO.listarTodos();

    }



    /**
     * Obtiene únicamente roles activos.
     *
     * @return Lista de roles activos.
     */
    public List<Rol> listarActivos() {


        return rolDAO.listarActivos();

    }



    /**
     * Verifica si existe un rol con determinado nombre.
     *
     * @param nombre Nombre a validar.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {


        if (nombre == null ||
                nombre.trim().isEmpty()) {


            return false;

        }


        return rolDAO.existeNombre(nombre);

    }


}