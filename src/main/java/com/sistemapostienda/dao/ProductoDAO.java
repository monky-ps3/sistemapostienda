/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.dao;

import com.sistemapostienda.database.Database;
import com.sistemapostienda.model.Producto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================ SistemaPOSTienda
 *
 * DAO encargado del acceso a la tabla productos.
 *
 * Responsabilidades:
 *
 * - Insertar productos. - Actualizar productos. - Eliminar productos. - Buscar
 * productos. - Listar productos. - Verificar duplicados.
 *
 * ============================================================
 */
public class ProductoDAO {

    /*
     * ============================================================
     * CONSULTAS SQL
     * ============================================================
     */
    /**
     * Inserta un nuevo producto.
     */
    private static final String INSERTAR
            = """
            INSERT INTO productos
            (
                categoria_id,
                marca_id,
                unidad_medida_id,
                codigo,
                sku,
                nombre,
                descripcion,
                imagen,
                aplica_iva,
                porcentaje_iva,
                maneja_inventario,
                maneja_lotes,
                maneja_caducidad,
                venta_por_peso,
                permite_decimales,
                stock_minimo,
                stock_maximo,
                permite_descuento,
                activo
            )
            VALUES
            (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
            """;

    /**
     * Actualiza un producto existente.
     */
    private static final String ACTUALIZAR
            = """
            UPDATE productos SET

                categoria_id = ?,
                marca_id = ?,
                unidad_medida_id = ?,
                codigo = ?,
                sku = ?,
                nombre = ?,
                descripcion = ?,
                imagen = ?,
                aplica_iva = ?,
                porcentaje_iva = ?,
                maneja_inventario = ?,
                maneja_lotes = ?,
                maneja_caducidad = ?,
                venta_por_peso = ?,
                permite_decimales = ?,
                stock_minimo = ?,
                stock_maximo = ?,
                permite_descuento = ?,
                activo = ?

            WHERE id = ?
            """;

    /**
     * Elimina un producto por ID.
     */
    private static final String ELIMINAR
            = """
            DELETE FROM productos
            WHERE id = ?
            """;

    /**
     * Busca un producto por ID.
     */
    private static final String BUSCAR_POR_ID
            = """
            SELECT *
            FROM productos
            WHERE id = ?
            """;

    /**
     * Busca un producto por código.
     */
    private static final String BUSCAR_POR_CODIGO
            = """
            SELECT *
            FROM productos
            WHERE codigo = ?
            """;

    /**
     * Busca un producto por SKU.
     */
    private static final String BUSCAR_POR_SKU
            = """
            SELECT *
            FROM productos
            WHERE sku = ?
            """;

    /**
     * Busca productos por nombre.
     */
    private static final String BUSCAR_POR_NOMBRE
            = """
            SELECT *
            FROM productos
            WHERE nombre LIKE ?
            ORDER BY nombre
            """;

    /**
     * Obtiene todos los productos.
     */
    private static final String LISTAR_TODOS
            = """
            SELECT *
            FROM productos
            ORDER BY nombre
            """;

    /**
     * Obtiene únicamente productos activos.
     */
    private static final String LISTAR_ACTIVOS
            = """
            SELECT *
            FROM productos
            WHERE activo = TRUE
            ORDER BY nombre
            """;

    /**
     * Obtiene productos de una categoría.
     */
    private static final String LISTAR_POR_CATEGORIA
            = """
            SELECT *
            FROM productos
            WHERE categoria_id = ?
            ORDER BY nombre
            """;

    /**
     * Obtiene productos de una marca.
     */
    private static final String LISTAR_POR_MARCA
            = """
            SELECT *
            FROM productos
            WHERE marca_id = ?
            ORDER BY nombre
            """;

    /**
     * Verifica si existe un código.
     */
    private static final String EXISTE_CODIGO
            = """
            SELECT COUNT(*)
            FROM productos
            WHERE codigo = ?
            """;

    /**
     * Verifica si existe un SKU.
     */
    private static final String EXISTE_SKU
            = """
            SELECT COUNT(*)
            FROM productos
            WHERE sku = ?
            """;

  private static final String EXISTE_CODIGO_EXCEPTO_ID
        = """
        SELECT COUNT(*)
        FROM productos
        WHERE codigo = ?
        AND id <> ?
        """;

private static final String EXISTE_SKU_EXCEPTO_ID
        = """
        SELECT COUNT(*)
        FROM productos
        WHERE sku = ?
        AND id <> ?
        """;  
    
    
    
    
    
