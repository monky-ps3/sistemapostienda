/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;


import com.sistemapostienda.dao.MarcaDAO;
import com.sistemapostienda.model.Marca;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Servicio encargado de administrar
 * la lógica de negocio relacionada con
 * las marcas.
 *
 * Se comunica con MarcaDAO para realizar
 * las operaciones sobre la base de datos.
 *
 * ============================================================
 */
public class MarcaService {

    private final MarcaDAO marcaDAO;

    /**
     * Constructor.
     */
    public MarcaService() {

        this.marcaDAO = new MarcaDAO();

    }

    /**
     * Guarda una nueva marca.
     *
     * @param marca Marca.
     *
     * @return true si fue registrada.
     */
    public boolean guardar(Marca marca) {

        if (marca == null) {
            return false;
        }

        if (marca.getNombre() == null
                || marca.getNombre().trim().isEmpty()) {

            return false;

        }

        if (marca.getCodigo() == null
                || marca.getCodigo().trim().isEmpty()) {

            return false;

        }

        if (marcaDAO.existeNombre(
                marca.getNombre())) {

            return false;

        }

        if (marcaDAO.existeCodigo(
                marca.getCodigo())) {

            return false;

        }

        return marcaDAO.guardar(marca);

    }

    /**
     * Actualiza una marca.
     *
     * @param marca Marca.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Marca marca) {

        if (marca == null) {
            return false;
        }

        if (marca.getId() == null
                || marca.getId() <= 0) {

            return false;

        }

        return marcaDAO.actualizar(marca);

    }

    /**
     * Elimina una marca.
     *
     * @param id ID de la marca.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {

            return false;

        }

        return marcaDAO.eliminar(id);

    }

    /**
     * Busca una marca por ID.
     *
     * @param id Identificador.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorId(Long id) {

        if (id == null || id <= 0) {

            return null;

        }

        return marcaDAO.buscarPorId(id);

    }

    /**
     * Busca una marca por nombre.
     *
     * @param nombre Nombre.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorNombre(String nombre) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return null;

        }

        return marcaDAO.buscarPorNombre(nombre);

    }

    /**
     * Busca una marca por código.
     *
     * @param codigo Código.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorCodigo(String codigo) {

        if (codigo == null
                || codigo.trim().isEmpty()) {

            return null;

        }

        return marcaDAO.buscarPorCodigo(codigo);

    }

    /**
     * Lista todas las marcas.
     *
     * @return Lista de marcas.
     */
    public List<Marca> listarTodos() {

        return marcaDAO.listarTodos();

    }

    /**
     * Lista únicamente las marcas activas.
     *
     * @return Lista de marcas activas.
     */
    public List<Marca> listarActivos() {

        return marcaDAO.listarActivos();

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

        return marcaDAO.existeNombre(nombre);

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

        return marcaDAO.existeCodigo(codigo);

    }

}