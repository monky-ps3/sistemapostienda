/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */



import com.sistemapostienda.model.VentaPago;
import com.sistemapostienda.service.VentaPagoService;

import java.util.List;

public class VentaPagoController {

    private final VentaPagoService ventaPagoService;

    // Constructor predeterminado
    public VentaPagoController() {
        this.ventaPagoService = new VentaPagoService();
    }

    // Constructor para inyectar el servicio
    public VentaPagoController(VentaPagoService ventaPagoService) {
        if (ventaPagoService == null) {
            throw new IllegalArgumentException(
                    "El servicio de pagos no puede ser nulo.");
        }

        this.ventaPagoService = ventaPagoService;
    }

    // Registrar un pago
    public VentaPago guardar(VentaPago pago) {
        return ventaPagoService.guardar(pago);
    }

    // Buscar un pago por ID
    public VentaPago buscarPorId(Long id) {
        return ventaPagoService.buscarPorId(id);
    }

    // Listar todos los pagos
    public List<VentaPago> listar() {
        return ventaPagoService.listar();
    }

    // Listar pagos asociados a una venta
    public List<VentaPago> listarPorVenta(Long ventaId) {
        return ventaPagoService.listarPorVenta(ventaId);
    }

    // Listar pagos por forma de pago
    public List<VentaPago> listarPorFormaPago(Long formaPagoId) {
        return ventaPagoService.listarPorFormaPago(formaPagoId);
    }

    // Listar pagos realizados por un usuario
    public List<VentaPago> listarPorUsuario(Long usuarioId) {
        return ventaPagoService.listarPorUsuario(usuarioId);
    }

    // Listar pagos por estado
    public List<VentaPago> listarPorEstado(String estado) {
        return ventaPagoService.listarPorEstado(estado);
    }

    // Listar pagos por tipo
    public List<VentaPago> listarPorTipoPago(String tipoPago) {
        return ventaPagoService.listarPorTipoPago(tipoPago);
    }

    // Cancelar un pago conservando su historial
    public boolean cancelar(Long id) {
        return ventaPagoService.cancelar(id);
    }
}
