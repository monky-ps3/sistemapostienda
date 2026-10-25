/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE corte_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sesion_caja_id BIGINT NOT NULL,

    ventas DECIMAL(12,2) NOT NULL DEFAULT 0,

    entradas DECIMAL(12,2) NOT NULL DEFAULT 0,

    salidas DECIMAL(12,2) NOT NULL DEFAULT 0,

    efectivo_esperado DECIMAL(12,2) NOT NULL,

    efectivo_contado DECIMAL(12,2) NOT NULL,

    diferencia DECIMAL(12,2) NOT NULL,

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT uk_corte_sesion
        UNIQUE(sesion_caja_id),

    CONSTRAINT fk_corte_sesion
        FOREIGN KEY(sesion_caja_id)
        REFERENCES sesiones_caja(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;