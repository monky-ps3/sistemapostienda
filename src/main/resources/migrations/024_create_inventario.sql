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
 * MIGRACIÓN 024: INVENTARIO
 * ============================================================
 *
 * Control de existencias por sucursal y producto.
 *
 * Permite manejar:
 * - Productos sin lotes.
 * - Productos con lotes.
 * - Productos con fecha de caducidad.
 * - Existencias reservadas.
 * - Costos de inventario.
 *
 * Cada producto tendrá un registro de inventario
 * sin lote o registros separados por cada lote.
 *
 * ============================================================
 */

CREATE TABLE inventario (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    /*
     * NULL para productos sin control de lotes.
     * Se registra el número de lote cuando aplica.
     */
    lote VARCHAR(100) NULL,

    fecha_caducidad DATE NULL,

    existencia DECIMAL(15,4) NOT NULL DEFAULT 0,

    existencia_reservada DECIMAL(15,4) NOT NULL DEFAULT 0,

    existencia_minima DECIMAL(15,4) NOT NULL DEFAULT 0,

    existencia_maxima DECIMAL(15,4) NULL,

    ultimo_costo DECIMAL(15,4) NOT NULL DEFAULT 0,

    costo_promedio DECIMAL(15,4) NOT NULL DEFAULT 0,

    ubicacion VARCHAR(100) NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Normaliza el lote NULL como cadena vacía
     * para poder garantizar un único registro
     * sin lote por sucursal y producto.
     */
    lote_clave VARCHAR(100)
        GENERATED ALWAYS AS (
            COALESCE(lote, '')
        ) STORED,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /* Clave compuesta para validar producto y sucursal
       al relacionar movimientos del inventario. */
    CONSTRAINT uk_inventario_id_sucursal_producto
        UNIQUE (id, sucursal_id, producto_id),

    /* Clave compuesta para validar el producto asociado
       a un registro de inventario desde el detalle de venta. */
    CONSTRAINT uk_inventario_id_producto
        UNIQUE (id, producto_id),

    /*
     * Relaciones.
     */
    CONSTRAINT fk_inventario_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Evita duplicar el inventario del mismo
     * producto, sucursal y lote.
     */
    CONSTRAINT uk_inventario_sucursal_producto_lote
        UNIQUE (sucursal_id, producto_id, lote_clave),

    /*
     * Validaciones de existencias.
     */
    CONSTRAINT chk_inventario_existencia
        CHECK (existencia >= 0),

    CONSTRAINT chk_inventario_reservada
        CHECK (
            existencia_reservada >= 0
            AND existencia_reservada <= existencia
        ),

    CONSTRAINT chk_inventario_minima
        CHECK (existencia_minima >= 0),

    CONSTRAINT chk_inventario_maxima
        CHECK (
            existencia_maxima IS NULL
            OR existencia_maxima >= existencia_minima
        ),

    /*
     * Validaciones de costos.
     */
    CONSTRAINT chk_inventario_costo
        CHECK (
            ultimo_costo >= 0
            AND costo_promedio >= 0
        ),

    /*
     * Un registro sin lote no puede tener
     * fecha de caducidad.
     *
     * Si existe un lote, no puede estar vacío.
     */
    CONSTRAINT chk_inventario_lote_caducidad
        CHECK (
            (lote IS NULL AND fecha_caducidad IS NULL)
            OR
            (
                lote IS NOT NULL
                AND CHAR_LENGTH(TRIM(lote)) > 0
            )
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Consultar inventario de un producto.
 */
CREATE INDEX idx_inventario_producto
ON inventario(producto_id);


/*
 * Consultar productos de una sucursal.
 */
CREATE INDEX idx_inventario_sucursal
ON inventario(sucursal_id);


/*
 * Consultar inventario activo por sucursal.
 */
CREATE INDEX idx_inventario_sucursal_activo
ON inventario(sucursal_id, activo);


/*
 * Consultar productos próximos a caducar.
 */
CREATE INDEX idx_inventario_caducidad
ON inventario(fecha_caducidad);


/*
 * Consultar existencias por disponibilidad.
 */
CREATE INDEX idx_inventario_existencia
ON inventario(sucursal_id, existencia);
