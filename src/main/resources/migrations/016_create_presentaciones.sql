/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE presentaciones (

    id BIGINT NOT NULL AUTO_INCREMENT,

    producto_id BIGINT NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    abreviatura VARCHAR(20),

    factor_conversion DECIMAL(12,3) NOT NULL,

    es_predeterminada BOOLEAN NOT NULL DEFAULT FALSE,

    permite_venta BOOLEAN NOT NULL DEFAULT TRUE,

    activa BOOLEAN NOT NULL DEFAULT TRUE,

    orden INT NOT NULL DEFAULT 1,

    observaciones VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_presentacion_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id),

    CONSTRAINT uk_presentacion_producto
        UNIQUE (producto_id, nombre),

    CONSTRAINT chk_presentacion_factor
        CHECK (factor_conversion > 0),

    CONSTRAINT chk_presentacion_orden
        CHECK (orden > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_presentacion_producto
ON presentaciones(producto_id);


CREATE INDEX idx_presentacion_activa
ON presentaciones(activa);