/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.ProductoDAO;
import com.sistemapostienda.model.Producto;

import java.math.BigDecimal;
import java.util.List;

/**
 * ============================================================ SistemaPOSTienda
 *
 * Service encargado de la lógica de negocio de productos.
 *
 * Comunicación:
 *
 * ProductoController ↓ ProductoService ↓ ProductoDAO ↓ Base de datos
 *
 * ============================================================
 */
public class ProductoService {

    /*
     * ============================================================
     * DAO
     * ============================================================
     */
    private final ProductoDAO productoDAO;

    /**
     * Constructor.
     */
    public ProductoService() {

        this.productoDAO = new ProductoDAO();

    }

    /*
     * ============================================================
     * GUARDAR
     * ============================================================
     */
    /**
     * Registra un nuevo producto.
     *
     * @param producto Producto que se desea registrar.
     *
     * @return true si fue registrado correctamente.
     */
    public boolean guardar(Producto producto) {

        /*
         * El objeto no puede ser null.
         */
        if (producto == null) {

            return false;

        }

        /*
         * La categoría es obligatoria.
         */
        if (producto.getCategoriaId() == null
                || producto.getCategoriaId() <= 0) {

            return false;

        }

        /*
         * La unidad de medida es obligatoria.
         */
        if (producto.getUnidadMedidaId() == null
                || producto.getUnidadMedidaId() <= 0) {

            return false;

        }

        /*
         * El código es obligatorio.
         */
        if (producto.getCodigo() == null
                || producto.getCodigo().trim().isEmpty()) {

            return false;

        }

        /*
         * El nombre es obligatorio.
         */
        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            return false;

        }

        /*
         * Verificamos que el código no exista.
         */
        if (productoDAO.existeCodigo(
                producto.getCodigo())) {

            return false;

        }

        /*
         * El SKU es opcional.
         *
         * Solamente verificamos duplicado
         * cuando realmente fue proporcionado.
         */
        if (producto.getSku() != null
                && !producto.getSku().trim().isEmpty()) {

            if (productoDAO.existeSku(
                    producto.getSku())) {

                return false;

            }

        }

        /*
         * Validamos el porcentaje de IVA.
         */
        if (!validarIva(producto)) {

            return false;

        }

        /*
         * Validamos cantidades de inventario.
         */
        if (!validarStock(producto)) {

            return false;

        }

        /*
         * Si todas las validaciones son correctas,
         * enviamos el producto al DAO.
         */
        return productoDAO.guardar(producto);

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

        if (producto == null) {

            return false;

        }

        /*
         * El producto debe tener ID.
         */
        if (producto.getId() == null
                || producto.getId() <= 0) {

            return false;

        }

        /*
         * Categoría obligatoria.
         */
        if (producto.getCategoriaId() == null
                || producto.getCategoriaId() <= 0) {

            return false;

        }

        /*
         * Unidad de medida obligatoria.
         */
        if (producto.getUnidadMedidaId() == null
                || producto.getUnidadMedidaId() <= 0) {

            return false;

        }

        /*
         * Código obligatorio.
         */
        if (producto.getCodigo() == null
                || producto.getCodigo().trim().isEmpty()) {

            return false;

        }

