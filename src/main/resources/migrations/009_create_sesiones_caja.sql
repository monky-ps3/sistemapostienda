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
 * MIGRACION 009: SESIONES DE CAJA
 * SistemaPOSTienda
 * ============================================================
 *
 * Cada sesion representa una apertura y cierre de una caja.
 *
 * Una caja solamente puede tener una sesion ABIERTA
 * al mismo tiempo.
 * ============================================================
 */

CREATE TABLE sesiones_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    folio VARCHAR(30) NOT NULL,

    caja_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    fecha_apertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    fecha_cierre TIMESTAMP NULL,

    fondo_inicial DECIMAL(12,2) NOT NULL,

    turno ENUM(
        'MATUTINO',
        'VESPERTINO',
        'NOCTURNO',
        'PERSONALIZADO'
    ) NOT NULL DEFAULT 'PERSONALIZADO',

    estado ENUM(
        'ABIERTA',
        'CERRADA'
    ) NOT NULL DEFAULT 'ABIERTA',

    observaciones VARCHAR(255) NULL,

    /*
     * Columna calculada:
     *
     * Si la sesion esta abierta, almacena el ID de la caja.
     * Si esta cerrada, almacena NULL.
     *
     * MySQL permite varios valores NULL en un indice UNIQUE,
     * pero no permite repetir el mismo ID de caja.
     */
    caja_abierta_id BIGINT
        GENERATED ALWAYS AS (
            CASE
                WHEN estado = 'ABIERTA' THEN caja_id
                ELSE NULL
            END
        ) STORED,

    PRIMARY KEY (id),

    CONSTRAINT uk_sesion_folio
        UNIQUE (folio),

    /*
     * Una sola sesion abierta por caja.
     */
    CONSTRAINT uk_sesion_caja_abierta
        UNIQUE (caja_abierta_id),

    /*
     * El folio no puede estar vacio.
     */
    CONSTRAINT chk_sesion_folio
        CHECK (CHAR_LENGTH(TRIM(folio)) > 0),

    /*
     * El fondo inicial no puede ser negativo.
     */
    CONSTRAINT chk_sesion_fondo
        CHECK (fondo_inicial >= 0),

    /*
     * La fecha de cierre debe ser posterior o igual
     * a la fecha de apertura.
     */
    CONSTRAINT chk_sesion_fechas
        CHECK (
            fecha_cierre IS NULL
            OR fecha_cierre >= fecha_apertura
        ),

    /*
     * Coherencia entre estado y fecha de cierre.
     */
    CONSTRAINT chk_sesion_estado_fecha
        CHECK (
            (estado = 'ABIERTA' AND fecha_cierre IS NULL)
            OR
            (estado = 'CERRADA' AND fecha_cierre IS NOT NULL)
        ),

    /*
     * No permitir eliminar una caja con sesiones.
     */
    CONSTRAINT fk_sesion_caja
        FOREIGN KEY (caja_id)
        REFERENCES cajas(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * No permitir eliminar usuarios que tengan sesiones.
     * Las cuentas deben desactivarse en lugar de eliminarse.
     */
    CONSTRAINT fk_sesion_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar sesiones por estado.
 */
CREATE INDEX idx_sesion_estado
    ON sesiones_caja(estado);


/*
 * Consultar sesiones de un usuario por fecha.
 */
CREATE INDEX idx_sesion_usuario_fecha
    ON sesiones_caja(usuario_id, fecha_apertura);


/*
 * Consultar sesiones de una caja por fecha.
 */
CREATE INDEX idx_sesion_caja_fecha
    ON sesiones_caja(caja_id, fecha_apertura);


/*
 * Consultar sesiones por fecha de apertura.
 */
CREATE INDEX idx_sesion_fecha_apertura
    ON sesiones_caja(fecha_apertura);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION JAVA:
 *
 * 1. Verificar que la caja este activa antes de abrirla.
 * 2. No permitir dos sesiones abiertas para la misma caja.
 * 3. Registrar el fondo inicial al abrir la sesion.
 * 4. Al cerrar, actualizar estado y fecha_cierre
 *    dentro de una misma transaccion.
 * 5. No modificar el fondo inicial despues de la apertura.
 * 6. Registrar aperturas y cierres en la bitacora.
 * 7. No eliminar sesiones de caja.
 * ============================================================
 */
