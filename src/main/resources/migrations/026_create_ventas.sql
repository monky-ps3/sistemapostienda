/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 6 jul 2026
 */


CREATE TABLE ventas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    caja_id BIGINT NOT NULL,

    sesion_caja_id BIGINT NOT NULL,

    cliente_id BIGINT NULL,

    usuario_id BIGINT NOT NULL,

    lista_precio_id BIGINT NULL,

    folio VARCHAR(30) NOT NULL,

    fecha_venta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    tipo_venta ENUM(
        'CONTADO',
        'CREDITO'
    ) NOT NULL DEFAULT 'CONTADO',

    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,

    descuento DECIMAL(12,2) NOT NULL DEFAULT 0,

    impuesto DECIMAL(12,2) NOT NULL DEFAULT 0,

    total DECIMAL(12,2) NOT NULL DEFAULT 0,

    total_pagado DECIMAL(12,2) NOT NULL DEFAULT 0,

    cambio DECIMAL(12,2) NOT NULL DEFAULT 0,

    estado ENUM(
        'BORRADOR',
        'CONFIRMADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'BORRADOR',

    observaciones TEXT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_venta_folio
        UNIQUE (folio),

    CONSTRAINT fk_venta_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id),

    CONSTRAINT fk_venta_caja
        FOREIGN KEY (caja_id)
        REFERENCES cajas(id),

    CONSTRAINT fk_venta_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id),

    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes(id),

    CONSTRAINT fk_venta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_venta_lista_precio
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id),

    CONSTRAINT chk_venta_importes
        CHECK (
            subtotal >= 0
            AND descuento >= 0
            AND impuesto >= 0
            AND total >= 0
            AND total_pagado >= 0
            AND cambio >= 0
        ),

    CONSTRAINT chk_venta_descuento
        CHECK (descuento <= subtotal)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_venta_fecha
    ON ventas(fecha_venta);

CREATE INDEX idx_venta_cliente
    ON ventas(cliente_id);

CREATE INDEX idx_venta_usuario
    ON ventas(usuario_id);

CREATE INDEX idx_venta_sucursal
    ON ventas(sucursal_id);

CREATE INDEX idx_venta_lista_precio
    ON ventas(lista_precio_id);

CREATE INDEX idx_venta_estado
    ON ventas(estado);

CREATE INDEX idx_venta_tipo
    ON ventas(tipo_venta);

CREATE INDEX idx_venta_caja
    ON ventas(caja_id);

CREATE INDEX idx_venta_sesion
    ON ventas(sesion_caja_id);
