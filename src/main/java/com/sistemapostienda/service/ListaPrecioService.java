/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.ListaPrecioDAO;
import com.sistemapostienda.model.ListaPrecio;

import java.util.List;

/**
 * Servicio de listas de precios.
 *
 * Se encarga de las reglas de negocio relacionadas
 * con las listas de precios.
 */
public class ListaPrecioService {

    private final ListaPrecioDAO listaPrecioDAO;

    public ListaPrecioService() {
        this.listaPrecioDAO = new ListaPrecioDAO();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    public boolean guardar(ListaPrecio lista) {

        if (lista == null) {
            return false;
        }

        // Validar código
        if (lista.getCodigo() == null
                || lista.getCodigo().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El código de la lista de precios es obligatorio.");
        }

        // Validar nombre
        if (lista.getNombre() == null
                || lista.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre de la lista de precios es obligatorio.");
        }

        // Validar alcance
        validarAlcance(lista);

        // Validar que no exista el código
        ListaPrecio existente =
                listaPrecioDAO.buscarPorCodigo(
                        lista.getCodigo().trim());

        if (existente != null) {
            throw new IllegalArgumentException(
                    "Ya existe una lista de precios con el código: "
                    + lista.getCodigo());
        }

        // Validar que no exista el nombre
        existente =
                listaPrecioDAO.buscarPorNombre(
                        lista.getNombre().trim());

        if (existente != null) {
            throw new IllegalArgumentException(
                    "Ya existe una lista de precios con el nombre: "
                    + lista.getNombre());
        }

        // Valores predeterminados
        if (lista.getPrioridad() == null) {
            lista.setPrioridad(1);
        }

        if (lista.getEsPredeterminada() == null) {
            lista.setEsPredeterminada(false);
        }

        if (lista.getActiva() == null) {
            lista.setActiva(true);
        }

        lista.setCodigo(lista.getCodigo().trim());
        lista.setNombre(lista.getNombre().trim());

        return listaPrecioDAO.guardar(lista);
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public ListaPrecio buscarPorId(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID de la lista de precios no es válido.");
        }

        return listaPrecioDAO.buscarPorId(id);
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public ListaPrecio buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio.");
        }

        return listaPrecioDAO.buscarPorCodigo(
                codigo.trim());
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public ListaPrecio buscarPorNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        return listaPrecioDAO.buscarPorNombre(
                nombre.trim());
    }

    // =========================================================
    // LISTAR TODAS
    // =========================================================

    public List<ListaPrecio> listarTodas() {
        return listaPrecioDAO.listarTodas();
    }

    // =========================================================
    // LISTAR ACTIVAS
    // =========================================================

    public List<ListaPrecio> listarActivas() {
        return listaPrecioDAO.listarActivas();
    }

    // =========================================================
    // LISTAR GLOBALES
    // =========================================================

    public List<ListaPrecio> listarGlobales() {
        return listaPrecioDAO.listarGlobales();
    }

    // =========================================================
    // LISTAR POR SUCURSAL
    // =========================================================

    public List<ListaPrecio> listarPorSucursal(
            Long sucursalId) {

        if (sucursalId == null || sucursalId <= 0) {
            throw new IllegalArgumentException(
                    "El ID de la sucursal no es válido.");
        }

        return listaPrecioDAO.listarPorSucursal(
                sucursalId);
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(ListaPrecio lista) {

        if (lista == null
                || lista.getId() == null
                || lista.getId() <= 0) {

            throw new IllegalArgumentException(
                    "La lista de precios no es válida.");
        }

        if (lista.getCodigo() == null
                || lista.getCodigo().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El código es obligatorio.");
        }

        if (lista.getNombre() == null
                || lista.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        validarAlcance(lista);

        // Buscar código existente
        ListaPrecio existente =
                listaPrecioDAO.buscarPorCodigo(
                        lista.getCodigo().trim());

        if (existente != null
                && !existente.getId().equals(lista.getId())) {

            throw new IllegalArgumentException(
                    "Ya existe otra lista con el código: "
                    + lista.getCodigo());
        }

        // Buscar nombre existente
        existente =
                listaPrecioDAO.buscarPorNombre(
                        lista.getNombre().trim());

        if (existente != null
                && !existente.getId().equals(lista.getId())) {

            throw new IllegalArgumentException(
                    "Ya existe otra lista con el nombre: "
                    + lista.getNombre());
        }

        lista.setCodigo(lista.getCodigo().trim());
        lista.setNombre(lista.getNombre().trim());

        return listaPrecioDAO.actualizar(lista);
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID no es válido.");
        }

        ListaPrecio lista =
                listaPrecioDAO.buscarPorId(id);

        if (lista == null) {
            throw new IllegalArgumentException(
                    "La lista de precios no existe.");
        }

        /*
         * No permitimos eliminar una lista
         * que esté marcada como predeterminada.
         */
        if (Boolean.TRUE.equals(
                lista.getEsPredeterminada())) {

            throw new IllegalStateException(
                    "No se puede eliminar la lista "
                    + "predeterminada.");
        }

        return listaPrecioDAO.eliminar(id);
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    public boolean activar(Long id) {

        validarId(id);

        return listaPrecioDAO.cambiarEstado(
                id,
                true);
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    public boolean desactivar(Long id) {

        validarId(id);

        ListaPrecio lista =
                listaPrecioDAO.buscarPorId(id);

        if (lista == null) {
            throw new IllegalArgumentException(
                    "La lista de precios no existe.");
        }

        /*
         * No permitimos desactivar la lista
         * predeterminada.
         */
        if (Boolean.TRUE.equals(
                lista.getEsPredeterminada())) {

            throw new IllegalStateException(
                    "No se puede desactivar "
                    + "la lista predeterminada.");
        }

        return listaPrecioDAO.cambiarEstado(
                id,
                false);
    }

    // =========================================================
    // VALIDAR ALCANCE
    // =========================================================

    private void validarAlcance(ListaPrecio lista) {

        String alcance = lista.getAlcance();

        if (alcance == null
                || alcance.trim().isEmpty()) {

            lista.setAlcance("GLOBAL");
            alcance = "GLOBAL";
        }

        alcance = alcance.trim().toUpperCase();

        if (!alcance.equals("GLOBAL")
                && !alcance.equals("SUCURSAL")) {

            throw new IllegalArgumentException(
                    "El alcance debe ser GLOBAL o SUCURSAL.");
        }

        lista.setAlcance(alcance);

        /*
         * Una lista GLOBAL no pertenece
         * a ninguna sucursal.
         */
        if (alcance.equals("GLOBAL")
                && lista.getSucursalId() != null) {

            throw new IllegalArgumentException(
                    "Una lista GLOBAL no puede tener "
                    + "una sucursal asignada.");
        }

        /*
         * Una lista por sucursal obligatoriamente
         * debe tener una sucursal.
         */
        if (alcance.equals("SUCURSAL")
                && lista.getSucursalId() == null) {

            throw new IllegalArgumentException(
                    "Una lista por SUCURSAL debe tener "
                    + "una sucursal asignada.");
        }
    }

    // =========================================================
    // VALIDAR ID
    // =========================================================

    private void validarId(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "El ID no es válido.");
        }
    }
}