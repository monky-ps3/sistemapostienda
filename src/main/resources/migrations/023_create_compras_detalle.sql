/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

/*
 * ============================================================
 * MIGRACIÓN 023: DETALLE DE COMPRAS
 * ============================================================
 *
 * Almacena los productos y cantidades de cada compra.
 *
 * Conserva los costos, descuentos, impuestos
 * y factores de conversión utilizados al comprar.
 *
 * Esto permite mantener el historial aunque después
 * cambien los precios o las presentaciones.
 *
 * ============================================================
 */

CREATE TABLE compras_detalle (

    id BIGINT NOT NULL AUTO_INCREMENT,

    compra_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    presentacion_id BIGINT NOT NULL,

    cantidad DECIMAL(15,4) NOT NULL,

    factor_conversion DECIMAL(15,6) NOT NULL,

    costo_unitario DECIMAL(15,4) NOT NULL,

    descuento DECIMAL(14,2) NOT NULL DEFAULT 0,

    porcentaje_impuesto DECIMAL(5,2) NOT NULL DEFAULT 0,

    importe_impuesto DECIMAL(14,2) NOT NULL DEFAULT 0,

    subtotal DECIMAL(14,2) NOT NULL,

    total DECIMAL(14,2) NOT NULL,

    lote VARCHAR(100) NULL,

    fecha_caducidad DATE NULL,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Al eliminar una compra en borrador,
     * sus detalles pueden eliminarse junto con ella.
     *
     * Las compras confirmadas no deben eliminarse
     * desde la aplicación.
     */
    CONSTRAINT fk_compra_detalle_compra
        FOREIGN KEY (compra_id)
        REFERENCES compras(id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE,

    CONSTRAINT fk_compra_detalle_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_compra_detalle_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT chk_compra_detalle_cantidad
        CHECK (cantidad > 0),

    CONSTRAINT chk_compra_detalle_factor
        CHECK (factor_conversion > 0),

    CONSTRAINT chk_compra_detalle_costo
        CHECK (costo_unitario >= 0),

    CONSTRAINT chk_compra_detalle_descuento
        CHECK (descuento >= 0),

    CONSTRAINT chk_compra_detalle_impuesto_porcentaje
        CHECK (
            porcentaje_impuesto >= 0
            AND porcentaje_impuesto <= 100
        ),

    CONSTRAINT chk_compra_detalle_impuesto
        CHECK (importe_impuesto >= 0),

    CONSTRAINT chk_compra_detalle_subtotal
        CHECK (subtotal >= 0),

    CONSTRAINT chk_compra_detalle_total
        CHECK (total >= 0),

    CONSTRAINT chk_compra_detalle_descuento_subtotal
        CHECK (descuento <= subtotal),

    CONSTRAINT chk_compra_detalle_total_calculo
        CHECK (
            total = subtotal - descuento + importe_impuesto
        ),

    CONSTRAINT chk_compra_detalle_caducidad
        CHECK (
            fecha_caducidad IS NULL
            OR lote IS NOT NULL
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_compra_detalle_producto
ON compras_detalle(producto_id);

CREATE INDEX idx_compra_detalle_presentacion
ON compras_detalle(presentacion_id);

CREATE INDEX idx_compra_detalle_lote
ON compras_detalle(lote);

CREATE INDEX idx_compra_detalle_caducidad
ON compras_detalle(fecha_caducidad);
