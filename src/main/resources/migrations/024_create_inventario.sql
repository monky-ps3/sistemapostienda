/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
CREATE TABLE inventario (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

   lote VARCHAR(100) NOT NULL DEFAULT '',

    fecha_caducidad DATE NULL,

    existencia DECIMAL(12,3) NOT NULL DEFAULT 0,

    existencia_reservada DECIMAL(12,3) NOT NULL DEFAULT 0,

    existencia_minima DECIMAL(12,3) NOT NULL DEFAULT 0,

    existencia_maxima DECIMAL(12,3) NULL,

    ultimo_costo DECIMAL(12,4) NOT NULL DEFAULT 0,

    costo_promedio DECIMAL(12,4) NOT NULL DEFAULT 0,

    ubicacion VARCHAR(100) NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_inventario_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id),

    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id),

    CONSTRAINT chk_inventario_existencia
        CHECK (existencia >= 0),

    CONSTRAINT chk_inventario_reservada
        CHECK (existencia_reservada >= 0),

    CONSTRAINT chk_inventario_minima
        CHECK (existencia_minima >= 0),

    CONSTRAINT chk_inventario_maxima
        CHECK (
            existencia_maxima IS NULL
            OR existencia_maxima >= existencia_minima
        ),

    CONSTRAINT chk_inventario_costo
        CHECK (
            ultimo_costo >= 0
            AND costo_promedio >= 0
        ),

    CONSTRAINT uk_inventario
        UNIQUE (
            sucursal_id,
            producto_id,
            lote
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_inventario_producto
    ON inventario(producto_id);

CREATE INDEX idx_inventario_sucursal
    ON inventario(sucursal_id);

CREATE INDEX idx_inventario_caducidad
    ON inventario(fecha_caducidad);

CREATE INDEX idx_inventario_lote
    ON inventario(lote);

CREATE INDEX idx_inventario_activo
    ON inventario(activo);

CREATE INDEX idx_inventario_existencia
    ON inventario(existencia);