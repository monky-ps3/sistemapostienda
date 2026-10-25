/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE movimientos_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sesion_caja_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    tipo ENUM(

        'APERTURA',

        'INGRESO',

        'SALIDA',

        'RETIRO',

        'AJUSTE_POSITIVO',

        'AJUSTE_NEGATIVO'

    ) NOT NULL,

    concepto VARCHAR(200) NOT NULL,

    monto DECIMAL(12,2) NOT NULL,

    observaciones VARCHAR(255),

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT fk_movimiento_sesion
        FOREIGN KEY(sesion_caja_id)
        REFERENCES sesiones_caja(id),

    CONSTRAINT fk_movimiento_usuario
        FOREIGN KEY(usuario_id)
        REFERENCES usuarios(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_movimiento_fecha
ON movimientos_caja(fecha);

CREATE INDEX idx_movimiento_sesion
ON movimientos_caja(sesion_caja_id);