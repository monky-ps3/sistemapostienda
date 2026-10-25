/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.UnidadMedida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 * ============================================================
 * SistemaPOSTienda
 *
 * DAO encargado de administrar las unidades de medida.
 *
 * Se encarga directamente de realizar las operaciones
 * sobre la tabla unidades_medida.
 *
 * ============================================================
 */
public class UnidadMedidaDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */

    /**
     * Inserta una nueva unidad de medida.
     */
    private static final String INSERTAR = """
        INSERT INTO unidades_medida
        (nombre, codigo, abreviatura, descripcion, activo)
        VALUES (?, ?, ?, ?, ?)
        """;

    /**
     * Actualiza una unidad de medida existente.
     */
    private static final String ACTUALIZAR = """
        UPDATE unidades_medida
        SET nombre = ?,
            codigo = ?,
            abreviatura = ?,
            descripcion = ?,
            activo = ?,
            updated_at = CURRENT_TIMESTAMP
        WHERE id = ?
        """;

    /**
     * Elimina una unidad de medida.
     */
    private static final String ELIMINAR = """
        DELETE FROM unidades_medida
        WHERE id = ?
        """;

    /**
     * Busca una unidad de medida por ID.
     */
    private static final String BUSCAR_POR_ID = """
        SELECT *
        FROM unidades_medida
        WHERE id = ?
        """;

    /**
     * Busca una unidad de medida por nombre.
     */
    private static final String BUSCAR_POR_NOMBRE = """
        SELECT *
        FROM unidades_medida
        WHERE nombre = ?
        """;

    /**
     * Busca una unidad de medida por código.
     */
    private static final String BUSCAR_POR_CODIGO = """
        SELECT *
        FROM unidades_medida
        WHERE codigo = ?
        """;

    /**
     * Busca una unidad de medida por abreviatura.
     */
    private static final String BUSCAR_POR_ABREVIATURA = """
        SELECT *
        FROM unidades_medida
        WHERE abreviatura = ?
        """;

    /**
     * Lista todas las unidades de medida.
     */
    private static final String LISTAR_TODOS = """
        SELECT *
        FROM unidades_medida
        ORDER BY nombre
        """;

    /**
     * Lista únicamente las unidades activas.
     */
    private static final String LISTAR_ACTIVOS = """
        SELECT *
        FROM unidades_medida
        WHERE activo = TRUE
        ORDER BY nombre
        """;

    /**
     * Verifica si existe un nombre.
     */
    private static final String EXISTE_NOMBRE = """
        SELECT COUNT(*)
        FROM unidades_medida
        WHERE nombre = ?
        """;

    /**
     * Verifica si existe un código.
     */
    private static final String EXISTE_CODIGO = """
        SELECT COUNT(*)
        FROM unidades_medida
        WHERE codigo = ?
        """;

    /**
     * Verifica si existe una abreviatura.
     */
    private static final String EXISTE_ABREVIATURA = """
        SELECT COUNT(*)
        FROM unidades_medida
        WHERE abreviatura = ?
        """;

    /**
     * Constructor.
     */
    public UnidadMedidaDAO() {

    }

    /**
     * Guarda una nueva unidad de medida.
     *
     * @param unidadMedida Unidad de medida.
     *
     * @return true si fue registrada correctamente.
     */
    public boolean guardar(UnidadMedida unidadMedida) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(INSERTAR)
        ) {

            ps.setString(
                    1,
                    unidadMedida.getNombre()
            );

            ps.setString(
                    2,
                    unidadMedida.getCodigo()
            );

            ps.setString(
                    3,
                    unidadMedida.getAbreviatura()
            );

            ps.setString(
                    4,
                    unidadMedida.getDescripcion()
            );

            ps.setBoolean(
                    5,
                    unidadMedida.getActivo()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar la unidad de medida."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Actualiza una unidad de medida existente.
     *
     * @param unidadMedida Unidad de medida.
     *
     * @return true si fue actualizada correctamente.
     */
    public boolean actualizar(UnidadMedida unidadMedida) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(ACTUALIZAR)
        ) {

            ps.setString(
                    1,
                    unidadMedida.getNombre()
            );

            ps.setString(
                    2,
                    unidadMedida.getCodigo()
            );

            ps.setString(
                    3,
                    unidadMedida.getAbreviatura()
            );

            ps.setString(
                    4,
                    unidadMedida.getDescripcion()
            );

            ps.setBoolean(
                    5,
                    unidadMedida.getActivo()
            );

            ps.setLong(
                    6,
                    unidadMedida.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar la unidad de medida."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }
    
    /**
     * Elimina una unidad de medida.
     *
     * @param id ID de la unidad de medida.
     *
     * @return true si fue eliminada correctamente.
     */
    public boolean eliminar(Long id) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(ELIMINAR)
        ) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar la unidad de medida."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Busca una unidad de medida por su ID.
     *
     * @param id Identificador de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorId(Long id) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_ID)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    UnidadMedida unidad =
                            new UnidadMedida();

                    unidad.setId(
                            rs.getLong("id")
                    );

                    unidad.setNombre(
                            rs.getString("nombre")
                    );

                    unidad.setCodigo(
                            rs.getString("codigo")
                    );

                    unidad.setAbreviatura(
                            rs.getString("abreviatura")
                    );

                    unidad.setDescripcion(
                            rs.getString("descripcion")
                    );

                    unidad.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        unidad.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        unidad.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return unidad;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la unidad de medida por ID."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una unidad de medida por nombre.
     *
     * @param nombre Nombre de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                BUSCAR_POR_NOMBRE
                        )
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    UnidadMedida unidad =
                            new UnidadMedida();

                    unidad.setId(
                            rs.getLong("id")
                    );

                    unidad.setNombre(
                            rs.getString("nombre")
                    );

                    unidad.setCodigo(
                            rs.getString("codigo")
                    );

                    unidad.setAbreviatura(
                            rs.getString("abreviatura")
                    );

                    unidad.setDescripcion(
                            rs.getString("descripcion")
                    );

                    unidad.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        unidad.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        unidad.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return unidad;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la unidad de medida por nombre."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una unidad de medida por código.
     *
     * @param codigo Código de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorCodigo(String codigo) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                BUSCAR_POR_CODIGO
                        )
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    UnidadMedida unidad =
                            new UnidadMedida();

                    unidad.setId(
                            rs.getLong("id")
                    );

                    unidad.setNombre(
                            rs.getString("nombre")
                    );

                    unidad.setCodigo(
                            rs.getString("codigo")
                    );

                    unidad.setAbreviatura(
                            rs.getString("abreviatura")
                    );

                    unidad.setDescripcion(
                            rs.getString("descripcion")
                    );

                    unidad.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        unidad.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        unidad.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return unidad;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la unidad de medida por código."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una unidad de medida por abreviatura.
     *
     * @param abreviatura Abreviatura de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorAbreviatura(
            String abreviatura) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                BUSCAR_POR_ABREVIATURA
                        )
        ) {

            ps.setString(1, abreviatura);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    UnidadMedida unidad =
                            new UnidadMedida();

                    unidad.setId(
                            rs.getLong("id")
                    );

                    unidad.setNombre(
                            rs.getString("nombre")
                    );

                    unidad.setCodigo(
                            rs.getString("codigo")
                    );

                    unidad.setAbreviatura(
                            rs.getString("abreviatura")
                    );

                    unidad.setDescripcion(
                            rs.getString("descripcion")
                    );

                    unidad.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        unidad.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        unidad.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return unidad;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la unidad de medida por abreviatura."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }
    
    /**
     * Obtiene todas las unidades de medida.
     *
     * @return Lista con todas las unidades.
     */
    public List<UnidadMedida> listarTodos() {

        List<UnidadMedida> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_TODOS
                        );

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                UnidadMedida unidad =
                        new UnidadMedida();

                unidad.setId(
                        rs.getLong("id")
                );

                unidad.setNombre(
                        rs.getString("nombre")
                );

                unidad.setCodigo(
                        rs.getString("codigo")
                );

                unidad.setAbreviatura(
                        rs.getString("abreviatura")
                );

                unidad.setDescripcion(
                        rs.getString("descripcion")
                );

                unidad.setActivo(
                        rs.getBoolean("activo")
                );

                if (rs.getTimestamp("created_at") != null) {

                    unidad.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    unidad.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(unidad);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar las unidades de medida."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Obtiene únicamente las unidades de medida activas.
     *
     * @return Lista de unidades activas.
     */
    public List<UnidadMedida> listarActivos() {

        List<UnidadMedida> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_ACTIVOS
                        );

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                UnidadMedida unidad =
                        new UnidadMedida();

                unidad.setId(
                        rs.getLong("id")
                );

                unidad.setNombre(
                        rs.getString("nombre")
                );

                unidad.setCodigo(
                        rs.getString("codigo")
                );

                unidad.setAbreviatura(
                        rs.getString("abreviatura")
                );

                unidad.setDescripcion(
                        rs.getString("descripcion")
                );

                unidad.setActivo(
                        rs.getBoolean("activo")
                );

                if (rs.getTimestamp("created_at") != null) {

                    unidad.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    unidad.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(unidad);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar las unidades activas."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Verifica si ya existe una unidad
     * con determinado nombre.
     *
     * @param nombre Nombre de la unidad.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                EXISTE_NOMBRE
                        )
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar el nombre de la unidad."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Verifica si ya existe una unidad
     * con determinado código.
     *
     * @param codigo Código de la unidad.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                EXISTE_CODIGO
                        )
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar el código de la unidad."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Verifica si ya existe una unidad
     * con determinada abreviatura.
     *
     * @param abreviatura Abreviatura de la unidad.
     *
     * @return true si existe.
     */
    public boolean existeAbreviatura(
            String abreviatura) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                EXISTE_ABREVIATURA
                        )
        ) {

            ps.setString(1, abreviatura);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar la abreviatura de la unidad."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

}