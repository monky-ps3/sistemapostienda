/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.UnidadMedidaDAO;
import com.sistemapostienda.model.UnidadMedida;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Servicio encargado de administrar la lógica de negocio
 * relacionada con las unidades de medida.
 *
 * El Service recibe las solicitudes del Controller,
 * realiza las validaciones necesarias y posteriormente
 * utiliza UnidadMedidaDAO para acceder a la base de datos.
 *
 * ============================================================
 */
public class UnidadMedidaService {

    /*
     * ============================================================
     * DAO
     * ============================================================
     */

    private final UnidadMedidaDAO unidadMedidaDAO;

    /**
     * Constructor.
     */
    public UnidadMedidaService() {

        this.unidadMedidaDAO = new UnidadMedidaDAO();

    }

    /*
     * ============================================================
     * GUARDAR
     * ============================================================
     */

    /**
     * Registra una nueva unidad de medida.
     *
     * Antes de guardar se validan:
     *
     * - Nombre
     * - Código
     * - Abreviatura
     * - Duplicados
     *
     * @param unidadMedida Unidad que se desea registrar.
     *
     * @return true si fue registrada correctamente.
     */
    public boolean guardar(UnidadMedida unidadMedida) {

        /*
         * Verificamos que el objeto no sea null.
         */
        if (unidadMedida == null) {

            return false;

        }

        /*
         * El nombre es obligatorio.
         */
        if (unidadMedida.getNombre() == null
                || unidadMedida.getNombre().trim().isEmpty()) {

            return false;

        }

        /*
         * El código es obligatorio.
         */
        if (unidadMedida.getCodigo() == null
                || unidadMedida.getCodigo().trim().isEmpty()) {

            return false;

        }

        /*
         * La abreviatura es obligatoria.
         */
        if (unidadMedida.getAbreviatura() == null
                || unidadMedida.getAbreviatura().trim().isEmpty()) {

            return false;

        }

        /*
         * Verificamos que no exista otra unidad
         * con el mismo nombre.
         */
        if (unidadMedidaDAO.existeNombre(
                unidadMedida.getNombre())) {

            return false;

        }

        /*
         * Verificamos que no exista otra unidad
         * con el mismo código.
         */
        if (unidadMedidaDAO.existeCodigo(
                unidadMedida.getCodigo())) {

            return false;

        }

        /*
         * Verificamos que no exista otra unidad
         * con la misma abreviatura.
         */
        if (unidadMedidaDAO.existeAbreviatura(
                unidadMedida.getAbreviatura())) {

            return false;

        }

        /*
         * Si todas las validaciones fueron correctas,
         * enviamos la información al DAO.
         */
        return unidadMedidaDAO.guardar(unidadMedida);

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */

    /**
     * Actualiza una unidad de medida existente.
     *
     * @param unidadMedida Unidad actualizada.
     *
     * @return true si fue actualizada correctamente.
     */
    public boolean actualizar(UnidadMedida unidadMedida) {

        /*
         * Verificamos que el objeto exista.
         */
        if (unidadMedida == null) {

            return false;

        }

        /*
         * Para actualizar necesitamos
         * el ID del registro.
         */
        if (unidadMedida.getId() == null
                || unidadMedida.getId() <= 0) {

            return false;

        }

        /*
         * El nombre es obligatorio.
         */
        if (unidadMedida.getNombre() == null
                || unidadMedida.getNombre().trim().isEmpty()) {

            return false;

        }

        /*
         * El código es obligatorio.
         */
        if (unidadMedida.getCodigo() == null
                || unidadMedida.getCodigo().trim().isEmpty()) {

            return false;

        }

        /*
         * La abreviatura es obligatoria.
         */
        if (unidadMedida.getAbreviatura() == null
                || unidadMedida.getAbreviatura().trim().isEmpty()) {

            return false;

        }

        /*
         * En este punto enviamos la actualización
         * al DAO.
         */
        return unidadMedidaDAO.actualizar(unidadMedida);

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina una unidad de medida.
     *
     * @param id ID de la unidad.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        /*
         * Validamos el ID.
         */
        if (id == null || id <= 0) {

            return false;

        }

        return unidadMedidaDAO.eliminar(id);

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */

    /**
     * Busca una unidad de medida por ID.
     *
     * @param id Identificador.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorId(Long id) {

        if (id == null || id <= 0) {

            return null;

        }

        return unidadMedidaDAO.buscarPorId(id);

    }

    /*
     * ============================================================
     * BUSCAR POR NOMBRE
     * ============================================================
     */

    /**
     * Busca una unidad por nombre.
     *
     * @param nombre Nombre de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorNombre(String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return null;

        }

        return unidadMedidaDAO.buscarPorNombre(nombre);

    }

    /*
     * ============================================================
     * BUSCAR POR CÓDIGO
     * ============================================================
     */

    /**
     * Busca una unidad por código.
     *
     * @param codigo Código.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return null;

        }

        return unidadMedidaDAO.buscarPorCodigo(codigo);

    }

    /*
     * ============================================================
     * BUSCAR POR ABREVIATURA
     * ============================================================
     */

    /**
     * Busca una unidad por abreviatura.
     *
     * @param abreviatura Abreviatura.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorAbreviatura(
            String abreviatura) {

        if (abreviatura == null
                || abreviatura.trim().isEmpty()) {

            return null;

        }

        return unidadMedidaDAO.buscarPorAbreviatura(
                abreviatura
        );

    }

    /*
     * ============================================================
     * LISTAR TODOS
     * ============================================================
     */

    /**
     * Obtiene todas las unidades de medida.
     *
     * @return Lista de unidades.
     */
    public List<UnidadMedida> listarTodos() {

        return unidadMedidaDAO.listarTodos();

    }

    /*
     * ============================================================
     * LISTAR ACTIVOS
     * ============================================================
     */

    /**
     * Obtiene únicamente las unidades activas.
     *
     * @return Lista de unidades activas.
     */
    public List<UnidadMedida> listarActivos() {

        return unidadMedidaDAO.listarActivos();

    }

    /*
     * ============================================================
     * EXISTE NOMBRE
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinado nombre.
     *
     * @param nombre Nombre.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return false;

        }

        return unidadMedidaDAO.existeNombre(nombre);

    }

    /*
     * ============================================================
     * EXISTE CÓDIGO
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinado código.
     *
     * @param codigo Código.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return false;

        }

        return unidadMedidaDAO.existeCodigo(codigo);

    }

    /*
     * ============================================================
     * EXISTE ABREVIATURA
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinada abreviatura.
     *
     * @param abreviatura Abreviatura.
     *
     * @return true si existe.
     */
    public boolean existeAbreviatura(
            String abreviatura) {

        if (abreviatura == null
                || abreviatura.trim().isEmpty()) {

            return false;

        }

        return unidadMedidaDAO.existeAbreviatura(
                abreviatura
        );

    }

}