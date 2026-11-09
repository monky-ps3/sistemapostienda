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
 * MIGRACIÓN 018: PRECIOS
 * ============================================================
 *
 * Cada presentación puede tener un precio diferente
 * dependiendo de la lista de precios.
 *
 * Ejemplo:
 * Producto: Refresco
 * Presentación: Pieza
 *
 * Público general: 18.00
 * Mayoreo:         16.00
 *
 * Los precios se conservan aunque se desactive
 * una presentación o una lista.
 *
 * ============================================================
 */

CREATE TABLE precios (

    id BIGINT NOT NULL AUTO_INCREMENT,

    presentacion_id BIGINT NOT NULL,

    lista_precio_id BIGINT NOT NULL,

    precio DECIMAL(12,2) NOT NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Una presentación solamente puede tener
     * un precio por cada lista.
     */
    CONSTRAINT uk_precio_presentacion_lista
        UNIQUE (presentacion_id, lista_precio_id),

    /*
     * Relación con presentaciones.
     *
     * No se permite eliminar una presentación
     * que tenga precios registrados.
     */
    CONSTRAINT fk_precio_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Relación con listas de precios.
     *
     * No se permite eliminar una lista que
     * tenga precios registrados.
     */
    CONSTRAINT fk_precio_lista
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * El precio no puede ser negativo.
     * Se permite cero para artículos gratuitos.
     */
    CONSTRAINT chk_precio_valor
        CHECK (precio >= 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Buscar precios de una lista y filtrar activos.
 */
CREATE INDEX idx_precio_lista_activo
ON precios(lista_precio_id, activo);


/*
 * Consultar precios activos.
 */
CREATE INDEX idx_precio_activo
ON precios(activo);
