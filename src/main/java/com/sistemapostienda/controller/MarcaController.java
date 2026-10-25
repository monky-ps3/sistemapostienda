/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.Marca;
import com.sistemapostienda.service.MarcaService;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de administrar
 * las operaciones relacionadas con
 * las marcas de productos.
 *
 * Controller
 *      │
 *      ▼
 * MarcaService
 *      │
 *      ▼
 * MarcaDAO
 *
 * ============================================================
 */
public class MarcaController {

    private final MarcaService marcaService;

    /**
     * Constructor.
     */
    public MarcaController() {

        this.marcaService = new MarcaService();

    }

    /**
     * Registra una nueva marca.
     *
     * @param marca Marca a registrar.
     *
     * @return true si fue registrada.
     */
    public boolean crear(Marca marca) {

        return marcaService.guardar(marca);

    }

    /**
     * Actualiza una marca existente.
     *
     * @param marca Marca actualizada.
     *
     * @return true si fue actualizada.
     */
    public boolean actualizar(Marca marca) {

        return marcaService.actualizar(marca);

    }

    /**
     * Elimina una marca.
     *
     * @param id Identificador de la marca.
     *
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        return marcaService.eliminar(id);

    }

    /**
     * Busca una marca por su ID.
     *
     * @param id Identificador.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorId(Long id) {

        return marcaService.buscarPorId(id);

    }

    /**
     * Busca una marca por su nombre.
     *
     * @param nombre Nombre de la marca.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorNombre(String nombre) {

        return marcaService.buscarPorNombre(nombre);

    }

    /**
     * Busca una marca por su código.
     *
     * @param codigo Código de la marca.
     *
     * @return Marca encontrada.
     */
    public Marca buscarPorCodigo(String codigo) {

        return marcaService.buscarPorCodigo(codigo);

    }

    /**
     * Obtiene todas las marcas.
     *
     * @return Lista de marcas.
     */
    public List<Marca> listarTodos() {

        return marcaService.listarTodos();

    }

    /**
     * Obtiene únicamente las marcas activas.
     *
     * @return Lista de marcas activas.
     */
    public List<Marca> listarActivos() {

        return marcaService.listarActivos();

    }

    /**
     * Verifica si un nombre de marca
     * ya se encuentra registrado.
     *
     * @param nombre Nombre de la marca.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        return marcaService.existeNombre(nombre);

    }

    /**
     * Verifica si un código de marca
     * ya se encuentra registrado.
     *
     * @param codigo Código de la marca.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        return marcaService.existeCodigo(codigo);

    }

}
