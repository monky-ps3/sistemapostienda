/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 6 jul 2026
 */


/*
 * ============================================================
 * MIGRACIÓN 028: FORMAS DE PAGO
 * ============================================================
 *
 * Catálogo configurable de formas de pago.
 *
 * Permite combinar diferentes formas de pago
 * en una misma venta.
 *
 * Ejemplo:
 * Venta de $1,000:
 * - $300 efectivo.
 * - $400 tarjeta.
 * - $300 transferencia.
 *
 * IMPORTANTE:
 * El crédito del cliente no es una forma de pago.
 * Representa un saldo pendiente que se cobrará
 * posteriormente mediante un abono.
 *
 * ============================================================
 */

CREATE TABLE formas_pago (

    id BIGINT NOT NULL AUTO_INCREMENT,

    clave VARCHAR(30) NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    descripcion VARCHAR(255) NULL,

    /*
     * Clasificación contable y operativa.
     *
     * EFECTIVO: dinero físico que ingresa a la caja.
     * NO_EFECTIVO: tarjeta, transferencia, cheque, etc.
     * OTRO: formas especiales configurables.
     */
    naturaleza ENUM(
        'EFECTIVO',
        'NO_EFECTIVO',
        'OTRO'
    ) NOT NULL DEFAULT 'NO_EFECTIVO',

    requiere_referencia BOOLEAN NOT NULL DEFAULT FALSE,

    permite_cambio BOOLEAN NOT NULL DEFAULT FALSE,

    permite_pago_parcial BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Indica si el importe recibido debe sumarse
     * al efectivo físico esperado de la caja.
     *
     * Normalmente solo EFECTIVO tendrá TRUE.
     */
    afecta_efectivo_caja BOOLEAN NOT NULL DEFAULT FALSE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    orden INT NOT NULL DEFAULT 1,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_formas_pago_clave
        UNIQUE (clave),

    CONSTRAINT uk_formas_pago_nombre
        UNIQUE (nombre),

    CONSTRAINT chk_formas_pago_clave
        CHECK (CHAR_LENGTH(TRIM(clave)) > 0),

    CONSTRAINT chk_formas_pago_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_formas_pago_orden
        CHECK (orden > 0),

    /*
     * Solo una forma de pago en efectivo
     * puede afectar el efectivo físico de la caja.
     */
    CONSTRAINT chk_formas_pago_efectivo
        CHECK (
            afecta_efectivo_caja = FALSE
            OR naturaleza = 'EFECTIVO'
        ),

    /*
     * El efectivo permite cambio.
     * Las formas de pago no efectivo no deben
     * generar cambio físico desde la caja.
     */
    CONSTRAINT chk_formas_pago_cambio
        CHECK (
            permite_cambio = FALSE
            OR naturaleza = 'EFECTIVO'
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_formas_pago_activo_orden
ON formas_pago(activo, orden);

CREATE INDEX idx_formas_pago_naturaleza
ON formas_pago(naturaleza);


/*
 * ============================================================
 * FORMAS DE PAGO INICIALES
 * ============================================================
 *
 * Este catálogo es una configuración inicial.
 * Se podrán agregar nuevas formas de pago desde
 * el sistema sin modificar la estructura de la tabla.
 *
 * ============================================================
 */

INSERT INTO formas_pago (
    clave,
    nombre,
    descripcion,
    naturaleza,
    requiere_referencia,
    permite_cambio,
    permite_pago_parcial,
    afecta_efectivo_caja,
    activo,
    orden
)
VALUES

(
    'EFECTIVO',
    'Efectivo',
    'Pago recibido en efectivo',
    'EFECTIVO',
    FALSE,
    TRUE,
    TRUE,
    TRUE,
    TRUE,
    1
),

(
    'TARJETA_DEBITO',
    'Tarjeta de Débito',
    'Pago mediante tarjeta bancaria de débito',
    'NO_EFECTIVO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    2
),

(
    'TARJETA_CREDITO',
    'Tarjeta de Crédito',
    'Pago mediante tarjeta bancaria de crédito',
    'NO_EFECTIVO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    3
),

(
    'TRANSFERENCIA',
    'Transferencia Bancaria',
    'Pago mediante transferencia bancaria',
    'NO_EFECTIVO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    4
),

(
    'SPEI',
    'SPEI',
    'Pago mediante transferencia SPEI',
    'NO_EFECTIVO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    5
),

(
    'CHEQUE',
    'Cheque',
    'Pago recibido mediante cheque',
    'NO_EFECTIVO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    6
),

(
    'VALE',
    'Vale',
    'Pago mediante vales autorizados',
    'OTRO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    7
),

(
    'MONEDERO',
    'Monedero Electrónico',
    'Pago mediante saldo disponible en monedero',
    'OTRO',
    FALSE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    8
),

(
    'OTRO',
    'Otro',
    'Otra forma de pago configurable',
    'OTRO',
    TRUE,
    FALSE,
    TRUE,
    FALSE,
    TRUE,
    9
);
