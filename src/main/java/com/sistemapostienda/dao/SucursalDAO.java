/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;


import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================ SistemaPOSTienda
 * SistemaPOSTienda
 * DAO encargado de realizar todas las operaciones SQL relacionadas con la tabla
 * sucursales.
 *
 * ============================================================
 */
public class SucursalDAO {


    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */
 /*
     * Inserta una nueva sucursal.
     */
    private static final String INSERTAR
            = """
            INSERT INTO sucursales
            (
                nombre,
                codigo,
                direccion,
                telefono,
                correo,
                activo
            )
            VALUES
            (
                ?, ?, ?, ?, ?, ?
            )
            """;


    /*
     * Actualiza una sucursal existente.
     */
    private static final String ACTUALIZAR
            = """
            UPDATE sucursales
            SET
                nombre = ?,
                codigo = ?,
                direccion = ?,
                telefono = ?,
                correo = ?,
                activo = ?
            WHERE id = ?
            """;


    /*
     * Elimina una sucursal por ID.
     */
    private static final String ELIMINAR
            = """
            DELETE FROM sucursales
            WHERE id = ?
            """;


    /*
     * Busca una sucursal por ID.
     */
    private static final String BUSCAR_ID
            = """
            SELECT *
            FROM sucursales
            WHERE id = ?
            """;


    /*
     * Busca una sucursal por código.
     */
    private static final String BUSCAR_CODIGO
            = """
            SELECT *
            FROM sucursales
            WHERE codigo = ?
            """;


    /*
     * Lista todas las sucursales.
     */
    private static final String LISTAR
            = """
            SELECT *
            FROM sucursales
            ORDER BY nombre
            """;


    /*
     * Lista solamente sucursales activas.
     */
    private static final String LISTAR_ACTIVAS
            = """
            SELECT *
            FROM sucursales
            WHERE activo = TRUE
            ORDER BY nombre
            """;


    /*
     * Verifica si existe un código registrado.
     */
    private static final String EXISTE_CODIGO
            = """
            SELECT COUNT(*)
            FROM sucursales
            WHERE codigo = ?
            """;

    /**
     * Constructor.
     */
    public SucursalDAO() {

    }

    /**
     * Convierte un registro de la base de datos en un objeto Sucursal.
     *
     * @param rs Resultado de la consulta.
     * @return Objeto Sucursal.
     * @throws SQLException
     */
    private Sucursal mapearSucursal(ResultSet rs)
            throws SQLException {

        Sucursal sucursal = new Sucursal();

        sucursal.setId(
                rs.getLong("id")
        );

        sucursal.setNombre(
                rs.getString("nombre")
        );

        sucursal.setCodigo(
                rs.getString("codigo")
        );

        sucursal.setDireccion(
                rs.getString("direccion")
        );

        sucursal.setTelefono(
                rs.getString("telefono")
        );

        sucursal.setCorreo(
                rs.getString("correo")
        );

        sucursal.setActivo(
                rs.getBoolean("activo")
        );

        if (rs.getTimestamp("created_at") != null) {

            sucursal.setCreatedAt(
                    rs.getTimestamp("created_at")
                            .toLocalDateTime()
            );

        }

        if (rs.getTimestamp("updated_at") != null) {

            sucursal.setUpdatedAt(
                    rs.getTimestamp("updated_at")
                            .toLocalDateTime()
            );

        }

        return sucursal;

    }

    /**
     * Guarda una nueva sucursal.
     *
     * @param sucursal Objeto sucursal a guardar.
     *
     * @return true si fue insertada correctamente.
     */
    public boolean guardar(Sucursal sucursal) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(INSERTAR)) {

            ps.setString(
                    1,
                    sucursal.getNombre()
            );

            ps.setString(
                    2,
                    sucursal.getCodigo()
            );

            ps.setString(
                    3,
                    sucursal.getDireccion()
            );

            ps.setString(
                    4,
                    sucursal.getTelefono()
            );

            ps.setString(
                    5,
                    sucursal.getCorreo()
            );

            ps.setBoolean(
                    6,
                    sucursal.isActivo()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar la sucursal."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Actualiza la información de una sucursal existente.
     *
     * @param sucursal Sucursal con los datos actualizados.
     *
     * @return true si la actualización fue correcta.
     */
    public boolean actualizar(Sucursal sucursal) {

        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(ACTUALIZAR)) {

            ps.setString(
                    1,
                    sucursal.getNombre()
            );

            ps.setString(
                    2,
                    sucursal.getCodigo()
            );

            ps.setString(
                    3,
                    sucursal.getDireccion()
            );

            ps.setString(
                    4,
                    sucursal.getTelefono()
            );

            ps.setString(
                    5,
                    sucursal.getCorreo()
            );

            ps.setBoolean(
                    6,
                    sucursal.isActivo()
            );

            ps.setLong(
                    7,
                    sucursal.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar la sucursal."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /**
     * Elimina una sucursal por su identificador.
     *
     * Nota: Más adelante podemos cambiar este método por eliminación lógica:
     *
     * activo = false
     *
     * Esto es recomendable cuando la sucursal ya tenga ventas, inventarios o
     * movimientos.
     *
     * @param id Identificador de la sucursal.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {
        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(ELIMINAR)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(
                    "Error al eliminar la sucursal."
            );
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Busca una sucursal mediante su ID.
     *
     * @param id Identificador de la sucursal.
     *
     * @return Sucursal encontrada o null.
     */
    public Sucursal buscarPorId(Long id) {
        try (Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(BUSCAR_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSucursal(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la sucursal por ID."
            );
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Busca una sucursal mediante su código.
     *
     * Ejemplo:
     *
     * CENTRO SUC001
     *
     * El código debe ser único.
     *
     * @param codigo Código de la sucursal.
     *
     * @return Sucursal encontrada o null.
     */
    public Sucursal buscarPorCodigo(String codigo) {
        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(BUSCAR_CODIGO)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearSucursal(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la sucursal por código.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene todas las sucursales registradas.
     *
     * Ordena el resultado por nombre.
     *
     * @return Lista de sucursales.
     */
    public List<Sucursal> listarTodos() {
        List<Sucursal> lista = new ArrayList<>();
        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps = conexion.prepareStatement(LISTAR); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearSucursal(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar las sucursales.");
            System.err.println(e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene únicamente las sucursales activas.
     *
     * Este método será utilizado para cargar listas de selección, por ejemplo:
     *
     * - Registro de usuarios - Apertura de caja - Ventas
     *
     * @return Lista de sucursales activas.
     */
    public List<Sucursal> listarActivas() {
        List<Sucursal> lista = new ArrayList<>();
        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps = conexion.prepareStatement(LISTAR_ACTIVAS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearSucursal(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar sucursales activas.");
            System.err.println(e.getMessage());
        }
        return lista;

    }

    /**
     * Verifica si ya existe una sucursal con el mismo código.
     *
     * Se utiliza para evitar códigos duplicados.
     *
     * Ejemplo:
     *
     * SUC001
     *
     * No permitir nuevamente:
     *
     * SUC001
     *
     * @param codigo Código de la sucursal.
     *
     * @return true si el código existe.
     */
    public boolean existeCodigo(String codigo) {
        try (
                Connection conexion = Database.getConnection(); PreparedStatement ps = conexion.prepareStatement(EXISTE_CODIGO)) {

            ps.setString(
                    1,
                    codigo
            );

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al validar código de sucursal."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

}
