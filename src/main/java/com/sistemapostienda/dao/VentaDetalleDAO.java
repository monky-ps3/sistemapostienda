/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

/**
 *
 * @author monky
 */


import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.VentaDetalle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VentaDetalleDAO {

    public VentaDetalle guardar(VentaDetalle detalle) throws SQLException {
        String sql = """
            INSERT INTO ventas_detalle (
                venta_id,
                producto_id,
                presentacion_id,
                inventario_id,
                nombre_producto,
                nombre_presentacion,
                cantidad,
                factor_conversion,
                precio_unitario,
                costo_unitario,
                descuento,
                porcentaje_impuesto,
                importe_impuesto,
                subtotal,
                total,
                utilidad,
                lote,
                fecha_caducidad
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, detalle.getVentaId());
            statement.setLong(2, detalle.getProductoId());
            statement.setLong(3, detalle.getPresentacionId());

            if (detalle.getInventarioId() != null) {
                statement.setLong(4, detalle.getInventarioId());
            } else {
                statement.setNull(4, java.sql.Types.BIGINT);
            }

            statement.setString(5, detalle.getNombreProducto());
            statement.setString(6, detalle.getNombrePresentacion());
            statement.setBigDecimal(7, detalle.getCantidad());
            statement.setBigDecimal(8, detalle.getFactorConversion());
            statement.setBigDecimal(9, detalle.getPrecioUnitario());
            statement.setBigDecimal(10, detalle.getCostoUnitario());
            statement.setBigDecimal(11, detalle.getDescuento());
            statement.setBigDecimal(12, detalle.getPorcentajeImpuesto());
            statement.setBigDecimal(13, detalle.getImporteImpuesto());
            statement.setBigDecimal(14, detalle.getSubtotal());
            statement.setBigDecimal(15, detalle.getTotal());
            statement.setBigDecimal(16, detalle.getUtilidad());
            statement.setString(17, detalle.getLote());

            if (detalle.getFechaCaducidad() != null) {
                statement.setDate(
                        18, Date.valueOf(detalle.getFechaCaducidad()));
            } else {
                statement.setNull(18, java.sql.Types.DATE);
            }

            int filas = statement.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo guardar el detalle de la venta.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    detalle.setId(keys.getLong(1));
                } else {
                    throw new SQLException(
                            "No se obtuvo el ID del detalle de venta.");
                }
            }
        }

        return buscarPorId(detalle.getId());
    }

    public VentaDetalle buscarPorId(Long id) throws SQLException {
        String sql = """
            SELECT *
            FROM ventas_detalle
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearVentaDetalle(rs);
                }
            }
        }

        return null;
    }

    public List<VentaDetalle> listar() throws SQLException {
        String sql = """
            SELECT *
            FROM ventas_detalle
            ORDER BY id DESC
            """;

        List<VentaDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                detalles.add(mapearVentaDetalle(rs));
            }
        }

        return detalles;
    }

    public List<VentaDetalle> listarPorVenta(Long ventaId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM ventas_detalle
            WHERE venta_id = ?
            ORDER BY id ASC
            """;

        List<VentaDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, ventaId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapearVentaDetalle(rs));
                }
            }
        }

        return detalles;
    }

    public List<VentaDetalle> listarPorProducto(Long productoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM ventas_detalle
            WHERE producto_id = ?
            ORDER BY created_at DESC, id DESC
            """;

        List<VentaDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, productoId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapearVentaDetalle(rs));
                }
            }
        }

        return detalles;
    }

    public List<VentaDetalle> listarPorInventario(Long inventarioId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM ventas_detalle
            WHERE inventario_id = ?
            ORDER BY created_at DESC, id DESC
            """;

        List<VentaDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, inventarioId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapearVentaDetalle(rs));
                }
            }
        }

        return detalles;
    }

    public boolean actualizar(VentaDetalle detalle) throws SQLException {
        String sql = """
            UPDATE ventas_detalle
            SET producto_id = ?,
                presentacion_id = ?,
                inventario_id = ?,
                nombre_producto = ?,
                nombre_presentacion = ?,
                cantidad = ?,
                factor_conversion = ?,
                precio_unitario = ?,
                costo_unitario = ?,
                descuento = ?,
                porcentaje_impuesto = ?,
                importe_impuesto = ?,
                subtotal = ?,
                total = ?,
                utilidad = ?,
                lote = ?,
                fecha_caducidad = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, detalle.getProductoId());
            statement.setLong(2, detalle.getPresentacionId());

            if (detalle.getInventarioId() != null) {
                statement.setLong(3, detalle.getInventarioId());
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }

            statement.setString(4, detalle.getNombreProducto());
            statement.setString(5, detalle.getNombrePresentacion());
            statement.setBigDecimal(6, detalle.getCantidad());
            statement.setBigDecimal(7, detalle.getFactorConversion());
            statement.setBigDecimal(8, detalle.getPrecioUnitario());
            statement.setBigDecimal(9, detalle.getCostoUnitario());
            statement.setBigDecimal(10, detalle.getDescuento());
            statement.setBigDecimal(11, detalle.getPorcentajeImpuesto());
            statement.setBigDecimal(12, detalle.getImporteImpuesto());
            statement.setBigDecimal(13, detalle.getSubtotal());
            statement.setBigDecimal(14, detalle.getTotal());
            statement.setBigDecimal(15, detalle.getUtilidad());
            statement.setString(16, detalle.getLote());

            if (detalle.getFechaCaducidad() != null) {
                statement.setDate(
                        17, Date.valueOf(detalle.getFechaCaducidad()));
            } else {
                statement.setNull(17, java.sql.Types.DATE);
            }

            statement.setLong(18, detalle.getId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(Long id) throws SQLException {
        String sql = """
            DELETE FROM ventas_detalle
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminarPorVenta(Long ventaId) throws SQLException {
        String sql = """
            DELETE FROM ventas_detalle
            WHERE venta_id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, ventaId);
            return statement.executeUpdate() > 0;
        }
    }

    private VentaDetalle mapearVentaDetalle(ResultSet rs)
            throws SQLException {

        VentaDetalle detalle = new VentaDetalle();

        detalle.setId(rs.getLong("id"));
        detalle.setVentaId(rs.getLong("venta_id"));
        detalle.setProductoId(rs.getLong("producto_id"));
        detalle.setPresentacionId(rs.getLong("presentacion_id"));

        long inventarioId = rs.getLong("inventario_id");
        detalle.setInventarioId(
                rs.wasNull() ? null : inventarioId);

        detalle.setNombreProducto(rs.getString("nombre_producto"));
        detalle.setNombrePresentacion(
                rs.getString("nombre_presentacion"));

        detalle.setCantidad(rs.getBigDecimal("cantidad"));
        detalle.setFactorConversion(
                rs.getBigDecimal("factor_conversion"));
        detalle.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        detalle.setCostoUnitario(rs.getBigDecimal("costo_unitario"));
        detalle.setDescuento(rs.getBigDecimal("descuento"));
        detalle.setPorcentajeImpuesto(
                rs.getBigDecimal("porcentaje_impuesto"));
        detalle.setImporteImpuesto(rs.getBigDecimal("importe_impuesto"));
        detalle.setSubtotal(rs.getBigDecimal("subtotal"));
        detalle.setTotal(rs.getBigDecimal("total"));
        detalle.setUtilidad(rs.getBigDecimal("utilidad"));
        detalle.setLote(rs.getString("lote"));

        Date fechaCaducidad = rs.getDate("fecha_caducidad");
        if (fechaCaducidad != null) {
            detalle.setFechaCaducidad(fechaCaducidad.toLocalDate());
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            detalle.setCreatedAt(createdAt.toLocalDateTime());
        }

        return detalle;
    }
}
