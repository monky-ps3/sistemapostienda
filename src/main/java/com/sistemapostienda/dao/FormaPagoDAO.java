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
import com.sistemapostienda.model.FormaPago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FormaPagoDAO {

    public FormaPago guardar(FormaPago formaPago) throws SQLException {
        String sql = """
            INSERT INTO formas_pago (
                clave,
                nombre,
                descripcion,
                requiere_referencia,
                permite_cambio,
                permite_pago_parcial,
                activo,
                orden
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, formaPago.getClave());
            statement.setString(2, formaPago.getNombre());
            statement.setString(3, formaPago.getDescripcion());
            statement.setBoolean(4, formaPago.isRequiereReferencia());
            statement.setBoolean(5, formaPago.isPermiteCambio());
            statement.setBoolean(6, formaPago.isPermitePagoParcial());
            statement.setBoolean(7, formaPago.isActivo());
            statement.setInt(8, formaPago.getOrden());

            int filas = statement.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo guardar la forma de pago.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    formaPago.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException(
                            "No se obtuvo el ID de la forma de pago.");
                }
            }
        }

        return buscarPorId(formaPago.getId());
    }

    public FormaPago buscarPorId(Long id) throws SQLException {
        String sql = """
            SELECT *
            FROM formas_pago
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearFormaPago(rs);
                }
            }
        }

        return null;
    }

    public FormaPago buscarPorClave(String clave) throws SQLException {
        String sql = """
            SELECT *
            FROM formas_pago
            WHERE clave = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearFormaPago(rs);
                }
            }
        }

        return null;
    }

    public FormaPago buscarPorNombre(String nombre) throws SQLException {
        String sql = """
            SELECT *
            FROM formas_pago
            WHERE nombre = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombre);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearFormaPago(rs);
                }
            }
        }

        return null;
    }

    public List<FormaPago> listar() throws SQLException {
        String sql = """
            SELECT *
            FROM formas_pago
            ORDER BY orden ASC, nombre ASC
            """;

        List<FormaPago> formasPago = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                formasPago.add(mapearFormaPago(rs));
            }
        }

        return formasPago;
    }

    public List<FormaPago> listarActivas() throws SQLException {
        String sql = """
            SELECT *
            FROM formas_pago
            WHERE activo = TRUE
            ORDER BY orden ASC, nombre ASC
            """;

        List<FormaPago> formasPago = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                formasPago.add(mapearFormaPago(rs));
            }
        }

        return formasPago;
    }

    public boolean actualizar(FormaPago formaPago) throws SQLException {
        String sql = """
            UPDATE formas_pago
            SET clave = ?,
                nombre = ?,
                descripcion = ?,
                requiere_referencia = ?,
                permite_cambio = ?,
                permite_pago_parcial = ?,
                activo = ?,
                orden = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, formaPago.getClave());
            statement.setString(2, formaPago.getNombre());
            statement.setString(3, formaPago.getDescripcion());
            statement.setBoolean(4, formaPago.isRequiereReferencia());
            statement.setBoolean(5, formaPago.isPermiteCambio());
            statement.setBoolean(6, formaPago.isPermitePagoParcial());
            statement.setBoolean(7, formaPago.isActivo());
            statement.setInt(8, formaPago.getOrden());
            statement.setLong(9, formaPago.getId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean cambiarEstado(Long id, boolean activo)
            throws SQLException {

        String sql = """
            UPDATE formas_pago
            SET activo = ?
            WHERE id = ?
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, activo);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean existeClave(String clave) throws SQLException {
        String sql = """
            SELECT 1
            FROM formas_pago
            WHERE clave = ?
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existeNombre(String nombre) throws SQLException {
        String sql = """
            SELECT 1
            FROM formas_pago
            WHERE nombre = ?
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombre);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existeClaveEnOtroRegistro(
            String clave, Long idExcluir) throws SQLException {

        String sql = """
            SELECT 1
            FROM formas_pago
            WHERE clave = ?
              AND id <> ?
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);
            statement.setLong(2, idExcluir);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existeNombreEnOtroRegistro(
            String nombre, Long idExcluir) throws SQLException {

        String sql = """
            SELECT 1
            FROM formas_pago
            WHERE nombre = ?
              AND id <> ?
            LIMIT 1
            """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombre);
            statement.setLong(2, idExcluir);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private FormaPago mapearFormaPago(ResultSet rs) throws SQLException {
        FormaPago formaPago = new FormaPago();

        formaPago.setId(rs.getLong("id"));
        formaPago.setClave(rs.getString("clave"));
        formaPago.setNombre(rs.getString("nombre"));
        formaPago.setDescripcion(rs.getString("descripcion"));
        formaPago.setRequiereReferencia(
                rs.getBoolean("requiere_referencia"));
        formaPago.setPermiteCambio(rs.getBoolean("permite_cambio"));
        formaPago.setPermitePagoParcial(
                rs.getBoolean("permite_pago_parcial"));
        formaPago.setActivo(rs.getBoolean("activo"));
        formaPago.setOrden(rs.getInt("orden"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            formaPago.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            formaPago.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return formaPago;
    }
}
