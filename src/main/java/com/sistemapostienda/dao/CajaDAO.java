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
import com.sistemapostienda.model.Caja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CajaDAO {

    /**
     * Registra una nueva caja.
     */
    public Long guardar(Caja caja) throws SQLException {

        String sql = """
            INSERT INTO cajas (
                sucursal_id,
                nombre,
                descripcion,
                activa
            ) VALUES (?, ?, ?, ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, caja.getSucursalId());
            statement.setString(2, caja.getNombre());
            statement.setString(3, caja.getDescripcion());
            statement.setBoolean(4, caja.isActiva());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("No se pudo registrar la caja.");
            }

            try (ResultSet resultado = statement.getGeneratedKeys()) {
                if (resultado.next()) {
                    long id = resultado.getLong(1);
                    caja.setId(id);
                    return id;
                }
            }
        }

        throw new SQLException("No se obtuvo el ID de la caja.");
    }

    /**
     * Busca una caja por su identificador.
     */
    public Caja buscarPorId(Long id) throws SQLException {

        String sql = """
            SELECT *
            FROM cajas
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return mapearCaja(resultado);
                }
            }
        }

        return null;
    }

    /**
     * Lista todas las cajas, activas e inactivas.
     */
    public List<Caja> listar() throws SQLException {

        String sql = """
            SELECT *
            FROM cajas
            ORDER BY sucursal_id, nombre
            """;

        List<Caja> cajas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                cajas.add(mapearCaja(resultado));
            }
        }

        return cajas;
    }

    /**
     * Lista todas las cajas pertenecientes a una sucursal.
     */
    public List<Caja> listarPorSucursal(Long sucursalId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM cajas
            WHERE sucursal_id = ?
            ORDER BY nombre
            """;

        List<Caja> cajas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    cajas.add(mapearCaja(resultado));
                }
            }
        }

        return cajas;
    }

    /**
     * Lista únicamente las cajas activas de una sucursal.
     */
    public List<Caja> listarActivasPorSucursal(Long sucursalId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM cajas
            WHERE sucursal_id = ?
              AND activa = TRUE
            ORDER BY nombre
            """;

        List<Caja> cajas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    cajas.add(mapearCaja(resultado));
                }
            }
        }

        return cajas;
    }

    /**
     * Actualiza el nombre y la descripción de una caja.
     * No modifica su sucursal ni su estado.
     */
    public boolean actualizar(Caja caja) throws SQLException {

        String sql = """
            UPDATE cajas
            SET nombre = ?,
                descripcion = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, caja.getNombre());
            statement.setString(2, caja.getDescripcion());
            statement.setLong(3, caja.getId());

            return statement.executeUpdate() == 1;
        }
    }

    /**
     * Da de baja o reactiva una caja.
     * No elimina físicamente el registro.
     */
    public boolean cambiarEstado(Long id, boolean activa)
            throws SQLException {

        String sql = """
            UPDATE cajas
            SET activa = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, activa);
            statement.setLong(2, id);

            return statement.executeUpdate() == 1;
        }
    }

    /**
     * Convierte un registro SQL en un objeto Caja.
     */
    private Caja mapearCaja(ResultSet resultado) throws SQLException {

        Caja caja = new Caja();

        caja.setId(resultado.getLong("id"));
        caja.setSucursalId(resultado.getLong("sucursal_id"));
        caja.setNombre(resultado.getString("nombre"));
        caja.setDescripcion(resultado.getString("descripcion"));
        caja.setActiva(resultado.getBoolean("activa"));

        Timestamp createdAt = resultado.getTimestamp("created_at");
        if (createdAt != null) {
            caja.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = resultado.getTimestamp("updated_at");
        if (updatedAt != null) {
            caja.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return caja;
    }
}
