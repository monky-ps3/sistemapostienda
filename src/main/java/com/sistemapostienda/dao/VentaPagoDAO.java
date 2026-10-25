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
import com.sistemapostienda.model.VentaPago;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class VentaPagoDAO {

    // Registrar un pago
    public VentaPago guardar(VentaPago pago) {
        String sql = """
            INSERT INTO ventas_pago (
                venta_id,
                forma_pago_id,
                usuario_id,
                caja_id,
                sesion_caja_id,
                tipo_pago,
                monto,
                monto_recibido,
                cambio,
                referencia,
                numero_operacion,
                fecha_pago,
                observaciones,
                estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, pago.getVentaId());
            statement.setLong(2, pago.getFormaPagoId());
            statement.setLong(3, pago.getUsuarioId());

            if (pago.getCajaId() != null) {
                statement.setLong(4, pago.getCajaId());
            } else {
                statement.setNull(4, Types.BIGINT);
            }

            if (pago.getSesionCajaId() != null) {
                statement.setLong(5, pago.getSesionCajaId());
            } else {
                statement.setNull(5, Types.BIGINT);
            }

            statement.setString(6, pago.getTipoPago());
            statement.setBigDecimal(7, pago.getMonto());
            statement.setBigDecimal(8, pago.getMontoRecibido());
            statement.setBigDecimal(9, pago.getCambio());
            statement.setString(10, pago.getReferencia());
            statement.setString(11, pago.getNumeroOperacion());

            if (pago.getFechaPago() != null) {
                statement.setTimestamp(
                        12, Timestamp.valueOf(pago.getFechaPago()));
            } else {
                statement.setNull(12, Types.TIMESTAMP);
            }

            statement.setString(13, pago.getObservaciones());
            statement.setString(14, pago.getEstado());

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setId(generatedKeys.getLong(1));
                    return buscarPorId(pago.getId());
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el pago.", e);
        }
    }

    // Buscar un pago por su ID
    public VentaPago buscarPorId(Long id) {
        String sql = "SELECT * FROM ventas_pago WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearVentaPago(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el pago.", e);
        }
    }

    // Listar todos los pagos
    public List<VentaPago> listar() {
        String sql = """
            SELECT * FROM ventas_pago
            ORDER BY fecha_pago DESC, id DESC
            """;

        return ejecutarConsulta(sql);
    }

    // Listar pagos de una venta
    public List<VentaPago> listarPorVenta(Long ventaId) {
        String sql = """
            SELECT * FROM ventas_pago
            WHERE venta_id = ?
            ORDER BY fecha_pago ASC, id ASC
            """;

        return ejecutarConsultaConId(sql, ventaId);
    }

    // Listar pagos por forma de pago
    public List<VentaPago> listarPorFormaPago(Long formaPagoId) {
        String sql = """
            SELECT * FROM ventas_pago
            WHERE forma_pago_id = ?
            ORDER BY fecha_pago DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, formaPagoId);
    }

    // Listar pagos realizados por un usuario
    public List<VentaPago> listarPorUsuario(Long usuarioId) {
        String sql = """
            SELECT * FROM ventas_pago
            WHERE usuario_id = ?
            ORDER BY fecha_pago DESC, id DESC
            """;

        return ejecutarConsultaConId(sql, usuarioId);
    }

    // Listar pagos por estado: CONFIRMADO o CANCELADO
    public List<VentaPago> listarPorEstado(String estado) {
        String sql = """
            SELECT * FROM ventas_pago
            WHERE estado = ?
            ORDER BY fecha_pago DESC, id DESC
            """;

        return ejecutarConsultaConTexto(sql, estado);
    }

    // Listar pagos por tipo: PAGO_VENTA o ABONO_CREDITO
    public List<VentaPago> listarPorTipoPago(String tipoPago) {
        String sql = """
            SELECT * FROM ventas_pago
            WHERE tipo_pago = ?
            ORDER BY fecha_pago DESC, id DESC
            """;

        return ejecutarConsultaConTexto(sql, tipoPago);
    }

    // Cancelar un pago sin eliminar su historial
    public boolean cancelar(Long id) {
        String sql = """
            UPDATE ventas_pago
            SET estado = 'CANCELADO'
            WHERE id = ?
              AND estado = 'CONFIRMADO'
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al cancelar el pago.", e);
        }
    }

    // Consulta general sin parámetros
    private List<VentaPago> ejecutarConsulta(String sql) {
        List<VentaPago> pagos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                pagos.add(mapearVentaPago(rs));
            }

            return pagos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los pagos.", e);
        }
    }

    // Consulta parametrizada por ID
    private List<VentaPago> ejecutarConsultaConId(
            String sql, Long id) {

        List<VentaPago> pagos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    pagos.add(mapearVentaPago(rs));
                }
            }

            return pagos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los pagos.", e);
        }
    }

    // Consulta parametrizada por texto
    private List<VentaPago> ejecutarConsultaConTexto(
            String sql, String valor) {

        List<VentaPago> pagos = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, valor);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    pagos.add(mapearVentaPago(rs));
                }
            }

            return pagos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los pagos.", e);
        }
    }

    // Convertir el registro de MySQL al modelo Java
    private VentaPago mapearVentaPago(ResultSet rs) throws SQLException {
        VentaPago pago = new VentaPago();

        pago.setId(rs.getLong("id"));
        pago.setVentaId(rs.getLong("venta_id"));
        pago.setFormaPagoId(rs.getLong("forma_pago_id"));
        pago.setUsuarioId(rs.getLong("usuario_id"));

        long cajaId = rs.getLong("caja_id");
        pago.setCajaId(rs.wasNull() ? null : cajaId);

        long sesionCajaId = rs.getLong("sesion_caja_id");
        pago.setSesionCajaId(rs.wasNull() ? null : sesionCajaId);

        pago.setTipoPago(rs.getString("tipo_pago"));
        pago.setMonto(rs.getBigDecimal("monto"));
        pago.setMontoRecibido(rs.getBigDecimal("monto_recibido"));
        pago.setCambio(rs.getBigDecimal("cambio"));
        pago.setReferencia(rs.getString("referencia"));
        pago.setNumeroOperacion(rs.getString("numero_operacion"));

        Timestamp fechaPago = rs.getTimestamp("fecha_pago");
        if (fechaPago != null) {
            pago.setFechaPago(fechaPago.toLocalDateTime());
        }

        pago.setObservaciones(rs.getString("observaciones"));
        pago.setEstado(rs.getString("estado"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            pago.setCreatedAt(createdAt.toLocalDateTime());
        }

        return pago;
    }
}
