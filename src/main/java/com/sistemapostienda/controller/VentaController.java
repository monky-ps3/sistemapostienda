/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */



import com.sistemapostienda.model.Venta;
import com.sistemapostienda.service.VentaService;

import java.util.List;

public class VentaController {

    private final VentaService ventaService;

    // Constructor predeterminado
    public VentaController() {
        this.ventaService = new VentaService();
    }

    // Constructor para inyectar el servicio
    public VentaController(VentaService ventaService) {
        if (ventaService == null) {
            throw new IllegalArgumentException(
                    "El servicio de ventas no puede ser nulo.");
        }

        this.ventaService = ventaService;
    }

    // Registrar una venta en estado BORRADOR
    public Venta guardar(Venta venta) {
        return ventaService.guardar(venta);
    }

    // Buscar una venta por ID
    public Venta buscarPorId(Long id) {
        return ventaService.buscarPorId(id);
    }

    // Buscar una venta por folio
    public Venta buscarPorFolio(String folio) {
        return ventaService.buscarPorFolio(folio);
    }

    // Listar todas las ventas
    public List<Venta> listar() {
        return ventaService.listar();
    }

    // Consultar ventas por sucursal
    public List<Venta> listarPorSucursal(Long sucursalId) {
        return ventaService.listarPorSucursal(sucursalId);
    }

    // Consultar ventas por cliente
    public List<Venta> listarPorCliente(Long clienteId) {
        return ventaService.listarPorCliente(clienteId);
    }

    // Consultar ventas por usuario
    public List<Venta> listarPorUsuario(Long usuarioId) {
        return ventaService.listarPorUsuario(usuarioId);
    }

    // Consultar ventas por caja
    public List<Venta> listarPorCaja(Long cajaId) {
        return ventaService.listarPorCaja(cajaId);
    }

    // Consultar ventas por sesión de caja
    public List<Venta> listarPorSesionCaja(Long sesionCajaId) {
        return ventaService.listarPorSesionCaja(sesionCajaId);
    }

    // Consultar ventas por estado
    public List<Venta> listarPorEstado(String estado) {
        return ventaService.listarPorEstado(estado);
    }

    // Consultar ventas por tipo
    public List<Venta> listarPorTipoVenta(String tipoVenta) {
        return ventaService.listarPorTipoVenta(tipoVenta);
    }

    // Actualizar una venta que todavía está en borrador
    public boolean actualizar(Venta venta) {
        return ventaService.actualizar(venta);
    }

    // Cancelar un borrador
    public boolean cancelarBorrador(Long id) {
        return ventaService.cancelarBorrador(id);
    }
}
