/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Usuario;

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
 * DAO encargado de administrar las operaciones SQL
 * relacionadas con la tabla usuarios.
 *
 * Un usuario pertenece a:
 *
 *      Rol
 *       |
 *       |
 *   Usuario
 *       |
 *       |
 *   Sucursal
 *
 * ============================================================
 */
public class UsuarioDAO {


    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */


    /*
     * Inserta un nuevo usuario.
     */
    private static final String INSERTAR =
            """
            INSERT INTO usuarios
            (
                rol_id,
                sucursal_id,
                nombre,
                apellido_paterno,
                apellido_materno,
                usuario,
                password,
                correo,
                telefono,
                activo
            )
            VALUES
            (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
            """;


    /*
     * Actualiza información del usuario.
     */
    private static final String ACTUALIZAR =
            """
            UPDATE usuarios
            SET
                rol_id = ?,
                sucursal_id = ?,
                nombre = ?,
                apellido_paterno = ?,
                apellido_materno = ?,
                usuario = ?,
                password = ?,
                correo = ?,
                telefono = ?,
                activo = ?
            WHERE id = ?
            """;


    /*
     * Elimina un usuario por ID.
     */
    private static final String ELIMINAR =
            """
            DELETE FROM usuarios
            WHERE id = ?
            """;


    /*
     * Busca usuario por ID.
     *
     * Incluye información del rol
     * y sucursal.
     */
    private static final String BUSCAR_ID =
            """
            SELECT
                u.*,
                r.nombre AS rol_nombre,
                s.nombre AS sucursal_nombre

            FROM usuarios u

            INNER JOIN roles r
            ON u.rol_id = r.id

            INNER JOIN sucursales s
            ON u.sucursal_id = s.id

            WHERE u.id = ?
            """;


    /*
     * Busca usuario mediante nombre de usuario.
     */
    private static final String BUSCAR_USUARIO =
            """
            SELECT
                u.*,
                r.nombre AS rol_nombre,
                s.nombre AS sucursal_nombre

            FROM usuarios u

            INNER JOIN roles r
            ON u.rol_id = r.id

            INNER JOIN sucursales s
            ON u.sucursal_id = s.id

            WHERE u.usuario = ?
            """;


    /*
     * Busca usuario mediante correo.
     */
    private static final String BUSCAR_CORREO =
            """
            SELECT
                u.*,
                r.nombre AS rol_nombre,
                s.nombre AS sucursal_nombre

            FROM usuarios u

            INNER JOIN roles r
            ON u.rol_id = r.id

            INNER JOIN sucursales s
            ON u.sucursal_id = s.id

            WHERE u.correo = ?
            """;


    /*
     * Lista todos los usuarios.
     */
    private static final String LISTAR =
            """
            SELECT
                u.*,
                r.nombre AS rol_nombre,
                s.nombre AS sucursal_nombre

            FROM usuarios u

            INNER JOIN roles r
            ON u.rol_id = r.id

            INNER JOIN sucursales s
            ON u.sucursal_id = s.id

            ORDER BY u.nombre
            """;


    /*
     * Lista usuarios activos.
     */
    private static final String LISTAR_ACTIVOS =
            """
            SELECT
                u.*,
                r.nombre AS rol_nombre,
                s.nombre AS sucursal_nombre

            FROM usuarios u

            INNER JOIN roles r
            ON u.rol_id = r.id

            INNER JOIN sucursales s
            ON u.sucursal_id = s.id

            WHERE u.activo = TRUE

            ORDER BY u.nombre
            """;


    /*
     * Verifica existencia del usuario.
     */
    private static final String EXISTE_USUARIO =
            """
            SELECT COUNT(*)
            FROM usuarios
            WHERE usuario = ?
            """;


    /*
     * Verifica existencia del correo.
     */
    private static final String EXISTE_CORREO =
            """
            SELECT COUNT(*)
            FROM usuarios
            WHERE correo = ?
            """;



