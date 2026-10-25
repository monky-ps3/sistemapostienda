/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.Inventario;
import com.sistemapostienda.service.InventarioService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para la gestión del inventario.
 *
 * Coordina las operaciones entre la interfaz del sistema
 * y InventarioService.
 */
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController() {
        this.inventarioService = new InventarioService();
    }

    /**
     * Crea un nuevo registro de inventario.
     */
    public Long crear(Inventario inventario) throws Exception {
        return inventarioService.crear(inventario);
    }

    /**
     * Busca un inventario por ID.
     */
    public Inventario buscarPorId(Long id) throws Exception {
        return inventarioService.buscarPorId(id);
    }

    /**
     * Busca el inventario de un producto en una sucursal
     * sin lote.
     */
    public Inventario buscarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws Exception {

        return inventarioService.buscarPorSucursalYProducto(
                sucursalId,
                productoId
        );
    }

    /**
     * Busca inventario por sucursal, producto y lote.
     */
    public Inventario buscarPorSucursalProductoYLote(
            Long sucursalId,
            Long productoId,
            String lote
    ) throws Exception {

        return inventarioService.buscarPorSucursalProductoYLote(
                sucursalId,
                productoId,
                lote
        );
    }

    /**
     * Lista todo el inventario.
     */
    public List<Inventario> listar() throws Exception {
        return inventarioService.listar();
    }

    /**
     * Lista el inventario de una sucursal.
     */
    public List<Inventario> listarPorSucursal(
            Long sucursalId
    ) throws Exception {

        return inventarioService.listarPorSucursal(sucursalId);
    }

    /**
     * Lista el inventario de un producto
     * en todas las sucursales.
     */
    public List<Inventario> listarPorProducto(
            Long productoId
    ) throws Exception {

        return inventarioService.listarPorProducto(productoId);
    }

    /**
     * Lista todos los lotes de un producto
     * dentro de una sucursal.
     */
    public List<Inventario> listarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws Exception {

        return inventarioService.listarPorSucursalYProducto(
                sucursalId,
                productoId
        );
    }

    /**
     * Actualiza un registro completo de inventario.
     */
    public boolean actualizar(Inventario inventario)
            throws Exception {

        return inventarioService.actualizar(inventario);
    }

    /**
     * Actualiza directamente la existencia.
     */
    public boolean actualizarExistencia(
            Long id,
            BigDecimal existencia
    ) throws Exception {

        return inventarioService.actualizarExistencia(
                id,
                existencia
        );
    }

    /**
     * Aumenta la existencia.
     */
    public boolean aumentarExistencia(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        return inventarioService.aumentarExistencia(
                id,
                cantidad
        );
    }

    /**
     * Disminuye la existencia.
     */
    public boolean disminuirExistencia(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        return inventarioService.disminuirExistencia(
                id,
                cantidad
        );
    }

    /**
     * Obtiene la existencia disponible.
     *
     * existencia - existencia reservada.
     */
    public BigDecimal obtenerExistenciaDisponible(
            Long id
    ) throws Exception {

        return inventarioService.obtenerExistenciaDisponible(id);
    }

    /**
     * Actualiza la existencia reservada.
     */
    public boolean actualizarExistenciaReservada(
            Long id,
            BigDecimal existenciaReservada
    ) throws Exception {

        return inventarioService.actualizarExistenciaReservada(
                id,
                existenciaReservada
        );
    }

    /**
     * Reserva una cantidad de inventario.
     */
    public boolean reservar(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        return inventarioService.reservar(
                id,
                cantidad
        );
    }

    /**
     * Libera una cantidad previamente reservada.
     */
    public boolean liberarReserva(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        return inventarioService.liberarReserva(
                id,
                cantidad
        );
    }

    /**
     * Actualiza los costos del inventario.
     */
    public boolean actualizarCostos(
            Long id,
            BigDecimal ultimoCosto,
            BigDecimal costoPromedio
    ) throws Exception {

        return inventarioService.actualizarCostos(
                id,
                ultimoCosto,
                costoPromedio
        );
    }

    /**
     * Activa o desactiva un registro de inventario.
     */
    public boolean actualizarActivo(
            Long id,
            boolean activo
    ) throws Exception {

        return inventarioService.actualizarActivo(
                id,
                activo
        );
    }

    /**
     * Elimina un registro de inventario.
     */
    public boolean eliminar(Long id) throws Exception {
        return inventarioService.eliminar(id);
    }

    /**
     * Indica si el inventario está por debajo
     * de su existencia mínima.
     */
    public boolean estaBajoMinimo(Long id) throws Exception {
        return inventarioService.estaBajoMinimo(id);
    }

    /**
     * Indica si el inventario está por encima
     * de su existencia máxima.
     */
    public boolean estaSobreMaximo(Long id) throws Exception {
        return inventarioService.estaSobreMaximo(id);
    }
}