/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.ListaPrecio;
import com.sistemapostienda.service.ListaPrecioService;

import java.util.List;

/**
 * Controller de listas de precios.
 *
 * Se encarga de coordinar las operaciones relacionadas
 * con las listas de precios.
 *
 * No contiene código SQL.
 * No accede directamente a Database.
 *
 * La comunicación es:
 *
 * Controller -> Service -> DAO -> Database
 */
public class ListaPrecioController {

    private final ListaPrecioService listaPrecioService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ListaPrecioController() {
        this.listaPrecioService = new ListaPrecioService();
    }

    // =========================================================
    // CREAR
    // =========================================================

    /**
     * Crea una nueva lista de precios.
     *
     * @param lista lista que se desea registrar
     * @return true si se registró correctamente
     */
    public boolean crear(ListaPrecio lista) {

        try {

            return listaPrecioService.guardar(lista);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al crear lista de precios: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al crear lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    /**
     * Busca una lista de precios por su ID.
     *
     * @param id identificador de la lista
     * @return lista encontrada o null
     */
    public ListaPrecio buscarPorId(Long id) {

        try {

            return listaPrecioService.buscarPorId(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return null;

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar lista por ID: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    /**
     * Busca una lista mediante su código.
     *
     * Ejemplo:
     * PUBLICO
     * MAYOREO
     * DISTRIBUIDOR
     */
    public ListaPrecio buscarPorCodigo(String codigo) {

        try {

            return listaPrecioService.buscarPorCodigo(codigo);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return null;

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar lista por código: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    /**
     * Busca una lista mediante su nombre.
     */
    public ListaPrecio buscarPorNombre(String nombre) {

        try {

            return listaPrecioService.buscarPorNombre(nombre);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return null;

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar lista por nombre: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // LISTAR TODAS
    // =========================================================

    /**
     * Obtiene todas las listas de precios.
     */
    public List<ListaPrecio> listarTodas() {

        try {

            return listaPrecioService.listarTodas();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar listas de precios: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR ACTIVAS
    // =========================================================

    /**
     * Obtiene únicamente las listas activas.
     */
    public List<ListaPrecio> listarActivas() {

        try {

            return listaPrecioService.listarActivas();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar listas activas: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR GLOBALES
    // =========================================================

    /**
     * Obtiene las listas que tienen alcance GLOBAL.
     *
     * Estas listas pueden utilizarse en todas
     * las sucursales.
     */
    public List<ListaPrecio> listarGlobales() {

        try {

            return listaPrecioService.listarGlobales();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar listas globales: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR POR SUCURSAL
    // =========================================================

    /**
     * Obtiene las listas pertenecientes a una sucursal.
     *
     * @param sucursalId ID de la sucursal
     */
    public List<ListaPrecio> listarPorSucursal(
            Long sucursalId) {

        try {

            return listaPrecioService.listarPorSucursal(
                    sucursalId
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return List.of();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar listas por sucursal: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza una lista existente.
     */
    public boolean actualizar(ListaPrecio lista) {

        try {

            return listaPrecioService.actualizar(lista);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al actualizar: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al actualizar lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina una lista de precios.
     */
    public boolean eliminar(Long id) {

        try {

            return listaPrecioService.eliminar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al eliminar: "
                    + e.getMessage()
            );

            return false;

        } catch (IllegalStateException e) {

            System.err.println(
                    "No se puede eliminar la lista: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al eliminar lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    /**
     * Activa una lista de precios.
     */
    public boolean activar(Long id) {

        try {

            return listaPrecioService.activar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al activar lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    /**
     * Desactiva una lista de precios.
     */
    public boolean desactivar(Long id) {

        try {

            return listaPrecioService.desactivar(id);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación: "
                    + e.getMessage()
            );

            return false;

        } catch (IllegalStateException e) {

            System.err.println(
                    "No se puede desactivar la lista: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al desactivar lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }
}