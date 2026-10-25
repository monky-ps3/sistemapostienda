/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */


import com.sistemapostienda.model.SesionCaja;
import com.sistemapostienda.service.SesionCajaService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SesionCajaController {

    private final SesionCajaService sesionCajaService;

    public SesionCajaController() {
        this.sesionCajaService = new SesionCajaService();
    }

    /**
     * Abre una sesión de caja.
     */
    public Long abrirSesion(
            Long cajaId,
            Long usuarioId,
            BigDecimal fondoInicial,
            String turno,
            String observaciones) throws SQLException {

        return sesionCajaService.abrirSesion(
                cajaId,
                usuarioId,
                fondoInicial,
                turno,
                observaciones
        );
    }

    /**
     * Cierra una sesión de caja abierta.
     */
    public boolean cerrarSesion(
            Long sesionId,
            String observaciones) throws SQLException {

        return sesionCajaService.cerrarSesion(
                sesionId,
                observaciones
        );
    }

    /**
     * Busca una sesión por su identificador.
     */
    public SesionCaja buscarPorId(Long id) throws SQLException {
        return sesionCajaService.buscarPorId(id);
    }

    /**
     * Busca una sesión por su folio.
     */
    public SesionCaja buscarPorFolio(String folio) throws SQLException {
        return sesionCajaService.buscarPorFolio(folio);
    }

    /**
     * Busca la sesión abierta de una caja.
     */
    public SesionCaja buscarSesionAbiertaPorCaja(Long cajaId)
            throws SQLException {

        return sesionCajaService.buscarSesionAbiertaPorCaja(cajaId);
    }

    /**
     * Lista todas las sesiones de caja.
     */
    public List<SesionCaja> listar() throws SQLException {
        return sesionCajaService.listar();
    }

    /**
     * Lista las sesiones de una caja.
     */
    public List<SesionCaja> listarPorCaja(Long cajaId)
            throws SQLException {

        return sesionCajaService.listarPorCaja(cajaId);
    }

    /**
     * Lista las sesiones de un usuario.
     */
    public List<SesionCaja> listarPorUsuario(Long usuarioId)
            throws SQLException {

        return sesionCajaService.listarPorUsuario(usuarioId);
    }

    /**
     * Comprueba si una caja tiene una sesión abierta.
     */
    public boolean tieneSesionAbierta(Long cajaId) throws SQLException {
        return sesionCajaService.tieneSesionAbierta(cajaId);
    }
}

