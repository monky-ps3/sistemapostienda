/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
CREATE TABLE compras_detalle (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Compra a la que pertenece este detalle.
     */
    compra_id BIGINT NOT NULL,

    /*
     * Producto comprado.
     */
    producto_id BIGINT NOT NULL,

    /*
     * Presentación utilizada en la compra.
     *
     * Ejemplo:
     * PIEZA
     * PAQUETE
     * CAJA
     */
    presentacion_id BIGINT NOT NULL,

    /*
     * Cantidad comprada en la presentación seleccionada.
     *
     * Ejemplo:
     * 5 cajas
     * 10 paquetes
     * 20 piezas
     */
    cantidad DECIMAL(12,3) NOT NULL,

    /*
     * Factor de conversión utilizado en el momento
     * de realizar la compra.
     *
     * Ejemplo:
     *
     * CAJA = 24 piezas
     * factor_conversion = 24
     *
     * Se guarda aquí para conservar el histórico,
     * aunque posteriormente cambie la configuración
     * de la presentación.
     */
    factor_conversion DECIMAL(12,3) NOT NULL,

    /*
     * Costo de una unidad de la presentación comprada.
     *
     * Se utilizan 4 decimales para permitir costos
     * muy precisos.
     */
    costo_unitario DECIMAL(12,4) NOT NULL,

    /*
     * Descuento aplicado a esta línea.
     */
    descuento DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Porcentaje de impuesto aplicado.
     *
     * Ejemplo:
     * 16.00
     * 0.00
     */
    porcentaje_impuesto DECIMAL(5,2) NOT NULL DEFAULT 0,

    /*
     * Importe monetario del impuesto.
     */
    importe_impuesto DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Importe antes del impuesto.
     */
    subtotal DECIMAL(12,2) NOT NULL,

    /*
     * Importe final de la línea.
     */
    total DECIMAL(12,2) NOT NULL,

    /*
     * Lote del producto.
     *
     * Se utiliza cuando el producto maneja lotes.
     */
    lote VARCHAR(100),

    /*
     * Fecha de caducidad.
     *
     * Se utiliza cuando el producto maneja caducidad.
     */
    fecha_caducidad DATE,

    /*
     * Auditoría.
     */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Relaciones.
     */
    CONSTRAINT fk_compra_detalle_compra
        FOREIGN KEY (compra_id)
        REFERENCES compras(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_compra_detalle_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id),

    CONSTRAINT fk_compra_detalle_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- ÍNDICES
-- ============================================================

CREATE INDEX idx_compra_detalle_compra
ON compras_detalle(compra_id);

CREATE INDEX idx_compra_detalle_producto
ON compras_detalle(producto_id);

CREATE INDEX idx_compra_detalle_presentacion
ON compras_detalle(presentacion_id);

CREATE INDEX idx_compra_detalle_lote
ON compras_detalle(lote);

CREATE INDEX idx_compra_detalle_caducidad
ON compras_detalle(fecha_caducidad);