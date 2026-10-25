/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

/**
 *
 * @author monky
 */


import com.sistemapostienda.model.Kardex;
import com.sistemapostienda.service.KardexService;

import java.util.List;

/**
 * Controller para la gestión del Kardex.
 *
 * Coordina las operaciones entre la interfaz del sistema
 * y KardexService.
 */
public class KardexController {

    private final KardexService kardexService;

    public KardexController() {
        this.kardexService = new KardexService();
    }

    /**
     * Registra un nuevo movimiento de Kardex.
     *
     * @param kardex movimiento a registrar
     * @return ID generado
     * @throws Exception si los datos no son válidos
     */
    public Long registrar(Kardex kardex) throws Exception {
        return kardexService.registrar(kardex);
    }

    /**
     * Busca un movimiento por ID.
     *
     * @param id ID del movimiento
     * @return movimiento encontrado o null
     * @throws Exception si el ID no es válido
     */
    public Kardex buscarPorId(Long id) throws Exception {
        return kardexService.buscarPorId(id);
    }

    /**
     * Lista todos los movimientos del Kardex.
     *
     * @return lista de movimientos
     * @throws Exception si ocurre un error
     */
    public List<Kardex> listar() throws Exception {
        return kardexService.listar();
    }

    /**
     * Lista movimientos de una sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorSucursal(
            Long sucursalId
    ) throws Exception {

        return kardexService.listarPorSucursal(
                sucursalId
        );
    }

    /**
     * Lista movimientos de un producto.
     *
     * @param productoId ID del producto
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorProducto(
            Long productoId
    ) throws Exception {

        return kardexService.listarPorProducto(
                productoId
        );
    }

    /**
     * Lista movimientos de un registro específico
     * de inventario.
     *
     * @param inventarioId ID del inventario
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorInventario(
            Long inventarioId
    ) throws Exception {

        return kardexService.listarPorInventario(
                inventarioId
        );
    }

    /**
     * Lista movimientos de un lote específico.
     *
     * @param sucursalId ID de la sucursal
     * @param productoId ID del producto
     * @param lote lote
     * @return lista de movimientos
     * @throws Exception si los datos no son válidos
     */
    public List<Kardex> listarPorLote(
            Long sucursalId,
            Long productoId,
            String lote
    ) throws Exception {

        return kardexService.listarPorLote(
                sucursalId,
                productoId,
                lote
        );
    }

    /**
     * Lista movimientos por tipo de movimiento.
     *
     * @param tipoMovimiento tipo de movimiento
     * @return lista de movimientos
     * @throws Exception si el tipo no es válido
     */
    public List<Kardex> listarPorTipoMovimiento(
            String tipoMovimiento
    ) throws Exception {

        return kardexService.listarPorTipoMovimiento(
                tipoMovimiento
        );
    }

    /**
     * Lista movimientos relacionados con una referencia.
     *
     * Por ejemplo:
     *
     * COMPRA + ID de compra
     * VENTA + ID de venta
     *
     * @param referencia referencia
     * @param referenciaId ID de referencia
     * @return lista de movimientos
     * @throws Exception si los datos no son válidos
     */
    public List<Kardex> listarPorReferencia(
            String referencia,
            Long referenciaId
    ) throws Exception {

        return kardexService.listarPorReferencia(
                referencia,
                referenciaId
        );
    }

    /**
     * Lista los movimientos realizados por un usuario.
     *
     * @param usuarioId ID del usuario
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorUsuario(
            Long usuarioId
    ) throws Exception {

        return kardexService.listarPorUsuario(
                usuarioId
        );
    }

    /**
     * Obtiene el último movimiento de un inventario.
     *
     * @param inventarioId ID del inventario
     * @return último movimiento o null
     * @throws Exception si el ID no es válido
     */
    public Kardex buscarUltimoPorInventario(
            Long inventarioId
    ) throws Exception {

        return kardexService.buscarUltimoPorInventario(
                inventarioId
        );
    }
}
