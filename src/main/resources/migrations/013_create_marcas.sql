/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

/*
 * ============================================================
 * MIGRACION 013: MARCAS
 * SistemaPOSTienda
 * ============================================================
 *
 * Catalogo general de marcas de productos.
 *
 * Las marcas se registran segun las necesidades del negocio.
 * No se cargan marcas de un giro comercial especifico.
 * ============================================================
 */

CREATE TABLE marcas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(150) NOT NULL,

    codigo VARCHAR(30) NOT NULL,

    descripcion VARCHAR(255) NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * No permitir nombres ni codigos duplicados.
     */
    CONSTRAINT uk_marca_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_marca_codigo
        UNIQUE (codigo),

    /*
     * Validar que los datos principales no esten vacios.
     */
    CONSTRAINT chk_marca_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_marca_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar marcas activas.
 */
CREATE INDEX idx_marca_activo
    ON marcas(activo);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS:
 *
 * 1. No insertar marcas predeterminadas en esta migracion.
 * 2. Cada negocio registra las marcas que comercializa.
 * 3. Una marca utilizada por productos debe desactivarse
 *    en lugar de eliminarse.
 * 4. La aplicacion debe validar nombres y codigos duplicados.
 * ============================================================
 */
