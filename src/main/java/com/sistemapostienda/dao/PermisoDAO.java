/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Permiso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * DAO encargado de administrar todas las operaciones
 * sobre la tabla permisos.
 *
 * Todas las consultas relacionadas con permisos deben
 * realizarse desde esta clase.
 *
 * ============================================================
 */
public class PermisoDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */

    private static final String INSERTAR =
            """             
            INSERT INTO permisos(clave,nombre,descripcion,modulo,activo)VALUES (?, ?, ?, ?, ?)""";

    private static final String ACTUALIZAR =
            """
            UPDATE permisos SET clave = ?, nombre = ?, descripcion = ?, modulo = ?, activo = ? WHERE id = ? """;

    private static final String ELIMINAR =
            """
            DELETE FROM permisos WHERE id = ? """;

    private static final String BUSCAR_ID =
            """
            SELECT * FROM permisos WHERE id = ? """;

    private static final String BUSCAR_CLAVE =
            """
            SELECT * FROM permisos WHERE clave = ? """;

    private static final String LISTAR =
            """
            SELECT * FROM permisos ORDER BY modulo, nombre """;

    private static final String LISTAR_ACTIVOS =
            """
            SELECT * FROM permisos WHERE activo = TRUE ORDER BY modulo, nombre """;

    private static final String EXISTE_CLAVE =
            """
            SELECT COUNT(*) FROM permisos WHERE clave = ? """;

    /**
     * Constructor.
     */
    public PermisoDAO() {

    }

    /**
     * Convierte un registro del ResultSet
     * en un objeto Permiso.
     *
     * Este método evita repetir código
     * en todas las consultas.
     *
     * @param rs ResultSet de la consulta.
     * @return Objeto Permiso.
     * @throws SQLException
     */
    private Permiso mapearPermiso(ResultSet rs) throws SQLException {

        Permiso permiso = new Permiso();

        permiso.setId(rs.getLong("id"));

        permiso.setClave(rs.getString("clave"));

        permiso.setNombre(rs.getString("nombre"));

        permiso.setDescripcion(rs.getString("descripcion"));

        permiso.setModulo(rs.getString("modulo"));

        permiso.setActivo(rs.getBoolean("activo"));

        if (rs.getTimestamp("created_at") != null) {

            permiso.setCreatedAt(
                    rs.getTimestamp("created_at")
                            .toLocalDateTime()
            );

        }

        if (rs.getTimestamp("updated_at") != null) {

            permiso.setUpdatedAt(
                    rs.getTimestamp("updated_at")
                            .toLocalDateTime()
            );

        }

        return permiso;

    }

    /**
     * Guarda un nuevo permiso.
     *
     * @param permiso Permiso a guardar.
     * @return true si fue insertado correctamente.
     */
    public boolean guardar(Permiso permiso) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(INSERTAR)
        ) {

            ps.setString(1, permiso.getClave());

            ps.setString(2, permiso.getNombre());

            ps.setString(3, permiso.getDescripcion());

            ps.setString(4, permiso.getModulo());

            ps.setBoolean(5, permiso.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al guardar el permiso.");

            System.err.println(e.getMessage());

        }

        return false;

    }
        /**
     * Actualiza la información de un permiso existente.
     *
     * @param permiso Permiso con la información actualizada.
     * @return true si el registro fue actualizado.
     */
    public boolean actualizar(Permiso permiso) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(ACTUALIZAR)
        ) {

            ps.setString(1, permiso.getClave());

            ps.setString(2, permiso.getNombre());

            ps.setString(3, permiso.getDescripcion());

            ps.setString(4, permiso.getModulo());

            ps.setBoolean(5, permiso.isActivo());

            ps.setLong(6, permiso.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al actualizar el permiso.");

            System.err.println(e.getMessage());

        }

        return false;

    }

    /**
     * Elimina un permiso por su identificador.
     *
     * Nota:
     * En el futuro este método puede convertirse
     * en una eliminación lógica (activo = FALSE).
     *
     * @param id Identificador del permiso.
     * @return true si fue eliminado.
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

            System.err.println("Error al eliminar el permiso.");

            System.err.println(e.getMessage());

        }

        return false;

    }

    /**
     * Busca un permiso mediante su identificador.
     *
     * @param id Identificador del permiso.
     * @return Objeto Permiso o null si no existe.
     */
    public Permiso buscarPorId(Long id) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_ID)
        ) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearPermiso(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al buscar el permiso por ID.");

            System.err.println(e.getMessage());

        }

        return null;

    }

    /**
     * Busca un permiso por su clave.
     *
     * La clave debe ser única dentro del sistema.
     *
     * @param clave Clave del permiso.
     * @return Objeto Permiso o null si no existe.
     */
    public Permiso buscarPorClave(String clave) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_CLAVE)
        ) {

            ps.setString(1, clave);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearPermiso(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al buscar el permiso por clave.");

            System.err.println(e.getMessage());

        }

        return null;

    }
    
    /**
     * Obtiene todos los permisos registrados.
     *
     * Los resultados se ordenan por módulo
     * y después por nombre.
     *
     * @return Lista de permisos.
     */
    public List<Permiso> listarTodos() {

        List<Permiso> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(mapearPermiso(rs));

            }

        } catch (SQLException e) {

            System.err.println("Error al listar los permisos.");

            System.err.println(e.getMessage());

        }

        return lista;

    }


    /**
     * Obtiene únicamente los permisos activos.
     *
     * Se utiliza para mostrar permisos disponibles
     * al momento de asignarlos a un rol.
     *
     * @return Lista de permisos activos.
     */
    public List<Permiso> listarActivos() {

        List<Permiso> lista = new ArrayList<>();

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_ACTIVOS);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(mapearPermiso(rs));

            }

        } catch (SQLException e) {

            System.err.println("Error al listar permisos activos.");

            System.err.println(e.getMessage());

        }

        return lista;

    }


    /**
     * Verifica si existe un permiso con la misma clave.
     *
     * Se utiliza para evitar permisos duplicados.
     *
     * Ejemplo:
     *
     * VENTA_CREAR
     * VENTA_EDITAR
     * PRODUCTO_ELIMINAR
     *
     * @param clave Clave del permiso.
     * @return true si la clave ya existe.
     */
    public boolean existeClave(String clave) {

        try (
                Connection conexion = Database.getConnection();
                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_CLAVE)
        ) {

            ps.setString(1, clave);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al validar la clave del permiso.");

            System.err.println(e.getMessage());

        }

        return false;

    }

}