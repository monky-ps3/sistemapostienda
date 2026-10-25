/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Inventario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de la tabla inventario.
 *
 * Esta clase se encarga únicamente del acceso a datos. Las reglas de negocio
 * deben manejarse desde InventarioService.
 */
public class InventarioDAO {

    /**
     * Guarda un nuevo registro de inventario.
     *
     * @param inventario objeto Inventario
     * @return ID generado
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Long guardar(Inventario inventario) throws SQLException {

        String sql = """
                INSERT INTO inventario (
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(
                sql,
                PreparedStatement.RETURN_GENERATED_KEYS
        )) {

            statement.setLong(1, inventario.getSucursalId());
            statement.setLong(2, inventario.getProductoId());

            if (inventario.getLote() != null) {
                statement.setString(3, inventario.getLote());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
            }

            if (inventario.getFechaCaducidad() != null) {
                statement.setDate(
                        4,
                        Date.valueOf(inventario.getFechaCaducidad())
                );
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }

            statement.setBigDecimal(5, inventario.getExistencia());
            statement.setBigDecimal(6, inventario.getExistenciaReservada());
            statement.setBigDecimal(7, inventario.getExistenciaMinima());

            if (inventario.getExistenciaMaxima() != null) {
                statement.setBigDecimal(
                        8,
                        inventario.getExistenciaMaxima()
                );
            } else {
                statement.setNull(8, java.sql.Types.DECIMAL);
            }

            statement.setBigDecimal(9, inventario.getUltimoCosto());
            statement.setBigDecimal(10, inventario.getCostoPromedio());

            if (inventario.getUbicacion() != null) {
                statement.setString(11, inventario.getUbicacion());
            } else {
                statement.setNull(11, java.sql.Types.VARCHAR);
            }

            statement.setBoolean(12, inventario.getActivo());

            int filas = statement.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo guardar el registro de inventario."
                );
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    inventario.setId(id);
                    return id;
                }

                throw new SQLException(
                        "No se obtuvo el ID generado del inventario."
                );
            }
        }
    }

    /**
     * Busca un registro de inventario por su ID.
     *
     * @param id ID del inventario
     * @return Inventario encontrado o null
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Inventario buscarPorId(Long id) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearInventario(rs);
                }
            }
        }

        return null;
    }

    /**
     * Busca el inventario de un producto dentro de una sucursal.
     *
     * Busca específicamente el registro sin lote.
     *
     * @param sucursalId ID de la sucursal
     * @param productoId ID del producto
     * @return Inventario encontrado o null
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Inventario buscarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE sucursal_id = ?
                  AND producto_id = ?
                  AND lote IS NULL
                LIMIT 1
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);
            statement.setLong(2, productoId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearInventario(rs);
                }
            }
        }

        return null;
    }

    /**
     * Busca un inventario específico por sucursal, producto y lote.
     *
     * @param sucursalId ID de la sucursal
     * @param productoId ID del producto
     * @param lote lote del producto
     * @return Inventario encontrado o null
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Inventario buscarPorSucursalProductoYLote(
            Long sucursalId,
            Long productoId,
            String lote
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE sucursal_id = ?
                  AND producto_id = ?
                  AND lote = ?
                LIMIT 1
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);
            statement.setLong(2, productoId);
            statement.setString(3, lote);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearInventario(rs);
                }
            }
        }

        return null;
    }

    /**
     * Lista todo el inventario.
     *
     * @return lista de inventarios
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Inventario> listar() throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                ORDER BY producto_id, lote
                """;

        List<Inventario> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearInventario(rs));
            }
        }

        return lista;
    }

    /**
     * Lista el inventario de una sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de inventarios
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Inventario> listarPorSucursal(
            Long sucursalId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE sucursal_id = ?
                ORDER BY producto_id, lote
                """;

        List<Inventario> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearInventario(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista el inventario de un producto en todas las sucursales.
     *
     * @param productoId ID del producto
     * @return lista de inventarios
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Inventario> listarPorProducto(
            Long productoId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE producto_id = ?
                ORDER BY sucursal_id, lote
                """;

        List<Inventario> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, productoId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearInventario(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista el inventario de un producto dentro de una sucursal.
     *
     * Incluye todos sus lotes.
     *
     * @param sucursalId ID de la sucursal
     * @param productoId ID del producto
     * @return lista de inventarios
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Inventario> listarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    lote,
                    fecha_caducidad,
                    existencia,
                    existencia_reservada,
                    existencia_minima,
                    existencia_maxima,
                    ultimo_costo,
                    costo_promedio,
                    ubicacion,
                    activo,
                    created_at,
                    updated_at
                FROM inventario
                WHERE sucursal_id = ?
                  AND producto_id = ?
                ORDER BY fecha_caducidad ASC, id ASC
                """;

        List<Inventario> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);
            statement.setLong(2, productoId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearInventario(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Actualiza un registro completo de inventario.
     *
     * @param inventario objeto Inventario
     * @return true si se actualizó correctamente
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean actualizar(Inventario inventario) throws SQLException {

        String sql = """
                UPDATE inventario
                SET
                    sucursal_id = ?,
                    producto_id = ?,
                    lote = ?,
                    fecha_caducidad = ?,
                    existencia = ?,
                    existencia_reservada = ?,
                    existencia_minima = ?,
                    existencia_maxima = ?,
                    ultimo_costo = ?,
                    costo_promedio = ?,
                    ubicacion = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, inventario.getSucursalId());
            statement.setLong(2, inventario.getProductoId());

            if (inventario.getLote() != null) {
                statement.setString(3, inventario.getLote());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
            }

            if (inventario.getFechaCaducidad() != null) {
                statement.setDate(
                        4,
                        Date.valueOf(inventario.getFechaCaducidad())
                );
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }

            statement.setBigDecimal(5, inventario.getExistencia());
            statement.setBigDecimal(6, inventario.getExistenciaReservada());
            statement.setBigDecimal(7, inventario.getExistenciaMinima());

            if (inventario.getExistenciaMaxima() != null) {
                statement.setBigDecimal(
                        8,
                        inventario.getExistenciaMaxima()
                );
            } else {
                statement.setNull(8, java.sql.Types.DECIMAL);
            }

            statement.setBigDecimal(9, inventario.getUltimoCosto());
            statement.setBigDecimal(10, inventario.getCostoPromedio());

            if (inventario.getUbicacion() != null) {
                statement.setString(11, inventario.getUbicacion());
            } else {
                statement.setNull(11, java.sql.Types.VARCHAR);
            }

            statement.setBoolean(12, inventario.getActivo());
            statement.setLong(13, inventario.getId());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza únicamente la existencia.
     *
     * Este método será útil posteriormente para las operaciones controladas por
     * InventarioService y Kardex.
     *
     * @param id ID del inventario
     * @param existencia nueva existencia
     * @return true si se actualizó
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean actualizarExistencia(
            Long id,
            java.math.BigDecimal existencia
    ) throws SQLException {

        String sql = """
                UPDATE inventario
                SET existencia = ?
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, existencia);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza la existencia reservada.
     *
     * @param id ID del inventario
     * @param existenciaReservada nueva existencia reservada
     * @return true si se actualizó
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean actualizarExistenciaReservada(
            Long id,
            java.math.BigDecimal existenciaReservada
    ) throws SQLException {

        String sql = """
                UPDATE inventario
                SET existencia_reservada = ?
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, existenciaReservada);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza los costos del inventario.
     *
     * @param id ID del inventario
     * @param ultimoCosto último costo
     * @param costoPromedio costo promedio
     * @return true si se actualizó
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean actualizarCostos(
            Long id,
            java.math.BigDecimal ultimoCosto,
            java.math.BigDecimal costoPromedio
    ) throws SQLException {

        String sql = """
                UPDATE inventario
                SET
                    ultimo_costo = ?,
                    costo_promedio = ?
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, ultimoCosto);
            statement.setBigDecimal(2, costoPromedio);
            statement.setLong(3, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Activa o desactiva un registro de inventario.
     *
     * @param id ID del inventario
     * @param activo nuevo estado
     * @return true si se actualizó
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean actualizarActivo(
            Long id,
            boolean activo
    ) throws SQLException {

        String sql = """
                UPDATE inventario
                SET activo = ?
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, activo);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un registro de inventario.
     *
     * Esta operación deberá ser restringida por InventarioService.
     *
     * @param id ID del inventario
     * @return true si se eliminó
     * @throws SQLException si ocurre un error con la base de datos
     */
    public boolean eliminar(Long id) throws SQLException {

        String sql = """
                DELETE FROM inventario
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Convierte un ResultSet en un objeto Inventario.
     *
     * @param rs ResultSet
     * @return objeto Inventario
     * @throws SQLException si ocurre un error al leer los datos
     */
    private Inventario mapearInventario(ResultSet rs) throws SQLException {

        Inventario inventario = new Inventario();

        inventario.setId(rs.getLong("id"));
        inventario.setSucursalId(rs.getLong("sucursal_id"));
        inventario.setProductoId(rs.getLong("producto_id"));
        inventario.setLote(rs.getString("lote"));

        Date fechaCaducidad = rs.getDate("fecha_caducidad");

        if (fechaCaducidad != null) {
            inventario.setFechaCaducidad(
                    fechaCaducidad.toLocalDate()
            );
        }

        inventario.setExistencia(
                rs.getBigDecimal("existencia")
        );

        inventario.setExistenciaReservada(
                rs.getBigDecimal("existencia_reservada")
        );

        inventario.setExistenciaMinima(
                rs.getBigDecimal("existencia_minima")
        );

        inventario.setExistenciaMaxima(
                rs.getBigDecimal("existencia_maxima")
        );

        inventario.setUltimoCosto(
                rs.getBigDecimal("ultimo_costo")
        );

        inventario.setCostoPromedio(
                rs.getBigDecimal("costo_promedio")
        );

        inventario.setUbicacion(
                rs.getString("ubicacion")
        );

        inventario.setActivo(
                rs.getBoolean("activo")
        );

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            inventario.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");

        if (updatedAt != null) {
            inventario.setUpdatedAt(
                    updatedAt.toLocalDateTime()
            );
        }

        return inventario;
    }

    /**
     * Descuenta existencias dentro de una transacción compartida. No permite
     * vender más unidades de las disponibles.
     */
    public boolean descontarExistencia(
            Connection connection,
            Long inventarioId,
            java.math.BigDecimal cantidad
    ) throws SQLException {

        String sql = """
        UPDATE inventario
        SET existencia = existencia - ?
        WHERE id = ?
          AND activo = TRUE
          AND existencia - existencia_reservada >= ?
        """;

        try (PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, cantidad);
            statement.setLong(2, inventarioId);
            statement.setBigDecimal(3, cantidad);

            return statement.executeUpdate() == 1;
        }
    }
}
