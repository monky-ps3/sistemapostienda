/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Cliente;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla clientes.
 *
 * Se encarga exclusivamente del acceso a la base de datos.
 */
public class ClienteDAO {

    // =========================================================
    // GUARDAR
    // =========================================================

    public boolean guardar(Cliente cliente) {

        String sql = """
                INSERT INTO clientes
                (
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo
                )
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            setDatosCliente(statement, cliente);

            int filas = statement.executeUpdate();

            if (filas == 0) {
                return false;
            }

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    cliente.setId(keys.getLong(1));
                }
            }

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Cliente buscarPorId(Long id) {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar cliente por ID: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public Cliente buscarPorCodigo(String codigo) {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE codigo = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, codigo);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar cliente por código: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    public Cliente buscarPorRfc(String rfc) {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE rfc = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, rfc);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar cliente por RFC: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR PÚBLICO EN GENERAL
    // =========================================================

    public Cliente buscarPublicoGeneral() {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE codigo = 'PUBLICO'
                LIMIT 1
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return mapearCliente(resultSet);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar público general: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Cliente> listarTodos() {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                ORDER BY nombre
                """;

        List<Cliente> clientes =
                new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                clientes.add(
                        mapearCliente(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar clientes: "
                    + e.getMessage()
            );
        }

        return clientes;
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Cliente> listarActivos() {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE activo = TRUE
                ORDER BY nombre
                """;

        List<Cliente> clientes =
                new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                clientes.add(
                        mapearCliente(resultSet)
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar clientes activos: "
                    + e.getMessage()
            );
        }

        return clientes;
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public List<Cliente> buscarPorNombre(
            String nombre) {

        String sql = """
                SELECT
                    id,
                    lista_precio_id,
                    codigo,
                    nombre,
                    razon_social,
                    rfc,
                    telefono,
                    celular,
                    email,
                    direccion,
                    ciudad,
                    estado,
                    codigo_postal,
                    limite_credito,
                    dias_credito,
                    descuento,
                    permite_credito,
                    referencia_domicilio,
                    activo,
                    created_at,
                    updated_at
                FROM clientes
                WHERE nombre LIKE ?
                ORDER BY nombre
                """;

        List<Cliente> clientes =
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

                    clientes.add(
                            mapearCliente(resultSet)
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar clientes por nombre: "
                    + e.getMessage()
            );
        }

        return clientes;
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(Cliente cliente) {

        String sql = """
                UPDATE clientes
                SET
                    lista_precio_id = ?,
                    codigo = ?,
                    nombre = ?,
                    razon_social = ?,
                    rfc = ?,
                    telefono = ?,
                    celular = ?,
                    email = ?,
                    direccion = ?,
                    ciudad = ?,
                    estado = ?,
                    codigo_postal = ?,
                    limite_credito = ?,
                    dias_credito = ?,
                    descuento = ?,
                    permite_credito = ?,
                    referencia_domicilio = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setDatosCliente(statement, cliente);

            statement.setLong(
                    19,
                    cliente.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar cliente: "
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
                DELETE FROM clientes
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar cliente: "
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
                UPDATE clientes
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
                    "Error al cambiar estado del cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ASIGNAR LISTA DE PRECIOS
    // =========================================================

    public boolean actualizarListaPrecio(
            Long clienteId,
            Long listaPrecioId) {

        String sql = """
                UPDATE clientes
                SET lista_precio_id = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            if (listaPrecioId == null) {
                statement.setNull(
                        1,
                        Types.BIGINT
                );
            } else {
                statement.setLong(
                        1,
                        listaPrecioId
                );
            }

            statement.setLong(2, clienteId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar lista de precios "
                    + "del cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // MÉTODO PARA ASIGNAR DATOS
    // =========================================================

    private void setDatosCliente(
            PreparedStatement statement,
            Cliente cliente)
            throws SQLException {

        // lista_precio_id
        if (cliente.getListaPrecioId() == null) {

            statement.setNull(
                    1,
                    Types.BIGINT
            );

        } else {

            statement.setLong(
                    1,
                    cliente.getListaPrecioId()
            );
        }

        // codigo
        statement.setString(
                2,
                cliente.getCodigo()
        );

        // nombre
        statement.setString(
                3,
                cliente.getNombre()
        );

        // razon_social
        statement.setString(
                4,
                cliente.getRazonSocial()
        );

        // rfc
        statement.setString(
                5,
                cliente.getRfc()
        );

        // telefono
        statement.setString(
                6,
                cliente.getTelefono()
        );

        // celular
        statement.setString(
                7,
                cliente.getCelular()
        );

        // email
        statement.setString(
                8,
                cliente.getEmail()
        );

        // direccion
        statement.setString(
                9,
                cliente.getDireccion()
        );

        // ciudad
        statement.setString(
                10,
                cliente.getCiudad()
        );

        // estado
        statement.setString(
                11,
                cliente.getEstado()
        );

        // codigo postal
        statement.setString(
                12,
                cliente.getCodigoPostal()
        );

        // limite credito
        BigDecimal limiteCredito =
                cliente.getLimiteCredito();

        statement.setBigDecimal(
                13,
                limiteCredito
        );

        // días crédito
        Integer diasCredito =
                cliente.getDiasCredito();

        statement.setInt(
                14,
                diasCredito
        );

        // descuento
        BigDecimal descuento =
                cliente.getDescuento();

        statement.setBigDecimal(
                15,
                descuento
        );

        // permite crédito
        statement.setBoolean(
                16,
                Boolean.TRUE.equals(
                        cliente.getPermiteCredito()
                )
        );

        // referencia domicilio
        statement.setString(
                17,
                cliente.getReferenciaDomicilio()
        );

        // activo
        statement.setBoolean(
                18,
                Boolean.TRUE.equals(
                        cliente.getActivo()
                )
        );
    }

    // =========================================================
    // MAPEAR RESULTSET
    // =========================================================

    private Cliente mapearCliente(
            ResultSet resultSet)
            throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setId(
                resultSet.getLong("id")
        );

        long listaPrecioId =
                resultSet.getLong(
                        "lista_precio_id"
                );

        if (resultSet.wasNull()) {
            cliente.setListaPrecioId(null);
        } else {
            cliente.setListaPrecioId(
                    listaPrecioId
            );
        }

        cliente.setCodigo(
                resultSet.getString("codigo")
        );

        cliente.setNombre(
                resultSet.getString("nombre")
        );

        cliente.setRazonSocial(
                resultSet.getString("razon_social")
        );

        cliente.setRfc(
                resultSet.getString("rfc")
        );

        cliente.setTelefono(
                resultSet.getString("telefono")
        );

        cliente.setCelular(
                resultSet.getString("celular")
        );

        cliente.setEmail(
                resultSet.getString("email")
        );

        cliente.setDireccion(
                resultSet.getString("direccion")
        );

        cliente.setCiudad(
                resultSet.getString("ciudad")
        );

        cliente.setEstado(
                resultSet.getString("estado")
        );

        cliente.setCodigoPostal(
                resultSet.getString("codigo_postal")
        );

        cliente.setLimiteCredito(
                resultSet.getBigDecimal(
                        "limite_credito"
                )
        );

        cliente.setDiasCredito(
                resultSet.getInt(
                        "dias_credito"
                )
        );

        cliente.setDescuento(
                resultSet.getBigDecimal(
                        "descuento"
                )
        );

        cliente.setPermiteCredito(
                resultSet.getBoolean(
                        "permite_credito"
                )
        );

        cliente.setReferenciaDomicilio(
                resultSet.getString(
                        "referencia_domicilio"
                )
        );

        cliente.setActivo(
                resultSet.getBoolean(
                        "activo"
                )
        );

        Timestamp created =
                resultSet.getTimestamp(
                        "created_at"
                );

        if (created != null) {

            cliente.setCreatedAt(
                    created.toLocalDateTime()
            );
        }

        Timestamp updated =
                resultSet.getTimestamp(
                        "updated_at"
                );

        if (updated != null) {

            cliente.setUpdatedAt(
                    updated.toLocalDateTime()
            );
        }

        return cliente;
    }
}