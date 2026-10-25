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
 * TABLA: listas_precios
 * ============================================================
 *
 * Define los diferentes tipos de precio que puede manejar
 * el sistema.
 *
 * Ejemplos:
 *
 * PUBLICO
 * MAYOREO
 * DISTRIBUIDOR
 * VIP
 * EMPLEADO
 *
 * Una lista puede tener:
 *
 * GLOBAL:
 *   Se utiliza en todas las sucursales.
 *
 * SUCURSAL:
 *   Se utiliza únicamente en una sucursal determinada.
 *
 * ============================================================
 */

CREATE TABLE listas_precios (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Código interno de la lista.
     *
     * Ejemplo:
     * PUBLICO
     * MAYOREO
     * CENTRO
     * NORTE
     */
    codigo VARCHAR(20) NOT NULL,

    /*
     * Nombre visible de la lista.
     */
    nombre VARCHAR(100) NOT NULL,

    /*
     * Descripción de la lista.
     */
    descripcion VARCHAR(255),

    /*
     * Prioridad de la lista.
     *
     * Un número menor representa mayor prioridad.
     */
    prioridad INT NOT NULL DEFAULT 1,

    /*
     * Indica si esta es la lista predeterminada
     * para las ventas.
     */
    es_predeterminada BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Define el alcance de la lista.
     *
     * GLOBAL    = disponible para todas las sucursales.
     * SUCURSAL  = pertenece a una sucursal específica.
     */
    alcance VARCHAR(20) NOT NULL DEFAULT 'GLOBAL',

    /*
     * Sucursal propietaria de la lista cuando
     * el alcance es SUCURSAL.
     *
     * Para una lista GLOBAL debe ser NULL.
     */
    sucursal_id BIGINT NULL,

    /*
     * Estado de la lista.
     */
    activa BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Auditoría.
     */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    /*
     * ========================================================
     * LLAVE PRIMARIA
     * ========================================================
     */
    PRIMARY KEY (id),

    /*
     * Código único.
     */
    CONSTRAINT uk_lista_precio_codigo
        UNIQUE (codigo),

    /*
     * Nombre único.
     */
    CONSTRAINT uk_lista_precio_nombre
        UNIQUE (nombre),

    /*
     * Relación con sucursales.
     */
    CONSTRAINT fk_lista_precio_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON DELETE RESTRICT,

    /*
     * Solo permitimos los dos tipos de alcance.
     */
    CONSTRAINT chk_lista_precio_alcance
        CHECK (alcance IN ('GLOBAL', 'SUCURSAL'))

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Buscar listas activas.
 */
CREATE INDEX idx_lista_precio_activa
ON listas_precios(activa);


/*
 * Ordenar o buscar por prioridad.
 */
CREATE INDEX idx_lista_precio_prioridad
ON listas_precios(prioridad);


/*
 * Buscar listas de una sucursal.
 */
CREATE INDEX idx_lista_precio_sucursal
ON listas_precios(sucursal_id);


/*
 * Buscar listas por tipo de alcance.
 */
CREATE INDEX idx_lista_precio_alcance
ON listas_precios(alcance);


/*
 * ============================================================
 * LISTAS GLOBALES
 * ============================================================
 */

INSERT INTO listas_precios
(
    codigo,
    nombre,
    descripcion,
    prioridad,
    es_predeterminada,
    alcance,
    sucursal_id,
    activa
)
VALUES
(
    'PUBLICO',
    'Público General',
    'Precio normal de venta al público',
    1,
    TRUE,
    'GLOBAL',
    NULL,
    TRUE
),
(
    'MAYOREO',
    'Mayoreo',
    'Precio para compras por volumen',
    2,
    FALSE,
    'GLOBAL',
    NULL,
    TRUE
),
(
    'DISTRIBUIDOR',
    'Distribuidor',
    'Precio especial para distribuidores',
    3,
    FALSE,
    'GLOBAL',
    NULL,
    TRUE
),
(
    'VIP',
    'Cliente Frecuente',
    'Precio especial para clientes frecuentes',
    4,
    FALSE,
    'GLOBAL',
    NULL,
    TRUE
),
(
    'EMPLEADO',
    'Empleado',
    'Precio especial para empleados',
    5,
    FALSE,
    'GLOBAL',
    NULL,
    TRUE
);