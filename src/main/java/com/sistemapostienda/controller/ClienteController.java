/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.Cliente;
import com.sistemapostienda.service.ClienteService;

import java.util.List;

/**
 * Controller para la gestión de clientes.
 *
 * El Controller comunica la interfaz con ClienteService.
 *
 * No contiene consultas SQL.
 * No accede directamente a Database.
 */
public class ClienteController {

    private final ClienteService clienteService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ClienteController() {

        this.clienteService = new ClienteService();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    /**
     * Registra un nuevo cliente.
     *
     * @param cliente cliente a registrar
     * @return true si fue guardado correctamente
     */
    public boolean guardar(Cliente cliente) {

        try {

            return clienteService.guardar(cliente);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al guardar cliente: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al guardar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    /**
     * Busca un cliente por su ID.
     */
    public Cliente buscarPorId(Long id) {

        try {

            return clienteService.buscarPorId(id);

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar cliente: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    /**
     * Busca un cliente mediante su código.
     */
    public Cliente buscarPorCodigo(
            String codigo) {

        try {

            return clienteService.buscarPorCodigo(
                    codigo
            );

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar cliente por código: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    /**
     * Busca un cliente mediante RFC.
     */
    public Cliente buscarPorRfc(
            String rfc) {

        try {

            return clienteService.buscarPorRfc(
                    rfc
            );

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar cliente por RFC: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // PÚBLICO EN GENERAL
    // =========================================================

    /**
     * Obtiene el cliente especial
     * PÚBLICO EN GENERAL.
     */
    public Cliente obtenerPublicoGeneral() {

        try {

            return clienteService.buscarPublicoGeneral();

        } catch (Exception e) {

            System.err.println(
                    "Error al obtener PÚBLICO EN GENERAL: "
                    + e.getMessage()
            );

            return null;
        }
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    /**
     * Obtiene todos los clientes.
     */
    public List<Cliente> listarTodos() {

        try {

            return clienteService.listarTodos();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar clientes: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    /**
     * Obtiene solamente clientes activos.
     */
    public List<Cliente> listarActivos() {

        try {

            return clienteService.listarActivos();

        } catch (Exception e) {

            System.err.println(
                    "Error al listar clientes activos: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    /**
     * Busca clientes por nombre.
     *
     * Permite búsquedas parciales.
     *
     * Ejemplo:
     *
     * "Juan"
     *
     * puede encontrar:
     *
     * Juan Pérez
     * Juan Carlos
     * Abarrotes Juan
     */
    public List<Cliente> buscarPorNombre(
            String nombre) {

        try {

            return clienteService.buscarPorNombre(
                    nombre
            );

        } catch (Exception e) {

            System.err.println(
                    "Error al buscar clientes por nombre: "
                    + e.getMessage()
            );

            return List.of();
        }
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza los datos de un cliente.
     */
    public boolean actualizar(
            Cliente cliente) {

        try {

            return clienteService.actualizar(
                    cliente
            );

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error de validación al actualizar "
                    + "cliente: "
                    + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.err.println(
                    "Error al actualizar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina un cliente.
     *
     * El Service se encarga de impedir que
     * PÚBLICO EN GENERAL sea eliminado.
     */
    public boolean eliminar(Long id) {

        try {

            return clienteService.eliminar(id);

        } catch (Exception e) {

            System.err.println(
                    "Error al eliminar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    /**
     * Activa un cliente.
     */
    public boolean activar(Long id) {

        try {

            return clienteService.activar(id);

        } catch (Exception e) {

            System.err.println(
                    "Error al activar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    /**
     * Desactiva un cliente.
     *
     * PÚBLICO EN GENERAL no puede desactivarse.
     */
    public boolean desactivar(Long id) {

        try {

            return clienteService.desactivar(id);

        } catch (Exception e) {

            System.err.println(
                    "Error al desactivar cliente: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // CAMBIAR LISTA DE PRECIOS
    // =========================================================

    /**
     * Cambia la lista de precios de un cliente.
     *
     * Ejemplo:
     *
     * Cliente Juan
     *
     * antes:
     * PUBLICO
     *
     * después:
     * MAYOREO
     */
    public boolean cambiarListaPrecio(
            Long clienteId,
            Long listaPrecioId) {

        try {

            return clienteService.cambiarListaPrecio(
                    clienteId,
                    listaPrecioId
            );

        } catch (Exception e) {

            System.err.println(
                    "Error al cambiar lista de precios: "
                    + e.getMessage()
            );

            return false;
        }
    }
}
