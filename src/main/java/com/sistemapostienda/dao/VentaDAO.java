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
import com.sistemapostienda.model.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Registrar una venta
    public Venta guardar(Venta venta) {
        String sql = """
            INSERT INTO ventas (
                sucursal_id,
                caja_id,
                sesion_caja_id,
                cliente_id,
                usuario_id,
                lista_precio_id,
                folio,
                fecha_venta,
                tipo_venta,
                subtotal,
                descuento,
                impuesto,
                total,
                total_pagado,
                cambio,
                estado,
                observaciones
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, venta.getSucursalId());
            statement.setLong(2, venta.getCajaId());
            statement.setLong(3, venta.getSesionCajaId());

            if (venta.getClienteId() != null) {
                statement.setLong(4, venta.getClienteId());
            } else {
                statement.setNull(4, Types.BIGINT);
            }

            statement.setLong(5, venta.getUsuarioId());

            if (venta.getListaPrecioId() != null) {
                statement.setLong(6, venta.getListaPrecioId());
            } else {
                statement.setNull(6, Types.BIGINT);
            }

            statement.setString(7, venta.getFolio());

            if (venta.getFechaVenta() != null) {
                statement.setTimestamp(
                        8, Timestamp.valueOf(venta.getFechaVenta()));
            } else {
                statement.setNull(8, Types.TIMESTAMP);
            }

            statement.setString(9, venta.getTipoVenta());
            statement.setBigDecimal(10, venta.getSubtotal());
            statement.setBigDecimal(11, venta.getDescuento());
            statement.setBigDecimal(12, venta.getImpuesto());
            statement.setBigDecimal(13, venta.getTotal());
            statement.setBigDecimal(14, venta.getTotalPagado());
            statement.setBigDecimal(15, venta.getCambio());
            statement.setString(16, venta.getEstado());
            statement.setString(17, venta.getObservaciones());

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setId(generatedKeys.getLong(1));
                    return buscarPorId(venta.getId());
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la venta.", e);
        }
    }

    // Buscar una venta por ID
    public Venta buscarPorId(Long id) {
        String sql = "SELECT * FROM ventas WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearVenta(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la venta.", e);
        }
    }

    // Buscar una venta por folio
    public Venta buscarPorFolio(String folio) {
        String sql = "SELECT * FROM ventas WHERE folio = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, folio);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearVenta(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la venta por folio.", e);
        }
    }

    // Listar todas las ventas
    public List<Venta> listar() {
        String sql = """
            SELECT * FROM ventas
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsulta(sql);
    }

    // Listar ventas de una sucursal
    public List<Venta> listarPorSucursal(Long sucursalId) {
        String sql = """
            SELECT * FROM ventas
            WHERE sucursal_id = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, sucursalId);
    }

    // Listar ventas de un cliente
    public List<Venta> listarPorCliente(Long clienteId) {
        String sql = """
            SELECT * FROM ventas
            WHERE cliente_id = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, clienteId);
    }

    // Listar ventas de un usuario
    public List<Venta> listarPorUsuario(Long usuarioId) {
        String sql = """
            SELECT * FROM ventas
            WHERE usuario_id = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, usuarioId);
    }

    // Listar ventas de una caja
    public List<Venta> listarPorCaja(Long cajaId) {
        String sql = """
            SELECT * FROM ventas
            WHERE caja_id = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, cajaId);
    }

    // Listar ventas de una sesión de caja
    public List<Venta> listarPorSesionCaja(Long sesionCajaId) {
        String sql = """
            SELECT * FROM ventas
            WHERE sesion_caja_id = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, sesionCajaId);
    }

    // Listar ventas por estado
    public List<Venta> listarPorEstado(String estado) {
        String sql = """
            SELECT * FROM ventas
            WHERE estado = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConTexto(sql, estado);
    }

    // Listar ventas por tipo
    public List<Venta> listarPorTipoVenta(String tipoVenta) {
        String sql = """
            SELECT * FROM ventas
            WHERE tipo_venta = ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        return ejecutarConsultaConTexto(sql, tipoVenta);
    }

    // Consultar ventas dentro de un rango de fechas
    public List<Venta> listarPorFecha(
            Timestamp fechaInicio, Timestamp fechaFin) {

        String sql = """
            SELECT * FROM ventas
            WHERE fecha_venta >= ?
              AND fecha_venta < ?
            ORDER BY fecha_venta DESC, id DESC
            """;

        List<Venta> ventas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, fechaInicio);
            statement.setTimestamp(2, fechaFin);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearVenta(rs));
                }
            }

            return ventas;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar las ventas por fecha.", e);
        }
    }

    // Actualizar los datos de una venta
    public boolean actualizar(Venta venta) {
        String sql = """
            UPDATE ventas SET
                sucursal_id = ?,
                caja_id = ?,
                sesion_caja_id = ?,
                cliente_id = ?,
                usuario_id = ?,
                lista_precio_id = ?,
                fecha_venta = ?,
                tipo_venta = ?,
                subtotal = ?,
                descuento = ?,
                impuesto = ?,
                total = ?,
                total_pagado = ?,
                cambio = ?,
                observaciones = ?
            WHERE id = ?
              AND estado = 'BORRADOR'
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, venta.getSucursalId());
            statement.setLong(2, venta.getCajaId());
            statement.setLong(3, venta.getSesionCajaId());

            if (venta.getClienteId() != null) {
                statement.setLong(4, venta.getClienteId());
            } else {
                statement.setNull(4, Types.BIGINT);
            }

            statement.setLong(5, venta.getUsuarioId());

            if (venta.getListaPrecioId() != null) {
                statement.setLong(6, venta.getListaPrecioId());
            } else {
                statement.setNull(6, Types.BIGINT);
            }

            if (venta.getFechaVenta() != null) {
                statement.setTimestamp(
                        7, Timestamp.valueOf(venta.getFechaVenta()));
            } else {
                statement.setNull(7, Types.TIMESTAMP);
            }

            statement.setString(8, venta.getTipoVenta());
            statement.setBigDecimal(9, venta.getSubtotal());
            statement.setBigDecimal(10, venta.getDescuento());
            statement.setBigDecimal(11, venta.getImpuesto());
            statement.setBigDecimal(12, venta.getTotal());
            statement.setBigDecimal(13, venta.getTotalPagado());
            statement.setBigDecimal(14, venta.getCambio());
            statement.setString(15, venta.getObservaciones());
            statement.setLong(16, venta.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la venta.", e);
        }
    }

    // Cambiar el estado de una venta
    public boolean cambiarEstado(Long id, String estado) {
        String sql = """
            UPDATE ventas
            SET estado = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, estado);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al cambiar el estado de la venta.", e);
        }
    }

    // Comprobar si existe un folio
    public boolean existeFolio(String folio) {
        String sql = """
            SELECT 1 FROM ventas
            WHERE folio = ?
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, folio);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al verificar el folio de la venta.", e);
        }
    }

    // Consulta general sin parámetros
    private List<Venta> ejecutarConsulta(String sql) {
        List<Venta> ventas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                ventas.add(mapearVenta(rs));
            }

            return ventas;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las ventas.", e);
        }
    }

    // Consulta parametrizada por ID
    private List<Venta> ejecutarConsultaConId(String sql, Long id) {
        List<Venta> ventas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearVenta(rs));
                }
            }

            return ventas;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las ventas.", e);
        }
    }

    // Consulta parametrizada por texto
    private List<Venta> ejecutarConsultaConTexto(
            String sql, String valor) {

        List<Venta> ventas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, valor);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearVenta(rs));
                }
            }

            return ventas;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las ventas.", e);
        }
    }

    // Convertir un registro de MySQL al modelo Venta
    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();

        venta.setId(rs.getLong("id"));
        venta.setSucursalId(rs.getLong("sucursal_id"));
        venta.setCajaId(rs.getLong("caja_id"));
        venta.setSesionCajaId(rs.getLong("sesion_caja_id"));

        long clienteId = rs.getLong("cliente_id");
        venta.setClienteId(rs.wasNull() ? null : clienteId);

        venta.setUsuarioId(rs.getLong("usuario_id"));

        long listaPrecioId = rs.getLong("lista_precio_id");
        venta.setListaPrecioId(rs.wasNull() ? null : listaPrecioId);

        venta.setFolio(rs.getString("folio"));

        Timestamp fechaVenta = rs.getTimestamp("fecha_venta");
        if (fechaVenta != null) {
            venta.setFechaVenta(fechaVenta.toLocalDateTime());
        }

        venta.setTipoVenta(rs.getString("tipo_venta"));
        venta.setSubtotal(rs.getBigDecimal("subtotal"));
        venta.setDescuento(rs.getBigDecimal("descuento"));
        venta.setImpuesto(rs.getBigDecimal("impuesto"));
        venta.setTotal(rs.getBigDecimal("total"));
        venta.setTotalPagado(rs.getBigDecimal("total_pagado"));
        venta.setCambio(rs.getBigDecimal("cambio"));
        venta.setEstado(rs.getString("estado"));
        venta.setObservaciones(rs.getString("observaciones"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            venta.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            venta.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return venta;
    }
}

