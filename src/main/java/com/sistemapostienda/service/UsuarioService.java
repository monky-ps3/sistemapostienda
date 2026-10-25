/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;



import com.sistemapostienda.dao.UsuarioDAO;
import com.sistemapostienda.model.Usuario;

import java.util.List;

/**
 * ============================================================
 * @author monky
 *
 * Servicio encargado de administrar
 * la lógica de negocio relacionada con
 * los usuarios del sistema.
 *
 * Se comunica con UsuarioDAO para realizar
 * las operaciones sobre la base de datos.
 *
 * ============================================================
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor.
     */
    public UsuarioService() {

        this.usuarioDAO = new UsuarioDAO();

    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario Usuario a registrar.
     *
     * @return true si fue registrado.
     */
    public boolean guardar(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre() == null
                || usuario.getNombre().trim().isEmpty()) {

            return false;
        }

        if (usuario.getUsuario() == null
                || usuario.getUsuario().trim().isEmpty()) {

            return false;
        }

        if (usuario.getPassword() == null
                || usuario.getPassword().trim().isEmpty()) {

            return false;
        }

        if (usuarioDAO.existeUsuario(
                usuario.getUsuario())) {

            return false;
        }

        if (usuario.getCorreo() != null
                && !usuario.getCorreo().trim().isEmpty()) {

            if (usuarioDAO.existeCorreo(
                    usuario.getCorreo())) {

                return false;

            }

        }

        return usuarioDAO.guardar(usuario);

    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario Usuario actualizado.
     *
     * @return true si fue actualizado.
     */
    public boolean actualizar(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getId() == null
                || usuario.getId() <= 0) {

            return false;
        }

        return usuarioDAO.actualizar(usuario);

    }

    /**
     * Elimina un usuario.
     *
     * @param id ID del usuario.
     *
     * @return true si fue eliminado.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {
            return false;
        }

        return usuarioDAO.eliminar(id);

    }

    /**
     * Busca un usuario por ID.
     *
     * @param id Identificador.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorId(Long id) {

        if (id == null || id <= 0) {
            return null;
        }

        return usuarioDAO.buscarPorId(id);

    }

    /**
     * Busca un usuario por nombre de usuario.
     *
     * @param usuario Nombre de usuario.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorUsuario(String usuario) {

        if (usuario == null
                || usuario.trim().isEmpty()) {

            return null;
        }

        return usuarioDAO.buscarPorUsuario(usuario);

    }

    /**
     * Busca un usuario por correo.
     *
     * @param correo Correo electrónico.
     *
     * @return Usuario encontrado.
     */
    public Usuario buscarPorCorreo(String correo) {

        if (correo == null
                || correo.trim().isEmpty()) {

            return null;
        }

        return usuarioDAO.buscarPorCorreo(correo);

    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> listarTodos() {

        return usuarioDAO.listarTodos();

    }

    /**
     * Obtiene únicamente los usuarios activos.
     *
     * @return Lista de usuarios activos.
     */
    public List<Usuario> listarActivos() {

        return usuarioDAO.listarActivos();

    }

    /**
     * Verifica si existe un nombre
     * de usuario registrado.
     *
     * @param usuario Nombre de usuario.
     *
     * @return true si existe.
     */
    public boolean existeUsuario(String usuario) {

        if (usuario == null
                || usuario.trim().isEmpty()) {

            return false;
        }

        return usuarioDAO.existeUsuario(usuario);

    }

    /**
     * Verifica si existe un correo registrado.
     *
     * @param correo Correo electrónico.
     *
     * @return true si existe.
     */
    public boolean existeCorreo(String correo) {

        if (correo == null
                || correo.trim().isEmpty()) {

            return false;
        }

        return usuarioDAO.existeCorreo(correo);

    }

}
