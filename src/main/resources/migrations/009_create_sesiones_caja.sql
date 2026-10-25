/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE sesiones_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    folio VARCHAR(30) NOT NULL,

    caja_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    fecha_apertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    fecha_cierre TIMESTAMP NULL,


    fondo_inicial DECIMAL(12,2) NOT NULL,
    turno ENUM('MATUTINO','VESPERTINO','NOCTURNO','PERSONALIZADO'),

    estado ENUM('ABIERTA','CERRADA') NOT NULL DEFAULT 'ABIERTA',

    observaciones VARCHAR(255),

    PRIMARY KEY(id),

    CONSTRAINT uk_sesion_folio
        UNIQUE(folio),

    CONSTRAINT fk_sesion_caja
        FOREIGN KEY(caja_id)
        REFERENCES cajas(id),

    CONSTRAINT fk_sesion_usuario
        FOREIGN KEY(usuario_id)
        REFERENCES usuarios(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_sesion_estado
ON sesiones_caja(estado);

CREATE INDEX idx_sesion_usuario
ON sesiones_caja(usuario_id);

CREATE INDEX idx_sesion_caja
ON sesiones_caja(caja_id);