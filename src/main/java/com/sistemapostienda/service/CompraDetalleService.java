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
 * Servicio para la gestión de detalles de compras.
 *
 * Contiene las reglas de negocio relacionadas con
 * los productos incluidos en una compra.
 */
public class CompraDetalleService {

    private final CompraDAO compraDAO;
    private final CompraDetalleDAO compraDetalleDAO;

    public CompraDetalleService() {
        this.compraDAO = new CompraDAO();
        this.compraDetalleDAO = new CompraDetalleDAO();
    }

    /**
     * Agrega un nuevo detalle a una compra.
     *
     * Solo se permite agregar detalles cuando la compra
     * se encuentra en estado BORRADOR.
     *
     * @param detalle objeto CompraDetalle
     * @return ID generado
     * @throws Exception si la información no es válida
     */
    public Long agregar(CompraDetalle detalle) throws Exception {

        validarDetalle(detalle);

        Compra compra = obtenerCompra(detalle.getCompraId());

        validarCompraEditable(compra);

        return compraDetalleDAO.guardar(detalle);
    }

    /**
     * Busca un detalle por ID.
     *
     * @param id ID del detalle
     * @return detalle encontrado o null
     * @throws Exception si el ID no es válido
     */
    public CompraDetalle buscarPorId(Long id) throws Exception {

        validarId(id);

        return compraDetalleDAO.buscarPorId(id);
    }

    /**
     * Obtiene todos los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return lista de detalles
     * @throws Exception si la compra no existe
     */
    public List<CompraDetalle> listarPorCompra(Long compraId)
            throws Exception {

        validarId(compraId);

        obtenerCompra(compraId);

        return compraDetalleDAO.listarPorCompra(compraId);
    }

    /**
     * Obtiene todos los detalles relacionados con un producto.
     *
     * @param productoId ID del producto
     * @return lista de detalles
     * @throws Exception si el ID no es válido
     */
    public List<CompraDetalle> listarPorProducto(Long productoId)
            throws Exception {

        validarId(productoId);

        return compraDetalleDAO.listarPorProducto(productoId);
    }

    /**
     * Actualiza un detalle.
     *
     * Solo se permite modificar detalles de compras
     * que todavía estén en BORRADOR.
     *
     * @param detalle objeto CompraDetalle
     * @return true si se actualizó
     * @throws Exception si la operación no es válida
     */
    public boolean actualizar(CompraDetalle detalle)
            throws Exception {

        validarDetalle(detalle);

        if (detalle.getId() == null ||
                detalle.getId() <= 0) {

            throw new Exception(
                    "El ID del detalle es obligatorio."
            );
        }

        CompraDetalle actual =
                compraDetalleDAO.buscarPorId(detalle.getId());

        if (actual == null) {
            throw new Exception(
                    "El detalle no existe."
            );
        }

        /*
         * El detalle pertenece a una compra.
         * No permitimos cambiarlo a otra compra
         * durante una actualización.
         */
        if (!actual.getCompraId().equals(detalle.getCompraId())) {
            throw new Exception(
                    "No se puede cambiar la compra del detalle."
            );
        }

        Compra compra =
                obtenerCompra(detalle.getCompraId());

        validarCompraEditable(compra);

        return compraDetalleDAO.actualizar(detalle);
    }

    /**
     * Elimina un detalle.
     *
     * Solo se permite eliminar detalles pertenecientes
     * a compras en BORRADOR.
     *
     * @param id ID del detalle
     * @return true si se eliminó
     * @throws Exception si la operación no es válida
     */
    public boolean eliminar(Long id) throws Exception {

        validarId(id);

        CompraDetalle detalle =
                compraDetalleDAO.buscarPorId(id);

        if (detalle == null) {
            throw new Exception(
                    "El detalle no existe."
            );
        }

        Compra compra =
                obtenerCompra(detalle.getCompraId());

        validarCompraEditable(compra);

        return compraDetalleDAO.eliminar(id);
    }

    /**
     * Elimina todos los detalles de una compra.
     *
     * Solo se permite si la compra está en BORRADOR.
     *
     * @param compraId ID de la compra
     * @return true si la operación se ejecutó
     * @throws Exception si la operación no es válida
     */
    public boolean eliminarPorCompra(Long compraId)
            throws Exception {

        validarId(compraId);

        Compra compra =
                obtenerCompra(compraId);

        validarCompraEditable(compra);

        return compraDetalleDAO.eliminarPorCompra(compraId);
    }

    /**
     * Valida si una compra tiene detalles.
     *
     * @param compraId ID de la compra
     * @return true si tiene al menos un detalle
     * @throws Exception si la compra no existe
     */
    public boolean tieneDetalles(Long compraId)
            throws Exception {

        validarId(compraId);

        obtenerCompra(compraId);

        List<CompraDetalle> detalles =
                compraDetalleDAO.listarPorCompra(compraId);

        return detalles != null && !detalles.isEmpty();
    }

    /**
     * Cuenta los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return cantidad de detalles
     * @throws Exception si la compra no existe
     */
    public int contarDetalles(Long compraId)
            throws Exception {

        validarId(compraId);

        obtenerCompra(compraId);

        List<CompraDetalle> detalles =
                compraDetalleDAO.listarPorCompra(compraId);

        if (detalles == null) {
            return 0;
        }

        return detalles.size();
    }

    /**
     * Calcula la cantidad total de unidades base
     * de una compra.
     *
     * Ejemplo:
     *
     * 3 cajas × factor 24 = 72 unidades.
     *
     * @param compraId ID de la compra
     * @return cantidad total en unidades base
     * @throws Exception si la compra no existe
     */
    public BigDecimal calcularUnidadesBase(Long compraId)
            throws Exception {

        validarId(compraId);

        obtenerCompra(compraId);

        List<CompraDetalle> detalles =
                compraDetalleDAO.listarPorCompra(compraId);

        BigDecimal total =
                BigDecimal.ZERO;

        for (CompraDetalle detalle : detalles) {

            BigDecimal cantidad =
                    detalle.getCantidad();

            BigDecimal factor =
                    detalle.getFactorConversion();

            if (cantidad != null &&
                    factor != null) {

                BigDecimal unidades =
                        cantidad.multiply(factor);

                total = total.add(unidades);
            }
        }

        return total;
    }

    /**
     * Obtiene la compra asociada a un detalle.
     */
    private Compra obtenerCompra(Long compraId)
            throws Exception {

        if (compraId == null ||
                compraId <= 0) {

            throw new Exception(
                    "El ID de la compra es obligatorio."
            );
        }

        Compra compra =
                compraDAO.buscarPorId(compraId);

        if (compra == null) {
            throw new Exception(
                    "La compra no existe."
            );
        }

        return compra;
    }

    /**
     * Valida que la compra pueda ser modificada.
     */
    private void validarCompraEditable(Compra compra)
            throws Exception {

        if (!"BORRADOR".equals(compra.getEstado())) {

            throw new Exception(
                    "Los detalles solo pueden modificarse "
                    + "mientras la compra esté en BORRADOR."
            );
        }
    }

    /**
     * Valida la información del detalle.
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
                "El descuento no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getImporteImpuesto(),
                "El importe de impuesto no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getSubtotal(),
                "El subtotal no puede ser negativo."
        );

        validarNoNegativo(
                detalle.getTotal(),
                "El total no puede ser negativo."
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
     * Valida que un valor no sea negativo.
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
}
