/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * ============================================================
 *  @author monky
 *
 * Modelo que representa un usuario del sistema.
 *
 * Un usuario pertenece a un rol y a una sucursal.
 *
 * ============================================================
 */
public class Usuario {

    private Long id;

    private Long rolId;

    private Long sucursalId;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String usuario;

    private String password;

    private String correo;

    private String telefono;

    private LocalDateTime ultimoAcceso;

    private boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /*
     * Relaciones
     */
    private Rol rol;

    private Sucursal sucursal;

    /**
     * Constructor vacío.
     */
    public Usuario() {

    }

    /**
     * Constructor completo.
     */
    public Usuario(
            Long id,
            Long rolId,
            Long sucursalId,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String usuario,
            String password,
            String correo,
            String telefono,
            LocalDateTime ultimoAcceso,
            boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.rolId = rolId;
        this.sucursalId = sucursalId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;
        this.telefono = telefono;
        this.ultimoAcceso = ultimoAcceso;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    //==========================================================
    // GETTERS Y SETTERS
    //==========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    //==========================================================
    // MÉTODOS
    //==========================================================

    /**
     * Devuelve el nombre completo del usuario.
     */
    public String getNombreCompleto() {

        StringBuilder sb = new StringBuilder();

        if (nombre != null) {
            sb.append(nombre.trim());
        }

        if (apellidoPaterno != null && !apellidoPaterno.isBlank()) {
            sb.append(" ").append(apellidoPaterno.trim());
        }

        if (apellidoMaterno != null && !apellidoMaterno.isBlank()) {
            sb.append(" ").append(apellidoMaterno.trim());
        }

        return sb.toString().trim();

    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }

}