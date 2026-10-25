/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.ListaPrecio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaPrecioDAO {

    // =========================================================
    // INSERTAR
    // =========================================================

    public boolean guardar(ListaPrecio lista) {

        String sql = """
                INSERT INTO listas_precios
                (
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, lista.getCodigo());
            statement.setString(2, lista.getNombre());
            statement.setString(3, lista.getDescripcion());
            statement.setInt(4, lista.getPrioridad());
            statement.setBoolean(5, lista.getEsPredeterminada());
            statement.setString(6, lista.getAlcance());

            if (lista.getSucursalId() != null) {
                statement.setLong(7, lista.getSucursalId());
            } else {
                statement.setNull(7, Types.BIGINT);
            }

            statement.setBoolean(8, lista.getActiva());

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return false;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    lista.setId(keys.getLong(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println(
                    "Error al guardar lista de precios: "
                    + e.getMessage());

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public ListaPrecio buscarPorId(Long id) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearListaPrecio(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al buscar lista de precios por ID: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public ListaPrecio buscarPorCodigo(String codigo) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE codigo = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, codigo);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearListaPrecio(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al buscar lista por código: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public ListaPrecio buscarPorNombre(String nombre) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE nombre = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nombre);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearListaPrecio(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al buscar lista por nombre: "
                    + e.getMessage());
        }

        return null;
    }

    // =========================================================
    // LISTAR TODAS
    // =========================================================

    public List<ListaPrecio> listarTodas() {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                ORDER BY prioridad ASC, nombre ASC
                """;

        List<ListaPrecio> listas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                listas.add(mapearListaPrecio(resultSet));
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al listar listas de precios: "
                    + e.getMessage());
        }

        return listas;
    }

    // =========================================================
    // LISTAR ACTIVAS
    // =========================================================

    public List<ListaPrecio> listarActivas() {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE activa = TRUE
                ORDER BY prioridad ASC, nombre ASC
                """;

        List<ListaPrecio> listas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                listas.add(mapearListaPrecio(resultSet));
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al listar listas activas: "
                    + e.getMessage());
        }

        return listas;
    }

    // =========================================================
    // LISTAR LISTAS GLOBALES
    // =========================================================

    public List<ListaPrecio> listarGlobales() {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE alcance = 'GLOBAL'
                ORDER BY prioridad ASC, nombre ASC
                """;

        List<ListaPrecio> listas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                listas.add(mapearListaPrecio(resultSet));
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al listar listas globales: "
                    + e.getMessage());
        }

        return listas;
    }

    // =========================================================
    // LISTAR POR SUCURSAL
    // =========================================================

    public List<ListaPrecio> listarPorSucursal(Long sucursalId) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    descripcion,
                    prioridad,
                    es_predeterminada,
                    alcance,
                    sucursal_id,
                    activa,
                    created_at,
                    updated_at
                FROM listas_precios
                WHERE sucursal_id = ?
                ORDER BY prioridad ASC, nombre ASC
                """;

        List<ListaPrecio> listas = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    listas.add(mapearListaPrecio(resultSet));
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al listar listas por sucursal: "
                    + e.getMessage());
        }

        return listas;
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(ListaPrecio lista) {

        String sql = """
                UPDATE listas_precios
                SET
                    codigo = ?,
                    nombre = ?,
                    descripcion = ?,
                    prioridad = ?,
                    es_predeterminada = ?,
                    alcance = ?,
                    sucursal_id = ?,
                    activa = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, lista.getCodigo());
            statement.setString(2, lista.getNombre());
            statement.setString(3, lista.getDescripcion());
            statement.setInt(4, lista.getPrioridad());
            statement.setBoolean(5, lista.getEsPredeterminada());
            statement.setString(6, lista.getAlcance());

            if (lista.getSucursalId() != null) {
                statement.setLong(7, lista.getSucursalId());
            } else {
                statement.setNull(7, Types.BIGINT);
            }

            statement.setBoolean(8, lista.getActiva());
            statement.setLong(9, lista.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Error al actualizar lista de precios: "
                    + e.getMessage());

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        String sql = """
                DELETE FROM listas_precios
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Error al eliminar lista de precios: "
                    + e.getMessage());

            return false;
        }
    }

    // =========================================================
    // ACTIVAR / DESACTIVAR
    // =========================================================

    public boolean cambiarEstado(Long id, boolean activa) {

        String sql = """
                UPDATE listas_precios
                SET activa = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, activa);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Error al cambiar estado de lista de precios: "
                    + e.getMessage());

            return false;
        }
    }

    // =========================================================
    // MÉTODO PRIVADO PARA MAPEAR RESULTSET
    // =========================================================

    private ListaPrecio mapearListaPrecio(ResultSet resultSet)
            throws SQLException {

        ListaPrecio lista = new ListaPrecio();

        lista.setId(resultSet.getLong("id"));

        lista.setCodigo(
                resultSet.getString("codigo"));

        lista.setNombre(
                resultSet.getString("nombre"));

        lista.setDescripcion(
                resultSet.getString("descripcion"));

        lista.setPrioridad(
                resultSet.getInt("prioridad"));

        lista.setEsPredeterminada(
                resultSet.getBoolean("es_predeterminada"));

        lista.setAlcance(
                resultSet.getString("alcance"));

        long sucursalId =
                resultSet.getLong("sucursal_id");

        if (resultSet.wasNull()) {
            lista.setSucursalId(null);
        } else {
            lista.setSucursalId(sucursalId);
        }

        lista.setActiva(
                resultSet.getBoolean("activa"));

        Timestamp created =
                resultSet.getTimestamp("created_at");

        if (created != null) {
            lista.setCreatedAt(
                    created.toLocalDateTime());
        }

        Timestamp updated =
                resultSet.getTimestamp("updated_at");

        if (updated != null) {
            lista.setUpdatedAt(
                    updated.toLocalDateTime());
        }

        return lista;
    }
}
