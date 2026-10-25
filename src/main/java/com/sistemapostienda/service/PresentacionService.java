/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.service;

import com.sistemapostienda.dao.PresentacionDAO;
import com.sistemapostienda.model.Presentacion;

import java.math.BigDecimal;
import java.util.List;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Service encargado de la lógica de negocio
 * de las presentaciones de productos.
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
public class PresentacionService {

    /*
     * ============================================================
     * DAO
     * ============================================================
     */

    private final PresentacionDAO presentacionDAO;

    /**
     * Constructor.
     */
    public PresentacionService() {

        this.presentacionDAO =
                new PresentacionDAO();

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

        /*
         * El objeto no puede ser null.
         */
        if (presentacion == null) {

            return false;

        }

        /*
         * El producto es obligatorio.
         */
        if (presentacion.getProductoId() == null
                || presentacion.getProductoId() <= 0) {

            return false;

        }

        /*
         * El nombre es obligatorio.
         */
        if (presentacion.getNombre() == null
                || presentacion.getNombre()
                        .trim()
                        .isEmpty()) {

            return false;

        }

        /*
         * El factor de conversión
         * debe ser mayor que cero.
         */
        if (!validarFactorConversion(
                presentacion)) {

            return false;

        }

        /*
         * El orden debe ser válido.
         */
        if (!validarOrden(presentacion)) {

            return false;

        }

        /*
         * Verificamos que no exista
         * otra presentación con el mismo
         * nombre para el mismo producto.
         */
        if (presentacionDAO.existeNombre(
                presentacion.getProductoId(),
                presentacion.getNombre())) {

            return false;

        }

        /*
         * Valores predeterminados de seguridad.
         */
        if (presentacion.getEsPredeterminada()
                == null) {

            presentacion.setEsPredeterminada(
                    false
            );

        }

        if (presentacion.getPermiteVenta()
                == null) {

            presentacion.setPermiteVenta(
                    true
            );

        }

        if (presentacion.getActiva()
                == null) {

            presentacion.setActiva(
                    true
            );

        }

        if (presentacion.getOrden()
                == null) {

            presentacion.setOrden(1);

        }

        /*
         * Si la presentación se marca como
         * predeterminada, primero quitamos
         * la marca de las demás.
         */
        if (Boolean.TRUE.equals(
                presentacion.getEsPredeterminada())) {

            presentacionDAO.quitarPredeterminada(
                    presentacion.getProductoId()
            );

        }

        return presentacionDAO.guardar(
                presentacion
        );

    }

    /*
     * ============================================================
     * ACTUALIZAR
     * ============================================================
     */

    /**
     * Actualiza una presentación existente.
     *
     * @param presentacion Presentación actualizada.
     *
     * @return true si fue actualizada correctamente.
     */
    public boolean actualizar(
            Presentacion presentacion) {

        if (presentacion == null) {

            return false;

        }

        /*
         * La presentación debe tener ID.
         */
        if (presentacion.getId() == null
                || presentacion.getId() <= 0) {

            return false;

        }

        /*
         * Producto obligatorio.
         */
        if (presentacion.getProductoId() == null
                || presentacion.getProductoId() <= 0) {

            return false;

        }

        /*
         * Nombre obligatorio.
         */
        if (presentacion.getNombre() == null
                || presentacion.getNombre()
                        .trim()
                        .isEmpty()) {

            return false;

        }

        /*
         * Factor válido.
         */
        if (!validarFactorConversion(
                presentacion)) {

            return false;

        }

        /*
         * Orden válido.
         */
        if (!validarOrden(presentacion)) {

            return false;

        }

        /*
         * Verificamos que el nombre no pertenezca
         * a otra presentación del mismo producto.
         */
        if (presentacionDAO.existeNombreExceptoId(
                presentacion.getProductoId(),
                presentacion.getNombre(),
                presentacion.getId())) {

            return false;

        }

        /*
         * Valores predeterminados.
         */
        if (presentacion.getEsPredeterminada()
                == null) {

            presentacion.setEsPredeterminada(
                    false
            );

        }

        if (presentacion.getPermiteVenta()
                == null) {

            presentacion.setPermiteVenta(
                    true
            );

        }

        if (presentacion.getActiva()
                == null) {

            presentacion.setActiva(
                    true
            );

        }

        if (presentacion.getOrden()
                == null) {

            presentacion.setOrden(1);

        }

        /*
         * Si se convierte en predeterminada,
         * quitamos la marca de las demás.
         */
        if (Boolean.TRUE.equals(
                presentacion.getEsPredeterminada())) {

            presentacionDAO.quitarPredeterminada(
                    presentacion.getProductoId()
            );

        }

        return presentacionDAO.actualizar(
                presentacion
        );

    }

