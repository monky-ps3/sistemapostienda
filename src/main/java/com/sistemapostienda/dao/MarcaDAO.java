/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Marca;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * DAO encargado de administrar las marcas.
 *
 * Contiene todas las operaciones CRUD sobre
 * la tabla marcas.
 *
 * ============================================================
 */
public class MarcaDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */

    /**
     * Inserta una nueva marca.
     */
    private static final String INSERTAR = """
        INSERT INTO marcas
        (nombre,codigo,descripcion,activo)
        VALUES (?,?,?,?)
        """;

    /**
     * Actualiza una marca.
     */
    private static final String ACTUALIZAR = """
        UPDATE marcas
        SET nombre=?,
            codigo=?,
            descripcion=?,
            activo=?,
            updated_at=CURRENT_TIMESTAMP
        WHERE id=?
        """;

    /**
     * Elimina una marca.
     */
    private static final String ELIMINAR = """
        DELETE FROM marcas
        WHERE id=?
        """;

    /**
     * Busca una marca por ID.
     */
    private static final String BUSCAR_POR_ID = """
        SELECT *
        FROM marcas
        WHERE id=?
        """;

    /**
     * Busca una marca por nombre.
     */
    private static final String BUSCAR_POR_NOMBRE = """
        SELECT *
        FROM marcas
        WHERE nombre=?
        """;

    /**
     * Busca una marca por código.
     */
    private static final String BUSCAR_POR_CODIGO = """
        SELECT *
        FROM marcas
        WHERE codigo=?
        """;

    /**
     * Lista todas las marcas.
     */
    private static final String LISTAR_TODOS = """
        SELECT *
        FROM marcas
        ORDER BY nombre
        """;

    /**
     * Lista únicamente las marcas activas.
     */
    private static final String LISTAR_ACTIVOS = """
        SELECT *
        FROM marcas
        WHERE activo=TRUE
        ORDER BY nombre
        """;

    /**
     * Verifica si existe un nombre.
     */
    private static final String EXISTE_NOMBRE = """
        SELECT COUNT(*)
        FROM marcas
        WHERE nombre=?
        """;

    /**
     * Verifica si existe un código.
     */
    private static final String EXISTE_CODIGO = """
        SELECT COUNT(*)
        FROM marcas
        WHERE codigo=?
        """;

    /**
     * Constructor.
     */
    public MarcaDAO() {

    }

    /**
     * Guarda una nueva marca.
     *
     * @param marca Marca.
     * @return true si fue registrada.
     */
    public boolean guardar(Marca marca) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(INSERTAR)
        ) {

            ps.setString(1, marca.getNombre());
            ps.setString(2, marca.getCodigo());
            ps.setString(3, marca.getDescripcion());
            ps.setBoolean(4, marca.getActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar la marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Actualiza una marca.
     *
     * @param marca Marca.
     * @return true si fue actualizada.
     */
    public boolean actualizar(Marca marca) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(ACTUALIZAR)
        ) {

            ps.setString(1, marca.getNombre());
            ps.setString(2, marca.getCodigo());
            ps.setString(3, marca.getDescripcion());
            ps.setBoolean(4, marca.getActivo());
            ps.setLong(5, marca.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar la marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }
    
    /**
     * Elimina una marca.
     *
     * @param id ID de la marca.
     *
     * @return true si fue eliminada.
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
                    "Error al eliminar la marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Busca una marca por su ID.
     *
     * @param id Identificador.
     *
     * @return Marca encontrada o null.
     */
    public Marca buscarPorId(Long id) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_ID)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Marca marca = new Marca();

                    marca.setId(
                            rs.getLong("id")
                    );

                    marca.setNombre(
                            rs.getString("nombre")
                    );

                    marca.setCodigo(
                            rs.getString("codigo")
                    );

                    marca.setDescripcion(
                            rs.getString("descripcion")
                    );

                    marca.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        marca.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        marca.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return marca;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la marca por ID."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una marca por nombre.
     *
     * @param nombre Nombre de la marca.
     *
     * @return Marca encontrada o null.
     */
    public Marca buscarPorNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_NOMBRE)
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Marca marca = new Marca();

                    marca.setId(rs.getLong("id"));
                    marca.setNombre(rs.getString("nombre"));
                    marca.setCodigo(rs.getString("codigo"));
                    marca.setDescripcion(rs.getString("descripcion"));
                    marca.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("created_at") != null) {

                        marca.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        marca.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return marca;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la marca por nombre."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una marca por código.
     *
     * @param codigo Código de la marca.
     *
     * @return Marca encontrada o null.
     */
    public Marca buscarPorCodigo(String codigo) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_CODIGO)
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Marca marca = new Marca();

                    marca.setId(rs.getLong("id"));
                    marca.setNombre(rs.getString("nombre"));
                    marca.setCodigo(rs.getString("codigo"));
                    marca.setDescripcion(rs.getString("descripcion"));
                    marca.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("created_at") != null) {

                        marca.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        marca.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return marca;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la marca por código."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }
    
    /**
     * Obtiene todas las marcas.
     *
     * @return Lista de marcas.
     */
    public List<Marca> listarTodos() {

        List<Marca> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_TODOS);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Marca marca = new Marca();

                marca.setId(
                        rs.getLong("id")
                );

                marca.setNombre(
                        rs.getString("nombre")
                );

                marca.setCodigo(
                        rs.getString("codigo")
                );

                marca.setDescripcion(
                        rs.getString("descripcion")
                );

                marca.setActivo(
                        rs.getBoolean("activo")
                );

                if (rs.getTimestamp("created_at") != null) {

                    marca.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    marca.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(marca);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar marcas."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Obtiene únicamente las marcas activas.
     *
     * @return Lista de marcas activas.
     */
    public List<Marca> listarActivos() {

        List<Marca> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_ACTIVOS);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Marca marca = new Marca();

                marca.setId(rs.getLong("id"));
                marca.setNombre(rs.getString("nombre"));
                marca.setCodigo(rs.getString("codigo"));
                marca.setDescripcion(rs.getString("descripcion"));
                marca.setActivo(rs.getBoolean("activo"));

                if (rs.getTimestamp("created_at") != null) {

                    marca.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    marca.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(marca);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar marcas activas."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Verifica si existe una marca
     * con el mismo nombre.
     *
     * @param nombre Nombre de la marca.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_NOMBRE)
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar el nombre de la marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Verifica si existe una marca
     * con el mismo código.
     *
     * @param codigo Código de la marca.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_CODIGO)
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar el código de la marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

}