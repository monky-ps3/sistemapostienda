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
 * MIGRACIÓN 017: LISTAS DE PRECIOS
 * ============================================================
 *
 * Permite manejar diferentes listas de precios:
 * - Público general
 * - Mayoreo
 * - Distribuidor
 * - Clientes especiales
 *
 * Las listas pueden ser globales o pertenecer
 * a una sucursal específica.
 *
 * ============================================================
 */

CREATE TABLE listas_precios (

    id BIGINT NOT NULL AUTO_INCREMENT,

    codigo VARCHAR(20) NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    descripcion VARCHAR(255) NULL,

    prioridad INT NOT NULL DEFAULT 1,

    es_predeterminada BOOLEAN NOT NULL DEFAULT FALSE,

    alcance ENUM('GLOBAL', 'SUCURSAL')
        NOT NULL DEFAULT 'GLOBAL',

    sucursal_id BIGINT NULL,

    activa BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Solo puede existir una lista predeterminada global.
     */
    lista_predeterminada_global TINYINT
        GENERATED ALWAYS AS (
            CASE
                WHEN es_predeterminada = TRUE
                 AND alcance = 'GLOBAL'
                THEN 1
                ELSE NULL
            END
        ) STORED,

    /*
     * Solo puede existir una lista predeterminada
     * por cada sucursal.
     */
    lista_predeterminada_sucursal BIGINT
        GENERATED ALWAYS AS (
            CASE
                WHEN es_predeterminada = TRUE
                 AND alcance = 'SUCURSAL'
                THEN sucursal_id
                ELSE NULL
            END
        ) STORED,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_lista_precio_codigo
        UNIQUE (codigo),

    CONSTRAINT uk_lista_precio_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_lista_predeterminada_global
        UNIQUE (lista_predeterminada_global),

    CONSTRAINT uk_lista_predeterminada_sucursal
        UNIQUE (lista_predeterminada_sucursal),

    CONSTRAINT fk_lista_precio_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT chk_lista_precio_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_lista_precio_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_lista_precio_prioridad
        CHECK (prioridad > 0),

    CONSTRAINT chk_lista_precio_sucursal
        CHECK (
            (alcance = 'GLOBAL' AND sucursal_id IS NULL)
            OR
            (alcance = 'SUCURSAL' AND sucursal_id IS NOT NULL)
        ),

    CONSTRAINT chk_lista_predeterminada_activa
        CHECK (
            es_predeterminada = FALSE
            OR activa = TRUE
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_lista_precio_activa
ON listas_precios(activa);

CREATE INDEX idx_lista_precio_prioridad
ON listas_precios(prioridad);

CREATE INDEX idx_lista_precio_sucursal_activa
ON listas_precios(sucursal_id, activa, prioridad);

CREATE INDEX idx_lista_precio_alcance_activa
ON listas_precios(alcance, activa);


/*
 * ============================================================
 * LISTA INICIAL
 * ============================================================
 *
 * Se crea únicamente la lista de público general.
 * Las demás listas se podrán crear desde el sistema
 * según las necesidades de cada negocio.
 *
 * ============================================================
 */

INSERT INTO listas_precios (
    codigo,
    nombre,
    descripcion,
    prioridad,
    es_predeterminada,
    alcance,
    sucursal_id,
    activa
)
VALUES (
    'PUBLICO',
    'Público General',
    'Lista de precios general para ventas al público',
    1,
    TRUE,
    'GLOBAL',
    NULL,
    TRUE
);
