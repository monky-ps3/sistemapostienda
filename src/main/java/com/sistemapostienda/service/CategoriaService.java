/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;


import com.sistemapostienda.dao.CategoriaDAO;
import com.sistemapostienda.model.Categoria;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Servicio encargado de administrar
 * la lógica de negocio relacionada con
 * las categorías.
 *
 * Se comunica con CategoriaDAO para realizar
 * las operaciones sobre la base de datos.
 *
 * ============================================================
 */
public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    /**
     * Constructor.
     */
    public CategoriaService() {

        this.categoriaDAO = new CategoriaDAO();

    }

    /**
     * Guarda una nueva categoría.
     *
     * @param categoria Categoría.
     *
     * @return true si fue registrada.
     */
    public boolean guardar(Categoria categoria) {

        if (categoria == null) {
            return false;
        }

        if (categoria.getNombre() == null
                || categoria.getNombre().trim().isEmpty()) {

            return false;

        }

        if (categoria.getCodigo() == null
                || categoria.getCodigo().trim().isEmpty()) {

            return false;

        }

        if (categoriaDAO.existeNombre(
                categoria.getNombre())) {

            return false;

        }

        if (categoriaDAO.existeCodigo(
                categoria.getCodigo())) {

            return false;

        }

        return categoriaDAO.guardar(categoria);

    }

    /**
     * Actualiza una categoría.
     *
     * @param categoria Categoría.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Categoria categoria) {

        if (categoria == null) {
            return false;
        }

        if (categoria.getId() == null
                || categoria.getId() <= 0) {

            return false;

        }

        return categoriaDAO.actualizar(categoria);

    }

    /**
     * Elimina una categoría.
     *
     * @param id ID de la categoría.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {

            return false;

        }

        return categoriaDAO.eliminar(id);

    }

    /**
     * Busca una categoría por ID.
     *
     * @param id Identificador.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorId(Long id) {

        if (id == null || id <= 0) {

            return null;

        }

        return categoriaDAO.buscarPorId(id);

    }

    /**
     * Busca una categoría por nombre.
     *
     * @param nombre Nombre.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorNombre(String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return null;

        }

        return categoriaDAO.buscarPorNombre(nombre);

    }

    /**
     * Busca una categoría por código.
     *
     * @param codigo Código.
     *
     * @return Categoría encontrada.
     */
    public Categoria buscarPorCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return null;

        }

        return categoriaDAO.buscarPorCodigo(codigo);

    }

    /**
     * Lista todas las categorías.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> listarTodos() {

        return categoriaDAO.listarTodos();

    }

    /**
     * Lista únicamente las categorías activas.
     *
     * @return Lista de categorías activas.
     */
    public List<Categoria> listarActivos() {

        return categoriaDAO.listarActivos();

    }

    /**
     * Verifica si existe un nombre.
     *
     * @param nombre Nombre.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return false;

        }

        return categoriaDAO.existeNombre(nombre);

    }

    /**
     * Verifica si existe un código.
     *
     * @param codigo Código.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return false;

        }

        return categoriaDAO.existeCodigo(codigo);

    }

}
