/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;



import com.sistemapostienda.model.Categoria;
import com.sistemapostienda.service.CategoriaService;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Controller encargado de administrar
 * las operaciones relacionadas con
 * las categorías de productos.
 *
 * Controller
 *      │
 *      ▼
 * CategoriaService
 *      │
 *      ▼
 * CategoriaDAO
 *
 * ============================================================
 */
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Constructor.
     */
    public CategoriaController() {

        this.categoriaService = new CategoriaService();

    }

    /**
     * Registra una nueva categoría.
     *
     * @param categoria Categoría a registrar.
     *
     * @return true si fue registrada.
     */
    public boolean crear(Categoria categoria) {

        return categoriaService.guardar(categoria);

    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoria Categoría actualizada.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Categoria categoria) {

        return categoriaService.actualizar(categoria);

    }

    /**
     * Elimina una categoría.
     *
     * @param id Identificador de la categoría.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        return categoriaService.eliminar(id);

    }

    /**
     * Busca una categoría por su ID.
     *
     * @param id Identificador.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorId(Long id) {

        return categoriaService.buscarPorId(id);

    }

    /**
     * Busca una categoría por su nombre.
     *
     * @param nombre Nombre de la categoría.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorNombre(String nombre) {

        return categoriaService.buscarPorNombre(nombre);

    }

    /**
     * Busca una categoría por su código.
     *
     * @param codigo Código de la categoría.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorCodigo(String codigo) {

        return categoriaService.buscarPorCodigo(codigo);

    }

    /**
     * Obtiene todas las categorías.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> listarTodos() {

        return categoriaService.listarTodos();

    }

    /**
     * Obtiene únicamente las categorías activas.
     *
     * @return Lista de categorías activas.
     */
    public List<Categoria> listarActivos() {

        return categoriaService.listarActivos();

    }

    /**
     * Verifica si un nombre de categoría
     * ya se encuentra registrado.
     *
     * @param nombre Nombre de la categoría.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        return categoriaService.existeNombre(nombre);

    }

    /**
     * Verifica si un código de categoría
     * ya se encuentra registrado.
     *
     * @param codigo Código de la categoría.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        return categoriaService.existeCodigo(codigo);

    }

}
