/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un proveedor dentro del sistema.
 *
 * Un proveedor es una empresa o persona a la que
 * el negocio compra mercancía o servicios.
 *
 * Las compras estarán relacionadas posteriormente
 * con el proveedor y con la sucursal donde se recibe
 * la mercancía.
 */
public class Proveedor {

    private Long id;

    /**
     * Código interno del proveedor.
     */
    private String codigo;

    /**
     * Nombre comercial o nombre del proveedor.
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
     * Persona de contacto.
     */
    private String contacto;

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
     * Días de crédito otorgados
     * por el proveedor.
     */
    private Integer diasCredito;

    /**
     * Límite de crédito que el proveedor
     * nos permite manejar.
     */
    private BigDecimal limiteCredito;

    /**
     * Indica si podemos realizar compras
     * a crédito con este proveedor.
     */
    private Boolean permiteCredito;

    /**
     * Observaciones adicionales.
     */
    private String observaciones;

    /**
     * Estado del proveedor.
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
    public Proveedor() {
    }

    /**
     * Constructor básico.
     */
    public Proveedor(
            String codigo,
            String nombre) {

        this.codigo = codigo;
        this.nombre = nombre;

        this.diasCredito = 0;
        this.limiteCredito = BigDecimal.ZERO;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Boolean getPermiteCredito() {
        return permiteCredito;
    }

    public void setPermiteCredito(Boolean permiteCredito) {
        this.permiteCredito = permiteCredito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
     * Indica si el proveedor está activo.
     */
    public boolean estaActivo() {

        return Boolean.TRUE.equals(this.activo);
    }

    /**
     * Indica si podemos comprarle a crédito.
     */
    public boolean tieneCredito() {

        return Boolean.TRUE.equals(
                this.permiteCredito
        );
    }

    /**
     * Indica si tiene RFC registrado.
     */
    public boolean tieneRfc() {

        return rfc != null
                && !rfc.trim().isEmpty();
    }

    /**
     * Indica si tiene una persona de contacto.
     */
    public boolean tieneContacto() {

        return contacto != null
                && !contacto.trim().isEmpty();
    }

    /**
     * Indica si el proveedor tiene crédito
     * configurado correctamente.
     */
    public boolean creditoConfigurado() {

        return tieneCredito()
                && limiteCredito != null
                && limiteCredito.compareTo(
                        BigDecimal.ZERO
                ) > 0
                && diasCredito != null
                && diasCredito > 0;
    }


    // =========================================================
    // TOSTRING
    // =========================================================

    @Override
    public String toString() {

        return "Proveedor{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", rfc='" + rfc + '\'' +
                ", contacto='" + contacto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", diasCredito=" + diasCredito +
                ", limiteCredito=" + limiteCredito +
                ", permiteCredito=" + permiteCredito +
                ", activo=" + activo +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}