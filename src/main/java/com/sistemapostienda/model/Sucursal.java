/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemapostienda.model;

import java.time.LocalDateTime;

/**
 * ============================================================
 * @author monky
 *
 * Modelo que representa una sucursal de la empresa.
 *
 * Una sucursal es el lugar físico donde se realizan
 * operaciones como ventas, compras, inventario y caja.
 *
 * ============================================================
 */
public class Sucursal {

    private Long id;

    private String nombre;

    private String codigo;

    private String direccion;

    private String telefono;

    private String correo;

    private boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Constructor vacío.
     */
    public Sucursal() {

    }

    /**
     * Constructor completo.
     */
    public Sucursal(
            Long id,
            String nombre,
            String codigo,
            String direccion,
            String telefono,
            String correo,
            boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    //==========================================================
    // MÉTODOS
    //==========================================================

    /**
     * Devuelve el nombre de la sucursal.
     * Se utiliza en JComboBox, JTable y registros.
     */
    @Override
    public String toString() {
        return nombre;
    }

}
