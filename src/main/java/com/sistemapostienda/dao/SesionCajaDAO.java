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
import com.sistemapostienda.model.SesionCaja;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SesionCajaDAO {

    /**
     * Registra una nueva sesión de caja.
     */
    public Long guardar(SesionCaja sesion) throws SQLException {

        String sql = """
            INSERT INTO sesiones_caja (
                folio,
                caja_id,
                usuario_id,
                fondo_inicial,
                turno,
                estado,
                observaciones
            ) VALUES (?, ?, ?, ?, ?, 'ABIERTA', ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, sesion.getFolio());
            statement.setLong(2, sesion.getCajaId());
            statement.setLong(3, sesion.getUsuarioId());
            statement.setBigDecimal(4, sesion.getFondoInicial());

            if (sesion.getTurno() != null) {
                statement.setString(5, sesion.getTurno());
            } else {
                statement.setNull(5, java.sql.Types.VARCHAR);
            }

            statement.setString(6, sesion.getObservaciones());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("No se pudo abrir la sesión de caja.");
            }

            try (ResultSet resultado = statement.getGeneratedKeys()) {
                if (resultado.next()) {
                    long id = resultado.getLong(1);
                    sesion.setId(id);
                    return id;
                }
            }
        }

        throw new SQLException("No se obtuvo el ID de la sesión de caja.");
    }

    /**
     * Busca una sesión por su identificador.
     */
    public SesionCaja buscarPorId(Long id) throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return mapearSesion(resultado);
                }
            }
        }

        return null;
    }

    /**
     * Busca una sesión por su folio.
     */
    public SesionCaja buscarPorFolio(String folio) throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            WHERE folio = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, folio);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return mapearSesion(resultado);
                }
            }
        }

        return null;
    }

    /**
     * Busca la sesión abierta más reciente de una caja.
     */
    public SesionCaja buscarSesionAbiertaPorCaja(Long cajaId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            WHERE caja_id = ?
              AND estado = 'ABIERTA'
            ORDER BY fecha_apertura DESC, id DESC
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cajaId);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return mapearSesion(resultado);
                }
            }
        }

        return null;
    }

    /**
     * Lista todas las sesiones de caja.
     */
    public List<SesionCaja> listar() throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            ORDER BY fecha_apertura DESC, id DESC
            """;

        List<SesionCaja> sesiones = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                sesiones.add(mapearSesion(resultado));
            }
        }

        return sesiones;
    }

    /**
     * Lista las sesiones de una caja específica.
     */
    public List<SesionCaja> listarPorCaja(Long cajaId) throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            WHERE caja_id = ?
            ORDER BY fecha_apertura DESC, id DESC
            """;

        List<SesionCaja> sesiones = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cajaId);

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    sesiones.add(mapearSesion(resultado));
                }
            }
        }

        return sesiones;
    }

    /**
     * Lista las sesiones de un usuario.
     */
    public List<SesionCaja> listarPorUsuario(Long usuarioId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM sesiones_caja
            WHERE usuario_id = ?
            ORDER BY fecha_apertura DESC, id DESC
            """;

        List<SesionCaja> sesiones = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, usuarioId);

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    sesiones.add(mapearSesion(resultado));
                }
            }
        }

        return sesiones;
    }

    /**
     * Cierra una sesión que todavía está abierta.
     */
    public boolean cerrar(Long id, String observaciones)
            throws SQLException {

        String sql = """
            UPDATE sesiones_caja
            SET estado = 'CERRADA',
                fecha_cierre = CURRENT_TIMESTAMP,
                observaciones = ?
            WHERE id = ?
              AND estado = 'ABIERTA'
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, observaciones);
            statement.setLong(2, id);

            return statement.executeUpdate() == 1;
        }
    }

    /**
     * Comprueba si una caja tiene una sesión abierta.
     */
    public boolean tieneSesionAbierta(Long cajaId) throws SQLException {

        String sql = """
            SELECT 1
            FROM sesiones_caja
            WHERE caja_id = ?
              AND estado = 'ABIERTA'
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cajaId);

            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        }
    }

    /**
     * Convierte un registro de la base de datos en un objeto SesionCaja.
     */
    private SesionCaja mapearSesion(ResultSet resultado)
            throws SQLException {

        SesionCaja sesion = new SesionCaja();

        sesion.setId(resultado.getLong("id"));
        sesion.setFolio(resultado.getString("folio"));
        sesion.setCajaId(resultado.getLong("caja_id"));
        sesion.setUsuarioId(resultado.getLong("usuario_id"));

        Timestamp apertura = resultado.getTimestamp("fecha_apertura");
        if (apertura != null) {
            sesion.setFechaApertura(apertura.toLocalDateTime());
        }

        Timestamp cierre = resultado.getTimestamp("fecha_cierre");
        if (cierre != null) {
            sesion.setFechaCierre(cierre.toLocalDateTime());
        }

        sesion.setFondoInicial(resultado.getBigDecimal("fondo_inicial"));
        sesion.setTurno(resultado.getString("turno"));
        sesion.setEstado(resultado.getString("estado"));
        sesion.setObservaciones(resultado.getString("observaciones"));

        return sesion;
    }
}

