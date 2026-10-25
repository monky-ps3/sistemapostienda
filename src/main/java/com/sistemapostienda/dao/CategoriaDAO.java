/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;


import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * DAO encargado de administrar las categorías.
 *
 * Contiene todas las operaciones CRUD sobre la
 * tabla categorias.
 *
 * ============================================================
 */
public class CategoriaDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */

    /**
     * Inserta una nueva categoría.
     */
    private static final String INSERTAR = """
        INSERT INTO categorias
        (nombre,codigo,descripcion,color,icono,orden,activo)
        VALUES (?,?,?,?,?,?,?)
        """;

    /**
     * Actualiza una categoría.
     */
    private static final String ACTUALIZAR = """
        UPDATE categorias
        SET nombre=?,
            codigo=?,
            descripcion=?,
            color=?,
            icono=?,
            orden=?,
            activo=?,
            updated_at=CURRENT_TIMESTAMP
        WHERE id=?
        """;

    /**
     * Elimina una categoría.
     */
    private static final String ELIMINAR = """
        DELETE FROM categorias
        WHERE id=?
        """;

    /**
     * Busca una categoría por ID.
     */
    private static final String BUSCAR_POR_ID = """
        SELECT *
        FROM categorias
        WHERE id=?
        """;

    /**
     * Busca una categoría por nombre.
     */
    private static final String BUSCAR_POR_NOMBRE = """
        SELECT *
        FROM categorias
        WHERE nombre=?
        """;

    /**
     * Busca una categoría por código.
     */
    private static final String BUSCAR_POR_CODIGO = """
        SELECT *
        FROM categorias
        WHERE codigo=?
        """;

    /**
     * Lista todas las categorías.
     */
    private static final String LISTAR_TODOS = """
        SELECT *
        FROM categorias
        ORDER BY orden,nombre
        """;

    /**
     * Lista únicamente las categorías activas.
     */
    private static final String LISTAR_ACTIVOS = """
        SELECT *
        FROM categorias
        WHERE activo=TRUE
        ORDER BY orden,nombre
        """;

    /**
     * Verifica si existe un nombre.
     */
    private static final String EXISTE_NOMBRE = """
        SELECT COUNT(*)
        FROM categorias
        WHERE nombre=?
        """;

    /**
     * Verifica si existe un código.
     */
    private static final String EXISTE_CODIGO = """
        SELECT COUNT(*)
        FROM categorias
        WHERE codigo=?
        """;

    /**
     * Constructor.
     */
    public CategoriaDAO() {

    }

    /**
     * Guarda una nueva categoría.
     *
     * @param categoria Categoría.
     * @return true si fue registrada.
     */
    public boolean guardar(Categoria categoria) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(INSERTAR)
        ) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getCodigo());
            ps.setString(3, categoria.getDescripcion());
            ps.setString(4, categoria.getColor());
            ps.setString(5, categoria.getIcono());
            ps.setInt(6, categoria.getOrden());
            ps.setBoolean(7, categoria.getActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar la categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoria Categoría.
     * @return true si fue actualizada.
     */
    public boolean actualizar(Categoria categoria) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(ACTUALIZAR)
        ) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getCodigo());
            ps.setString(3, categoria.getDescripcion());
            ps.setString(4, categoria.getColor());
            ps.setString(5, categoria.getIcono());
            ps.setInt(6, categoria.getOrden());
            ps.setBoolean(7, categoria.getActivo());
            ps.setLong(8, categoria.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar la categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }
    /**
     * Elimina una categoría.
     *
     * @param id ID de la categoría.
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
                    "Error al eliminar la categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Busca una categoría por su ID.
     *
     * @param id Identificador.
     *
     * @return Categoría encontrada o null.
     */
    public Categoria buscarPorId(Long id) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_ID)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria = new Categoria();

                    categoria.setId(
                            rs.getLong("id")
                    );

                    categoria.setNombre(
                            rs.getString("nombre")
                    );

                    categoria.setCodigo(
                            rs.getString("codigo")
                    );

                    categoria.setDescripcion(
                            rs.getString("descripcion")
                    );

                    categoria.setColor(
                            rs.getString("color")
                    );

                    categoria.setIcono(
                            rs.getString("icono")
                    );

                    categoria.setOrden(
                            rs.getInt("orden")
                    );

                    categoria.setActivo(
                            rs.getBoolean("activo")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        categoria.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        categoria.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return categoria;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la categoría por ID."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una categoría por nombre.
     *
     * @param nombre Nombre de la categoría.
     *
     * @return Categoría encontrada o null.
     */
    public Categoria buscarPorNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_NOMBRE)
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria = new Categoria();

                    categoria.setId(rs.getLong("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setCodigo(rs.getString("codigo"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setColor(rs.getString("color"));
                    categoria.setIcono(rs.getString("icono"));
                    categoria.setOrden(rs.getInt("orden"));
                    categoria.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("created_at") != null) {

                        categoria.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        categoria.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return categoria;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la categoría por nombre."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /**
     * Busca una categoría por código.
     *
     * @param codigo Código de la categoría.
     *
     * @return Categoría encontrada o null.
     */
    public Categoria buscarPorCodigo(String codigo) {

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_POR_CODIGO)
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Categoria categoria = new Categoria();

                    categoria.setId(rs.getLong("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    categoria.setCodigo(rs.getString("codigo"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setColor(rs.getString("color"));
                    categoria.setIcono(rs.getString("icono"));
                    categoria.setOrden(rs.getInt("orden"));
                    categoria.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("created_at") != null) {

                        categoria.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );

                    }

                    if (rs.getTimestamp("updated_at") != null) {

                        categoria.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );

                    }

                    return categoria;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar la categoría por código."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }
        /**
     * Obtiene todas las categorías.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> listarTodos() {

        List<Categoria> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_TODOS);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setId(
                        rs.getLong("id")
                );

                categoria.setNombre(
                        rs.getString("nombre")
                );

                categoria.setCodigo(
                        rs.getString("codigo")
                );

                categoria.setDescripcion(
                        rs.getString("descripcion")
                );

                categoria.setColor(
                        rs.getString("color")
                );

                categoria.setIcono(
                        rs.getString("icono")
                );

                categoria.setOrden(
                        rs.getInt("orden")
                );

                categoria.setActivo(
                        rs.getBoolean("activo")
                );

                if (rs.getTimestamp("created_at") != null) {

                    categoria.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    categoria.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(categoria);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar categorías."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Obtiene únicamente las categorías activas.
     *
     * @return Lista de categorías activas.
     */
    public List<Categoria> listarActivos() {

        List<Categoria> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_ACTIVOS);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setId(rs.getLong("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setCodigo(rs.getString("codigo"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setColor(rs.getString("color"));
                categoria.setIcono(rs.getString("icono"));
                categoria.setOrden(rs.getInt("orden"));
                categoria.setActivo(rs.getBoolean("activo"));

                if (rs.getTimestamp("created_at") != null) {

                    categoria.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                }

                if (rs.getTimestamp("updated_at") != null) {

                    categoria.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                                    .toLocalDateTime()
                    );

                }

                lista.add(categoria);

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar categorías activas."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /**
     * Verifica si existe una categoría
     * con el mismo nombre.
     *
     * @param nombre Nombre de la categoría.
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
                    "Error al verificar el nombre de la categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Verifica si existe una categoría
     * con el mismo código.
     *
     * @param codigo Código de la categoría.
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
                    "Error al verificar el código de la categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

}