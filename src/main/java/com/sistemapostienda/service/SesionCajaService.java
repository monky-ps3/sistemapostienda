/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */



import com.sistemapostienda.dao.SesionCajaDAO;
import com.sistemapostienda.model.SesionCaja;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SesionCajaService {

    private final SesionCajaDAO sesionCajaDAO;

    public SesionCajaService() {
        this.sesionCajaDAO = new SesionCajaDAO();
    }

    /**
     * Abre una nueva sesión de caja.
     */
    public Long abrirSesion(
            Long cajaId,
            Long usuarioId,
            BigDecimal fondoInicial,
            String turno,
            String observaciones) throws SQLException {

        validarId(cajaId, "La caja es obligatoria.");
        validarId(usuarioId, "El usuario es obligatorio.");

        if (fondoInicial == null || fondoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El fondo inicial no puede ser negativo ni estar vacío.");
        }

        validarTurno(turno);

        if (sesionCajaDAO.tieneSesionAbierta(cajaId)) {
            throw new IllegalStateException(
                    "Esta caja ya tiene una sesión abierta.");
        }

        SesionCaja sesion = new SesionCaja();

        sesion.setFolio(generarFolio());
        sesion.setCajaId(cajaId);
        sesion.setUsuarioId(usuarioId);
        sesion.setFondoInicial(fondoInicial);
        sesion.setTurno(turno);
        sesion.setEstado("ABIERTA");
        sesion.setObservaciones(observaciones);

        return sesionCajaDAO.guardar(sesion);
    }

    /**
     * Cierra una sesión abierta.
     */
    public boolean cerrarSesion(
            Long sesionId,
            String observaciones) throws SQLException {

        validarId(sesionId, "El identificador de la sesión es obligatorio.");

        SesionCaja sesion = sesionCajaDAO.buscarPorId(sesionId);

        if (sesion == null) {
            throw new IllegalArgumentException(
                    "La sesión de caja no existe.");
        }

        if (!"ABIERTA".equals(sesion.getEstado())) {
            throw new IllegalStateException(
                    "La sesión ya está cerrada.");
        }

        return sesionCajaDAO.cerrar(sesionId, observaciones);
    }

    /**
     * Consulta una sesión por su ID.
     */
    public SesionCaja buscarPorId(Long id) throws SQLException {
        validarId(id, "El identificador de la sesión es obligatorio.");
        return sesionCajaDAO.buscarPorId(id);
    }

    /**
     * Consulta una sesión por su folio.
     */
    public SesionCaja buscarPorFolio(String folio) throws SQLException {

        if (folio == null || folio.isBlank()) {
            throw new IllegalArgumentException(
                    "El folio de la sesión es obligatorio.");
        }

        return sesionCajaDAO.buscarPorFolio(folio.trim());
    }

    /**
     * Busca la sesión abierta de una caja.
     */
    public SesionCaja buscarSesionAbiertaPorCaja(Long cajaId)
            throws SQLException {

        validarId(cajaId, "El identificador de la caja es obligatorio.");

        return sesionCajaDAO.buscarSesionAbiertaPorCaja(cajaId);
    }

    /**
     * Lista todas las sesiones.
     */
    public List<SesionCaja> listar() throws SQLException {
        return sesionCajaDAO.listar();
    }

    /**
     * Lista las sesiones de una caja.
     */
    public List<SesionCaja> listarPorCaja(Long cajaId)
            throws SQLException {

        validarId(cajaId, "El identificador de la caja es obligatorio.");

        return sesionCajaDAO.listarPorCaja(cajaId);
    }

    /**
     * Lista las sesiones de un usuario.
     */
    public List<SesionCaja> listarPorUsuario(Long usuarioId)
            throws SQLException {

        validarId(usuarioId, "El identificador del usuario es obligatorio.");

        return sesionCajaDAO.listarPorUsuario(usuarioId);
    }

    /**
     * Comprueba si una caja tiene una sesión abierta.
     */
    public boolean tieneSesionAbierta(Long cajaId) throws SQLException {

        validarId(cajaId, "El identificador de la caja es obligatorio.");

        return sesionCajaDAO.tieneSesionAbierta(cajaId);
    }

    private void validarId(Long id, String mensaje) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private void validarTurno(String turno) {

        if (turno == null || turno.isBlank()) {
            return;
        }

        switch (turno) {
            case "MATUTINO", "VESPERTINO", "NOCTURNO", "PERSONALIZADO" -> {
                // Turno válido.
            }
            default -> throw new IllegalArgumentException(
                    "El turno debe ser MATUTINO, VESPERTINO, "
                    + "NOCTURNO o PERSONALIZADO.");
        }
    }

    private String generarFolio() {
        return "SC-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
