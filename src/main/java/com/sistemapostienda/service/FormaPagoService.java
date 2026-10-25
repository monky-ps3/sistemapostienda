/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */


import com.sistemapostienda.dao.FormaPagoDAO;
import com.sistemapostienda.model.FormaPago;

import java.sql.SQLException;
import java.util.List;

public class FormaPagoService {

    private final FormaPagoDAO formaPagoDAO;

    public FormaPagoService() {
        this.formaPagoDAO = new FormaPagoDAO();
    }

    public FormaPago guardar(FormaPago formaPago) throws SQLException {
        validarDatos(formaPago);
        validarDuplicados(formaPago, null);

        formaPago.setClave(formaPago.getClave().trim().toUpperCase());
        formaPago.setNombre(formaPago.getNombre().trim());
        normalizarDescripcion(formaPago);

        if (formaPago.getOrden() <= 0) {
            formaPago.setOrden(1);
        }

        return formaPagoDAO.guardar(formaPago);
    }

    public FormaPago buscarPorId(Long id) throws SQLException {
        validarId(id);
        return formaPagoDAO.buscarPorId(id);
    }

    public FormaPago buscarPorClave(String clave) throws SQLException {
        if (clave == null || clave.isBlank()) {
            throw new IllegalArgumentException(
                    "La clave de la forma de pago es obligatoria.");
        }

        return formaPagoDAO.buscarPorClave(
                clave.trim().toUpperCase());
    }

    public FormaPago buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la forma de pago es obligatorio.");
        }

        return formaPagoDAO.buscarPorNombre(nombre.trim());
    }

    public List<FormaPago> listar() throws SQLException {
        return formaPagoDAO.listar();
    }

    public List<FormaPago> listarActivas() throws SQLException {
        return formaPagoDAO.listarActivas();
    }

    public boolean actualizar(FormaPago formaPago) throws SQLException {
        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "La forma de pago es obligatoria.");
        }

        validarId(formaPago.getId());
        validarDatos(formaPago);
        validarDuplicados(formaPago, formaPago.getId());

        formaPago.setClave(formaPago.getClave().trim().toUpperCase());
        formaPago.setNombre(formaPago.getNombre().trim());
        normalizarDescripcion(formaPago);

        if (formaPago.getOrden() <= 0) {
            formaPago.setOrden(1);
        }

        return formaPagoDAO.actualizar(formaPago);
    }

    public boolean activar(Long id) throws SQLException {
        validarExistencia(id);
        return formaPagoDAO.cambiarEstado(id, true);
    }

    public boolean desactivar(Long id) throws SQLException {
        FormaPago formaPago = validarExistencia(id);

        if ("EFECTIVO".equalsIgnoreCase(formaPago.getClave())) {
            throw new IllegalArgumentException(
                    "No se puede desactivar la forma de pago Efectivo.");
        }

        return formaPagoDAO.cambiarEstado(id, false);
    }

    public boolean existeClave(String clave) throws SQLException {
        if (clave == null || clave.isBlank()) {
            throw new IllegalArgumentException(
                    "La clave es obligatoria.");
        }

        return formaPagoDAO.existeClave(
                clave.trim().toUpperCase());
    }

    public boolean existeNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        return formaPagoDAO.existeNombre(nombre.trim());
    }

    private void validarDatos(FormaPago formaPago) {
        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "La forma de pago es obligatoria.");
        }

        if (formaPago.getClave() == null
                || formaPago.getClave().isBlank()) {
            throw new IllegalArgumentException(
                    "La clave es obligatoria.");
        }

        String clave = formaPago.getClave().trim();

        if (!clave.matches("[A-Za-z0-9_-]+")) {
            throw new IllegalArgumentException(
                    "La clave solo puede contener letras, números, "
                    + "guion y guion bajo.");
        }

        if (clave.length() > 30) {
            throw new IllegalArgumentException(
                    "La clave no puede superar los 30 caracteres.");
        }

        if (formaPago.getNombre() == null
                || formaPago.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        if (formaPago.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 100 caracteres.");
        }

        if (formaPago.getDescripcion() != null
                && formaPago.getDescripcion().length() > 255) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar los 255 caracteres.");
        }

        if (formaPago.getOrden() < 0) {
            throw new IllegalArgumentException(
                    "El orden no puede ser negativo.");
        }

        if (formaPago.isPermiteCambio()
                && !"EFECTIVO".equalsIgnoreCase(clave)) {
            throw new IllegalArgumentException(
                    "Solo la forma de pago Efectivo puede permitir cambio.");
        }
    }

    private void validarDuplicados(
            FormaPago formaPago, Long idExcluir) throws SQLException {

        String clave = formaPago.getClave().trim().toUpperCase();
        String nombre = formaPago.getNombre().trim();

        if (idExcluir == null) {
            if (formaPagoDAO.existeClave(clave)) {
                throw new IllegalArgumentException(
                        "Ya existe una forma de pago con esa clave.");
            }

            if (formaPagoDAO.existeNombre(nombre)) {
                throw new IllegalArgumentException(
                        "Ya existe una forma de pago con ese nombre.");
            }
        } else {
            if (formaPagoDAO.existeClaveEnOtroRegistro(
                    clave, idExcluir)) {
                throw new IllegalArgumentException(
                        "Otra forma de pago ya utiliza esa clave.");
            }

            if (formaPagoDAO.existeNombreEnOtroRegistro(
                    nombre, idExcluir)) {
                throw new IllegalArgumentException(
                        "Otra forma de pago ya utiliza ese nombre.");
            }
        }
    }

    private FormaPago validarExistencia(Long id) throws SQLException {
        validarId(id);

        FormaPago formaPago = formaPagoDAO.buscarPorId(id);

        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "La forma de pago no existe.");
        }

        return formaPago;
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero.");
        }
    }

    private void normalizarDescripcion(FormaPago formaPago) {
        if (formaPago.getDescripcion() != null) {
            String descripcion = formaPago.getDescripcion().trim();

            formaPago.setDescripcion(
                    descripcion.isEmpty() ? null : descripcion);
        }
    }
}