    /*
     * ============================================================
     * VALIDAR FACTOR DE CONVERSIÓN
     * ============================================================
     */

    /**
     * El factor debe ser mayor que cero.
     *
     * Ejemplos válidos:
     *
     * 0.500
     * 1.000
     * 2.000
     * 24.000
     *
     * @param presentacion Presentación.
     *
     * @return true si es válido.
     */
    private boolean validarFactorConversion(
            Presentacion presentacion) {

        if (presentacion.getFactorConversion()
                == null) {

            return false;

        }

        return presentacion.getFactorConversion()
                .compareTo(BigDecimal.ZERO) > 0;

    }

    /*
     * ============================================================
     * VALIDAR ORDEN
     * ============================================================
     */

    /**
     * Valida el orden de la presentación.
     *
     * @param presentacion Presentación.
     *
     * @return true si el orden es válido.
     */
    private boolean validarOrden(
            Presentacion presentacion) {

        if (presentacion.getOrden() == null) {

            presentacion.setOrden(1);

            return true;

        }

        return presentacion.getOrden() > 0;

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
     * @return true si fue eliminada.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {

            return false;

        }

        return presentacionDAO.eliminar(id);

    }

    /*
     * ============================================================
     * BUSCAR POR ID
     * ============================================================
     */

    public Presentacion buscarPorId(Long id) {

        if (id == null || id <= 0) {

            return null;

        }

        return presentacionDAO.buscarPorId(id);

    }

    /*
     * ============================================================
     * LISTAR TODAS
     * ============================================================
     */

    public List<Presentacion> listarTodas() {

        return presentacionDAO.listarTodas();

    }

    /*
     * ============================================================
     * LISTAR POR PRODUCTO
     * ============================================================
     */

    public List<Presentacion> listarPorProducto(
            Long productoId) {

        if (productoId == null
                || productoId <= 0) {

            return List.of();

        }

        return presentacionDAO.listarPorProducto(
                productoId
        );

    }

    /*
     * ============================================================
     * LISTAR ACTIVAS PARA VENTA
     * ============================================================
     */

    public List<Presentacion> listarActivasPorProducto(
            Long productoId) {

        if (productoId == null
                || productoId <= 0) {

            return List.of();

        }

        return presentacionDAO
                .listarActivasPorProducto(
                        productoId
                );

    }

    /*
     * ============================================================
     * BUSCAR PREDETERMINADA
     * ============================================================
     */

    public Presentacion buscarPredeterminada(
            Long productoId) {

        if (productoId == null
                || productoId <= 0) {

            return null;

        }

        return presentacionDAO
                .buscarPredeterminada(
                        productoId
                );

    }

    /*
     * ============================================================
     * EXISTE NOMBRE
     * ============================================================
     */

    public boolean existeNombre(
            Long productoId,
            String nombre) {

        if (productoId == null
                || productoId <= 0) {

            return false;

        }

        if (nombre == null
                || nombre.trim().isEmpty()) {

            return false;

        }

        return presentacionDAO.existeNombre(
                productoId,
                nombre
        );

    }

}

