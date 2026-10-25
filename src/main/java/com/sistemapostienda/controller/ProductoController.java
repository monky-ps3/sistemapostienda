/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;


import com.sistemapostienda.model.Producto;
import com.sistemapostienda.service.ProductoService;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de administrar las operaciones
 * relacionadas con los productos.
 *
 * Comunicación:
 *
 * ProductoController
 *        ↓
 * ProductoService
 *        ↓
 * ProductoDAO
 *        ↓
 * Base de datos
 *
 * El Controller no contiene consultas SQL.
 *
 * ============================================================
 */
public class ProductoController {

    /*
     * ============================================================
     * SERVICE
     * ============================================================
     */

    private final ProductoService productoService;

    /**
     * Constructor.
     */
    public ProductoController() {

        this.productoService =
                new ProductoService();

    }

    /*
     * ============================================================
     * CREAR
     * ============================================================
     */

    /**
     * Registra un nuevo producto.
     *
     * @param producto Producto que se desea registrar.
     *
     * @return true si fue registrado correctamente.
     */
    public boolean crear(Producto producto) {

        return productoService.guardar(
                producto
        );

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
    public boolean actualizar(
            Producto producto) {

        return productoService.actualizar(
                producto
        );

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina un producto por ID.
     *
     * @param id ID del producto.
     *
     * @return true si fue eliminado correctamente.
     */
    public boolean eliminar(Long id) {

        return productoService.eliminar(id);

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

        return productoService.buscarPorId(id);

    }

    /*
     * ============================================================
     * BUSCAR POR CÓDIGO
     * ============================================================
     */

    /**
     * Busca un producto mediante su código.
     *
     * Este método será especialmente útil
     * cuando posteriormente conectemos el lector
     * de códigos de barras.
     *
     * @param codigo Código del producto.
     *
     * @return Producto encontrado o null.
     */
    public Producto buscarPorCodigo(
            String codigo) {

        return productoService.buscarPorCodigo(
                codigo
        );

    }

    /*
     * ============================================================
     * BUSCAR POR SKU
     * ============================================================
     */

    /**
     * Busca un producto mediante su SKU.
     *
     * @param sku SKU del producto.
     *
     * @return Producto encontrado o null.
     */
    public Producto buscarPorSku(
            String sku) {

        return productoService.buscarPorSku(
                sku
        );

    }

    /*
     * ============================================================
     * BUSCAR POR NOMBRE
     * ============================================================
     */

    /**
     * Busca productos mediante parte de su nombre.
     *
     * Ejemplo:
     *
     * buscarPorNombre("coca")
     *
     * Puede devolver:
     *
     * Coca Cola 600ml
     * Coca Cola 1L
     * Coca Cola 2L
     *
     * @param nombre Texto que se desea buscar.
     *
     * @return Lista de productos encontrados.
     */
    public List<Producto> buscarPorNombre(
            String nombre) {

        return productoService.buscarPorNombre(
                nombre
        );

    }

    /*
     * ============================================================
     * LISTAR TODOS
     * ============================================================
     */

    /**
     * Obtiene todos los productos.
     *
     * @return Lista completa de productos.
     */
    public List<Producto> listarTodos() {

        return productoService.listarTodos();

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

        return productoService.listarActivos();

    }

    /*
     * ============================================================
     * LISTAR POR CATEGORÍA
     * ============================================================
     */

    /**
     * Obtiene los productos pertenecientes
     * a una categoría.
     *
     * @param categoriaId ID de la categoría.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarPorCategoria(
            Long categoriaId) {

        return productoService.listarPorCategoria(
                categoriaId
        );

    }

    /*
     * ============================================================
     * LISTAR POR MARCA
     * ============================================================
     */

    /**
     * Obtiene los productos pertenecientes
     * a una marca.
     *
     * @param marcaId ID de la marca.
     *
     * @return Lista de productos.
     */
    public List<Producto> listarPorMarca(
            Long marcaId) {

        return productoService.listarPorMarca(
                marcaId
        );

    }

    /*
     * ============================================================
     * EXISTE CÓDIGO
     * ============================================================
     */

    /**
     * Verifica si ya existe un producto
     * con determinado código.
     *
     * @param codigo Código del producto.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(
            String codigo) {

        return productoService.existeCodigo(
                codigo
        );

    }

    /*
     * ============================================================
     * EXISTE SKU
     * ============================================================
     */

    /**
     * Verifica si ya existe un producto
     * con determinado SKU.
     *
     * @param sku SKU del producto.
     *
     * @return true si existe.
     */
    public boolean existeSku(
            String sku) {

        return productoService.existeSku(
                sku
        );

    }

}

