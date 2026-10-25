/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.ListaPrecioDAO;
import com.sistemapostienda.dao.PrecioDAO;
import com.sistemapostienda.dao.PresentacionDAO;
import com.sistemapostienda.model.ListaPrecio;
import com.sistemapostienda.model.Precio;
import com.sistemapostienda.model.Presentacion;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio de precios.
 *
 * Contiene las reglas de negocio relacionadas
 * con los precios de las presentaciones.
 *
 * Arquitectura:
 *
 * Controller
 *     ↓
 * Service
 *     ↓
 * DAO
 *     ↓
 * Database
 */
public class PrecioService {

    private final PrecioDAO precioDAO;
    private final PresentacionDAO presentacionDAO;
    private final ListaPrecioDAO listaPrecioDAO;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PrecioService() {
        this.precioDAO = new PrecioDAO();
        this.presentacionDAO = new PresentacionDAO();
        this.listaPrecioDAO = new ListaPrecioDAO();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    /**
     * Registra un nuevo precio.
     *
     * Antes de guardar se verifica:
     *
     * - Presentación válida.
     * - Lista de precios válida.
     * - Precio válido.
     * - Presentación existente.
     * - Lista de precios existente.
     * - No exista ya la combinación
     *   presentación + lista de precios.
     */
    public boolean guardar(Precio precio) {

        validarPrecioBasico(precio);

        // Verificar presentación
        Presentacion presentacion =
                presentacionDAO.buscarPorId(
                        precio.getPresentacionId()
                );

        if (presentacion == null) {

            throw new IllegalArgumentException(
                    "La presentación indicada no existe."
            );
        }

        // Verificar que la presentación esté activa
        if (!Boolean.TRUE.equals(
                presentacion.getActiva())) {

            throw new IllegalArgumentException(
                    "No se puede asignar un precio "
                    + "a una presentación inactiva."
            );
        }

        // Verificar lista de precios
        ListaPrecio listaPrecio =
                listaPrecioDAO.buscarPorId(
                        precio.getListaPrecioId()
                );

        if (listaPrecio == null) {

            throw new IllegalArgumentException(
                    "La lista de precios indicada no existe."
            );
        }

        // Verificar que la lista esté activa
        if (!Boolean.TRUE.equals(
                listaPrecio.getActiva())) {

            throw new IllegalArgumentException(
                    "No se puede asignar un precio "
                    + "a una lista de precios inactiva."
            );
        }

        // Verificar duplicado
        Precio existente =
                precioDAO.buscarPorPresentacionYLista(
                        precio.getPresentacionId(),
                        precio.getListaPrecioId()
                );

        if (existente != null) {

            throw new IllegalArgumentException(
                    "Ya existe un precio para esta "
                    + "presentación en la lista de precios indicada."
            );
        }

        // Valor predeterminado
        if (precio.getActivo() == null) {
            precio.setActivo(true);
        }

        return precioDAO.guardar(precio);
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Precio buscarPorId(Long id) {

        validarId(id);

        return precioDAO.buscarPorId(id);
    }

    // =========================================================
    // BUSCAR POR PRESENTACIÓN Y LISTA
    // =========================================================

    public Precio buscarPorPresentacionYLista(
            Long presentacionId,
            Long listaPrecioId) {

        validarPresentacionId(presentacionId);
        validarListaPrecioId(listaPrecioId);

        return precioDAO.buscarPorPresentacionYLista(
                presentacionId,
                listaPrecioId
        );
    }

    // =========================================================
    // LISTAR POR PRESENTACIÓN
    // =========================================================

    /**
     * Obtiene todos los precios configurados
     * para una presentación.
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

        validarPresentacionId(presentacionId);

        return precioDAO.listarPorPresentacion(
                presentacionId
        );
    }

    // =========================================================
    // LISTAR POR LISTA DE PRECIOS
    // =========================================================

    /**
     * Obtiene todos los precios pertenecientes
     * a una lista determinada.
     */
    public List<Precio> listarPorListaPrecio(
            Long listaPrecioId) {

        validarListaPrecioId(listaPrecioId);

        return precioDAO.listarPorListaPrecio(
                listaPrecioId
        );
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Precio> listarActivos() {

        return precioDAO.listarActivos();
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Precio> listarTodos() {

        return precioDAO.listarTodos();
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza un precio existente.
     *
     * También verifica que la nueva combinación
     * presentación + lista no esté siendo utilizada
     * por otro registro.
     */
    public boolean actualizar(Precio precio) {

        if (precio == null
                || precio.getId() == null
                || precio.getId() <= 0) {

            throw new IllegalArgumentException(
                    "El precio no es válido."
            );
        }

        validarPrecioBasico(precio);

        // Verificar que el precio actual exista
        Precio actual =
                precioDAO.buscarPorId(
                        precio.getId()
                );

        if (actual == null) {

            throw new IllegalArgumentException(
                    "El precio que intenta actualizar "
                    + "no existe."
            );
        }

        // Verificar presentación
        Presentacion presentacion =
                presentacionDAO.buscarPorId(
                        precio.getPresentacionId()
                );

        if (presentacion == null) {

            throw new IllegalArgumentException(
                    "La presentación indicada no existe."
            );
        }

        if (!Boolean.TRUE.equals(
                presentacion.getActiva())) {

            throw new IllegalArgumentException(
                    "No se puede utilizar una "
                    + "presentación inactiva."
            );
        }

        // Verificar lista
        ListaPrecio listaPrecio =
                listaPrecioDAO.buscarPorId(
                        precio.getListaPrecioId()
                );

        if (listaPrecio == null) {

            throw new IllegalArgumentException(
                    "La lista de precios indicada no existe."
            );
        }

        if (!Boolean.TRUE.equals(
                listaPrecio.getActiva())) {

            throw new IllegalArgumentException(
                    "No se puede utilizar una "
                    + "lista de precios inactiva."
            );
        }

        // Verificar duplicado
        Precio existente =
                precioDAO.buscarPorPresentacionYLista(
                        precio.getPresentacionId(),
                        precio.getListaPrecioId()
                );

        if (existente != null
                && !existente.getId()
                        .equals(precio.getId())) {

            throw new IllegalArgumentException(
                    "Ya existe otro precio para esta "
                    + "presentación y lista de precios."
            );
        }

        return precioDAO.actualizar(precio);
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        validarId(id);

        Precio precio =
                precioDAO.buscarPorId(id);

        if (precio == null) {

            throw new IllegalArgumentException(
                    "El precio no existe."
            );
        }

        return precioDAO.eliminar(id);
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    public boolean activar(Long id) {

        validarId(id);

        Precio precio =
                precioDAO.buscarPorId(id);

        if (precio == null) {

            throw new IllegalArgumentException(
                    "El precio no existe."
            );
        }

        // Verificar presentación
        Presentacion presentacion =
                presentacionDAO.buscarPorId(
                        precio.getPresentacionId()
                );

        if (presentacion == null) {

            throw new IllegalArgumentException(
                    "La presentación asociada "
                    + "no existe."
            );
        }

        if (!Boolean.TRUE.equals(
                presentacion.getActiva())) {

            throw new IllegalStateException(
                    "No se puede activar el precio "
                    + "porque la presentación está inactiva."
            );
        }

        // Verificar lista
        ListaPrecio listaPrecio =
                listaPrecioDAO.buscarPorId(
                        precio.getListaPrecioId()
                );

        if (listaPrecio == null) {

            throw new IllegalArgumentException(
                    "La lista de precios asociada "
                    + "no existe."
            );
        }

        if (!Boolean.TRUE.equals(
                listaPrecio.getActiva())) {

            throw new IllegalStateException(
                    "No se puede activar el precio "
                    + "porque la lista de precios está inactiva."
            );
        }

        return precioDAO.cambiarEstado(
                id,
                true
        );
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    public boolean desactivar(Long id) {

        validarId(id);

        Precio precio =
                precioDAO.buscarPorId(id);

        if (precio == null) {

            throw new IllegalArgumentException(
                    "El precio no existe."
            );
        }

        return precioDAO.cambiarEstado(
                id,
                false
        );
    }

    // =========================================================
    // VALIDACIÓN BÁSICA
    // =========================================================

    private void validarPrecioBasico(
            Precio precio) {

        if (precio == null) {

            throw new IllegalArgumentException(
                    "El precio no puede ser nulo."
            );
        }

        validarPresentacionId(
                precio.getPresentacionId()
        );

        validarListaPrecioId(
                precio.getListaPrecioId()
        );

        if (precio.getPrecio() == null) {

            throw new IllegalArgumentException(
                    "El precio es obligatorio."
            );
        }

        /*
         * Un precio negativo no tiene sentido
         * dentro de nuestro POS.
         */
        if (precio.getPrecio()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "El precio no puede ser negativo."
            );
        }

        /*
         * Limitamos a dos decimales porque nuestra
         * columna es DECIMAL(12,2).
         */
        if (precio.getPrecio().scale() > 2) {

            throw new IllegalArgumentException(
                    "El precio no puede tener más "
                    + "de dos decimales."
            );
        }
    }

    // =========================================================
    // VALIDAR PRESENTACIÓN
    // =========================================================

    private void validarPresentacionId(
            Long presentacionId) {

        if (presentacionId == null
                || presentacionId <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la presentación no es válido."
            );
        }
    }

    // =========================================================
    // VALIDAR LISTA DE PRECIOS
    // =========================================================

    private void validarListaPrecioId(
            Long listaPrecioId) {

        if (listaPrecioId == null
                || listaPrecioId <= 0) {

            throw new IllegalArgumentException(
                    "El ID de la lista de precios no es válido."
            );
        }
    }

    // =========================================================
    // VALIDAR ID
    // =========================================================

    private void validarId(Long id) {

        if (id == null || id <= 0) {

            throw new IllegalArgumentException(
                    "El ID no es válido."
            );
        }
    }
}