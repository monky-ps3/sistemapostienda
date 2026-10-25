/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */


import com.sistemapostienda.model.FormaPago;
import com.sistemapostienda.service.FormaPagoService;

import java.sql.SQLException;
import java.util.List;

public class FormaPagoController {

    private final FormaPagoService formaPagoService;

    public FormaPagoController() {
        this.formaPagoService = new FormaPagoService();
    }

    public FormaPago guardar(FormaPago formaPago) throws SQLException {
        return formaPagoService.guardar(formaPago);
    }

    public FormaPago buscarPorId(Long id) throws SQLException {
        return formaPagoService.buscarPorId(id);
    }

    public FormaPago buscarPorClave(String clave) throws SQLException {
        return formaPagoService.buscarPorClave(clave);
    }

    public FormaPago buscarPorNombre(String nombre) throws SQLException {
        return formaPagoService.buscarPorNombre(nombre);
    }

    public List<FormaPago> listar() throws SQLException {
        return formaPagoService.listar();
    }

    public List<FormaPago> listarActivas() throws SQLException {
        return formaPagoService.listarActivas();
    }

    public boolean actualizar(FormaPago formaPago) throws SQLException {
        return formaPagoService.actualizar(formaPago);
    }

    public boolean activar(Long id) throws SQLException {
        return formaPagoService.activar(id);
    }

    public boolean desactivar(Long id) throws SQLException {
        return formaPagoService.desactivar(id);
    }

    public boolean existeClave(String clave) throws SQLException {
        return formaPagoService.existeClave(clave);
    }

    public boolean existeNombre(String nombre) throws SQLException {
        return formaPagoService.existeNombre(nombre);
    }
}
