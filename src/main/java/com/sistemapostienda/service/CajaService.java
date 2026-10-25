/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */



import com.sistemapostienda.dao.CajaDAO;
import com.sistemapostienda.dao.SesionCajaDAO;
import com.sistemapostienda.model.Caja;

import java.sql.SQLException;
import java.util.List;

public class CajaService {

    private final CajaDAO cajaDAO;
    private final SesionCajaDAO sesionCajaDAO;

    public CajaService() {
        this.cajaDAO = new CajaDAO();
        this.sesionCajaDAO = new SesionCajaDAO();
    }

    /**
     * Registra una nueva caja activa.
     */
    public Long guardar(Caja caja) throws SQLException {

        validarCaja(caja);

        if (caja.getId() != null) {
            throw new IllegalArgumentException(
                    "No se puede registrar una caja que ya tiene ID.");
        }

        caja.setSucursalId(caja.getSucursalId());
        caja.setNombre(caja.getNombre().trim());
        caja.setDescripcion(normalizarDescripcion(caja.getDescripcion()));

        // Toda caja nueva comienza activa.
        caja.setActiva(true);

        return cajaDAO.guardar(caja);
    }

    /**
     * Busca una caja por su identificador.
     */
    public Caja buscarPorId(Long id) throws SQLException {

        validarId(id, "El ID de la caja es obligatorio.");

        return cajaDAO.buscarPorId(id);
    }

    /**
     * Lista todas las cajas.
     */
    public List<Caja> listar() throws SQLException {
        return cajaDAO.listar();
    }

    /**
     * Lista todas las cajas de una sucursal.
     */
    public List<Caja> listarPorSucursal(Long sucursalId)
            throws SQLException {

        validarId(sucursalId, "La sucursal es obligatoria.");

        return cajaDAO.listarPorSucursal(sucursalId);
    }

    /**
     * Lista únicamente las cajas activas de una sucursal.
     */
    public List<Caja> listarActivasPorSucursal(Long sucursalId)
            throws SQLException {

        validarId(sucursalId, "La sucursal es obligatoria.");

        return cajaDAO.listarActivasPorSucursal(sucursalId);
    }

    /**
     * Actualiza el nombre y la descripción de una caja.
     */
    public boolean actualizar(Caja caja) throws SQLException {

        if (caja == null) {
            throw new IllegalArgumentException(
                    "Los datos de la caja son obligatorios.");
        }

        validarId(caja.getId(), "El ID de la caja es obligatorio.");
        validarDatos(caja);

        Caja existente = cajaDAO.buscarPorId(caja.getId());

        if (existente == null) {
            throw new IllegalArgumentException(
                    "La caja que intentas actualizar no existe.");
        }

        if (!existente.isActiva()) {
            throw new IllegalStateException(
                    "No se puede modificar una caja dada de baja.");
        }

        caja.setNombre(caja.getNombre().trim());
        caja.setDescripcion(normalizarDescripcion(caja.getDescripcion()));

        return cajaDAO.actualizar(caja);
    }

    /**
     * Da de baja una caja sin eliminar su historial.
     */
    public boolean darDeBaja(Long id) throws SQLException {

        validarId(id, "El ID de la caja es obligatorio.");

        Caja caja = cajaDAO.buscarPorId(id);

        if (caja == null) {
            throw new IllegalArgumentException(
                    "La caja no existe.");
        }

        if (!caja.isActiva()) {
            throw new IllegalStateException(
                    "La caja ya se encuentra dada de baja.");
        }

        if (sesionCajaDAO.tieneSesionAbierta(id)) {
            throw new IllegalStateException(
                    "No se puede dar de baja la caja porque tiene "
                    + "una sesión abierta.");
        }

        return cajaDAO.cambiarEstado(id, false);
    }

    /**
     * Reactiva una caja que se encuentra dada de baja.
     */
    public boolean reactivar(Long id) throws SQLException {

        validarId(id, "El ID de la caja es obligatorio.");

        Caja caja = cajaDAO.buscarPorId(id);

        if (caja == null) {
            throw new IllegalArgumentException(
                    "La caja no existe.");
        }

        if (caja.isActiva()) {
            throw new IllegalStateException(
                    "La caja ya se encuentra activa.");
        }

        return cajaDAO.cambiarEstado(id, true);
    }

    /**
     * Valida los datos generales de la caja.
     */
    private void validarCaja(Caja caja) {

        if (caja == null) {
            throw new IllegalArgumentException(
                    "Los datos de la caja son obligatorios.");
        }

        validarId(
                caja.getSucursalId(),
                "Debes seleccionar una sucursal."
        );

        validarDatos(caja);
    }

    /**
     * Valida nombre y descripción.
     */
    private void validarDatos(Caja caja) {

        String nombre = caja.getNombre();

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la caja es obligatorio.");
        }

        if (nombre.trim().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 100 caracteres.");
        }

        if (caja.getDescripcion() != null
                && caja.getDescripcion().length() > 255) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar los 255 caracteres.");
        }
    }

    /**
     * Comprueba que un identificador sea válido.
     */
    private void validarId(Long id, String mensaje) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    /**
     * Convierte descripciones vacías en null.
     */
    private String normalizarDescripcion(String descripcion) {

        if (descripcion == null || descripcion.isBlank()) {
            return null;
        }

        return descripcion.trim();
    }
}
