/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;

import com.sistemapostienda.model.Proveedor;
import com.sistemapostienda.service.ProveedorService;

import java.util.List;

/**
 * Controller para la gestión de proveedores.
 *
 * Se encarga de coordinar las operaciones relacionadas
 * con proveedores.
 *
 * Las reglas de negocio están en ProveedorService.
 * El acceso a la base de datos está en ProveedorDAO.
 */
public class ProveedorController {

    private final ProveedorService proveedorService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ProveedorController() {

        this.proveedorService =
                new ProveedorService();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    /**
     * Registra un nuevo proveedor.
     *
     * @param proveedor proveedor que se desea registrar
     * @return true si se registró correctamente
     */
    public boolean guardar(Proveedor proveedor) {

        return proveedorService.guardar(proveedor);
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    /**
     * Busca un proveedor por su ID.
     *
     * @param id ID del proveedor
     * @return proveedor encontrado o null
     */
    public Proveedor buscarPorId(Long id) {

        return proveedorService.buscarPorId(id);
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    /**
     * Busca un proveedor por código.
     *
     * @param codigo código del proveedor
     * @return proveedor encontrado o null
     */
    public Proveedor buscarPorCodigo(
            String codigo) {

        return proveedorService.buscarPorCodigo(
                codigo
        );
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    /**
     * Busca un proveedor por RFC.
     *
     * @param rfc RFC del proveedor
     * @return proveedor encontrado o null
     */
    public Proveedor buscarPorRfc(
            String rfc) {

        return proveedorService.buscarPorRfc(
                rfc
        );
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    /**
     * Obtiene todos los proveedores.
     *
     * @return lista de proveedores
     */
    public List<Proveedor> listarTodos() {

        return proveedorService.listarTodos();
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    /**
     * Obtiene únicamente proveedores activos.
     *
     * @return lista de proveedores activos
     */
    public List<Proveedor> listarActivos() {

        return proveedorService.listarActivos();
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    /**
     * Busca proveedores por nombre.
     *
     * @param nombre texto de búsqueda
     * @return lista de proveedores encontrados
     */
    public List<Proveedor> buscarPorNombre(
            String nombre) {

        return proveedorService.buscarPorNombre(
                nombre
        );
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza los datos de un proveedor.
     *
     * @param proveedor proveedor con los datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(
            Proveedor proveedor) {

        return proveedorService.actualizar(
                proveedor
        );
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina un proveedor.
     *
     * @param id ID del proveedor
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(Long id) {

        return proveedorService.eliminar(id);
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    /**
     * Activa un proveedor.
     *
     * @param id ID del proveedor
     * @return true si se activó correctamente
     */
    public boolean activar(Long id) {

        return proveedorService.activar(id);
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    /**
     * Desactiva un proveedor.
     *
     * @param id ID del proveedor
     * @return true si se desactivó correctamente
     */
    public boolean desactivar(Long id) {

        return proveedorService.desactivar(id);
    }
}