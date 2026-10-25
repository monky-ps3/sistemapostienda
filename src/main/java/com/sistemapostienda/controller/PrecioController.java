/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.Precio;
import com.sistemapostienda.service.PrecioService;

import java.util.List;

/**
 * Controller para la gestión de precios.
 *
 * Arquitectura:
 *
 * Controller
 *      ↓
 * PrecioService
 *      ↓
 * PrecioDAO
 *      ↓
 * Database
 */
public class PrecioController {

    private final PrecioService precioService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PrecioController() {
        this.precioService = new PrecioService();
    }

    // =========================================================
    // CREAR
    // =========================================================

    /**
     * Registra un nuevo precio.
     */
    public boolean crear(Precio precio) {

        try {

            return precioService.guardar(precio);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al crear precio: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al crear precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Precio buscarPorId(Long id) {

        try {

            return precioService.buscarPorId(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return null;

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar precio por ID: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // BUSCAR POR PRESENTACIÓN Y LISTA
    // =========================================================

    /**
     * Busca el precio correspondiente a una combinación:
     *
     * presentación + lista de precios.
     *
     * Ejemplo:
     *
     * Presentación: Caja 24 piezas
     * Lista: Público General
     */
    public Precio buscarPorPresentacionYLista(
            Long presentacionId,
            Long listaPrecioId) {

        try {

            return precioService.buscarPorPresentacionYLista(
                    presentacionId,
                    listaPrecioId
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return null;

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar precio: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // LISTAR POR PRESENTACIÓN
    // =========================================================

    /**
     * Obtiene todos los precios de una presentación.
     *
     * Ejemplo:
     *
     * Coca-Cola pieza:
     *
     * Público       $18
     * Mayoreo       $17
     * Distribuidor  $16
     */
    public List<Precio> listarPorPresentacion(
            Long presentacionId) {

        try {

            return precioService.listarPorPresentacion(
                    presentacionId
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return List.of();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar precios de la presentación: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR POR LISTA DE PRECIOS
    // =========================================================

    /**
     * Obtiene todos los precios de una lista.
     */
    public List<Precio> listarPorListaPrecio(
            Long listaPrecioId) {

        try {

            return precioService.listarPorListaPrecio(
                    listaPrecioId
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return List.of();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar precios por lista: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Precio> listarActivos() {

        try {

            return precioService.listarActivos();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar precios activos: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Precio> listarTodos() {

        try {

            return precioService.listarTodos();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar precios: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza un precio existente.
     */
    public boolean actualizar(Precio precio) {

        try {

            return precioService.actualizar(precio);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al actualizar precio: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al actualizar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        try {

            return precioService.eliminar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al eliminar precio: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al eliminar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    public boolean activar(Long id) {

        try {

            return precioService.activar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al activar precio: "
                    + e.getMessage()
            );

            return false;

        } catch (IllegalStateException e) {

            System.err.println(
                    "No se puede activar el precio: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al activar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    public boolean desactivar(Long id) {

        try {

            return precioService.desactivar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al desactivar precio: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al desactivar precio: "
                    + e.getMessage()
            );

            return false;
        }
    }
}