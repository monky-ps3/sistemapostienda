/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Proveedor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla proveedores.
 *
 * Se encarga únicamente del acceso a la base de datos.
 */
public class ProveedorDAO {

    // =========================================================
    // GUARDAR
    // =========================================================

    public boolean guardar(Proveedor proveedor) {

        String sql = """
                INSERT INTO proveedores
                (
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo
                )
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            setDatosProveedor(statement, proveedor);

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return false;
            }

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    proveedor.setId(keys.getLong(1));
                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar proveedor: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Proveedor buscarPorId(Long id) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearProveedor(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar proveedor por ID: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public Proveedor buscarPorCodigo(
            String codigo) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                WHERE codigo = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, codigo);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearProveedor(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar proveedor por código: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    public Proveedor buscarPorRfc(
            String rfc) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                WHERE rfc = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, rfc);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearProveedor(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar proveedor por RFC: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Proveedor> listarTodos() {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                ORDER BY nombre
                """;

        List<Proveedor> proveedores =
                new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                proveedores.add(
                        mapearProveedor(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar proveedores: "
                    + e.getMessage()
            );
        }

        return proveedores;
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Proveedor> listarActivos() {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                WHERE activo = TRUE
                ORDER BY nombre
                """;

        List<Proveedor> proveedores =
                new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                proveedores.add(
                        mapearProveedor(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar proveedores activos: "
                    + e.getMessage()
            );
        }

        return proveedores;
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public List<Proveedor> buscarPorNombre(
            String nombre) {

        String sql = """
                SELECT
                    id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    contacto,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    dias_credito,
                    limite_credito,
                    permite_credito,
                    observaciones,
                    activo,
                    created_at,
                    updated_at
                FROM proveedores
                WHERE nombre LIKE ?
                ORDER BY nombre
                """;

        List<Proveedor> proveedores =
                new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    "%" + nombre + "%"
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    proveedores.add(
                            mapearProveedor(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar proveedores por nombre: "
                    + e.getMessage()
            );
        }

        return proveedores;
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(
            Proveedor proveedor) {

        String sql = """
                UPDATE proveedores
                SET
                    codigo = ?,
                    nombre = ?,
                    razon_social = ?,
                    rfc = ?,
                    contacto = ?,
                    telefono = ?,
                    celular = ?,
                    email = ?,
                    direccion = ?,
                    ciudad = ?,
                    estado = ?,
                    codigo_postal = ?,
                    dias_credito = ?,
                    limite_credito = ?,
                    permite_credito = ?,
                    observaciones = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setDatosProveedor(statement, proveedor);

            statement.setLong(
                    18,
                    proveedor.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar proveedor: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        String sql = """
                DELETE FROM proveedores
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar proveedor: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // CAMBIAR ESTADO
    // =========================================================

    public boolean cambiarEstado(
            Long id,
            boolean activo) {

        String sql = """
                UPDATE proveedores
                SET activo = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, activo);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al cambiar estado del proveedor: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ASIGNAR DATOS DEL PROVEEDOR
    // =========================================================

    private void setDatosProveedor(
            PreparedStatement statement,
            Proveedor proveedor)
            throws SQLException {

        statement.setString(
                1,
                proveedor.getCodigo()
        );

        statement.setString(
                2,
                proveedor.getNombre()
        );

        statement.setString(
                3,
                proveedor.getRazonSocial()
        );

        statement.setString(
                4,
                proveedor.getRfc()
        );

        statement.setString(
                5,
                proveedor.getContacto()
        );

        statement.setString(
                6,
                proveedor.getTelefono()
        );

        statement.setString(
                7,
                proveedor.getCelular()
        );

        statement.setString(
                8,
                proveedor.getEmail()
        );

        statement.setString(
                9,
                proveedor.getDireccion()
        );

        statement.setString(
                10,
                proveedor.getCiudad()
        );

        statement.setString(
                11,
                proveedor.getEstado()
        );

        statement.setString(
                12,
                proveedor.getCodigoPostal()
        );

        // Días de crédito
        if (proveedor.getDiasCredito() == null) {

            statement.setInt(13, 0);

        } else {

            statement.setInt(
                    13,
                    proveedor.getDiasCredito()
            );
        }

        // Límite de crédito
        if (proveedor.getLimiteCredito() == null) {

            statement.setBigDecimal(
                    14,
                    BigDecimal.ZERO
            );

        } else {

            statement.setBigDecimal(
                    14,
                    proveedor.getLimiteCredito()
            );
        }

        // Permite crédito
        statement.setBoolean(
                15,
                Boolean.TRUE.equals(
                        proveedor.getPermiteCredito()
                )
        );

        // Observaciones
        statement.setString(
                16,
                proveedor.getObservaciones()
        );

        // Activo
        statement.setBoolean(
                17,
                Boolean.TRUE.equals(
                        proveedor.getActivo()
                )
        );
    }

    // =========================================================
    // MAPEAR RESULTSET
    // =========================================================

    private Proveedor mapearProveedor(
            ResultSet resultSet)
            throws SQLException {

        Proveedor proveedor =
                new Proveedor();

        proveedor.setId(
                resultSet.getLong("id")
        );

        proveedor.setCodigo(
                resultSet.getString("codigo")
        );

        proveedor.setNombre(
                resultSet.getString("nombre")
        );

        proveedor.setRazonSocial(
                resultSet.getString("razon_social")
        );

        proveedor.setRfc(
                resultSet.getString("rfc")
        );

        proveedor.setContacto(
                resultSet.getString("contacto")
        );

        proveedor.setTelefono(
                resultSet.getString("telefono")
        );

        proveedor.setCelular(
                resultSet.getString("celular")
        );

        proveedor.setEmail(
                resultSet.getString("email")
        );

        proveedor.setDireccion(
                resultSet.getString("direccion")
        );

        proveedor.setCiudad(
                resultSet.getString("ciudad")
        );

        proveedor.setEstado(
                resultSet.getString("estado")
        );

        proveedor.setCodigoPostal(
                resultSet.getString("codigo_postal")
        );

        proveedor.setDiasCredito(
                resultSet.getInt("dias_credito")
        );

        proveedor.setLimiteCredito(
                resultSet.getBigDecimal(
                        "limite_credito"
                )
        );

        proveedor.setPermiteCredito(
                resultSet.getBoolean(
                        "permite_credito"
                )
        );

        proveedor.setObservaciones(
                resultSet.getString(
                        "observaciones"
                )
        );

        proveedor.setActivo(
                resultSet.getBoolean("activo")
        );

        Timestamp created =
                resultSet.getTimestamp(
                        "created_at"
                );

        if (created != null) {

            proveedor.setCreatedAt(
                    created.toLocalDateTime()
            );
        }

        Timestamp updated =
                resultSet.getTimestamp(
                        "updated_at"
                );

        if (updated != null) {

            proveedor.setUpdatedAt(
                    updated.toLocalDateTime()
            );
        }

        return proveedor;
    }
}