    /**
     * Constructor.
     */
    public UsuarioDAO() {

    }



    /**
     * Convierte un registro de la base de datos
     * en un objeto Usuario.
     *
     * @param rs Resultado de consulta.
     *
     * @return Usuario.
     *
     * @throws SQLException
     */
    private Usuario mapearUsuario(ResultSet rs)
            throws SQLException {


        Usuario usuario = new Usuario();


        usuario.setId(
                rs.getLong("id")
        );


        usuario.setRolId(
                rs.getLong("rol_id")
        );


        usuario.setSucursalId(
                rs.getLong("sucursal_id")
        );


        usuario.setNombre(
                rs.getString("nombre")
        );


        usuario.setApellidoPaterno(
                rs.getString("apellido_paterno")
        );


        usuario.setApellidoMaterno(
                rs.getString("apellido_materno")
        );


        usuario.setUsuario(
                rs.getString("usuario")
        );


        usuario.setPassword(
                rs.getString("password")
        );


        usuario.setCorreo(
                rs.getString("correo")
        );


        usuario.setTelefono(
                rs.getString("telefono")
        );


        usuario.setActivo(
                rs.getBoolean("activo")
        );


        if (rs.getTimestamp("ultimo_acceso") != null) {

            usuario.setUltimoAcceso(
                    rs.getTimestamp("ultimo_acceso")
                            .toLocalDateTime()
            );

        }


        if (rs.getTimestamp("created_at") != null) {

            usuario.setCreatedAt(
                    rs.getTimestamp("created_at")
                            .toLocalDateTime()
            );

        }


        if (rs.getTimestamp("updated_at") != null) {

            usuario.setUpdatedAt(
                    rs.getTimestamp("updated_at")
                            .toLocalDateTime()
            );

        }


        return usuario;

    }



    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario Usuario a registrar.
     *
     * @return true si fue guardado.
     */
    public boolean guardar(Usuario usuario) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(INSERTAR)
        ) {


            ps.setLong(
                    1,
                    usuario.getRolId()
            );


            ps.setLong(
                    2,
                    usuario.getSucursalId()
            );


            ps.setString(
                    3,
                    usuario.getNombre()
            );


            ps.setString(
                    4,
                    usuario.getApellidoPaterno()
            );


            ps.setString(
                    5,
                    usuario.getApellidoMaterno()
            );


            ps.setString(
                    6,
                    usuario.getUsuario()
            );


            ps.setString(
                    7,
                    usuario.getPassword()
            );


            ps.setString(
                    8,
                    usuario.getCorreo()
            );


            ps.setString(
                    9,
                    usuario.getTelefono()
            );


            ps.setBoolean(
                    10,
                    usuario.isActivo()
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.err.println(
                    "Error al guardar usuario."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }
    
    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario Usuario con los nuevos datos.
     *
     * @return true si fue actualizado correctamente.
     */
    public boolean actualizar(Usuario usuario) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(ACTUALIZAR)
        ) {


            ps.setLong(
                    1,
                    usuario.getRolId()
            );


            ps.setLong(
                    2,
                    usuario.getSucursalId()
            );


            ps.setString(
                    3,
                    usuario.getNombre()
            );


            ps.setString(
                    4,
                    usuario.getApellidoPaterno()
            );


            ps.setString(
                    5,
                    usuario.getApellidoMaterno()
            );


            ps.setString(
                    6,
                    usuario.getUsuario()
            );


            ps.setString(
                    7,
                    usuario.getPassword()
            );


            ps.setString(
                    8,
                    usuario.getCorreo()
            );


            ps.setString(
                    9,
                    usuario.getTelefono()
            );


            ps.setBoolean(
                    10,
                    usuario.isActivo()
            );


            ps.setLong(
                    11,
                    usuario.getId()
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.err.println(
                    "Error al actualizar usuario."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }



    /**
     * Elimina un usuario por su ID.
     *
     * Nota:
     * Más adelante podemos cambiarlo a eliminación lógica:
     *
     * activo = false
     *
     * porque los usuarios pueden tener movimientos
     * asociados.
     *
     * @param id ID del usuario.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(ELIMINAR)
        ) {


            ps.setLong(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {


            System.err.println(
                    "Error al eliminar usuario."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }



    /**
     * Busca un usuario mediante su ID.
     *
     * @param id ID del usuario.
     *
     * @return Usuario encontrado o null.
     */
    public Usuario buscarPorId(Long id) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_ID)
        ) {


            ps.setLong(
                    1,
                    id
            );


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return mapearUsuario(rs);


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al buscar usuario por ID."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return null;

    }



    /**
     * Busca un usuario mediante su nombre de usuario.
     *
     * Este método será importante para el login.
     *
     * Ejemplo:
     *
     * usuario:
     * admin
     *
     * retorna:
     *
     * Usuario
     *  |
     *  ├── Rol
     *  └── Sucursal
     *
     *
     * @param nombreUsuario Usuario registrado.
     *
     * @return Usuario encontrado o null.
     */
    public Usuario buscarPorUsuario(String nombreUsuario) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_USUARIO)
        ) {


            ps.setString(
                    1,
                    nombreUsuario
            );


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return mapearUsuario(rs);


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al buscar usuario por nombre."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return null;

    }
    /**
     * Busca un usuario mediante su correo.
     *
     * @param correo Correo del usuario.
     *
     * @return Usuario encontrado o null.
     */
    public Usuario buscarPorCorreo(String correo) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(BUSCAR_CORREO)
        ) {


            ps.setString(
                    1,
                    correo
            );


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return mapearUsuario(rs);


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al buscar usuario por correo."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return null;

    }



    /**
     * Obtiene todos los usuarios registrados.
     *
     * Incluye información de:
     *
     * - Rol
     * - Sucursal
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> listarTodos() {


        List<Usuario> lista = new ArrayList<>();


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR);

                ResultSet rs = ps.executeQuery()
        ) {


            while (rs.next()) {


                lista.add(
                        mapearUsuario(rs)
                );


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al listar usuarios."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return lista;

    }



    /**
     * Obtiene solamente usuarios activos.
     *
     * Útil para:
     *
     * - Inicio de sesión
     * - Selección de usuarios
     * - Asignaciones
     *
     * @return Lista de usuarios activos.
     */
    public List<Usuario> listarActivos() {


        List<Usuario> lista = new ArrayList<>();


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(LISTAR_ACTIVOS);

                ResultSet rs = ps.executeQuery()
        ) {


            while (rs.next()) {


                lista.add(
                        mapearUsuario(rs)
                );


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al listar usuarios activos."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return lista;

    }



    /**
     * Verifica si existe un nombre de usuario registrado.
     *
     * Evita duplicados.
     *
     * Ejemplo:
     *
     * admin
     *
     * No permitir nuevamente:
     *
     * admin
     *
     * @param nombreUsuario Nombre de usuario.
     *
     * @return true si existe.
     */
    public boolean existeUsuario(String nombreUsuario) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_USUARIO)
        ) {


            ps.setString(
                    1,
                    nombreUsuario
            );


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return rs.getInt(1) > 0;


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al validar usuario."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }



    /**
     * Verifica si existe un correo registrado.
     *
     * @param correo Correo a validar.
     *
     * @return true si existe.
     */
    public boolean existeCorreo(String correo) {


        try (
                Connection conexion = Database.getConnection();

                PreparedStatement ps =
                        conexion.prepareStatement(EXISTE_CORREO)
        ) {


            ps.setString(
                    1,
                    correo
            );


            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {


                    return rs.getInt(1) > 0;


                }


            }


        } catch (SQLException e) {


            System.err.println(
                    "Error al validar correo."
            );


            System.err.println(
                    e.getMessage()
            );


        }


        return false;

    }


}