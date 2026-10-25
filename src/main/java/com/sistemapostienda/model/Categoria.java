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
 * Modelo que representa una categoría de productos.
 *
 * Una categoría permite organizar los productos
 * del inventario.
 *
 * Ejemplo:
 *
 * Abarrotes
 * Bebidas
 * Lácteos
 * Limpieza
 * Papelería
 *
 * ============================================================
 */
public class Categoria {

    /*
     * ============================================================
     * ATRIBUTOS
     * ============================================================
     */

    /**
     * Identificador único.
     */
    private Long id;

    /**
     * Nombre de la categoría.
     */
    private String nombre;

    /**
     * Código interno.
     *
     * Ejemplo:
     * ABR
     * BEB
     * LIM
     */
    private String codigo;

    /**
     * Descripción.
     */
    private String descripcion;

    /**
     * Color para mostrar
     * en la interfaz.
     *
     * Ejemplo:
     * #3498DB
     */
    private String color;

    /**
     * Nombre del icono.
     *
     * Ejemplo:
     * shopping-cart
     */
    private String icono;

    /**
     * Orden de visualización.
     */
    private Integer orden;

    /**
     * Estado.
     */
    private Boolean activo;

    /**
     * Fecha de creación.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de actualización.
     */
    private LocalDateTime updatedAt;

    /*
     * ============================================================
     * CONSTRUCTORES
     * ============================================================
     */

    public Categoria() {

    }

    public Categoria(Long id,
                     String nombre,
                     String codigo,
                     String descripcion,
                     String color,
                     String icono,
                     Integer orden,
                     Boolean activo,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt) {

        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.color = color;
        this.icono = icono;
        this.orden = orden;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    /*
     * ============================================================
     * GETTERS & SETTERS
     * ============================================================
     */

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
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

    /*
     * ============================================================
     * MÉTODOS
     * ============================================================
     */

    @Override
    public String toString() {

        return nombre;

    }

}
