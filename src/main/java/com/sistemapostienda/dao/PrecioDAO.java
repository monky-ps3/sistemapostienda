/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Precio;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla precios.
 *
 * Se encarga únicamente del acceso a la base de datos.
 */
public class PrecioDAO {

    // =========================================================
    // GUARDAR
    // =========================================================

    public boolean guardar(Precio precio) {

        String sql = """
                INSERT INTO precios
                (
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(
                    1,
                    precio.getPresentacionId()
            );

            statement.setLong(
                    2,
                    precio.getListaPrecioId()
            );

            statement.setBigDecimal(
                    3,
                    precio.getPrecio()
            );

            statement.setBoolean(
                    4,
                    precio.getActivo()
            );

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return false;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    precio.setId(keys.getLong(1));
                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Precio buscarPorId(Long id) {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearPrecio(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar precio por ID: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR PRESENTACIÓN Y LISTA
    // =========================================================

    public Precio buscarPorPresentacionYLista(
            Long presentacionId,
            Long listaPrecioId) {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                WHERE presentacion_id = ?
                  AND lista_precio_id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, presentacionId);
            statement.setLong(2, listaPrecioId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearPrecio(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar precio por presentación y lista: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // LISTAR POR PRESENTACIÓN
    // =========================================================

    public List<Precio> listarPorPresentacion(
            Long presentacionId) {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                WHERE presentacion_id = ?
                ORDER BY lista_precio_id
                """;

        List<Precio> precios = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, presentacionId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    precios.add(
                            mapearPrecio(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar precios por presentación: "
                    + e.getMessage()
            );
        }

        return precios;
    }

    // =========================================================
    // LISTAR POR LISTA DE PRECIOS
    // =========================================================

    public List<Precio> listarPorListaPrecio(
            Long listaPrecioId) {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                WHERE lista_precio_id = ?
                ORDER BY presentacion_id
                """;

        List<Precio> precios = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, listaPrecioId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    precios.add(
                            mapearPrecio(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar precios por lista: "
                    + e.getMessage()
            );
        }

        return precios;
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Precio> listarActivos() {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                WHERE activo = TRUE
                ORDER BY presentacion_id, lista_precio_id
                """;

        List<Precio> precios = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                precios.add(
                        mapearPrecio(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar precios activos: "
                    + e.getMessage()
            );
        }

        return precios;
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Precio> listarTodos() {

        String sql = """
                SELECT
                    id,
                    presentacion_id,
                    lista_precio_id,
                    precio,
                    activo,
                    created_at,
                    updated_at
                FROM precios
                ORDER BY presentacion_id, lista_precio_id
                """;

        List<Precio> precios = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                precios.add(
                        mapearPrecio(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar precios: "
                    + e.getMessage()
            );
        }

        return precios;
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(Precio precio) {

        String sql = """
                UPDATE precios
                SET
                    presentacion_id = ?,
                    lista_precio_id = ?,
                    precio = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(
                    1,
                    precio.getPresentacionId()
            );

            statement.setLong(
                    2,
                    precio.getListaPrecioId()
            );

            statement.setBigDecimal(
                    3,
                    precio.getPrecio()
            );

            statement.setBoolean(
                    4,
                    precio.getActivo()
            );

            statement.setLong(
                    5,
                    precio.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        String sql = """
                DELETE FROM precios
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // CAMBIAR ESTADO
    // =========================================================

    public boolean cambiarEstado(
            Long id,
            boolean activo) {

        String sql = """
                UPDATE precios
                SET activo = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, activo);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al cambiar estado del precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // MAPEAR RESULTSET
    // =========================================================

    private Precio mapearPrecio(ResultSet resultSet)
            throws SQLException {

        Precio precio = new Precio();

        precio.setId(
                resultSet.getLong("id")
        );

        precio.setPresentacionId(
                resultSet.getLong("presentacion_id")
        );

        precio.setListaPrecioId(
                resultSet.getLong("lista_precio_id")
        );

        BigDecimal valor =
                resultSet.getBigDecimal("precio");

        precio.setPrecio(valor);

        precio.setActivo(
                resultSet.getBoolean("activo")
        );

        Timestamp created =
                resultSet.getTimestamp("created_at");

        if (created != null) {
            precio.setCreatedAt(
                    created.toLocalDateTime()
            );
        }

        Timestamp updated =
                resultSet.getTimestamp("updated_at");

        if (updated != null) {
            precio.setUpdatedAt(
                    updated.toLocalDateTime()
            );
        }

        return precio;
    }
}