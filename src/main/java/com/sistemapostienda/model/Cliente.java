/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un cliente dentro del sistema.
 *
 * Un cliente puede tener una lista de precios asignada.
 *
 * Ejemplo:
 *
 * Cliente:
 * Juan Pérez
 *
 * Lista de precios:
 * MAYOREO
 *
 * También existe el cliente especial:
 *
 * PÚBLICO EN GENERAL
 */
public class Cliente {

    private Long id;

    /**
     * Lista de precios asignada al cliente.
     */
    private Long listaPrecioId;

    /**
     * Código interno del cliente.
     */
    private String codigo;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Razón social.
     */
    private String razonSocial;

    /**
     * RFC.
     */
    private String rfc;

    /**
     * Teléfono.
     */
    private String telefono;

    /**
     * Celular.
     */
    private String celular;

    /**
     * Correo electrónico.
     */
    private String email;

    /**
     * Dirección.
     */
    private String direccion;

    /**
     * Ciudad.
     */
    private String ciudad;

    /**
     * Estado.
     */
    private String estado;

    /**
     * Código postal.
     */
    private String codigoPostal;

    /**
     * Límite máximo de crédito permitido.
     */
    private BigDecimal limiteCredito;

    /**
     * Número de días de crédito.
     */
    private Integer diasCredito;

    /**
     * Descuento asignado al cliente.
     *
     * Ejemplo:
     * 5.00 = 5%
     * 10.00 = 10%
     */
    private BigDecimal descuento;

    /**
     * Indica si el cliente puede comprar a crédito.
     */
    private Boolean permiteCredito;

    /**
     * Referencia adicional del domicilio.
     */
    private String referenciaDomicilio;

    /**
     * Estado del cliente.
     */
    private Boolean activo;

    /**
     * Fecha de creación.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización.
     */
    private LocalDateTime updatedAt;


    // =========================================================
    // CONSTRUCTORES
    // =========================================================

    /**
     * Constructor vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor básico.
     *
     * Útil para crear rápidamente un cliente.
     */
    public Cliente(
            String codigo,
            String nombre,
            Long listaPrecioId) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.listaPrecioId = listaPrecioId;

        this.limiteCredito = BigDecimal.ZERO;
        this.diasCredito = 0;
        this.descuento = BigDecimal.ZERO;
        this.permiteCredito = false;
        this.activo = true;
    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListaPrecioId() {
        return listaPrecioId;
    }

    public void setListaPrecioId(Long listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Boolean getPermiteCredito() {
        return permiteCredito;
    }

    public void setPermiteCredito(Boolean permiteCredito) {
        this.permiteCredito = permiteCredito;
    }

    public String getReferenciaDomicilio() {
        return referenciaDomicilio;
    }

    public void setReferenciaDomicilio(
            String referenciaDomicilio) {

        this.referenciaDomicilio = referenciaDomicilio;
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


    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    /**
     * Indica si el cliente está activo.
     */
    public boolean estaActivo() {

        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Indica si el cliente tiene crédito habilitado.
     */
    public boolean tieneCredito() {

        return Boolean.TRUE.equals(this.permiteCredito);
    }

    /**
     * Indica si el cliente tiene una lista de precios asignada.
     */
    public boolean tieneListaPrecio() {

        return this.listaPrecioId != null
                && this.listaPrecioId > 0;
    }

    /**
     * Indica si el cliente tiene RFC.
     */
    public boolean tieneRfc() {

        return rfc != null
                && !rfc.trim().isEmpty();
    }

    /**
     * Indica si el cliente es el público en general.
     */
    public boolean esPublicoGeneral() {

        return "PUBLICO".equalsIgnoreCase(
                this.codigo
        );
    }


    // =========================================================
    // TOSTRING
    // =========================================================

    @Override
    public String toString() {

        return "Cliente{" +
                "id=" + id +
                ", listaPrecioId=" + listaPrecioId +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", rfc='" + rfc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", limiteCredito=" + limiteCredito +
                ", diasCredito=" + diasCredito +
                ", descuento=" + descuento +
                ", permiteCredito=" + permiteCredito +
                ", activo=" + activo +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
