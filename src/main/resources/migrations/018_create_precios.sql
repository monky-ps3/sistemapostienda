/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */


CREATE TABLE precios (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Presentación a la que pertenece el precio.
     *
     * Ejemplo:
     * Coca-Cola → Pieza
     * Coca-Cola → Caja
     */
    presentacion_id BIGINT NOT NULL,

    /*
     * Lista de precios utilizada.
     *
     * Ejemplo:
     * Público
     * Mayoreo
     * VIP
     */
    lista_precio_id BIGINT NOT NULL,

    /*
     * Precio de venta.
     */
    precio DECIMAL(12,2) NOT NULL,

    /*
     * Estado del precio.
     */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Auditoría.
     */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    /*
     * Llave primaria.
     */
    PRIMARY KEY (id),

    /*
     * Relación con presentaciones.
     */
    CONSTRAINT fk_precio_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)
        ON DELETE CASCADE,

    /*
     * Relación con listas de precios.
     */
    CONSTRAINT fk_precio_lista
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id)
        ON DELETE CASCADE,

    /*
     * Una presentación solamente puede tener
     * un precio dentro de una determinada lista.
     */
    CONSTRAINT uk_precio_presentacion_lista
        UNIQUE (presentacion_id, lista_precio_id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_precio_presentacion
ON precios(presentacion_id);

CREATE INDEX idx_precio_lista
ON precios(lista_precio_id);

CREATE INDEX idx_precio_activo
ON precios(activo);