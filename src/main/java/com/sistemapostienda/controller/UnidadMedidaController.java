/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.UnidadMedida;
import com.sistemapostienda.service.UnidadMedidaService;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller encargado de administrar las operaciones
 * relacionadas con las unidades de medida.
 *
 * La comunicación es:
 *
 * Controller
 *      ↓
 * UnidadMedidaService
 *      ↓
 * UnidadMedidaDAO
 *      ↓
 * Base de datos
 *
 * El Controller NO realiza directamente consultas SQL.
 *
 * ============================================================
 */
public class UnidadMedidaController {

    /*
     * ============================================================
     * SERVICE
     * ============================================================
     */

    private final UnidadMedidaService unidadMedidaService;

    /**
     * Constructor.
     */
    public UnidadMedidaController() {

        this.unidadMedidaService =
                new UnidadMedidaService();

    }

    /*
     * ============================================================
     * CREAR
     * ============================================================
     */

    /**
     * Registra una nueva unidad de medida.
     *
     * @param unidadMedida Unidad que se desea registrar.
     *
     * @return true si fue registrada correctamente.
     */
    public boolean crear(UnidadMedida unidadMedida) {

        return unidadMedidaService.guardar(
                unidadMedida
        );

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */

    /**
     * Actualiza una unidad de medida existente.
     *
     * @param unidadMedida Unidad actualizada.
     *
     * @return true si fue actualizada correctamente.
     */
    public boolean actualizar(
            UnidadMedida unidadMedida) {

        return unidadMedidaService.actualizar(
                unidadMedida
        );

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina una unidad de medida.
     *
     * @param id ID de la unidad.
     *
     * @return true si fue eliminada correctamente.
     */
    public boolean eliminar(Long id) {

        return unidadMedidaService.eliminar(id);

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */

    /**
     * Busca una unidad de medida por ID.
     *
     * @param id Identificador.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorId(Long id) {

        return unidadMedidaService.buscarPorId(id);

    }

    /*
     * ============================================================
     * BUSCAR POR NOMBRE
     * ============================================================
     */

    /**
     * Busca una unidad de medida por nombre.
     *
     * @param nombre Nombre de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorNombre(
            String nombre) {

        return unidadMedidaService.buscarPorNombre(
                nombre
        );

    }

    /*
     * ============================================================
     * BUSCAR POR CÓDIGO
     * ============================================================
     */

    /**
     * Busca una unidad de medida por código.
     *
     * @param codigo Código de la unidad.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorCodigo(
            String codigo) {

        return unidadMedidaService.buscarPorCodigo(
                codigo
        );

    }

    /*
     * ============================================================
     * BUSCAR POR ABREVIATURA
     * ============================================================
     */

    /**
     * Busca una unidad de medida por abreviatura.
     *
     * @param abreviatura Abreviatura.
     *
     * @return Unidad encontrada o null.
     */
    public UnidadMedida buscarPorAbreviatura(
            String abreviatura) {

        return unidadMedidaService.buscarPorAbreviatura(
                abreviatura
        );

    }

    /*
     * ============================================================
     * LISTAR TODOS
     * ============================================================
     */

    /**
     * Obtiene todas las unidades de medida.
     *
     * @return Lista de unidades.
     */
    public List<UnidadMedida> listarTodos() {

        return unidadMedidaService.listarTodos();

    }

    /*
     * ============================================================
     * LISTAR ACTIVOS
     * ============================================================
     */

    /**
     * Obtiene únicamente las unidades activas.
     *
     * @return Lista de unidades activas.
     */
    public List<UnidadMedida> listarActivos() {

        return unidadMedidaService.listarActivos();

    }

    /*
     * ============================================================
     * EXISTE NOMBRE
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinado nombre.
     *
     * @param nombre Nombre de la unidad.
     *
     * @return true si existe.
     */
    public boolean existeNombre(String nombre) {

        return unidadMedidaService.existeNombre(
                nombre
        );

    }

    /*
     * ============================================================
     * EXISTE CÓDIGO
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinado código.
     *
     * @param codigo Código de la unidad.
     *
     * @return true si existe.
     */
    public boolean existeCodigo(String codigo) {

        return unidadMedidaService.existeCodigo(
                codigo
        );

    }

    /*
     * ============================================================
     * EXISTE ABREVIATURA
     * ============================================================
     */

    /**
     * Verifica si existe una unidad
     * con determinada abreviatura.
     *
     * @param abreviatura Abreviatura.
     *
     * @return true si existe.
     */
    public boolean existeAbreviatura(
            String abreviatura) {

        return unidadMedidaService.existeAbreviatura(
                abreviatura
        );

    }

}
