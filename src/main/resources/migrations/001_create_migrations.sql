/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026 
 */


/*
 * Tabla de control de migraciones del sistema POS.
 * Cada versión debe registrarse una sola vez.
 */

CREATE TABLE migrations (

    id BIGINT NOT NULL AUTO_INCREMENT,

    version INT NOT NULL,

    nombre VARCHAR(150) NOT NULL,

    ejecutada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    tiempo_ms INT NOT NULL DEFAULT 0,

    estado VARCHAR(20) NOT NULL DEFAULT 'OK',

    PRIMARY KEY (id),

    CONSTRAINT uk_migrations_version
        UNIQUE (version),

    CONSTRAINT chk_migrations_version
        CHECK (version > 0),

    CONSTRAINT chk_migrations_tiempo
        CHECK (tiempo_ms >= 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
