/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Presentacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PresentacionDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */

    /*
     * Inserta una nueva presentación.
     */
    private static final String INSERTAR
            = """
            INSERT INTO presentaciones
            (
                producto_id,
                nombre,
                abreviatura,
                factor_conversion,
                es_predeterminada,
                permite_venta,
                activa,
                orden,
                observaciones
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    /*
     * Actualiza una presentación existente.
     */
    private static final String ACTUALIZAR
            = """
            UPDATE presentaciones
            SET
                producto_id = ?,
                nombre = ?,
                abreviatura = ?,
                factor_conversion = ?,
                es_predeterminada = ?,
                permite_venta = ?,
                activa = ?,
                orden = ?,
                observaciones = ?
            WHERE id = ?
            """;

    /*
     * Elimina una presentación.
     */
    private static final String ELIMINAR
            = """
            DELETE FROM presentaciones
            WHERE id = ?
            """;

    /*
     * Busca una presentación por ID.
     */
    private static final String BUSCAR_POR_ID
            = """
            SELECT *
            FROM presentaciones
            WHERE id = ?
            """;

    /*
     * Lista todas las presentaciones.
     */
    private static final String LISTAR_TODAS
            = """
            SELECT *
            FROM presentaciones
            ORDER BY producto_id, orden, nombre
            """;

    /*
     * Lista las presentaciones de un producto.
     */
    private static final String LISTAR_POR_PRODUCTO
            = """
            SELECT *
            FROM presentaciones
            WHERE producto_id = ?
            ORDER BY orden, nombre
            """;

    /*
     * Lista únicamente las presentaciones activas
     * de un producto.
     */
    private static final String LISTAR_ACTIVAS_POR_PRODUCTO
            = """
            SELECT *
            FROM presentaciones
            WHERE producto_id = ?
              AND activa = TRUE
              AND permite_venta = TRUE
            ORDER BY orden, nombre
            """;

    /*
     * Busca la presentación predeterminada
     * de un producto.
     */
    private static final String BUSCAR_PREDETERMINADA
            = """
            SELECT *
            FROM presentaciones
            WHERE producto_id = ?
              AND es_predeterminada = TRUE
            LIMIT 1
            """;

    /*
     * Verifica si existe una presentación
     * con el mismo nombre para el producto.
     */
    private static final String EXISTE_NOMBRE
            = """
            SELECT COUNT(*)
            FROM presentaciones
            WHERE producto_id = ?
              AND nombre = ?
            """;

    /*
     * Verifica nombre duplicado excluyendo
     * la presentación que estamos actualizando.
     */
    private static final String EXISTE_NOMBRE_EXCEPTO_ID
            = """
            SELECT COUNT(*)
            FROM presentaciones
            WHERE producto_id = ?
              AND nombre = ?
              AND id <> ?
            """;

    /*
     * Cuenta cuántas presentaciones predeterminadas
     * tiene un producto.
     */
    private static final String CONTAR_PREDETERMINADAS
            = """
            SELECT COUNT(*)
            FROM presentaciones
            WHERE producto_id = ?
              AND es_predeterminada = TRUE
            """;

    /*
     * Elimina la marca de predeterminada de todas
     * las presentaciones de un producto.
     */
    private static final String QUITAR_PREDETERMINADA
            = """
            UPDATE presentaciones
            SET es_predeterminada = FALSE
            WHERE producto_id = ?
            """;

    /*
     * ============================================================
     * CONSTRUCTOR
     * ============================================================
     */

    public PresentacionDAO() {

    }

    /*
     * ============================================================
     * GUARDAR
     * ============================================================
     */

    /**
     * Guarda una nueva presentación.
     *
     * @param presentacion Presentación a guardar.
     *
     * @return true si se guardó correctamente.
     */
    public boolean guardar(
            Presentacion presentacion) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                INSERTAR
                        )
        ) {

            ps.setLong(
                    1,
                    presentacion.getProductoId()
            );

            ps.setString(
                    2,
                    presentacion.getNombre()
            );

            ps.setString(
                    3,
                    presentacion.getAbreviatura()
            );

            ps.setBigDecimal(
                    4,
                    presentacion.getFactorConversion()
            );

            ps.setBoolean(
                    5,
                    presentacion.getEsPredeterminada()
            );

            ps.setBoolean(
                    6,
                    presentacion.getPermiteVenta()
            );

            ps.setBoolean(
                    7,
                    presentacion.getActiva()
            );

            ps.setInt(
                    8,
                    presentacion.getOrden()
            );

            ps.setString(
                    9,
                    presentacion.getObservaciones()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar la presentación."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */

    /**
     * Actualiza una presentación existente.
     *
     * @param presentacion Presentación actualizada.
     *
     * @return true si se actualizó correctamente.
     */
    public boolean actualizar(
            Presentacion presentacion) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                ACTUALIZAR
                        )
        ) {

            ps.setLong(
                    1,
                    presentacion.getProductoId()
            );

            ps.setString(
                    2,
                    presentacion.getNombre()
            );

            ps.setString(
                    3,
                    presentacion.getAbreviatura()
            );

            ps.setBigDecimal(
                    4,
                    presentacion.getFactorConversion()
            );

            ps.setBoolean(
                    5,
                    presentacion.getEsPredeterminada()
            );

            ps.setBoolean(
                    6,
                    presentacion.getPermiteVenta()
            );

            ps.setBoolean(
                    7,
                    presentacion.getActiva()
            );

            ps.setInt(
                    8,
                    presentacion.getOrden()
            );

            ps.setString(
                    9,
                    presentacion.getObservaciones()
            );

            ps.setLong(
                    10,
                    presentacion.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar la presentación."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina una presentación por su ID.
     *
     * @param id ID de la presentación.
     *
     * @return true si fue eliminada correctamente.
     */
    public boolean eliminar(Long id) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                ELIMINAR
                        )
        ) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar la presentación."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */

    /**
     * Busca una presentación por su ID.
     *
     * @param id ID de la presentación.
     *
     * @return Presentación encontrada o null.
     */
    public Presentacion buscarPorId(Long id) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                BUSCAR_POR_ID
                        )
        ) {

            ps.setLong(1, id);

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {

                    return mapearPresentacion(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar presentación por ID."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /*
     * ============================================================
     * LISTAR TODAS
     * ============================================================
     */

    /**
     * Obtiene todas las presentaciones.
     *
     * @return Lista completa de presentaciones.
     */
    public List<Presentacion> listarTodas() {

        List<Presentacion> lista =
                new ArrayList<>();

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_TODAS
                        );

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        mapearPresentacion(rs)
                );

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar presentaciones."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * LISTAR POR PRODUCTO
     * ============================================================
     */

    /**
     * Obtiene todas las presentaciones
     * pertenecientes a un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Lista de presentaciones.
     */
    public List<Presentacion> listarPorProducto(
            Long productoId) {

        List<Presentacion> lista =
                new ArrayList<>();

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_POR_PRODUCTO
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapearPresentacion(rs)
                    );

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar presentaciones "
                    + "del producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * LISTAR ACTIVAS
     * ============================================================
     */

    /**
     * Obtiene las presentaciones activas
     * y permitidas para venta de un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Lista de presentaciones disponibles para venta.
     */
    public List<Presentacion> listarActivasPorProducto(
            Long productoId) {

        List<Presentacion> lista =
                new ArrayList<>();

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_ACTIVAS_POR_PRODUCTO
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapearPresentacion(rs)
                    );

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar presentaciones "
                    + "activas del producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * BUSCAR PREDETERMINADA
     * ============================================================
     */

    /**
     * Obtiene la presentación predeterminada
     * de un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Presentación predeterminada o null.
     */
    public Presentacion buscarPredeterminada(
            Long productoId) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                BUSCAR_PREDETERMINADA
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {

                    return mapearPresentacion(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar presentación "
                    + "predeterminada."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /*
     * ============================================================
     * MAPEAR PRESENTACIÓN
     * ============================================================
     */

    /**
     * Convierte un ResultSet en un objeto Presentacion.
     *
     * @param rs Resultado de la consulta.
     *
     * @return Presentacion.
     *
     * @throws SQLException Si ocurre un error al leer los datos.
     */
    private Presentacion mapearPresentacion(
            ResultSet rs) throws SQLException {

        Presentacion presentacion =
                new Presentacion();

        /*
         * ID
         */
        presentacion.setId(
                rs.getLong("id")
        );

        /*
         * PRODUCTO
         */
        presentacion.setProductoId(
                rs.getLong("producto_id")
        );

        /*
         * DATOS DE LA PRESENTACIÓN
         */
        presentacion.setNombre(
                rs.getString("nombre")
        );

        presentacion.setAbreviatura(
                rs.getString("abreviatura")
        );

        presentacion.setFactorConversion(
                rs.getBigDecimal(
                        "factor_conversion"
                )
        );

        /*
         * CONFIGURACIÓN
         */
        presentacion.setEsPredeterminada(
                rs.getBoolean(
                        "es_predeterminada"
                )
        );

        presentacion.setPermiteVenta(
                rs.getBoolean(
                        "permite_venta"
                )
        );

        presentacion.setActiva(
                rs.getBoolean("activa")
        );

        presentacion.setOrden(
                rs.getInt("orden")
        );

        /*
         * OBSERVACIONES
         */
        presentacion.setObservaciones(
                rs.getString("observaciones")
        );

        /*
         * FECHAS
         */
        if (rs.getTimestamp("created_at") != null) {

            presentacion.setCreatedAt(
                    rs.getTimestamp("created_at")
                            .toLocalDateTime()
            );

        }

        if (rs.getTimestamp("updated_at") != null) {

            presentacion.setUpdatedAt(
                    rs.getTimestamp("updated_at")
                            .toLocalDateTime()
            );

        }

        return presentacion;

    }
   
    /*
     * ============================================================
     * EXISTE NOMBRE
     * ============================================================
     */

    /**
     * Verifica si ya existe una presentación
     * con determinado nombre para un producto.
     *
     * @param productoId ID del producto.
     * @param nombre Nombre de la presentación.
     *
     * @return true si existe.
     */
    public boolean existeNombre(
            Long productoId,
            String nombre) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                EXISTE_NOMBRE
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            ps.setString(
                    2,
                    nombre
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar nombre "
                    + "de presentación."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * EXISTE NOMBRE EXCEPTO ID
     * ============================================================
     */

    /**
     * Verifica si existe otra presentación
     * con el mismo nombre para el producto.
     *
     * Se utiliza al actualizar.
     *
     * @param productoId ID del producto.
     * @param nombre Nombre de la presentación.
     * @param id ID de la presentación actual.
     *
     * @return true si existe otra presentación
     * con ese nombre.
     */
    public boolean existeNombreExceptoId(
            Long productoId,
            String nombre,
            Long id) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                EXISTE_NOMBRE_EXCEPTO_ID
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            ps.setString(
                    2,
                    nombre
            );

            ps.setLong(
                    3,
                    id
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar nombre "
                    + "de presentación."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * CONTAR PREDETERMINADAS
     * ============================================================
     */

    /**
     * Cuenta cuántas presentaciones predeterminadas
     * tiene actualmente un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Número de presentaciones predeterminadas.
     */
    public int contarPredeterminadas(
            Long productoId) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                CONTAR_PREDETERMINADAS
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            try (ResultSet rs =
                    ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al contar presentaciones "
                    + "predeterminadas."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return 0;

    }

    /*
     * ============================================================
     * QUITAR PREDETERMINADA
     * ============================================================
     */

    /**
     * Quita la marca de predeterminada
     * a todas las presentaciones de un producto.
     *
     * Después de ejecutar este método podremos
     * establecer una nueva presentación como
     * predeterminada.
     *
     * @param productoId ID del producto.
     *
     * @return true si se modificó al menos un registro.
     */
    public boolean quitarPredeterminada(
            Long productoId) {

        try (
                Connection conexion =
                        Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                QUITAR_PREDETERMINADA
                        )
        ) {

            ps.setLong(
                    1,
                    productoId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al quitar presentación "
                    + "predeterminada."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

}


    

