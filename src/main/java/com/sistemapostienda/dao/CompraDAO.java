/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    /**
     * Guarda una nueva compra.
     *
     * @param compra objeto Compra
     * @return ID generado
     * @throws SQLException si ocurre un error en la base de datos
     */
    public Long guardar(Compra compra) throws SQLException {

        String sql = """
                INSERT INTO compras (
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            statement.setLong(1, compra.getSucursalId());
            statement.setLong(2, compra.getProveedorId());
            statement.setLong(3, compra.getUsuarioId());
            statement.setString(4, compra.getFolio());
            statement.setString(5, compra.getFactura());
            statement.setString(6, compra.getReferencia());

            if (compra.getFechaCompra() != null) {
                statement.setTimestamp(
                        7,
                        Timestamp.valueOf(compra.getFechaCompra())
                );
            } else {
                statement.setTimestamp(7, null);
            }

            statement.setString(8, compra.getTipoCompra());
            statement.setBigDecimal(9, compra.getSubtotal());
            statement.setBigDecimal(10, compra.getDescuento());
            statement.setBigDecimal(11, compra.getImpuesto());
            statement.setBigDecimal(12, compra.getTotal());
            statement.setString(13, compra.getObservaciones());
            statement.setString(14, compra.getEstado());

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {

                if (rs.next()) {
                    Long id = rs.getLong(1);
                    compra.setId(id);
                    return id;
                }
            }
        }

        return null;
    }

    /**
     * Busca una compra por ID.
     *
     * @param id ID de la compra
     * @return Compra encontrada o null
     * @throws SQLException si ocurre un error
     */
    public Compra buscarPorId(Long id) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado,
                    created_at,
                    updated_at
                FROM compras
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearCompra(rs);
                }
            }
        }

        return null;
    }

    /**
     * Busca una compra por folio.
     *
     * @param folio folio de la compra
     * @return Compra encontrada o null
     * @throws SQLException si ocurre un error
     */
    public Compra buscarPorFolio(String folio) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado,
                    created_at,
                    updated_at
                FROM compras
                WHERE folio = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, folio);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearCompra(rs);
                }
            }
        }

        return null;
    }

    /**
     * Obtiene todas las compras.
     *
     * @return lista de compras
     * @throws SQLException si ocurre un error
     */
    public List<Compra> listar() throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado,
                    created_at,
                    updated_at
                FROM compras
                ORDER BY fecha_compra DESC, id DESC
                """;

        List<Compra> compras = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                compras.add(mapearCompra(rs));
            }
        }

        return compras;
    }

    /**
     * Obtiene compras de una sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de compras
     * @throws SQLException si ocurre un error
     */
    public List<Compra> listarPorSucursal(Long sucursalId) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado,
                    created_at,
                    updated_at
                FROM compras
                WHERE sucursal_id = ?
                ORDER BY fecha_compra DESC, id DESC
                """;

        List<Compra> compras = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    compras.add(mapearCompra(rs));
                }
            }
        }

        return compras;
    }

    /**
     * Obtiene compras de un proveedor.
     *
     * @param proveedorId ID del proveedor
     * @return lista de compras
     * @throws SQLException si ocurre un error
     */
    public List<Compra> listarPorProveedor(Long proveedorId) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    proveedor_id,
                    usuario_id,
                    folio,
                    factura,
                    referencia,
                    fecha_compra,
                    tipo_compra,
                    subtotal,
                    descuento,
                    impuesto,
                    total,
                    observaciones,
                    estado,
                    created_at,
                    updated_at
                FROM compras
                WHERE proveedor_id = ?
                ORDER BY fecha_compra DESC, id DESC
                """;

        List<Compra> compras = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, proveedorId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    compras.add(mapearCompra(rs));
                }
            }
        }

        return compras;
    }

    /**
     * Actualiza una compra.
     *
     * @param compra objeto Compra
     * @return true si se actualizó
     * @throws SQLException si ocurre un error
     */
    public boolean actualizar(Compra compra) throws SQLException {

        String sql = """
                UPDATE compras SET
                    sucursal_id = ?,
                    proveedor_id = ?,
                    usuario_id = ?,
                    folio = ?,
                    factura = ?,
                    referencia = ?,
                    fecha_compra = ?,
                    tipo_compra = ?,
                    subtotal = ?,
                    descuento = ?,
                    impuesto = ?,
                    total = ?,
                    observaciones = ?,
                    estado = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, compra.getSucursalId());
            statement.setLong(2, compra.getProveedorId());
            statement.setLong(3, compra.getUsuarioId());
            statement.setString(4, compra.getFolio());
            statement.setString(5, compra.getFactura());
            statement.setString(6, compra.getReferencia());

            if (compra.getFechaCompra() != null) {
                statement.setTimestamp(
                        7,
                        Timestamp.valueOf(compra.getFechaCompra())
                );
            } else {
                statement.setTimestamp(7, null);
            }

            statement.setString(8, compra.getTipoCompra());
            statement.setBigDecimal(9, compra.getSubtotal());
            statement.setBigDecimal(10, compra.getDescuento());
            statement.setBigDecimal(11, compra.getImpuesto());
            statement.setBigDecimal(12, compra.getTotal());
            statement.setString(13, compra.getObservaciones());
            statement.setString(14, compra.getEstado());
            statement.setLong(15, compra.getId());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Cambia únicamente el estado de una compra.
     *
     * @param id ID de la compra
     * @param estado nuevo estado
     * @return true si se actualizó
     * @throws SQLException si ocurre un error
     */
    public boolean actualizarEstado(Long id, String estado) throws SQLException {

        String sql = """
                UPDATE compras
                SET estado = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, estado);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una compra.
     *
     * IMPORTANTE:
     * Esta operación será controlada posteriormente desde el Service.
     * No debemos permitir eliminar compras confirmadas sin aplicar
     * las reglas correspondientes de inventario y Kardex.
     *
     * @param id ID de la compra
     * @return true si se eliminó
     * @throws SQLException si ocurre un error
     */
    public boolean eliminar(Long id) throws SQLException {

        String sql = """
                DELETE FROM compras
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Convierte un ResultSet en un objeto Compra.
     */
    private Compra mapearCompra(ResultSet rs) throws SQLException {

        Compra compra = new Compra();

        compra.setId(rs.getLong("id"));
        compra.setSucursalId(rs.getLong("sucursal_id"));
        compra.setProveedorId(rs.getLong("proveedor_id"));
        compra.setUsuarioId(rs.getLong("usuario_id"));
        compra.setFolio(rs.getString("folio"));
        compra.setFactura(rs.getString("factura"));
        compra.setReferencia(rs.getString("referencia"));

        Timestamp fechaCompra = rs.getTimestamp("fecha_compra");

        if (fechaCompra != null) {
            compra.setFechaCompra(fechaCompra.toLocalDateTime());
        }

        compra.setTipoCompra(rs.getString("tipo_compra"));
        compra.setSubtotal(rs.getBigDecimal("subtotal"));
        compra.setDescuento(rs.getBigDecimal("descuento"));
        compra.setImpuesto(rs.getBigDecimal("impuesto"));
        compra.setTotal(rs.getBigDecimal("total"));
        compra.setObservaciones(rs.getString("observaciones"));
        compra.setEstado(rs.getString("estado"));

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            compra.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");

        if (updatedAt != null) {
            compra.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return compra;
    }
}
