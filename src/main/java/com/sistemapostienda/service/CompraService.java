/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.services;

import com.sistemapostienda.dao.CompraDAO;
import com.sistemapostienda.dao.CompraDetalleDAO;
import com.sistemapostienda.model.Compra;
import com.sistemapostienda.model.CompraDetalle;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio de compras.
 *
 * Contiene las reglas de negocio relacionadas con las compras.
 */
public class CompraService {

    private final CompraDAO compraDAO;
    private final CompraDetalleDAO compraDetalleDAO;

    public CompraService() {
        this.compraDAO = new CompraDAO();
        this.compraDetalleDAO = new CompraDetalleDAO();
    }

    /**
     * Registra una nueva compra.
     *
     * La compra se crea inicialmente como BORRADOR.
     *
     * @param compra objeto Compra
     * @return ID de la compra creada
     * @throws Exception si la información no es válida
     */
    public Long crearCompra(Compra compra) throws Exception {

        validarCompra(compra);

        if (compra.getEstado() == null ||
                compra.getEstado().isBlank()) {

            compra.setEstado("BORRADOR");
        }

        validarEstadoNuevo(compra.getEstado());

        Compra existente =
                compraDAO.buscarPorFolio(compra.getFolio());

        if (existente != null) {
            throw new Exception(
                    "Ya existe una compra con el folio: "
                    + compra.getFolio()
            );
        }

        return compraDAO.guardar(compra);
    }

    /**
     * Busca una compra por ID.
     */
    public Compra buscarPorId(Long id) throws Exception {

        validarId(id);

        return compraDAO.buscarPorId(id);
    }

    /**
     * Busca una compra por folio.
     */
    public Compra buscarPorFolio(String folio) throws Exception {

        if (folio == null || folio.isBlank()) {
            throw new Exception("El folio es obligatorio.");
        }

        return compraDAO.buscarPorFolio(folio);
    }

    /**
     * Lista todas las compras.
     */
    public List<Compra> listar() throws Exception {
        return compraDAO.listar();
    }

    /**
     * Lista compras de una sucursal.
     */
    public List<Compra> listarPorSucursal(Long sucursalId)
            throws Exception {

        validarId(sucursalId);

        return compraDAO.listarPorSucursal(sucursalId);
    }

    /**
     * Lista compras de un proveedor.
     */
    public List<Compra> listarPorProveedor(Long proveedorId)
            throws Exception {

        validarId(proveedorId);

        return compraDAO.listarPorProveedor(proveedorId);
    }

    /**
     * Obtiene todos los detalles de una compra.
     */
    public List<CompraDetalle> listarDetalles(Long compraId)
            throws Exception {

        validarId(compraId);

        Compra compra = compraDAO.buscarPorId(compraId);

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        return compraDetalleDAO.listarPorCompra(compraId);
    }

