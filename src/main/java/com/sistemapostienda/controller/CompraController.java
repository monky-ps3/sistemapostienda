/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controllers;

import com.sistemapostienda.model.Compra;
import com.sistemapostienda.model.CompraDetalle;
import com.sistemapostienda.services.CompraDetalleService;
import com.sistemapostienda.services.CompraService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para la gestión de compras.
 *
 * Coordina las operaciones entre la interfaz del sistema
 * y los Services de compras.
 */
public class CompraController {

    private final CompraService compraService;
    private final CompraDetalleService compraDetalleService;

    public CompraController() {
        this.compraService = new CompraService();
        this.compraDetalleService = new CompraDetalleService();
    }

    // =========================================================
    // COMPRAS
    // =========================================================

    /**
     * Crea una nueva compra.
     *
     * @param compra objeto Compra
     * @return ID de la compra creada
     */
    public Long crearCompra(Compra compra) throws Exception {
        return compraService.crearCompra(compra);
    }

    /**
     * Busca una compra por ID.
     *
     * @param id ID de la compra
     * @return compra encontrada
     */
    public Compra buscarPorId(Long id) throws Exception {
        return compraService.buscarPorId(id);
    }

    /**
     * Busca una compra por folio.
     *
     * @param folio folio de la compra
     * @return compra encontrada
     */
    public Compra buscarPorFolio(String folio) throws Exception {
        return compraService.buscarPorFolio(folio);
    }

    /**
     * Lista todas las compras.
     *
     * @return lista de compras
     */
    public List<Compra> listarCompras() throws Exception {
        return compraService.listar();
    }

    /**
     * Lista las compras de una sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de compras
     */
    public List<Compra> listarComprasPorSucursal(
            Long sucursalId
    ) throws Exception {

        return compraService.listarPorSucursal(sucursalId);
    }

    /**
     * Lista las compras de un proveedor.
     *
     * @param proveedorId ID del proveedor
     * @return lista de compras
     */
    public List<Compra> listarComprasPorProveedor(
            Long proveedorId
    ) throws Exception {

        return compraService.listarPorProveedor(proveedorId);
    }

    /**
     * Actualiza una compra.
     *
     * Solo el Service decide si puede modificarse.
     *
     * @param compra objeto Compra
     * @return true si se actualizó
     */
    public boolean actualizarCompra(Compra compra)
            throws Exception {

        return compraService.actualizar(compra);
    }

    /**
     * Confirma una compra.
     *
     * @param compraId ID de la compra
     * @return true si se confirmó
     */
    public boolean confirmarCompra(Long compraId)
            throws Exception {

        return compraService.confirmarCompra(compraId);
    }

    /**
     * Cancela una compra.
     *
     * @param compraId ID de la compra
     * @return true si se canceló
     */
    public boolean cancelarCompra(Long compraId)
            throws Exception {

        return compraService.cancelarCompra(compraId);
    }

    /**
     * Elimina una compra.
     *
     * Solo se permitirá si está en BORRADOR.
     *
     * @param compraId ID de la compra
     * @return true si se eliminó
     */
    public boolean eliminarCompra(Long compraId)
            throws Exception {

        return compraService.eliminar(compraId);
    }

    // =========================================================
    // DETALLES
    // =========================================================

    /**
     * Agrega un producto a una compra.
     *
     * @param detalle objeto CompraDetalle
     * @return ID del detalle creado
     */
    public Long agregarDetalle(CompraDetalle detalle)
            throws Exception {

        return compraDetalleService.agregar(detalle);
    }

    /**
     * Busca un detalle por ID.
     *
     * @param id ID del detalle
     * @return detalle encontrado
     */
    public CompraDetalle buscarDetallePorId(Long id)
            throws Exception {

        return compraDetalleService.buscarPorId(id);
    }

    /**
     * Lista los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return lista de detalles
     */
    public List<CompraDetalle> listarDetalles(
            Long compraId
    ) throws Exception {

        return compraDetalleService.listarPorCompra(compraId);
    }

    /**
     * Lista los detalles relacionados con un producto.
     *
     * @param productoId ID del producto
     * @return lista de detalles
     */
    public List<CompraDetalle> listarDetallesPorProducto(
            Long productoId
    ) throws Exception {

        return compraDetalleService.listarPorProducto(productoId);
    }

    /**
     * Actualiza un detalle.
     *
     * @param detalle objeto CompraDetalle
     * @return true si se actualizó
     */
    public boolean actualizarDetalle(
            CompraDetalle detalle
    ) throws Exception {

        return compraDetalleService.actualizar(detalle);
    }

    /**
     * Elimina un detalle.
     *
     * @param detalleId ID del detalle
     * @return true si se eliminó
     */
    public boolean eliminarDetalle(Long detalleId)
            throws Exception {

        return compraDetalleService.eliminar(detalleId);
    }

    /**
     * Elimina todos los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return true si se ejecutó correctamente
     */
    public boolean eliminarDetallesPorCompra(Long compraId)
            throws Exception {

        return compraDetalleService.eliminarPorCompra(compraId);
    }

    /**
     * Comprueba si una compra tiene detalles.
     *
     * @param compraId ID de la compra
     * @return true si tiene detalles
     */
    public boolean tieneDetalles(Long compraId)
            throws Exception {

        return compraDetalleService.tieneDetalles(compraId);
    }

    /**
     * Cuenta los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return cantidad de detalles
     */
    public int contarDetalles(Long compraId)
            throws Exception {

        return compraDetalleService.contarDetalles(compraId);
    }

    /**
     * Calcula las unidades base de todos los detalles
     * de una compra.
     *
     * Ejemplo:
     *
     * 3 cajas × 24 = 72 unidades.
     *
     * @param compraId ID de la compra
     * @return unidades base
     */
    public BigDecimal calcularUnidadesBase(Long compraId)
            throws Exception {

        return compraDetalleService.calcularUnidadesBase(
                compraId
        );
    }
}