        /*
         * Nombre obligatorio.
         */
        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            return false;

        }

        /*
         * Validamos IVA.
         */
        if (!validarIva(producto)) {

            return false;

        }

        /*
         * Validamos stock.
         */
        if (!validarStock(producto)) {

            return false;

        }

        /*
         * En una actualización debemos permitir
         * que el producto conserve su propio código
         * y SKU.
         *
         * Por eso aquí todavía no utilizamos
         * existeCodigo() directamente.
         *
         * Esa validación la podremos hacer posteriormente
         * mediante consultas que excluyan el ID actual.
         */
 /*
 * Verificamos que el código no pertenezca
 * a otro producto.
         */
        if (productoDAO.existeCodigoExceptoId(
                producto.getCodigo(),
                producto.getId())) {

            return false;

        }

        /*
 * El SKU es opcional.
 *
 * Si existe, verificamos que no pertenezca
 * a otro producto.
         */
        if (producto.getSku() != null
                && !producto.getSku().trim().isEmpty()) {

            if (productoDAO.existeSkuExceptoId(
                    producto.getSku(),
                    producto.getId())) {

                return false;

            }

        }

        /*
 * Todas las validaciones fueron correctas.
         */
        return productoDAO.actualizar(producto);

    }

    /*
     * ============================================================
     * VALIDAR IVA
     * ============================================================
     */
    /**
     * Valida la configuración del IVA.
     *
     * @param producto Producto.
     *
     * @return true si la configuración es válida.
     */
    private boolean validarIva(Producto producto) {

        /*
         * Si aplica IVA, necesitamos porcentaje.
         */
        if (Boolean.TRUE.equals(
                producto.getAplicaIva())) {

            if (producto.getPorcentajeIva() == null) {

                return false;

            }

            /*
             * El porcentaje no puede ser negativo.
             */
            if (producto.getPorcentajeIva()
                    .compareTo(BigDecimal.ZERO) < 0) {

                return false;

            }

            /*
             * No permitimos una tasa mayor a 100%.
             */
            if (producto.getPorcentajeIva()
                    .compareTo(
                            new BigDecimal("100")
                    ) > 0) {

                return false;

            }

        } else {

            /*
             * Si no aplica IVA, forzamos
             * el porcentaje a cero.
             */
            producto.setPorcentajeIva(
                    BigDecimal.ZERO
            );

        }

        return true;

    }

    /*
     * ============================================================
     * VALIDAR STOCK
     * ============================================================
     */
    /**
     * Valida stock mínimo y máximo.
     *
     * @param producto Producto.
     *
     * @return true si los valores son correctos.
     */
    private boolean validarStock(
            Producto producto) {

        /*
         * El stock mínimo no puede ser null.
         */
        if (producto.getStockMinimo() == null) {

            producto.setStockMinimo(
                    BigDecimal.ZERO
            );

        }

        /*
         * El stock mínimo no puede ser negativo.
         */
        if (producto.getStockMinimo()
                .compareTo(BigDecimal.ZERO) < 0) {

            return false;

        }

        /*
         * El stock máximo es opcional.
         */
        if (producto.getStockMaximo() != null) {

            /*
             * No puede ser negativo.
             */
            if (producto.getStockMaximo()
                    .compareTo(BigDecimal.ZERO) < 0) {

                return false;

            }

            /*
             * El máximo no debería ser menor
             * que el mínimo.
             */
            if (producto.getStockMaximo()
                    .compareTo(
                            producto.getStockMinimo()
                    ) < 0) {

                return false;

            }

        }

        return true;

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */
    /**
     * Elimina un producto.
     *
     * @param id ID del producto.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {

            return false;

        }

        return productoDAO.eliminar(id);

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */
    public Producto buscarPorId(Long id) {

        if (id == null || id <= 0) {

            return null;

        }

        return productoDAO.buscarPorId(id);

    }

    /*
     * ============================================================
     * BUSCAR POR CÓDIGO
     * ============================================================
     */
    public Producto buscarPorCodigo(
            String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return null;

        }

        return productoDAO.buscarPorCodigo(codigo);

    }

    /*
     * ============================================================
     * BUSCAR POR SKU
     * ============================================================
     */
    public Producto buscarPorSku(
            String sku) {

        if (sku == null
                || sku.trim().isEmpty()) {

            return null;

        }

        return productoDAO.buscarPorSku(sku);

    }

    /*
     * ============================================================
     * BUSCAR POR NOMBRE
     * ============================================================
     */
    public List<Producto> buscarPorNombre(
            String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return List.of();

        }

        return productoDAO.buscarPorNombre(nombre);

    }

    /*
     * ============================================================
     * LISTAR TODOS
     * ============================================================
     */
    public List<Producto> listarTodos() {

        return productoDAO.listarTodos();

    }

    /*
     * ============================================================
     * LISTAR ACTIVOS
     * ============================================================
     */
    public List<Producto> listarActivos() {

        return productoDAO.listarActivos();

    }

    /*
     * ============================================================
     * LISTAR POR CATEGORÍA
     * ============================================================
     */
    public List<Producto> listarPorCategoria(
            Long categoriaId) {

        if (categoriaId == null
                || categoriaId <= 0) {

            return List.of();

        }

        return productoDAO.listarPorCategoria(
                categoriaId
        );

    }

    /*
     * ============================================================
     * LISTAR POR MARCA
     * ============================================================
     */
    public List<Producto> listarPorMarca(
            Long marcaId) {

        if (marcaId == null
                || marcaId <= 0) {

            return List.of();

        }

        return productoDAO.listarPorMarca(
                marcaId
        );

    }

    /*
     * ============================================================
     * EXISTE CÓDIGO
     * ============================================================
     */
    public boolean existeCodigo(
            String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return false;

        }

        return productoDAO.existeCodigo(codigo);

    }

    /*
     * ============================================================
     * EXISTE SKU
     * ============================================================
     */
    public boolean existeSku(
            String sku) {

        if (sku == null
                || sku.trim().isEmpty()) {

            return false;

        }

        return productoDAO.existeSku(sku);

    }

}
