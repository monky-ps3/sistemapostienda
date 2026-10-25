/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */



import com.sistemapostienda.dao.VentaDetalleDAO;
import com.sistemapostienda.model.VentaDetalle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class VentaDetalleService {

    private final VentaDetalleDAO ventaDetalleDAO;

    public VentaDetalleService() {
        this.ventaDetalleDAO = new VentaDetalleDAO();
    }

    public VentaDetalle guardar(VentaDetalle detalle) throws SQLException {
        validarDatos(detalle);
        calcularImportes(detalle);

        return ventaDetalleDAO.guardar(detalle);
    }

    public VentaDetalle buscarPorId(Long id) throws SQLException {
        validarId(id);
        return ventaDetalleDAO.buscarPorId(id);
    }

    public List<VentaDetalle> listar() throws SQLException {
        return ventaDetalleDAO.listar();
    }

    public List<VentaDetalle> listarPorVenta(Long ventaId)
            throws SQLException {

        validarId(ventaId);
        return ventaDetalleDAO.listarPorVenta(ventaId);
    }

    public List<VentaDetalle> listarPorProducto(Long productoId)
            throws SQLException {

        validarId(productoId);
        return ventaDetalleDAO.listarPorProducto(productoId);
    }

    public List<VentaDetalle> listarPorInventario(Long inventarioId)
            throws SQLException {

        validarId(inventarioId);
        return ventaDetalleDAO.listarPorInventario(inventarioId);
    }

    public boolean actualizar(VentaDetalle detalle) throws SQLException {
        validarDatos(detalle);
        validarId(detalle.getId());
        calcularImportes(detalle);

        return ventaDetalleDAO.actualizar(detalle);
    }

    public boolean eliminar(Long id) throws SQLException {
        validarId(id);
        return ventaDetalleDAO.eliminar(id);
    }

    public boolean eliminarPorVenta(Long ventaId) throws SQLException {
        validarId(ventaId);
        return ventaDetalleDAO.eliminarPorVenta(ventaId);
    }

    public BigDecimal calcularUnidadesBase(VentaDetalle detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException(
                    "El detalle de venta es obligatorio.");
        }

        validarPositivo(detalle.getCantidad(), "La cantidad");
        validarPositivo(
                detalle.getFactorConversion(),
                "El factor de conversión");

        return detalle.getCantidad()
                .multiply(detalle.getFactorConversion())
                .setScale(3, RoundingMode.HALF_UP);
    }

    public void calcularImportes(VentaDetalle detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException(
                    "El detalle de venta es obligatorio.");
        }

        validarPositivo(detalle.getCantidad(), "La cantidad");
        validarPositivo(
                detalle.getPrecioUnitario(), "El precio unitario");

        BigDecimal subtotal = detalle.getCantidad()
                .multiply(detalle.getPrecioUnitario())
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal descuento = valorOZero(detalle.getDescuento());
        BigDecimal porcentajeImpuesto =
                valorOZero(detalle.getPorcentajeImpuesto());

        if (descuento.signum() < 0
                || descuento.compareTo(subtotal) > 0) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre cero y el subtotal.");
        }

        if (porcentajeImpuesto.signum() < 0
                || porcentajeImpuesto.compareTo(
                        new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException(
                    "El porcentaje de impuesto debe estar entre 0 y 100.");
        }

        BigDecimal baseImponible = subtotal.subtract(descuento);

        BigDecimal importeImpuesto = baseImponible
                .multiply(porcentajeImpuesto)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal total = baseImponible.add(importeImpuesto);

        BigDecimal costoUnitario =
                valorOZero(detalle.getCostoUnitario());

        if (costoUnitario.signum() < 0) {
            throw new IllegalArgumentException(
                    "El costo unitario no puede ser negativo.");
        }

        BigDecimal costoTotal = detalle.getCantidad()
                .multiply(costoUnitario)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal utilidad = baseImponible.subtract(costoTotal);

        detalle.setSubtotal(subtotal);
        detalle.setDescuento(descuento);
        detalle.setPorcentajeImpuesto(porcentajeImpuesto);
        detalle.setImporteImpuesto(importeImpuesto);
        detalle.setTotal(total);
        detalle.setUtilidad(utilidad);
    }

    private void validarDatos(VentaDetalle detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException(
                    "El detalle de venta es obligatorio.");
        }

        validarId(detalle.getVentaId());
        validarId(detalle.getProductoId());
        validarId(detalle.getPresentacionId());

        if (detalle.getInventarioId() != null) {
            validarId(detalle.getInventarioId());
        }

        if (detalle.getNombreProducto() == null
                || detalle.getNombreProducto().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio.");
        }

        if (detalle.getNombreProducto().trim().length() > 200) {
            throw new IllegalArgumentException(
                    "El nombre del producto no puede superar 200 caracteres.");
        }

        if (detalle.getNombrePresentacion() == null
                || detalle.getNombrePresentacion().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la presentación es obligatorio.");
        }

        if (detalle.getNombrePresentacion().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre de la presentación no puede superar "
                    + "100 caracteres.");
        }

        validarPositivo(detalle.getCantidad(), "La cantidad");
        validarPositivo(
                detalle.getFactorConversion(),
                "El factor de conversión");
        validarPositivo(
                detalle.getPrecioUnitario(),
                "El precio unitario");

        if (detalle.getLote() != null
                && detalle.getLote().length() > 100) {
            throw new IllegalArgumentException(
                    "El lote no puede superar 100 caracteres.");
        }
    }

    private void validarPositivo(BigDecimal valor, String campo) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException(
                    campo + " debe ser mayor que cero.");
        }
    }

    private BigDecimal valorOZero(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser mayor que cero.");
        }
    }
}
