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
import com.sistemapostienda.model.Kardex;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de la tabla kardex.
 *
 * Esta clase se encarga únicamente del acceso a datos. Las reglas de negocio se
 * manejan desde KardexService.
 */
public class KardexDAO {

    /**
     * Guarda un nuevo movimiento en el Kardex.
     *
     * @param kardex movimiento a guardar
     * @return ID generado
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Long guardar(Kardex kardex) throws SQLException {

        String sql = """
                INSERT INTO kardex (
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(
                sql,
                PreparedStatement.RETURN_GENERATED_KEYS
        )) {

            statement.setLong(1, kardex.getSucursalId());
            statement.setLong(2, kardex.getProductoId());

            if (kardex.getPresentacionId() != null) {
                statement.setLong(3, kardex.getPresentacionId());
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }

            if (kardex.getInventarioId() != null) {
                statement.setLong(4, kardex.getInventarioId());
            } else {
                statement.setNull(4, java.sql.Types.BIGINT);
            }

            statement.setLong(5, kardex.getUsuarioId());

            statement.setString(
                    6,
                    kardex.getTipoMovimiento()
            );

            if (kardex.getReferenciaId() != null) {
                statement.setLong(
                        7,
                        kardex.getReferenciaId()
                );
            } else {
                statement.setNull(
                        7,
                        java.sql.Types.BIGINT
                );
            }

            if (kardex.getReferencia() != null) {
                statement.setString(
                        8,
                        kardex.getReferencia()
                );
            } else {
                statement.setNull(
                        8,
                        java.sql.Types.VARCHAR
                );
            }

            if (kardex.getLote() != null) {
                statement.setString(
                        9,
                        kardex.getLote()
                );
            } else {
                statement.setNull(
                        9,
                        java.sql.Types.VARCHAR
                );
            }

            if (kardex.getFechaCaducidad() != null) {
                statement.setDate(
                        10,
                        Date.valueOf(
                                kardex.getFechaCaducidad()
                        )
                );
            } else {
                statement.setNull(
                        10,
                        java.sql.Types.DATE
                );
            }

            statement.setBigDecimal(
                    11,
                    kardex.getEntrada()
            );

            statement.setBigDecimal(
                    12,
                    kardex.getSalida()
            );

            statement.setBigDecimal(
                    13,
                    kardex.getExistenciaAnterior()
            );

            statement.setBigDecimal(
                    14,
                    kardex.getExistenciaActual()
            );

            statement.setBigDecimal(
                    15,
                    kardex.getCostoUnitario()
            );

            statement.setBigDecimal(
                    16,
                    kardex.getCostoTotal()
            );

            if (kardex.getObservaciones() != null) {
                statement.setString(
                        17,
                        kardex.getObservaciones()
                );
            } else {
                statement.setNull(
                        17,
                        java.sql.Types.VARCHAR
                );
            }

            int filas = statement.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo guardar el movimiento de Kardex."
                );
            }

            try (ResultSet generatedKeys
                    = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {

                    Long id = generatedKeys.getLong(1);

                    kardex.setId(id);

                    return id;
                }

                throw new SQLException(
                        "No se obtuvo el ID generado del Kardex."
                );
            }
        }
    }

    /**
     * Guarda un movimiento de Kardex usando una conexión compartida. La
     * conexión pertenece al servicio y no debe cerrarse aquí.
     */
    public Long guardar(Connection connection, Kardex kardex)
            throws SQLException {

        String sql = """
        INSERT INTO kardex (
            sucursal_id,
            producto_id,
            presentacion_id,
            inventario_id,
            usuario_id,
            tipo_movimiento,
            referencia_id,
            referencia,
            lote,
            fecha_caducidad,
            entrada,
            salida,
            existencia_anterior,
            existencia_actual,
            costo_unitario,
            costo_total,
            observaciones
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, kardex.getSucursalId());
            statement.setLong(2, kardex.getProductoId());

            if (kardex.getPresentacionId() != null) {
                statement.setLong(3, kardex.getPresentacionId());
            } else {
                statement.setNull(3, java.sql.Types.BIGINT);
            }

            if (kardex.getInventarioId() != null) {
                statement.setLong(4, kardex.getInventarioId());
            } else {
                statement.setNull(4, java.sql.Types.BIGINT);
            }

            statement.setLong(5, kardex.getUsuarioId());
            statement.setString(6, kardex.getTipoMovimiento());

            if (kardex.getReferenciaId() != null) {
                statement.setLong(7, kardex.getReferenciaId());
            } else {
                statement.setNull(7, java.sql.Types.BIGINT);
            }

            statement.setString(8, kardex.getReferencia());
            statement.setString(9, kardex.getLote());

            if (kardex.getFechaCaducidad() != null) {
                statement.setDate(
                        10,
                        java.sql.Date.valueOf(kardex.getFechaCaducidad())
                );
            } else {
                statement.setNull(10, java.sql.Types.DATE);
            }

            statement.setBigDecimal(11, kardex.getEntrada());
            statement.setBigDecimal(12, kardex.getSalida());
            statement.setBigDecimal(13, kardex.getExistenciaAnterior());
            statement.setBigDecimal(14, kardex.getExistenciaActual());
            statement.setBigDecimal(15, kardex.getCostoUnitario());
            statement.setBigDecimal(16, kardex.getCostoTotal());
            statement.setString(17, kardex.getObservaciones());

            int filas = statement.executeUpdate();

            if (filas != 1) {
                throw new SQLException(
                        "No se pudo registrar el movimiento en Kardex."
                );
            }

            try (java.sql.ResultSet resultado
                    = statement.getGeneratedKeys()) {

                if (resultado.next()) {
                    long id = resultado.getLong(1);
                    kardex.setId(id);
                    return id;
                }
            }

            throw new SQLException(
                    "Se registró el movimiento, pero no se obtuvo su ID."
            );
        }
    }

    /**
     * Busca un movimiento de Kardex por ID.
     *
     * @param id ID del movimiento
     * @return movimiento encontrado o null
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Kardex buscarPorId(Long id) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE id = ?
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearKardex(rs);
                }
            }
        }

        return null;
    }

    /**
     * Lista todos los movimientos del Kardex.
     *
     * Los movimientos se muestran del más reciente al más antiguo.
     *
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listar() throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearKardex(rs));
            }
        }

        return lista;
    }

    /**
     * Lista movimientos de una sucursal.
     *
     * @param sucursalId ID de la sucursal
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorSucursal(
            Long sucursalId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE sucursal_id = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos de un producto.
     *
     * @param productoId ID del producto
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorProducto(
            Long productoId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE producto_id = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, productoId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos de un inventario específico.
     *
     * Esto es especialmente útil para consultar el historial de un lote
     * determinado.
     *
     * @param inventarioId ID del inventario
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorInventario(
            Long inventarioId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE inventario_id = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, inventarioId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos de un lote.
     *
     * @param sucursalId ID de la sucursal
     * @param productoId ID del producto
     * @param lote lote
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorLote(
            Long sucursalId,
            Long productoId,
            String lote
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE sucursal_id = ?
                  AND producto_id = ?
                  AND lote = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, sucursalId);
            statement.setLong(2, productoId);
            statement.setString(3, lote);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos por tipo.
     *
     * @param tipoMovimiento tipo de movimiento
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorTipoMovimiento(
            String tipoMovimiento
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE tipo_movimiento = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(1, tipoMovimiento);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos relacionados con una referencia.
     *
     * Ejemplo:
     *
     * referencia = "COMPRA" referenciaId = 15
     *
     * Permite obtener los movimientos generados por una compra específica.
     *
     * @param referencia referencia del movimiento
     * @param referenciaId ID de la referencia
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorReferencia(
            String referencia,
            Long referenciaId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE referencia = ?
                  AND referencia_id = ?
                ORDER BY created_at ASC, id ASC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(1, referencia);
            statement.setLong(2, referenciaId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Lista movimientos de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return lista de movimientos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Kardex> listarPorUsuario(
            Long usuarioId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE usuario_id = ?
                ORDER BY created_at DESC, id DESC
                """;

        List<Kardex> lista = new ArrayList<>();

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, usuarioId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearKardex(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Obtiene el último movimiento de un inventario.
     *
     * @param inventarioId ID del inventario
     * @return último movimiento o null
     * @throws SQLException si ocurre un error con la base de datos
     */
    public Kardex buscarUltimoPorInventario(
            Long inventarioId
    ) throws SQLException {

        String sql = """
                SELECT
                    id,
                    sucursal_id,
                    producto_id,
                    presentacion_id,
                    inventario_id,
                    usuario_id,
                    tipo_movimiento,
                    referencia_id,
                    referencia,
                    lote,
                    fecha_caducidad,
                    entrada,
                    salida,
                    existencia_anterior,
                    existencia_actual,
                    costo_unitario,
                    costo_total,
                    observaciones,
                    created_at
                FROM kardex
                WHERE inventario_id = ?
                ORDER BY created_at DESC, id DESC
                LIMIT 1
                """;

        try (
                Connection connection = Database.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setLong(1, inventarioId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapearKardex(rs);
                }
            }
        }

        return null;
    }

    /**
     * Convierte un ResultSet en un objeto Kardex.
     *
     * @param rs ResultSet
     * @return objeto Kardex
     * @throws SQLException si ocurre un error al leer los datos
     */
    private Kardex mapearKardex(ResultSet rs)
            throws SQLException {

        Kardex kardex = new Kardex();

        kardex.setId(
                rs.getLong("id")
        );

        kardex.setSucursalId(
                rs.getLong("sucursal_id")
        );

        kardex.setProductoId(
                rs.getLong("producto_id")
        );

        long presentacionId
                = rs.getLong("presentacion_id");

        if (!rs.wasNull()) {
            kardex.setPresentacionId(
                    presentacionId
            );
        }

        long inventarioId
                = rs.getLong("inventario_id");

        if (!rs.wasNull()) {
            kardex.setInventarioId(
                    inventarioId
            );
        }

        kardex.setUsuarioId(
                rs.getLong("usuario_id")
        );

        kardex.setTipoMovimiento(
                rs.getString("tipo_movimiento")
        );

        long referenciaId
                = rs.getLong("referencia_id");

        if (!rs.wasNull()) {
            kardex.setReferenciaId(
                    referenciaId
            );
        }

        kardex.setReferencia(
                rs.getString("referencia")
        );

        kardex.setLote(
                rs.getString("lote")
        );

        Date fechaCaducidad
                = rs.getDate("fecha_caducidad");

        if (fechaCaducidad != null) {
            kardex.setFechaCaducidad(
                    fechaCaducidad.toLocalDate()
            );
        }

        kardex.setEntrada(
                rs.getBigDecimal("entrada")
        );

        kardex.setSalida(
                rs.getBigDecimal("salida")
        );

        kardex.setExistenciaAnterior(
                rs.getBigDecimal(
                        "existencia_anterior"
                )
        );

        kardex.setExistenciaActual(
                rs.getBigDecimal(
                        "existencia_actual"
                )
        );

        kardex.setCostoUnitario(
                rs.getBigDecimal(
                        "costo_unitario"
                )
        );

        kardex.setCostoTotal(
                rs.getBigDecimal(
                        "costo_total"
                )
        );

        kardex.setObservaciones(
                rs.getString("observaciones")
        );

        Timestamp createdAt
                = rs.getTimestamp("created_at");

        if (createdAt != null) {
            kardex.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return kardex;
    }

}