    /**
     * Agrega un detalle a una compra.
     *
     * Solamente se permite modificar una compra en BORRADOR.
     */
    public Long agregarDetalle(CompraDetalle detalle)
            throws Exception {

        validarDetalle(detalle);

        if (detalle.getCompraId() == null) {
            throw new Exception(
                    "El detalle debe pertenecer a una compra."
            );
        }

        Compra compra =
                compraDAO.buscarPorId(detalle.getCompraId());

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "Solo se pueden agregar detalles a compras en BORRADOR."
            );
        }

        return compraDetalleDAO.guardar(detalle);
    }

    /**
     * Actualiza una compra.
     *
     * Solo se permite modificar una compra en BORRADOR.
     */
    public boolean actualizar(Compra compra)
            throws Exception {

        validarCompra(compra);

        if (compra.getId() == null) {
            throw new Exception(
                    "El ID de la compra es obligatorio."
            );
        }

        Compra actual =
                compraDAO.buscarPorId(compra.getId());

        if (actual == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if (!"BORRADOR".equals(actual.getEstado())) {
            throw new Exception(
                    "Solo se pueden modificar compras en BORRADOR."
            );
        }

        Compra existente =
                compraDAO.buscarPorFolio(compra.getFolio());

        if (existente != null &&
                !existente.getId().equals(compra.getId())) {

            throw new Exception(
                    "Ya existe otra compra con el folio: "
                    + compra.getFolio()
            );
        }

        compra.setEstado("BORRADOR");

        return compraDAO.actualizar(compra);
    }

    /**
     * Actualiza un detalle de compra.
     *
     * Solo se permite modificar detalles de una compra en BORRADOR.
     */
    public boolean actualizarDetalle(CompraDetalle detalle)
            throws Exception {

        validarDetalle(detalle);

        if (detalle.getId() == null) {
            throw new Exception(
                    "El ID del detalle es obligatorio."
            );
        }

        if (detalle.getCompraId() == null) {
            throw new Exception(
                    "El detalle debe tener una compra."
            );
        }

        Compra compra =
                compraDAO.buscarPorId(detalle.getCompraId());

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "Solo se pueden modificar detalles de compras en BORRADOR."
            );
        }

        return compraDetalleDAO.actualizar(detalle);
    }

    /**
     * Elimina un detalle de compra.
     *
     * Solo se permite eliminar detalles de una compra BORRADOR.
     */
    public boolean eliminarDetalle(Long detalleId)
            throws Exception {

        validarId(detalleId);

        CompraDetalle detalle =
                compraDetalleDAO.buscarPorId(detalleId);

        if (detalle == null) {
            throw new Exception(
                    "El detalle no existe."
            );
        }

        Compra compra =
                compraDAO.buscarPorId(detalle.getCompraId());

        if (compra == null) {
            throw new Exception(
                    "La compra asociada no existe."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "Solo se pueden eliminar detalles de compras en BORRADOR."
            );
        }

        return compraDetalleDAO.eliminar(detalleId);
    }

    /**
     * Confirma una compra.
     *
     * IMPORTANTE:
     * En esta etapa solamente cambia el estado.
     *
     * Posteriormente aquí integraremos:
     *
     * Compra
     *   ↓
     * Inventario
     *   ↓
     * Kardex
     *
     * Todo deberá ejecutarse dentro de una transacción.
     */
    public boolean confirmarCompra(Long compraId)
            throws Exception {

        validarId(compraId);

        Compra compra =
                compraDAO.buscarPorId(compraId);

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "Solo se pueden confirmar compras en BORRADOR."
            );
        }

        List<CompraDetalle> detalles =
                compraDetalleDAO.listarPorCompra(compraId);

        if (detalles == null || detalles.isEmpty()) {
            throw new Exception(
                    "No se puede confirmar una compra sin detalles."
            );
        }

        for (CompraDetalle detalle : detalles) {
            validarDetalle(detalle);
        }

        return compraDAO.actualizarEstado(
                compraId,
                "CONFIRMADA"
        );
    }

    /**
     * Cancela una compra.
     *
     * Una compra confirmada no debe cancelarse directamente
     * hasta que integremos la reversión de inventario y Kardex.
     */
    public boolean cancelarCompra(Long compraId)
            throws Exception {

        validarId(compraId);

        Compra compra =
                compraDAO.buscarPorId(compraId);

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if ("CANCELADA".equals(compra.getEstado())) {
            throw new Exception(
                    "La compra ya está cancelada."
            );
        }

        if ("CONFIRMADA".equals(compra.getEstado())) {
            throw new Exception(
                    "Una compra CONFIRMADA no puede cancelarse todavía. "
                + "Primero debe implementarse la reversión de inventario y Kardex."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "La compra no puede cancelarse en su estado actual."
            );
        }

        return compraDAO.actualizarEstado(
                compraId,
                "CANCELADA"
        );
    }

    /**
     * Elimina una compra.
     *
     * Solamente se permite eliminar una compra BORRADOR.
     */
    public boolean eliminar(Long compraId)
            throws Exception {

        validarId(compraId);

        Compra compra =
                compraDAO.buscarPorId(compraId);

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        if (!"BORRADOR".equals(compra.getEstado())) {
            throw new Exception(
                    "Solo se pueden eliminar compras en BORRADOR."
            );
        }

        return compraDAO.eliminar(compraId);
    }

    /**
     * Valida la información básica de una compra.
     */
    private void validarCompra(Compra compra)
            throws Exception {

        if (compra == null) {
            throw new Exception(
                    "La compra no puede ser nula."
            );
        }

        if (compra.getSucursalId() == null ||
                compra.getSucursalId() <= 0) {

            throw new Exception(
                    "La sucursal es obligatoria."
            );
        }

        if (compra.getProveedorId() == null ||
                compra.getProveedorId() <= 0) {

            throw new Exception(
                    "El proveedor es obligatorio."
            );
        }

        if (compra.getUsuarioId() == null ||
                compra.getUsuarioId() <= 0) {

            throw new Exception(
                    "El usuario es obligatorio."
            );
        }

        if (compra.getFolio() == null ||
                compra.getFolio().isBlank()) {

            throw new Exception(
                    "El folio es obligatorio."
            );
        }

        if (compra.getTipoCompra() == null ||
                compra.getTipoCompra().isBlank()) {

            throw new Exception(
                    "El tipo de compra es obligatorio."
            );
        }

        if (!"CONTADO".equals(compra.getTipoCompra()) &&
                !"CREDITO".equals(compra.getTipoCompra())) {

            throw new Exception(
                    "El tipo de compra debe ser CONTADO o CREDITO."
            );
        }

        validarImportes(compra);
    }

    /**
     * Valida los importes de la compra.
     */
    private void validarImportes(Compra compra)
            throws Exception {

        validarNoNegativo(
                compra.getSubtotal(),
                "El subtotal no puede ser negativo."
        );

        validarNoNegativo(
                compra.getDescuento(),
                "El descuento no puede ser negativo."
        );

        validarNoNegativo(
                compra.getImpuesto(),
                "El impuesto no puede ser negativo."
        );

        validarNoNegativo(
                compra.getTotal(),
                "El total no puede ser negativo."
        );
    }

    /**
     * Valida un detalle de compra.
     */
    private void validarDetalle(CompraDetalle detalle)
            throws Exception {

        if (detalle == null) {
            throw new Exception(
                    "El detalle no puede ser nulo."
            );
        }

        if (detalle.getCompraId() == null ||
                detalle.getCompraId() <= 0) {

            throw new Exception(
                    "La compra es obligatoria."
            );
        }

        if (detalle.getProductoId() == null ||
                detalle.getProductoId() <= 0) {

            throw new Exception(
                    "El producto es obligatorio."
            );
        }

        if (detalle.getPresentacionId() == null ||
                detalle.getPresentacionId() <= 0) {

            throw new Exception(
                    "La presentación es obligatoria."
            );
        }

        if (detalle.getCantidad() == null ||
                detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {

            throw new Exception(
                    "La cantidad debe ser mayor que cero."
            );
        }

        if (detalle.getFactorConversion() == null ||
                detalle.getFactorConversion().compareTo(BigDecimal.ZERO) <= 0) {

            throw new Exception(
                    "El factor de conversión debe ser mayor que cero."
            );
        }

        if (detalle.getCostoUnitario() == null ||
                detalle.getCostoUnitario().compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "El costo unitario no puede ser negativo."
            );
        }

        validarNoNegativo(
                detalle.getDescuento(),
                "El descuento del detalle no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getImporteImpuesto(),
                "El importe de impuesto no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getSubtotal(),
                "El subtotal del detalle no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getTotal(),
                "El total del detalle no puede ser negativo."
        );

        if (detalle.getPorcentajeImpuesto() == null) {
            detalle.setPorcentajeImpuesto(BigDecimal.ZERO);
        }

        if (detalle.getDescuento() == null) {
            detalle.setDescuento(BigDecimal.ZERO);
        }

        if (detalle.getImporteImpuesto() == null) {
            detalle.setImporteImpuesto(BigDecimal.ZERO);
        }

        if (detalle.getSubtotal() == null) {
            detalle.setSubtotal(BigDecimal.ZERO);
        }

        if (detalle.getTotal() == null) {
            detalle.setTotal(BigDecimal.ZERO);
        }
    }

    /**
     * Valida que un valor monetario no sea negativo.
     */
    private void validarNoNegativo(
            BigDecimal valor,
            String mensaje
    ) throws Exception {

        if (valor != null &&
                valor.compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(mensaje);
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
     * Valida el estado inicial de una compra.
     */
    private void validarEstadoNuevo(String estado)
            throws Exception {

        if (!"BORRADOR".equals(estado) &&
                !"CONFIRMADA".equals(estado) &&
                !"CANCELADA".equals(estado)) {

            throw new Exception(
                    "Estado de compra no válido."
            );
        }
    }
}
