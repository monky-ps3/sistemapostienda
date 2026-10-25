/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.CompraDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDetalleDAO {

    /**
     * Guarda un detalle de compra.
     *
     * @param detalle objeto CompraDetalle
     * @return ID generado
     * @throws SQLException si ocurre un error en la base de datos
     */
    public Long guardar(CompraDetalle detalle) throws SQLException {

        String sql = """
                INSERT INTO compras_detalle (
                    compra_id,
                    producto_id,
                    presentacion_id,
                    cantidad,
                    factor_conversion,
                    costo_unitario,
                    descuento,
                    porcentaje_impuesto,
                    importe_impuesto,
                    subtotal,
                    total,
                    lote,
                    fecha_caducidad
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setLong(1, detalle.getCompraId());
            statement.setLong(2, detalle.getProductoId());
            statement.setLong(3, detalle.getPresentacionId());
            statement.setBigDecimal(4, detalle.getCantidad());
            statement.setBigDecimal(5, detalle.getFactorConversion());
            statement.setBigDecimal(6, detalle.getCostoUnitario());
            statement.setBigDecimal(7, detalle.getDescuento());
            statement.setBigDecimal(8, detalle.getPorcentajeImpuesto());
            statement.setBigDecimal(9, detalle.getImporteImpuesto());
            statement.setBigDecimal(10, detalle.getSubtotal());
            statement.setBigDecimal(11, detalle.getTotal());
            statement.setString(12, detalle.getLote());

            if (detalle.getFechaCaducidad() != null) {
                statement.setDate(
                        13,
                        Date.valueOf(detalle.getFechaCaducidad())
                );
            } else {
                statement.setDate(13, null);
            }

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {

                if (rs.next()) {
                    Long id = rs.getLong(1);
                    detalle.setId(id);
                    return id;
                }
            }
        }

        return null;
    }

    /**
     * Busca un detalle por ID.
     *
     * @param id ID del detalle
     * @return detalle encontrado o null
     * @throws SQLException si ocurre un error
     */
    public CompraDetalle buscarPorId(Long id) throws SQLException {

        String sql = """
                SELECT
                    id,
                    compra_id,
                    producto_id,
                    presentacion_id,
                    cantidad,
                    factor_conversion,
                    costo_unitario,
                    descuento,
                    porcentaje_impuesto,
                    importe_impuesto,
                    subtotal,
                    total,
                    lote,
                    fecha_caducidad,
                    created_at
                FROM compras_detalle
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearDetalle(rs);
                }
            }
        }

        return null;
    }

    /**
     * Obtiene todos los detalles de una compra.
     *
     * @param compraId ID de la compra
     * @return lista de detalles
     * @throws SQLException si ocurre un error
     */
    public List<CompraDetalle> listarPorCompra(Long compraId)
            throws SQLException {

        String sql = """
                SELECT
                    id,
                    compra_id,
                    producto_id,
                    presentacion_id,
                    cantidad,
                    factor_conversion,
                    costo_unitario,
                    descuento,
                    porcentaje_impuesto,
                    importe_impuesto,
                    subtotal,
                    total,
                    lote,
                    fecha_caducidad,
                    created_at
                FROM compras_detalle
                WHERE compra_id = ?
                ORDER BY id ASC
                """;

        List<CompraDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, compraId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    detalles.add(mapearDetalle(rs));
                }
            }
        }

        return detalles;
    }

    /**
     * Obtiene los detalles de un producto.
     *
     * @param productoId ID del producto
     * @return lista de detalles
     * @throws SQLException si ocurre un error
     */
    public List<CompraDetalle> listarPorProducto(Long productoId)
            throws SQLException {

        String sql = """
                SELECT
                    id,
                    compra_id,
                    producto_id,
                    presentacion_id,
                    cantidad,
                    factor_conversion,
                    costo_unitario,
                    descuento,
                    porcentaje_impuesto,
                    importe_impuesto,
                    subtotal,
                    total,
                    lote,
                    fecha_caducidad,
                    created_at
                FROM compras_detalle
                WHERE producto_id = ?
                ORDER BY id DESC
                """;

        List<CompraDetalle> detalles = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, productoId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    detalles.add(mapearDetalle(rs));
                }
            }
        }

        return detalles;
    }

    /**
     * Actualiza un detalle de compra.
     *
     * @param detalle objeto CompraDetalle
     * @return true si se actualizó
     * @throws SQLException si ocurre un error
     */
    public boolean actualizar(CompraDetalle detalle) throws SQLException {

        String sql = """
                UPDATE compras_detalle SET
                    producto_id = ?,
                    presentacion_id = ?,
                    cantidad = ?,
                    factor_conversion = ?,
                    costo_unitario = ?,
                    descuento = ?,
                    porcentaje_impuesto = ?,
                    importe_impuesto = ?,
                    subtotal = ?,
                    total = ?,
                    lote = ?,
                    fecha_caducidad = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, detalle.getProductoId());
            statement.setLong(2, detalle.getPresentacionId());
            statement.setBigDecimal(3, detalle.getCantidad());
            statement.setBigDecimal(4, detalle.getFactorConversion());
            statement.setBigDecimal(5, detalle.getCostoUnitario());
            statement.setBigDecimal(6, detalle.getDescuento());
            statement.setBigDecimal(7, detalle.getPorcentajeImpuesto());
            statement.setBigDecimal(8, detalle.getImporteImpuesto());
            statement.setBigDecimal(9, detalle.getSubtotal());
            statement.setBigDecimal(10, detalle.getTotal());
            statement.setString(11, detalle.getLote());

            if (detalle.getFechaCaducidad() != null) {
                statement.setDate(
                        12,
                        Date.valueOf(detalle.getFechaCaducidad())
                );
            } else {
                statement.setDate(12, null);
            }

            statement.setLong(13, detalle.getId());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un detalle de compra.
     *
     * @param id ID del detalle
     * @return true si se eliminó
     * @throws SQLException si ocurre un error
     */
    public boolean eliminar(Long id) throws SQLException {

        String sql = """
                DELETE FROM compras_detalle
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina todos los detalles pertenecientes a una compra.
     *
     * @param compraId ID de la compra
     * @return true si se ejecutó correctamente
     * @throws SQLException si ocurre un error
     */
    public boolean eliminarPorCompra(Long compraId) throws SQLException {

        String sql = """
                DELETE FROM compras_detalle
                WHERE compra_id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, compraId);

            statement.executeUpdate();

            return true;
        }
    }

    /**
     * Convierte un ResultSet en un objeto CompraDetalle.
     */
    private CompraDetalle mapearDetalle(ResultSet rs) throws SQLException {

        CompraDetalle detalle = new CompraDetalle();

        detalle.setId(rs.getLong("id"));
        detalle.setCompraId(rs.getLong("compra_id"));
        detalle.setProductoId(rs.getLong("producto_id"));
        detalle.setPresentacionId(rs.getLong("presentacion_id"));
        detalle.setCantidad(rs.getBigDecimal("cantidad"));
        detalle.setFactorConversion(
                rs.getBigDecimal("factor_conversion")
        );
        detalle.setCostoUnitario(
                rs.getBigDecimal("costo_unitario")
        );
        detalle.setDescuento(
                rs.getBigDecimal("descuento")
        );
        detalle.setPorcentajeImpuesto(
                rs.getBigDecimal("porcentaje_impuesto")
        );
        detalle.setImporteImpuesto(
                rs.getBigDecimal("importe_impuesto")
        );
        detalle.setSubtotal(
                rs.getBigDecimal("subtotal")
        );
        detalle.setTotal(
                rs.getBigDecimal("total")
        );
        detalle.setLote(
                rs.getString("lote")
        );

        Date fechaCaducidad = rs.getDate("fecha_caducidad");

        if (fechaCaducidad != null) {
            detalle.setFechaCaducidad(
                    fechaCaducidad.toLocalDate()
            );
        }

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            detalle.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return detalle;
    }
}