    /**
     * Constructor.
     */
    public ProductoDAO() {

    }

    /*
     * ============================================================
     * GUARDAR
     * ============================================================
     */
    /**
     * Guarda un nuevo producto.
     *
     * @param producto Producto que se desea guardar.
     *
     * @return true si fue insertado correctamente.
     */
    public boolean guardar(Producto producto) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        INSERTAR
                )) {

            ps.setLong(
                    1,
                    producto.getCategoriaId()
            );

            /*
             * marca_id puede ser NULL.
             */
            if (producto.getMarcaId() != null) {

                ps.setLong(
                        2,
                        producto.getMarcaId()
                );

            } else {

                ps.setNull(
                        2,
                        java.sql.Types.BIGINT
                );

            }

            ps.setLong(
                    3,
                    producto.getUnidadMedidaId()
            );

            ps.setString(
                    4,
                    producto.getCodigo()
            );

            ps.setString(
                    5,
                    producto.getSku()
            );

            ps.setString(
                    6,
                    producto.getNombre()
            );

            ps.setString(
                    7,
                    producto.getDescripcion()
            );

            ps.setString(
                    8,
                    producto.getImagen()
            );

            ps.setBoolean(
                    9,
                    producto.getAplicaIva()
            );

            ps.setBigDecimal(
                    10,
                    producto.getPorcentajeIva()
            );

            ps.setBoolean(
                    11,
                    producto.getManejaInventario()
            );

            ps.setBoolean(
                    12,
                    producto.getManejaLotes()
            );

            ps.setBoolean(
                    13,
                    producto.getManejaCaducidad()
            );

            ps.setBoolean(
                    14,
                    producto.getVentaPorPeso()
            );

            ps.setBoolean(
                    15,
                    producto.getPermiteDecimales()
            );

            ps.setBigDecimal(
                    16,
                    producto.getStockMinimo()
            );

            ps.setBigDecimal(
                    17,
                    producto.getStockMaximo()
            );

            ps.setBoolean(
                    18,
                    producto.getPermiteDescuento()
            );

            ps.setBoolean(
                    19,
                    producto.getActivo()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar el producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */
    /**
     * Actualiza un producto existente.
     *
     * @param producto Producto actualizado.
     *
     * @return true si fue actualizado correctamente.
     */
    public boolean actualizar(Producto producto) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        ACTUALIZAR
                )) {

            ps.setLong(
                    1,
                    producto.getCategoriaId()
            );

            /*
             * marca_id puede ser NULL.
             */
            if (producto.getMarcaId() != null) {

                ps.setLong(
                        2,
                        producto.getMarcaId()
                );

            } else {

                ps.setNull(
                        2,
                        java.sql.Types.BIGINT
                );

            }

            ps.setLong(
                    3,
                    producto.getUnidadMedidaId()
            );

            ps.setString(
                    4,
                    producto.getCodigo()
            );

            ps.setString(
                    5,
                    producto.getSku()
            );

            ps.setString(
                    6,
                    producto.getNombre()
            );

            ps.setString(
                    7,
                    producto.getDescripcion()
            );

            ps.setString(
                    8,
                    producto.getImagen()
            );

            ps.setBoolean(
                    9,
                    producto.getAplicaIva()
            );

            ps.setBigDecimal(
                    10,
                    producto.getPorcentajeIva()
            );

            ps.setBoolean(
                    11,
                    producto.getManejaInventario()
            );

            ps.setBoolean(
                    12,
                    producto.getManejaLotes()
            );

            ps.setBoolean(
                    13,
                    producto.getManejaCaducidad()
            );

            ps.setBoolean(
                    14,
                    producto.getVentaPorPeso()
            );

            ps.setBoolean(
                    15,
                    producto.getPermiteDecimales()
            );

            ps.setBigDecimal(
                    16,
                    producto.getStockMinimo()
            );

            ps.setBigDecimal(
                    17,
                    producto.getStockMaximo()
            );

            ps.setBoolean(
                    18,
                    producto.getPermiteDescuento()
            );

            ps.setBoolean(
                    19,
                    producto.getActivo()
            );

            ps.setLong(
                    20,
                    producto.getId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar el producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto.
     *
     * @return true si fue eliminado correctamente.
     */
    public boolean eliminar(Long id) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        ELIMINAR
                )) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al eliminar el producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */
    /**
     * Busca un producto por su ID.
     *
     * @param id ID del producto.
     *
     * @return Producto encontrado o null.
     */
    public Producto buscarPorId(Long id) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        BUSCAR_POR_ID
                )) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearProducto(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar producto por ID."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /*
     * ============================================================
     * BUSCAR POR CÓDIGO
     * ============================================================
     */
    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     *
     * @return Producto encontrado o null.
     */
    public Producto buscarPorCodigo(String codigo) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        BUSCAR_POR_CODIGO
                )) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearProducto(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar producto por código."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /*
     * ============================================================
     * BUSCAR POR SKU
     * ============================================================
     */
    /**
     * Busca un producto por SKU.
     *
     * @param sku SKU del producto.
     *
     * @return Producto encontrado o null.
     */
    public Producto buscarPorSku(String sku) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        BUSCAR_POR_SKU
                )) {

            ps.setString(1, sku);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapearProducto(rs);

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar producto por SKU."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return null;

    }

    /*
     * ============================================================
     * BUSCAR POR NOMBRE
     * ============================================================
     */
    /**
     * Busca productos cuyo nombre contenga el texto indicado.
     *
     * Ejemplo:
     *
     * buscarPorNombre("coca")
     *
     * Puede encontrar:
     *
     * Coca Cola 600ml Coca Cola 1L Coca Cola 2L
     *
     * @param nombre Texto a buscar.
     *
     * @return Lista de productos encontrados.
     */
    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> lista = new ArrayList<>();

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        BUSCAR_POR_NOMBRE
                )) {

            ps.setString(
                    1,
                    "%" + nombre + "%"
            );

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapearProducto(rs)
                    );

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar productos por nombre."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * MAPEAR RESULTSET A PRODUCTO
     * ============================================================
     */
    /**
     * Convierte un registro del ResultSet en un objeto Producto.
     *
     * Este método evita repetir todo el código de asignación de campos en cada
     * búsqueda.
     *
     * @param rs ResultSet de la consulta.
     *
     * @return Producto construido.
     *
     * @throws SQLException Si ocurre un error al leer los datos.
     */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto
                = new Producto();
        /*ID*/
        producto.setId(rs.getLong("id"));
        /* RELACIONES*/
        producto.setCategoriaId(rs.getLong("categoria_id"));
        /* marca_id puede ser NULL.* Por eso comprobamos wasNull().*/
        long marcaId = rs.getLong("marca_id");
        if (rs.wasNull()) {
            producto.setMarcaId(null);
        } else {
            producto.setMarcaId(marcaId);
        }
        producto.setUnidadMedidaId(rs.getLong("unidad_medida_id"));

        /* DATOS DEL PRODUCTO*/
        producto.setCodigo(rs.getString("codigo"));
        producto.setSku(rs.getString("sku"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setImagen(rs.getString("imagen"));

        /*
         * IVA
         */
        producto.setAplicaIva(
                rs.getBoolean("aplica_iva")
        );

        producto.setPorcentajeIva(
                rs.getBigDecimal("porcentaje_iva")
        );

        /*
         * INVENTARIO
         */
        producto.setManejaInventario(rs.getBoolean("maneja_inventario"));

        producto.setManejaLotes(rs.getBoolean("maneja_lotes"));

        producto.setManejaCaducidad(
                rs.getBoolean("maneja_caducidad")
        );

        /*
         * VENTA
         */
        producto.setVentaPorPeso(
                rs.getBoolean("venta_por_peso")
        );

        producto.setPermiteDecimales(
                rs.getBoolean("permite_decimales")
        );

        /*
         * STOCK MÍNIMO Y MÁXIMO
         */
        producto.setStockMinimo(
                rs.getBigDecimal("stock_minimo")
        );

        producto.setStockMaximo(
                rs.getBigDecimal("stock_maximo")
        );

        /*
         * DESCUENTO
         */
        producto.setPermiteDescuento(
                rs.getBoolean("permite_descuento")
        );

        /*
         * ESTADO
         */
        producto.setActivo(
                rs.getBoolean("activo")
        );

        /*
         * FECHAS
         */
        if (rs.getTimestamp("created_at") != null) {

            producto.setCreatedAt(
                    rs.getTimestamp("created_at")
                            .toLocalDateTime()
            );

        }

        if (rs.getTimestamp("updated_at") != null) {

            producto.setUpdatedAt(
                    rs.getTimestamp("updated_at")
                            .toLocalDateTime()
            );

        }

        return producto;

    }

    /*
     * ============================================================
     * LISTAR TODOS
     * ============================================================
     */

    /**
     * Obtiene todos los productos registrados.
     *
     * @return Lista completa de productos.
     */
    public List<Producto> listarTodos() {

        List<Producto> lista
                = new ArrayList<>();

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        LISTAR_TODOS
                ); ResultSet rs
                = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(
                        mapearProducto(rs)
                );

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar todos los productos."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * LISTAR ACTIVOS
     * ============================================================
     */
    /**
     * Obtiene únicamente los productos activos.
     *
     * @return Lista de productos activos.
     */
    public List<Producto> listarActivos() {

        List<Producto> lista
                = new ArrayList<>();

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        LISTAR_ACTIVOS
                ); ResultSet rs
                = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(
                        mapearProducto(rs)
                );

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar productos activos."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * LISTAR POR CATEGORÍA
     * ============================================================
     */
    /**
     * Obtiene todos los productos pertenecientes a una categoría determinada.
     *
     * @param categoriaId ID de la categoría.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarPorCategoria(
            Long categoriaId) {

        List<Producto> lista
                = new ArrayList<>();

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        LISTAR_POR_CATEGORIA
                )) {

            ps.setLong(
                    1,
                    categoriaId
            );

            try (ResultSet rs
                    = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapearProducto(rs)
                    );

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar productos por categoría."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * LISTAR POR MARCA
     * ============================================================
     */
    /**
     * Obtiene todos los productos pertenecientes a una marca determinada.
     *
     * @param marcaId ID de la marca.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarPorMarca(
            Long marcaId) {

        List<Producto> lista
                = new ArrayList<>();

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        LISTAR_POR_MARCA
                )) {

            ps.setLong(
                    1,
                    marcaId
            );

            try (ResultSet rs
                    = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            mapearProducto(rs)
                    );

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar productos por marca."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return lista;

    }

    /*
     * ============================================================
     * EXISTE CÓDIGO
     * ============================================================
     */
    /**
     * Verifica si existe un producto con determinado código.
     *
     * @param codigo Código del producto.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        EXISTE_CODIGO
                )) {

            ps.setString(
                    1,
                    codigo
            );

            try (ResultSet rs
                    = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar código del producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    /*
     * ============================================================
     * EXISTE SKU
     * ============================================================
     */
    /**
     * Verifica si existe un producto con determinado SKU.
     *
     * @param sku SKU del producto.
     *
     * @return true si existe.
     */
    public boolean existeSku(String sku) {

        try (
                Connection conexion
                = Database.getConnection(); PreparedStatement ps
                = conexion.prepareStatement(
                        EXISTE_SKU
                )) {

            ps.setString(
                    1,
                    sku
            );

            try (ResultSet rs
                    = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al verificar SKU del producto."
            );

            System.err.println(
                    e.getMessage()
            );

        }

        return false;

    }

    
    
    /**
 * Verifica si existe un código en otro producto
 * diferente al ID proporcionado.
 *
 * @param codigo Código que se desea verificar.
 * @param id ID del producto que se está actualizando.
 *
 * @return true si el código pertenece a otro producto.
 */
public boolean existeCodigoExceptoId(
        String codigo,
        Long id) {

    try (
            Connection conexion =
                    Database.getConnection();

            PreparedStatement ps =
                    conexion.prepareStatement(
                            EXISTE_CODIGO_EXCEPTO_ID
                    )
    ) {

        ps.setString(1, codigo);

        ps.setLong(2, id);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1) > 0;

            }

        }

    } catch (SQLException e) {

        System.err.println(
                "Error al verificar código del producto."
        );

        System.err.println(
                e.getMessage()
        );

    }

    return false;

}


/**
 * Verifica si existe un SKU en otro producto
 * diferente al ID proporcionado.
 *
 * @param sku SKU que se desea verificar.
 * @param id ID del producto que se está actualizando.
 *
 * @return true si el SKU pertenece a otro producto.
 */
public boolean existeSkuExceptoId(
        String sku,
        Long id) {

    try (
            Connection conexion =
                    Database.getConnection();

            PreparedStatement ps =
                    conexion.prepareStatement(
                            EXISTE_SKU_EXCEPTO_ID
                    )
    ) {

        ps.setString(1, sku);

        ps.setLong(2, id);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1) > 0;

            }

        }

    } catch (SQLException e) {

        System.err.println(
                "Error al verificar SKU del producto."
        );

        System.err.println(
                e.getMessage()
        );

    }

    return false;

}
}
