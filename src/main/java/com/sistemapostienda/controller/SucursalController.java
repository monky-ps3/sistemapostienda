/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;


import com.sistemapostienda.model.Sucursal;
import com.sistemapostienda.service.SucursalService;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Controller encargado de administrar
 * las operaciones relacionadas con
 * las sucursales del sistema.
 *
 * Controller
 *      │
 *      ▼
 * SucursalService
 *      │
 *      ▼
 * SucursalDAO
 *
 * ============================================================
 */
public class SucursalController {

    private final SucursalService sucursalService;

    /**
     * Constructor.
     */
    public SucursalController() {

        this.sucursalService = new SucursalService();

    }

    /**
     * Registra una nueva sucursal.
     *
     * @param sucursal Sucursal a registrar.
     *
     * @return true si fue registrada.
     */
    public boolean crear(Sucursal sucursal) {

        return sucursalService.guardar(sucursal);

    }

    /**
     * Actualiza una sucursal existente.
     *
     * @param sucursal Sucursal actualizada.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Sucursal sucursal) {

        return sucursalService.actualizar(sucursal);

    }

    /**
     * Elimina una sucursal.
     *
     * @param id Identificador de la sucursal.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        return sucursalService.eliminar(id);

    }

    /**
     * Busca una sucursal por su ID.
     *
     * @param id Identificador.
     *
     * @return Sucursal encontrada.
     */
    public Sucursal buscarPorId(Long id) {

        return sucursalService.buscarPorId(id);

    }

    /**
     * Busca una sucursal por su código.
     *
     * @param codigo Código de la sucursal.
     *
     * @return Sucursal encontrada.
     */
    public Sucursal buscarPorCodigo(String codigo) {

        return sucursalService.buscarPorCodigo(codigo);

    }

    /**
     * Obtiene todas las sucursales.
     *
     * @return Lista de sucursales.
     */
    public List<Sucursal> listarTodos() {

        return sucursalService.listarTodos();

    }

    /**
     * Obtiene únicamente las sucursales activas.
     *
     * @return Lista de sucursales activas.
     */
    public List<Sucursal> listarActivas() {

        return sucursalService.listarActivas();

    }

    /**
     * Verifica si un código de sucursal
     * ya se encuentra registrado.
     *
     * @param codigo Código de la sucursal.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        return sucursalService.existeCodigo(codigo);

    }

}
