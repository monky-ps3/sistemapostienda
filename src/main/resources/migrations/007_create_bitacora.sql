/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE bitacora (

    id BIGINT NOT NULL AUTO_INCREMENT,

    usuario_id BIGINT,

    sucursal_id BIGINT,

    modulo VARCHAR(100) NOT NULL,

    accion VARCHAR(100) NOT NULL,

    descripcion TEXT,

    ip VARCHAR(45),

    equipo VARCHAR(150),

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT fk_bitacora_usuario
        FOREIGN KEY(usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_bitacora_sucursal
        FOREIGN KEY(sucursal_id)
        REFERENCES sucursales(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_bitacora_usuario
ON bitacora(usuario_id);

CREATE INDEX idx_bitacora_fecha
ON bitacora(fecha);

CREATE INDEX idx_bitacora_modulo
ON bitacora(modulo);