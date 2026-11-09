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
 * MIGRACIÓN 019: CÓDIGOS DE BARRAS
 * ============================================================
 *
 * Una presentación puede tener varios códigos:
 * - Código del fabricante.
 * - Código interno.
 * - Código generado por báscula.
 *
 * Cada presentación puede tener como máximo
 * un código principal.
 *
 * ============================================================
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

    principal BOOLEAN NOT NULL DEFAULT FALSE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    permite_venta BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Índice único auxiliar.
     * Solo contiene el ID de la presentación
     * cuando el código es principal y está activo.
     *
     * Los códigos inactivos no bloquean la creación
     * de un nuevo código principal.
     */
    presentacion_codigo_principal BIGINT
        GENERATED ALWAYS AS (
            CASE
                WHEN principal = TRUE AND activo = TRUE
                THEN presentacion_id
                ELSE NULL
            END
        ) STORED,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Un código no puede pertenecer a dos registros.
     */
    CONSTRAINT uk_codigo_barras
        UNIQUE (codigo),

    /*
     * Una presentación solo puede tener
     * un código principal activo.
     */
    CONSTRAINT uk_codigo_principal_presentacion
        UNIQUE (presentacion_codigo_principal),

    /*
     * Relación con presentaciones.
     */
    CONSTRAINT fk_codigo_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validar que el código no esté vacío.
     */
    CONSTRAINT chk_codigo_barras_no_vacio
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Consultar códigos de una presentación.
 */
CREATE INDEX idx_codigo_presentacion_activo
ON codigos_barras(presentacion_id, activo);


/*
 * Buscar códigos disponibles para venta.
 */
CREATE INDEX idx_codigo_venta
ON codigos_barras(codigo, activo, permite_venta);


/*
 * Filtrar por tipo de código.
 */
CREATE INDEX idx_codigo_tipo_activo
ON codigos_barras(tipo, activo);
