/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */


import com.sistemapostienda.dao.KardexDAO;
import com.sistemapostienda.model.Kardex;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service para la gestión del Kardex.
 *
 * Contiene las reglas de negocio relacionadas con
 * los movimientos históricos del inventario.
 *
 * Los movimientos de Kardex son históricos:
 * una vez registrados no deben modificarse ni eliminarse.
 */
public class KardexService {

    private final KardexDAO kardexDAO;

    public KardexService() {
        this.kardexDAO = new KardexDAO();
    }

    /**
     * Registra un nuevo movimiento de Kardex.
     *
     * Este método valida que el movimiento sea coherente
     * antes de almacenarlo.
     *
     * @param kardex movimiento a registrar
     * @return ID generado
     * @throws Exception si los datos no son válidos
     */
    public Long registrar(Kardex kardex) throws Exception {

        validarKardex(kardex);

        BigDecimal entrada =
                valorCeroSiNull(kardex.getEntrada());

        BigDecimal salida =
                valorCeroSiNull(kardex.getSalida());

        BigDecimal existenciaAnterior =
                valorCeroSiNull(
                        kardex.getExistenciaAnterior()
                );

        BigDecimal existenciaActual =
                valorCeroSiNull(
                        kardex.getExistenciaActual()
                );

        /*
         * Validamos que la existencia resultante
         * corresponda al movimiento registrado.
         */
        BigDecimal existenciaCalculada =
                existenciaAnterior
                        .add(entrada)
                        .subtract(salida);

        if (existenciaCalculada.compareTo(existenciaActual) != 0) {
            throw new Exception(
                    "La existencia actual no coincide con "
                    + "el movimiento registrado."
            );
        }

        /*
         * La existencia nunca puede ser negativa.
         */
        if (existenciaActual.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "La existencia actual no puede ser negativa."
            );
        }

        kardex.setEntrada(entrada);
        kardex.setSalida(salida);
        kardex.setExistenciaAnterior(existenciaAnterior);
        kardex.setExistenciaActual(existenciaActual);

        if (kardex.getCostoUnitario() == null) {
            kardex.setCostoUnitario(BigDecimal.ZERO);
        }

        if (kardex.getCostoTotal() == null) {
            kardex.setCostoTotal(BigDecimal.ZERO);
        }

