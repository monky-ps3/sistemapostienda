/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ============================================================
 * SistemaPOSTienda
 *
 * Modelo que representa un producto.
 *
 * El producto se relaciona con:
 *
 * categoria_id      -> categorias
 * marca_id          -> marcas
 * unidad_medida_id  -> unidades_medida
 *
 * El stock actual NO se almacena aquí.
 * Será manejado posteriormente por inventarios.
 *
 * ============================================================
 */
public class Producto {

    /*
     * ============================================================
     * ATRIBUTOS
     * ============================================================
     */

    /**
     * Identificador único del producto.
     */
    private Long id;

    /**
     * ID de la categoría.
     */
    private Long categoriaId;

    /**
     * ID de la marca.
     *
     * Puede ser null.
     */
    private Long marcaId;

    /**
     * ID de la unidad de medida.
     */
    private Long unidadMedidaId;

    /**
     * Código principal del producto.
     */
    private String codigo;

    /**
     * SKU interno del producto.
     */
    private String sku;

    /**
     * Nombre del producto.
     */
    private String nombre;

    /**
     * Descripción del producto.
     */
    private String descripcion;

    /**
     * Ruta o nombre de la imagen.
     */
    private String imagen;

    /**
     * Indica si el producto aplica IVA.
     */
    private Boolean aplicaIva;

    /**
     * Porcentaje de IVA.
     */
    private BigDecimal porcentajeIva;

    /**
     * Indica si maneja inventario.
     */
    private Boolean manejaInventario;

    /**
     * Indica si maneja lotes.
     */
    private Boolean manejaLotes;

    /**
     * Indica si maneja caducidad.
     */
    private Boolean manejaCaducidad;

    /**
     * Indica si se vende por peso.
     */
    private Boolean ventaPorPeso;

    /**
     * Indica si permite cantidades decimales.
     */
    private Boolean permiteDecimales;

    /**
     * Stock mínimo recomendado.
     */
    private BigDecimal stockMinimo;

    /**
     * Stock máximo recomendado.
     */
    private BigDecimal stockMaximo;

    /**
     * Indica si permite descuentos.
     */
    private Boolean permiteDescuento;

    /**
     * Estado del producto.
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
     * CONSTRUCTOR VACÍO
     * ============================================================
     */

    public Producto() {

    }

    /*
     * ============================================================
     * CONSTRUCTOR COMPLETO
     * ============================================================
     */

    public Producto(
            Long id,
            Long categoriaId,
            Long marcaId,
            Long unidadMedidaId,
            String codigo,
            String sku,
            String nombre,
            String descripcion,
            String imagen,
            Boolean aplicaIva,
            BigDecimal porcentajeIva,
            Boolean manejaInventario,
            Boolean manejaLotes,
            Boolean manejaCaducidad,
            Boolean ventaPorPeso,
            Boolean permiteDecimales,
            BigDecimal stockMinimo,
            BigDecimal stockMaximo,
            Boolean permiteDescuento,
            Boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.categoriaId = categoriaId;
        this.marcaId = marcaId;
        this.unidadMedidaId = unidadMedidaId;
        this.codigo = codigo;
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.aplicaIva = aplicaIva;
        this.porcentajeIva = porcentajeIva;
        this.manejaInventario = manejaInventario;
        this.manejaLotes = manejaLotes;
        this.manejaCaducidad = manejaCaducidad;
        this.ventaPorPeso = ventaPorPeso;
        this.permiteDecimales = permiteDecimales;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.permiteDescuento = permiteDescuento;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    /*
     * ============================================================
     * GETTERS Y SETTERS
     * ============================================================
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public Long getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Long unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getAplicaIva() {
        return aplicaIva;
    }

    public void setAplicaIva(Boolean aplicaIva) {
        this.aplicaIva = aplicaIva;
    }

    public BigDecimal getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(BigDecimal porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public Boolean getManejaInventario() {
        return manejaInventario;
    }

    public void setManejaInventario(Boolean manejaInventario) {
        this.manejaInventario = manejaInventario;
    }

    public Boolean getManejaLotes() {
        return manejaLotes;
    }

    public void setManejaLotes(Boolean manejaLotes) {
        this.manejaLotes = manejaLotes;
    }

    public Boolean getManejaCaducidad() {
        return manejaCaducidad;
    }

    public void setManejaCaducidad(Boolean manejaCaducidad) {
        this.manejaCaducidad = manejaCaducidad;
    }

    public Boolean getVentaPorPeso() {
        return ventaPorPeso;
    }

    public void setVentaPorPeso(Boolean ventaPorPeso) {
        this.ventaPorPeso = ventaPorPeso;
    }

    public Boolean getPermiteDecimales() {
        return permiteDecimales;
    }

    public void setPermiteDecimales(Boolean permiteDecimales) {
        this.permiteDecimales = permiteDecimales;
    }

    public BigDecimal getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(BigDecimal stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public BigDecimal getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(BigDecimal stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public Boolean getPermiteDescuento() {
        return permiteDescuento;
    }

    public void setPermiteDescuento(Boolean permiteDescuento) {
        this.permiteDescuento = permiteDescuento;
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
     * TOSTRING
     * ============================================================
     */

    /**
     * Devuelve el nombre del producto.
     *
     * Es útil cuando posteriormente utilicemos
     * JComboBox u otros componentes de la interfaz.
     */
    @Override
    public String toString() {

        return nombre;

    }

}