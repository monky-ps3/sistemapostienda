/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controllers;

import com.sistemapostienda.model.CompraDetalle;
import com.sistemapostienda.services.CompraDetalleService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para la gestión de detalles de compras.
 *
 * Coordina las operaciones entre la interfaz del sistema
 * y CompraDetalleService.
 */
public class CompraDetalleController {

    private final CompraDetalleService compraDetalleService;

    public CompraDetalleController() {
        this.compraDetalleService = new CompraDetalleService();
    }

    // =========================================================
    // CREAR
    // =========================================================

    /**
     * Agrega un detalle a una compra.
     *
     * @param detalle objeto CompraDetalle
     * @return ID generado
     */
    public Long agregar(CompraDetalle detalle)
            throws Exception {

        return compraDetalleService.agregar(detalle);
    }

    // =========================================================
    // CONSULTAS
    // =========================================================

    /**
     * Busca un detalle por ID.
     *
     * @param id ID del detalle
     * @return detalle encontrado
     */
    public CompraDetalle buscarPorId(Long id)
            throws Exception {

        return compraDetalleService.buscarPorId(id);
    }

    /**
     * Lista todos los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return lista de detalles
     */
    public List<CompraDetalle> listarPorCompra(
            Long compraId
    ) throws Exception {

        return compraDetalleService.listarPorCompra(
                compraId
        );
    }

    /**
     * Lista todos los detalles relacionados con un producto.
     *
     * @param productoId ID del producto
     * @return lista de detalles
     */
    public List<CompraDetalle> listarPorProducto(
            Long productoId
    ) throws Exception {

        return compraDetalleService.listarPorProducto(
                productoId
        );
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza un detalle de compra.
     *
     * Solo será posible si la compra está en BORRADOR.
     *
     * @param detalle objeto CompraDetalle
     * @return true si se actualizó
     */
    public boolean actualizar(
            CompraDetalle detalle
    ) throws Exception {

        return compraDetalleService.actualizar(
                detalle
        );
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina un detalle de compra.
     *
     * Solo será posible si la compra está en BORRADOR.
     *
     * @param id ID del detalle
     * @return true si se eliminó
     */
    public boolean eliminar(Long id)
            throws Exception {

        return compraDetalleService.eliminar(id);
    }

    /**
     * Elimina todos los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return true si se ejecutó correctamente
     */
    public boolean eliminarPorCompra(Long compraId)
            throws Exception {

        return compraDetalleService.eliminarPorCompra(
                compraId
        );
    }

    // =========================================================
    // UTILIDADES
    // =========================================================

    /**
     * Comprueba si una compra tiene detalles.
     *
     * @param compraId ID de la compra
     * @return true si tiene al menos un detalle
     */
    public boolean tieneDetalles(Long compraId)
            throws Exception {

        return compraDetalleService.tieneDetalles(
                compraId
        );
    }

    /**
     * Cuenta los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return cantidad de detalles
     */
    public int contarDetalles(Long compraId)
            throws Exception {

        return compraDetalleService.contarDetalles(
                compraId
        );
    }

    /**
     * Calcula las unidades base de una compra.
     *
     * Ejemplo:
     *
     * 3 cajas × 24 piezas = 72 unidades.
     *
     * @param compraId ID de la compra
     * @return cantidad total de unidades base
     */
    public BigDecimal calcularUnidadesBase(
            Long compraId
    ) throws Exception {

        return compraDetalleService.calcularUnidadesBase(
                compraId
        );
    }
}