/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */


CREATE TABLE codigos_barras (

    id BIGINT NOT NULL AUTO_INCREMENT,

    presentacion_id BIGINT NOT NULL,

    codigo VARCHAR(100) NOT NULL,

    tipo ENUM(
        'FABRICANTE',
        'INTERNO',
        'BASCULA'
    ) NOT NULL DEFAULT 'FABRICANTE',

    principal BOOLEAN NOT NULL DEFAULT TRUE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

 permite_venta BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT uk_codigo_barras
        UNIQUE(codigo),

    CONSTRAINT fk_codigo_presentacion
        FOREIGN KEY(presentacion_id)
        REFERENCES presentaciones(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_codigo_presentacion
ON codigos_barras(presentacion_id);

CREATE INDEX idx_codigo_principal
ON codigos_barras(principal);

CREATE INDEX idx_codigo_activo
ON codigos_barras(activo);
