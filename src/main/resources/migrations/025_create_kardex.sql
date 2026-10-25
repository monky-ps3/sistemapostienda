/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE kardex (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    presentacion_id BIGINT NULL,

    inventario_id BIGINT NULL,

    usuario_id BIGINT NOT NULL,

    tipo_movimiento ENUM(
        'INVENTARIO_INICIAL',
        'COMPRA',
        'VENTA',
        'DEVOLUCION_CLIENTE',
        'DEVOLUCION_PROVEEDOR',
        'AJUSTE_ENTRADA',
        'AJUSTE_SALIDA',
        'TRASPASO_ENTRADA',
        'TRASPASO_SALIDA',
        'CANCELACION_COMPRA',
        'CANCELACION_VENTA'
    ) NOT NULL,

    referencia_id BIGINT NULL,

    referencia VARCHAR(100) NULL,

    lote VARCHAR(100) NULL,

    fecha_caducidad DATE NULL,

    entrada DECIMAL(12,3) NOT NULL DEFAULT 0,

    salida DECIMAL(12,3) NOT NULL DEFAULT 0,

    existencia_anterior DECIMAL(12,3) NOT NULL,

    existencia_actual DECIMAL(12,3) NOT NULL,

    costo_unitario DECIMAL(12,4) NOT NULL DEFAULT 0,

    costo_total DECIMAL(12,2) NOT NULL DEFAULT 0,

    observaciones VARCHAR(255) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT fk_kardex_sucursal
        FOREIGN KEY(sucursal_id)
        REFERENCES sucursales(id),

    CONSTRAINT fk_kardex_producto
        FOREIGN KEY(producto_id)
        REFERENCES productos(id),

    CONSTRAINT fk_kardex_presentacion
        FOREIGN KEY(presentacion_id)
        REFERENCES presentaciones(id),

    CONSTRAINT fk_kardex_inventario
        FOREIGN KEY(inventario_id)
        REFERENCES inventario(id),

    CONSTRAINT fk_kardex_usuario
        FOREIGN KEY(usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT chk_kardex_entrada
        CHECK (entrada >= 0),

    CONSTRAINT chk_kardex_salida
        CHECK (salida >= 0),

    CONSTRAINT chk_kardex_existencia_anterior
        CHECK (existencia_anterior >= 0),

    CONSTRAINT chk_kardex_existencia_actual
        CHECK (existencia_actual >= 0),

    CONSTRAINT chk_kardex_costo
        CHECK (costo_unitario >= 0),

    CONSTRAINT chk_kardex_costo_total
        CHECK (costo_total >= 0),

    CONSTRAINT chk_kardex_entrada_salida
        CHECK (
            (entrada > 0 AND salida = 0)
            OR
            (salida > 0 AND entrada = 0)
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_kardex_producto
    ON kardex(producto_id);


CREATE INDEX idx_kardex_sucursal
    ON kardex(sucursal_id);


CREATE INDEX idx_kardex_presentacion
    ON kardex(presentacion_id);


CREATE INDEX idx_kardex_inventario
    ON kardex(inventario_id);


CREATE INDEX idx_kardex_usuario
    ON kardex(usuario_id);


CREATE INDEX idx_kardex_fecha
    ON kardex(created_at);


CREATE INDEX idx_kardex_movimiento
    ON kardex(tipo_movimiento);


CREATE INDEX idx_kardex_referencia
    ON kardex(referencia_id);


CREATE INDEX idx_kardex_lote
    ON kardex(lote);