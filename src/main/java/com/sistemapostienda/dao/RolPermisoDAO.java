/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Permiso;
import com.sistemapostienda.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 * DAO encargado de administrar la relación entre:
 *
 * roles | | rol_permiso | | permisos
 *
 * Esta clase no administra permisos ni roles directamente, únicamente controla
 * las asignaciones entre ambos.
 *
 * ============================================================
 */
public class RolPermisoDAO {


    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */
 /*
     * Asigna un permiso a un rol.
     */
    private static final String INSERTAR
            = """
            INSERT INTO rol_permiso
            (rol_id,permiso_id)VALUES(?, ?)
            """;


    /*
     * Elimina una asignación específica.
     */
    private static final String ELIMINAR
            = """
            DELETE FROM rol_permiso WHERE rol_id = ? AND permiso_id = ?""";


    /*
     * Verifica si un permiso ya está asignado
     * a un rol.
     */
    private static final String EXISTE_RELACION
            = """
            SELECT COUNT(*) FROM rol_permiso WHERE rol_id = ? AND permiso_id = ? """;


    /*
     * Obtiene todos los permisos asignados
     * a un determinado rol.
     */
    private static final String LISTAR_PERMISOS_POR_ROL
            = """
            SELECT p.* FROM permisos p INNER JOIN rol_permiso rp ON p.id = rp.permiso_id WHERE rp.rol_id = ?
            ORDER BY p.modulo, p.nombre""";


    /*
     * Obtiene todos los roles que tienen
     * asignado un permiso.
     */
    private static final String LISTAR_ROLES_POR_PERMISO
            = """
            SELECT r.* FROM roles r INNER JOIN rol_permiso rp ON r.id = rp.rol_id WHERE rp.permiso_id = ?
            ORDER BY r.nombre """;


    /*
     * Elimina todos los permisos asignados
     * a un rol.
     *
     * Útil cuando se desea reconstruir
     * completamente los permisos de un rol.
     */
    private static final String ELIMINAR_POR_ROL
            = """
            DELETE FROM rol_permiso WHERE rol_id = ? """;

    /**
     * Constructor.
     */
    public RolPermisoDAO() {

    }

    /**
     * Asigna un permiso a un rol.
     *
     * Ejemplo:
     *
     * Administrador | +---- PRODUCTO_CREAR
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si la asignación fue creada.
     */
    public boolean asignarPermiso(Long rolId, Long permisoId) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(INSERTAR)) {

            ps.setLong(1, rolId);

            ps.setLong(2, permisoId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al asignar permiso al rol."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }
    
    /**
     * Quita un permiso asignado a un rol.
     *
     * Ejemplo:
     *
     * Administrador
     *        |
     *        X---- PRODUCTO_ELIMINAR
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si la asignación fue eliminada.
     */
    public boolean quitarPermiso(Long rolId, Long permisoId) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(ELIMINAR)
        ) {


            ps.setLong(1, rolId);

            ps.setLong(2, permisoId);


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.err.println(
                    "Error al quitar permiso del rol."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }



    /**
     * Verifica si un permiso ya está asignado
     * a un rol.
     *
     * Evita crear registros duplicados.
     *
     * Ejemplo:
     *
     * No permitir:
     *
     * rol_id = 1
     * permiso_id = 5
     *
     * repetido varias veces.
     *
     * @param rolId ID del rol.
     * @param permisoId ID del permiso.
     *
     * @return true si existe la relación.
     */
    public boolean existeRelacion(Long rolId, Long permisoId) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_RELACION)
        ) {


            ps.setLong(1, rolId);

            ps.setLong(2, permisoId);


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return rs.getInt(1) > 0;


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al verificar relación rol-permiso."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }



    /**
     * Obtiene todos los permisos asignados
     * a un rol determinado.
     *
     * Ejemplo:
     *
     * Rol:
     * Administrador
     *
     * Resultado:
     *
     * - USUARIO_CREAR
     * - USUARIO_EDITAR
     * - PRODUCTO_CREAR
     * - VENTA_CREAR
     *
     *
     * @param rolId ID del rol.
     *
     * @return Lista de permisos.
     */
    public List<Permiso> obtenerPermisosPorRol(Long rolId) {


        List<Permiso> lista = new ArrayList<>();


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_PERMISOS_POR_ROL
                        )
        ) {


            ps.setLong(1, rolId);


            try (ResultSet rs = ps.executeQuery()) {


                while (rs.next()) {


                    Permiso permiso = new Permiso();


                    permiso.setId(
                            rs.getLong("id")
                    );


                    permiso.setClave(
                            rs.getString("clave")
                    );


                    permiso.setNombre(
                            rs.getString("nombre")
                    );


                    permiso.setDescripcion(
                            rs.getString("descripcion")
                    );


                    permiso.setModulo(
                            rs.getString("modulo")
                    );


                    permiso.setActivo(
                            rs.getBoolean("activo")
                    );


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


                    lista.add(permiso);


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al obtener permisos del rol."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return lista;

    }

    /**
     * Obtiene todos los roles que tienen asignado
     * un permiso determinado.
     *
     * Ejemplo:
     *
     * Permiso:
     * VENTA_CREAR
     *
     * Resultado:
     *
     * - Administrador
     * - Supervisor
     * - Cajero
     *
     * @param permisoId ID del permiso.
     *
     * @return Lista de roles.
     */
    public List<Rol> obtenerRolesPorPermiso(Long permisoId) {


        List<Rol> lista = new ArrayList<>();


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                LISTAR_ROLES_POR_PERMISO
                        )
        ) {


            ps.setLong(1, permisoId);


            try (ResultSet rs = ps.executeQuery()) {


                while (rs.next()) {


                    Rol rol = new Rol();


                    rol.setId(
                            rs.getLong("id")
                    );


                    rol.setNombre(
                            rs.getString("nombre")
                    );


                    rol.setDescripcion(
                            rs.getString("descripcion")
                    );


                    rol.setActivo(
                            rs.getBoolean("activo")
                    );


                    if (rs.getTimestamp("created_at") != null) {


                        rol.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );


                    }


                    if (rs.getTimestamp("updated_at") != null) {


                        rol.setUpdatedAt(
                                rs.getTimestamp("updated_at")
                                        .toLocalDateTime()
                        );


                    }


                    lista.add(rol);


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al obtener roles del permiso."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return lista;

    }



    /**
     * Elimina todos los permisos asignados
     * a un rol.
     *
     * Se utiliza cuando se desea reconstruir
     * completamente los permisos de un rol.
     *
     * Ejemplo:
     *
     * Antes:
     *
     * Administrador
     *  - Usuarios
     *  - Ventas
     *  - Productos
     *
     *
     * Después de limpiar:
     *
     * Administrador
     *  - Sin permisos
     *
     *
     * Posteriormente se vuelven a asignar
     * los permisos seleccionados.
     *
     * @param rolId ID del rol.
     *
     * @return true si fueron eliminados.
     */
    public boolean eliminarTodosPorRol(Long rolId) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(
                                ELIMINAR_POR_ROL
                        )
        ) {


            ps.setLong(1, rolId);


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.err.println(
                    "Error al eliminar permisos del rol."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }


}