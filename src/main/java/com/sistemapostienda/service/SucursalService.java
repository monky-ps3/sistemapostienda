/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.service;

import com.sistemapostienda.dao.SucursalDAO;
import com.sistemapostienda.model.Sucursal;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Servicio encargado de la administración
 * de las sucursales.
 *
 * Se encarga de aplicar las reglas de negocio
 * antes de acceder al DAO.
 *
 * ============================================================
 */
public class SucursalService {

    private final SucursalDAO sucursalDAO;

    /**
     * Constructor.
     */
    public SucursalService() {

        this.sucursalDAO = new SucursalDAO();

    }

    /**
     * Guarda una nueva sucursal.
     *
     * @param sucursal Sucursal a registrar.
     *
     * @return true si fue registrada.
     */
    public boolean guardar(Sucursal sucursal) {

        if (sucursal == null) {
            return false;
        }

        if (sucursal.getNombre() == null
                || sucursal.getNombre().trim().isEmpty()) {

            return false;
        }

        if (sucursal.getCodigo() == null
                || sucursal.getCodigo().trim().isEmpty()) {

            return false;
        }

        if (sucursalDAO.existeCodigo(
                sucursal.getCodigo())) {

            return false;
        }

        return sucursalDAO.guardar(sucursal);

    }

    /**
     * Actualiza una sucursal.
     *
     * @param sucursal Sucursal con los nuevos datos.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Sucursal sucursal) {

        if (sucursal == null) {
            return false;
        }

        if (sucursal.getId() == null
                || sucursal.getId() <= 0) {

            return false;
        }

        return sucursalDAO.actualizar(sucursal);

    }

    /**
     * Elimina una sucursal.
     *
     * @param id ID de la sucursal.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {
            return false;
        }

        return sucursalDAO.eliminar(id);

    }

    /**
     * Busca una sucursal por ID.
     *
     * @param id Identificador.
     *
     * @return Sucursal encontrada.
     */
    public Sucursal buscarPorId(Long id) {

        if (id == null || id <= 0) {
            return null;
        }

        return sucursalDAO.buscarPorId(id);

    }

    /**
     * Busca una sucursal por código.
     *
     * @param codigo Código de la sucursal.
     *
     * @return Sucursal encontrada.
     */
    public Sucursal buscarPorCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return null;
        }

        return sucursalDAO.buscarPorCodigo(codigo);

    }

    /**
     * Obtiene todas las sucursales.
     *
     * @return Lista completa.
     */
    public List<Sucursal> listarTodos() {

        return sucursalDAO.listarTodos();

    }

    /**
     * Obtiene únicamente las sucursales activas.
     *
     * @return Lista de sucursales activas.
     */
    public List<Sucursal> listarActivas() {

        return sucursalDAO.listarActivas();

    }

    /**
     * Verifica si un código ya existe.
     *
     * @param codigo Código a validar.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return false;
        }

        return sucursalDAO.existeCodigo(codigo);

    }

}