/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */



import com.sistemapostienda.model.Caja;
import com.sistemapostienda.service.CajaService;

import java.sql.SQLException;
import java.util.List;

public class CajaController {

    private final CajaService cajaService;

    public CajaController() {
        this.cajaService = new CajaService();
    }

    /**
     * Registra una nueva caja.
     */
    public Long guardar(Caja caja) throws SQLException {
        return cajaService.guardar(caja);
    }

    /**
     * Busca una caja por su identificador.
     */
    public Caja buscarPorId(Long id) throws SQLException {
        return cajaService.buscarPorId(id);
    }

    /**
     * Lista todas las cajas.
     */
    public List<Caja> listar() throws SQLException {
        return cajaService.listar();
    }

    /**
     * Lista las cajas de una sucursal.
     */
    public List<Caja> listarPorSucursal(Long sucursalId)
            throws SQLException {

        return cajaService.listarPorSucursal(sucursalId);
    }

    /**
     * Lista las cajas activas de una sucursal.
     */
    public List<Caja> listarActivasPorSucursal(Long sucursalId)
            throws SQLException {

        return cajaService.listarActivasPorSucursal(sucursalId);
    }

    /**
     * Actualiza los datos de una caja.
     */
    public boolean actualizar(Caja caja) throws SQLException {
        return cajaService.actualizar(caja);
    }

    /**
     * Da de baja una caja sin eliminar su historial.
     */
    public boolean darDeBaja(Long id) throws SQLException {
        return cajaService.darDeBaja(id);
    }

    /**
     * Reactiva una caja dada de baja.
     */
    public boolean reactivar(Long id) throws SQLException {
        return cajaService.reactivar(id);
    }
}
