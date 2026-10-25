/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.controller;




import com.sistemapostienda.model.Usuario;
import com.sistemapostienda.service.UsuarioService;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Controller encargado de administrar
 * las operaciones relacionadas con
 * los usuarios del sistema.
 *
 * Controller
 *      │
 *      ▼
 * UsuarioService
 *      │
 *      ▼
 * UsuarioDAO
 *
 * ============================================================
 */
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Constructor.
     */
    public UsuarioController() {

        this.usuarioService = new UsuarioService();

    }

    /**
     * Registra un nuevo usuario.
     *
     * @param usuario Usuario a registrar.
     *
     * @return true si fue registrado.
     */
    public boolean crear(Usuario usuario) {

        return usuarioService.guardar(usuario);

    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario Usuario con los nuevos datos.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Usuario usuario) {

        return usuarioService.actualizar(usuario);

    }

    /**
     * Elimina un usuario.
     *
     * @param id ID del usuario.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {

        return usuarioService.eliminar(id);

    }

    /**
     * Busca un usuario por ID.
     *
     * @param id Identificador.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorId(Long id) {

        return usuarioService.buscarPorId(id);

    }

    /**
     * Busca un usuario por nombre de usuario.
     *
     * @param usuario Nombre de usuario.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorUsuario(String usuario) {

        return usuarioService.buscarPorUsuario(usuario);

    }

    /**
     * Busca un usuario mediante su correo.
     *
     * @param correo Correo electrónico.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorCorreo(String correo) {

        return usuarioService.buscarPorCorreo(correo);

    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> listarTodos() {

        return usuarioService.listarTodos();

    }

    /**
     * Obtiene únicamente los usuarios activos.
     *
     * @return Lista de usuarios activos.
     */
    public List<Usuario> listarActivos() {

        return usuarioService.listarActivos();

    }

    /**
     * Verifica si un nombre de usuario
     * ya existe en el sistema.
     *
     * @param usuario Nombre de usuario.
     *
     * @return true si existe.
     */
    public boolean existeUsuario(String usuario) {

        return usuarioService.existeUsuario(usuario);

    }

    /**
     * Verifica si un correo electrónico
     * ya se encuentra registrado.
     *
     * @param correo Correo electrónico.
     *
     * @return true si existe.
     */
    public boolean existeCorreo(String correo) {

        return usuarioService.existeCorreo(correo);

    }

}