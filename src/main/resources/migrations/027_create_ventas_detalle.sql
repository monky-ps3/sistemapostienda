/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 6 jul 2026
 */

/*
 * ============================================================
 * MIGRACIÓN 027: DETALLE DE VENTAS
 * ============================================================
 *
 * Registra cada producto vendido.
 *
 * Conserva los datos del producto, presentación,
 * cantidades, costos, precios, descuentos e impuestos
 * utilizados al momento de la venta.
 *
 * ============================================================
 */

CREATE TABLE ventas_detalle (

    id BIGINT NOT NULL AUTO_INCREMENT,

    venta_id BIGINT NOT NULL,

    /* Sucursal heredada de la venta para validar que el
       inventario descontado pertenezca a esa misma sucursal. */
    sucursal_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    presentacion_id BIGINT NOT NULL,

    inventario_id BIGINT NULL,

    /*
     * Datos históricos del producto.
     * Evitan perder información si cambia su nombre
     * o código en el catálogo.
     */
    producto_codigo VARCHAR(50) NOT NULL,

    producto_nombre VARCHAR(200) NOT NULL,

    presentacion_nombre VARCHAR(100) NOT NULL,

    cantidad DECIMAL(15,4) NOT NULL,

    factor_conversion DECIMAL(15,6) NOT NULL,

    precio_unitario DECIMAL(15,4) NOT NULL,

    costo_unitario DECIMAL(15,4) NOT NULL,

    descuento DECIMAL(14,2) NOT NULL DEFAULT 0,

    porcentaje_impuesto DECIMAL(5,2) NOT NULL DEFAULT 0,

    importe_impuesto DECIMAL(14,2) NOT NULL DEFAULT 0,

    subtotal DECIMAL(14,2) NOT NULL,

    total DECIMAL(14,2) NOT NULL,

    /*
     * Puede ser negativo si se vende por debajo del costo.
     */
    utilidad DECIMAL(14,2) NOT NULL DEFAULT 0,

    lote VARCHAR(100) NULL,

    fecha_caducidad DATE NULL,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * No eliminar detalles que formen parte
     * del historial de una venta.
     */
    CONSTRAINT fk_venta_detalle_venta_sucursal
        FOREIGN KEY (venta_id, sucursal_id)
        REFERENCES ventas(id, sucursal_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_detalle_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_detalle_presentacion_producto
        FOREIGN KEY (presentacion_id, producto_id)
        REFERENCES presentaciones(id, producto_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_detalle_inventario_sucursal_producto
        FOREIGN KEY (inventario_id, sucursal_id, producto_id)
        REFERENCES inventario(id, sucursal_id, producto_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT chk_venta_detalle_codigo
        CHECK (CHAR_LENGTH(TRIM(producto_codigo)) > 0),

    CONSTRAINT chk_venta_detalle_nombre
        CHECK (CHAR_LENGTH(TRIM(producto_nombre)) > 0),

    CONSTRAINT chk_venta_detalle_presentacion_nombre
        CHECK (CHAR_LENGTH(TRIM(presentacion_nombre)) > 0),

    CONSTRAINT chk_venta_detalle_cantidad
        CHECK (cantidad > 0),

    CONSTRAINT chk_venta_detalle_factor
        CHECK (factor_conversion > 0),

    CONSTRAINT chk_venta_detalle_precio
        CHECK (precio_unitario >= 0),

    CONSTRAINT chk_venta_detalle_costo
        CHECK (costo_unitario >= 0),

    CONSTRAINT chk_venta_detalle_descuento
        CHECK (descuento >= 0),

    CONSTRAINT chk_venta_detalle_impuesto_porcentaje
        CHECK (
            porcentaje_impuesto >= 0
            AND porcentaje_impuesto <= 100
        ),

    CONSTRAINT chk_venta_detalle_impuesto
        CHECK (importe_impuesto >= 0),

    CONSTRAINT chk_venta_detalle_subtotal
        CHECK (subtotal >= 0),

    CONSTRAINT chk_venta_detalle_total
        CHECK (total >= 0),

    CONSTRAINT chk_venta_detalle_descuento_subtotal
        CHECK (descuento <= subtotal),

    CONSTRAINT chk_venta_detalle_total_calculo
        CHECK (
            total = subtotal - descuento + importe_impuesto
        ),

    /*
     * Si existe caducidad, debe existir un lote.
     */
    CONSTRAINT chk_venta_detalle_lote_caducidad
        CHECK (
            fecha_caducidad IS NULL
            OR (
                lote IS NOT NULL
                AND CHAR_LENGTH(TRIM(lote)) > 0
            )
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_venta_detalle_producto
ON ventas_detalle(producto_id);

CREATE INDEX idx_venta_detalle_presentacion
ON ventas_detalle(presentacion_id);

CREATE INDEX idx_venta_detalle_inventario
ON ventas_detalle(inventario_id);

CREATE INDEX idx_venta_detalle_lote
ON ventas_detalle(lote);

CREATE INDEX idx_venta_detalle_caducidad
ON ventas_detalle(fecha_caducidad);