        return kardexDAO.guardar(kardex);
    }

    /**
     * Busca un movimiento por ID.
     *
     * @param id ID del movimiento
     * @return Kardex encontrado o null
     * @throws Exception si el ID no es válido
     */
    public Kardex buscarPorId(Long id) throws Exception {

        validarId(id);

        return kardexDAO.buscarPorId(id);
    }

    /**
     * Lista todos los movimientos.
     *
     * @return lista de movimientos
     * @throws Exception si ocurre un error
     */
    public List<Kardex> listar() throws Exception {
        return kardexDAO.listar();
    }

    /**
     * Lista movimientos por sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorSucursal(
            Long sucursalId
    ) throws Exception {

        validarId(sucursalId);

        return kardexDAO.listarPorSucursal(
                sucursalId
        );
    }

    /**
     * Lista movimientos por producto.
     *
     * @param productoId ID del producto
     * @return lista de movimientos
     * @throws Exception si el ID no es válido
     */
    public List<Kardex> listarPorProducto(
            Long productoId
    ) throws Exception {

        validarId(productoId);

        return kardexDAO.listarPorProducto(
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

        validarId(inventarioId);

        return kardexDAO.listarPorInventario(
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

        validarId(sucursalId);
        validarId(productoId);

        if (lote == null || lote.trim().isEmpty()) {
            throw new Exception(
                    "El lote es obligatorio."
            );
        }

        return kardexDAO.listarPorLote(
                sucursalId,
                productoId,
                lote.trim()
        );
    }

    /**
     * Lista movimientos por tipo.
     *
     * @param tipoMovimiento tipo de movimiento
     * @return lista de movimientos
     * @throws Exception si el tipo no es válido
     */
    public List<Kardex> listarPorTipoMovimiento(
            String tipoMovimiento
    ) throws Exception {

        validarTipoMovimiento(tipoMovimiento);

        return kardexDAO.listarPorTipoMovimiento(
                tipoMovimiento
        );
    }

    /**
     * Lista movimientos relacionados con una referencia.
     *
     * Ejemplo:
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

        if (referencia == null
                || referencia.trim().isEmpty()) {

            throw new Exception(
                    "La referencia es obligatoria."
            );
        }

        validarId(referenciaId);

        return kardexDAO.listarPorReferencia(
                referencia.trim(),
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

        validarId(usuarioId);

        return kardexDAO.listarPorUsuario(
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

        validarId(inventarioId);

        return kardexDAO.buscarUltimoPorInventario(
                inventarioId
        );
    }

    /**
     * Valida los datos principales del movimiento.
     */
    private void validarKardex(Kardex kardex)
            throws Exception {

        if (kardex == null) {
            throw new Exception(
                    "El movimiento de Kardex es obligatorio."
            );
        }

        validarId(kardex.getSucursalId());
        validarId(kardex.getProductoId());
        validarId(kardex.getUsuarioId());

        /*
         * El inventario puede ser null para determinados
         * movimientos especiales.
         */
        if (kardex.getInventarioId() != null) {
            validarId(kardex.getInventarioId());
        }

        /*
         * La presentación es opcional.
         */
        if (kardex.getPresentacionId() != null) {
            validarId(kardex.getPresentacionId());
        }

        validarTipoMovimiento(
                kardex.getTipoMovimiento()
        );

        BigDecimal entrada =
                valorCeroSiNull(kardex.getEntrada());

        BigDecimal salida =
                valorCeroSiNull(kardex.getSalida());

        /*
         * No permitimos cantidades negativas.
         */
        if (entrada.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "La entrada no puede ser negativa."
            );
        }

        if (salida.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "La salida no puede ser negativa."
            );
        }

        /*
         * Exactamente uno debe tener valor.
         *
         * Entrada > 0 y salida = 0
         * o
         * Entrada = 0 y salida > 0
         */
        if (entrada.compareTo(BigDecimal.ZERO) == 0
                && salida.compareTo(BigDecimal.ZERO) == 0) {

            throw new Exception(
                    "El movimiento debe tener una entrada "
                    + "o una salida."
            );
        }

        if (entrada.compareTo(BigDecimal.ZERO) > 0
                && salida.compareTo(BigDecimal.ZERO) > 0) {

            throw new Exception(
                    "Un movimiento no puede tener entrada "
                    + "y salida al mismo tiempo."
            );
        }

        /*
         * Las existencias son obligatorias conceptualmente.
         */
        if (kardex.getExistenciaAnterior() == null) {
            throw new Exception(
                    "La existencia anterior es obligatoria."
            );
        }

        if (kardex.getExistenciaActual() == null) {
            throw new Exception(
                    "La existencia actual es obligatoria."
            );
        }

        if (kardex.getExistenciaAnterior()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "La existencia anterior no puede ser negativa."
            );
        }

        if (kardex.getExistenciaActual()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "La existencia actual no puede ser negativa."
            );
        }

        /*
         * El costo tampoco puede ser negativo.
         */
        if (kardex.getCostoUnitario() != null
                && kardex.getCostoUnitario()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "El costo unitario no puede ser negativo."
            );
        }

        if (kardex.getCostoTotal() != null
                && kardex.getCostoTotal()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "El costo total no puede ser negativo."
            );
        }

        /*
         * Si existe una referencia, su ID también debe existir.
         */
        if (kardex.getReferenciaId() != null) {
            validarId(kardex.getReferenciaId());
        }
    }

    /**
     * Valida que el tipo de movimiento sea uno de los
     * permitidos por la tabla Kardex.
     */
    private void validarTipoMovimiento(
            String tipoMovimiento
    ) throws Exception {

        if (tipoMovimiento == null
                || tipoMovimiento.trim().isEmpty()) {

            throw new Exception(
                    "El tipo de movimiento es obligatorio."
            );
        }

        String tipo = tipoMovimiento.trim();

        switch (tipo) {

            case "INVENTARIO_INICIAL":
            case "COMPRA":
            case "VENTA":
            case "DEVOLUCION_CLIENTE":
            case "DEVOLUCION_PROVEEDOR":
            case "AJUSTE_ENTRADA":
            case "AJUSTE_SALIDA":
            case "TRASPASO_ENTRADA":
            case "TRASPASO_SALIDA":
            case "CANCELACION_COMPRA":
            case "CANCELACION_VENTA":
                break;

            default:
                throw new Exception(
                        "Tipo de movimiento no válido: "
                        + tipoMovimiento
                );
        }
    }

    /**
     * Valida un ID.
     */
    private void validarId(Long id)
            throws Exception {

        if (id == null || id <= 0) {
            throw new Exception(
                    "El ID debe ser mayor que cero."
            );
        }
    }

    /**
     * Convierte un valor null a cero.
     */
    private BigDecimal valorCeroSiNull(
            BigDecimal valor
    ) {

        return valor != null
                ? valor
                : BigDecimal.ZERO;
    }
}