/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.controller;

import com.sistemapostienda.model.Presentacion;
import com.sistemapostienda.service.PresentacionService;

import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Controller de Presentaciones.
 *
 * Comunicación:
 *
 * PresentacionController
 *          ↓
 * PresentacionService
 *          ↓
 * PresentacionDAO
 *          ↓
 * Base de datos
 *
 * ============================================================
 */
public class PresentacionController {

    /*
     * ============================================================
     * SERVICE
     * ============================================================
     */

    private final PresentacionService presentacionService;

    /**
     * Constructor.
     */
    public PresentacionController() {

        this.presentacionService =
                new PresentacionService();

    }

    /*
     * ============================================================
     * GUARDAR
     * ============================================================
     */

    /**
     * Registra una nueva presentación.
     *
     * @param presentacion Presentación a registrar.
     *
     * @return true si fue registrada correctamente.
     */
    public boolean guardar(
            Presentacion presentacion) {

        return presentacionService.guardar(
                presentacion
        );

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */

    /**
     * Actualiza una presentación.
     *
     * @param presentacion Presentación actualizada.
     *
     * @return true si fue actualizada correctamente.
     */
    public boolean actualizar(
            Presentacion presentacion) {

        return presentacionService.actualizar(
                presentacion
        );

    }

    /*
     * ============================================================
     * ELIMINAR
     * ============================================================
     */

    /**
     * Elimina una presentación.
     *
     * @param id ID de la presentación.
     *
     * @return true si fue eliminada correctamente.
     */
    public boolean eliminar(Long id) {

        return presentacionService.eliminar(id);

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */

    /**
     * Busca una presentación por su ID.
     *
     * @param id ID de la presentación.
     *
     * @return Presentación encontrada o null.
     */
    public Presentacion buscarPorId(Long id) {

        return presentacionService.buscarPorId(id);

    }

    /*
     * ============================================================
     * LISTAR TODAS
     * ============================================================
     */

    /**
     * Obtiene todas las presentaciones.
     *
     * @return Lista de presentaciones.
     */
    public List<Presentacion> listarTodas() {

        return presentacionService.listarTodas();

    }

    /*
     * ============================================================
     * LISTAR POR PRODUCTO
     * ============================================================
     */

    /**
     * Obtiene todas las presentaciones
     * pertenecientes a un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Lista de presentaciones.
     */
    public List<Presentacion> listarPorProducto(
            Long productoId) {

        return presentacionService.listarPorProducto(
                productoId
        );

    }

    /*
     * ============================================================
     * LISTAR ACTIVAS PARA VENTA
     * ============================================================
     */

    /**
     * Obtiene las presentaciones que están activas
     * y permitidas para venta.
     *
     * @param productoId ID del producto.
     *
     * @return Lista de presentaciones disponibles.
     */
    public List<Presentacion> listarActivasPorProducto(
            Long productoId) {

        return presentacionService
                .listarActivasPorProducto(
                        productoId
                );

    }

    /*
     * ============================================================
     * BUSCAR PREDETERMINADA
     * ============================================================
     */

    /**
     * Obtiene la presentación predeterminada
     * de un producto.
     *
     * @param productoId ID del producto.
     *
     * @return Presentación predeterminada o null.
     */
    public Presentacion buscarPredeterminada(
            Long productoId) {

        return presentacionService
                .buscarPredeterminada(
                        productoId
                );

    }

    /*
     * ============================================================
     * EXISTE NOMBRE
     * ============================================================
     */

    /**
     * Verifica si ya existe una presentación
     * con determinado nombre para un producto.
     *
     * @param productoId ID del producto.
     * @param nombre Nombre de la presentación.
     *
     * @return true si existe.
     */
    public boolean existeNombre(
            Long productoId,
            String nombre) {

        return presentacionService.existeNombre(
                productoId,
                nombre
        );

    }

}

