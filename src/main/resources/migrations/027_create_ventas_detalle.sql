/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 6 jul 2026
 */

CREATE TABLE ventas_detalle (

    id BIGINT NOT NULL AUTO_INCREMENT,

    venta_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    presentacion_id BIGINT NOT NULL,

    inventario_id BIGINT,

    cantidad DECIMAL(12,3) NOT NULL,

    factor_conversion DECIMAL(12,3) NOT NULL,

    precio_unitario DECIMAL(12,4) NOT NULL,

    costo_unitario DECIMAL(12,4) NOT NULL,

    descuento DECIMAL(12,2) NOT NULL DEFAULT 0,

    porcentaje_impuesto DECIMAL(5,2) NOT NULL DEFAULT 0,

    importe_impuesto DECIMAL(12,2) NOT NULL DEFAULT 0,

    subtotal DECIMAL(12,2) NOT NULL,

    total DECIMAL(12,2) NOT NULL,

    utilidad DECIMAL(12,2) NOT NULL DEFAULT 0,

    lote VARCHAR(100),

    fecha_caducidad DATE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT fk_venta_detalle_venta
        FOREIGN KEY (venta_id)
        REFERENCES ventas(id),

    CONSTRAINT fk_venta_detalle_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id),

    CONSTRAINT fk_venta_detalle_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id),

    CONSTRAINT fk_venta_detalle_inventario
        FOREIGN KEY (inventario_id)
        REFERENCES inventario(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_venta_detalle_venta
ON ventas_detalle(venta_id);

CREATE INDEX idx_venta_detalle_producto
ON ventas_detalle(producto_id);

CREATE INDEX idx_venta_detalle_inventario
ON ventas_detalle(inventario_id);

CREATE INDEX idx_venta_detalle_lote
ON ventas_detalle(lote);