/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *
 * SistemaPOSTienda
 *
 * DAO encargado de realizar todas las operaciones sobre la tabla roles.
 *
 * Todas las consultas SQL relacionadas con Roles deben realizarse desde esta
 * clase.
 *
 * ============================================================
 */
public class RolDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */
    private static final String INSERTAR
            = """ 
              INSERT INTO roles(nombre,descripcion,activo)VALUES(?, ?, ?)""";

    private static final String ACTUALIZAR
            = """
              UPDATE roles SET nombre = ?,descripcion = ?, activo = ? WHERE id = ? """;

    private static final String ELIMINAR
            = """
            DELETE FROM roles WHERE id = ? """;

    private static final String BUSCAR_ID
            = """
            SELECT * FROM roles WHERE id = ? """;

    private static final String BUSCAR_NOMBRE
            = """ 
              SELECT * FROM roles WHERE nombre = ? """;

    private static final String LISTAR
            = """
            SELECT * FROM roles ORDER BY nombre """;

    private static final String LISTAR_ACTIVOS
            = """
            SELECT * FROM roles WHERE activo = TRUE ORDER BY nombre """;

    private static final String EXISTE_NOMBRE
            = """ 
            SELECT COUNT(*) FROM roles WHERE nombre = ? """;

    /**
     * Constructor.
     */
    public RolDAO() {

    }

    /**
     * Convierte un registro de la base de datos en un objeto Rol.
     *
     * Este método evita repetir código en todas las consultas.
     *
     * @param rs ResultSet de la consulta
     * @return Objeto Rol
     * @throws SQLException
     */
    private Rol mapearRol(ResultSet rs) throws SQLException {

        Rol rol = new Rol();

        rol.setId(rs.getLong("id"));

        rol.setNombre(rs.getString("nombre"));

        rol.setDescripcion(rs.getString("descripcion"));

        rol.setActivo(rs.getBoolean("activo"));

        if (rs.getTimestamp("created_at") != null) {
            rol.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        if (rs.getTimestamp("updated_at") != null) {
            rol.setUpdatedAt(
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        return rol;

    }

    /**
     * Guarda un nuevo rol.
     *
     * @param rol Rol a guardar.
     * @return true si el registro fue insertado.
     */
    public boolean guardar(Rol rol) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(INSERTAR)) {

            ps.setString(1, rol.getNombre());

            ps.setString(2, rol.getDescripcion());

            ps.setBoolean(3, rol.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al guardar el rol.");

            System.err.println(e.getMessage());

        }

        return false;

    }

    /**
     * Actualiza la información de un rol existente.
     *
     * @param rol Rol con la información actualizada.
     * @return true si el registro fue actualizado.
     */
    public boolean actualizar(Rol rol) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(ACTUALIZAR)) {

            ps.setString(1, rol.getNombre());

            ps.setString(2, rol.getDescripcion());

            ps.setBoolean(3, rol.isActivo());

            ps.setLong(4, rol.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al actualizar el rol.");

            System.err.println(e.getMessage());

        }

        return false;

    }

    /**
     * Elimina un rol por su identificador.
     *
     * Nota: En el futuro podría cambiarse por eliminación lógica, estableciendo
     * activo = FALSE.
     *
     * @param id Identificador del rol.
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(ELIMINAR)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al eliminar el rol.");

            System.err.println(e.getMessage());

        }

        return false;

    }

    /**
     * Busca un rol mediante su identificador.
     *
     * @param id Identificador del rol.
     * @return Objeto Rol o null si no existe.
     */
    public Rol buscarPorId(Long id) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(BUSCAR_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearRol(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al buscar el rol por ID.");

            System.err.println(e.getMessage());

        }

        return null;

    }

    /**
     * Busca un rol por su nombre.
     *
     * Se utiliza principalmente para validar registros duplicados.
     *
     * @param nombre Nombre del rol.
     * @return Objeto Rol o null si no existe.
     */
    public Rol buscarPorNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(BUSCAR_NOMBRE)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearRol(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al buscar el rol por nombre.");

            System.err.println(e.getMessage());

        }

        return null;

    }

    /**
     * Obtiene todos los roles registrados.
     *
     * La lista se ordena alfabéticamente por nombre.
     *
     * @return Lista de roles.
     */
    public List<Rol> listarTodos() {

        List<Rol> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(LISTAR); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(mapearRol(rs));

            }

        } catch (SQLException e) {

            System.err.println("Error al listar los roles.");

            System.err.println(e.getMessage());

        }

        return lista;

    }

    /**
     * Obtiene únicamente los roles activos.
     *
     * Se utiliza principalmente para llenar JComboBox y listas de selección.
     *
     * @return Lista de roles activos.
     */
    public List<Rol> listarActivos() {

        List<Rol> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(LISTAR_ACTIVOS); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(mapearRol(rs));

            }

        } catch (SQLException e) {

            System.err.println("Error al listar los roles activos.");

            System.err.println(e.getMessage());

        }

        return lista;

    }

    /**
     * Verifica si ya existe un rol con el mismo nombre.
     *
     * Se utiliza para evitar registros duplicados.
     *
     * @param nombre Nombre del rol.
     * @return true si el nombre ya existe.
     */
    public boolean existeNombre(String nombre) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(EXISTE_NOMBRE)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al validar el nombre del rol.");

            System.err.println(e.getMessage());

        }

        return false;

    }

}
