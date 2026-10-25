/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 6 jul 2026
 */

CREATE TABLE formas_pago (

    id BIGINT NOT NULL AUTO_INCREMENT,

    clave VARCHAR(30) NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    descripcion VARCHAR(255) NULL,

    requiere_referencia BOOLEAN NOT NULL DEFAULT FALSE,

    permite_cambio BOOLEAN NOT NULL DEFAULT FALSE,

    permite_pago_parcial BOOLEAN NOT NULL DEFAULT TRUE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    orden INT NOT NULL DEFAULT 1,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_formas_pago_clave
        UNIQUE (clave),

    CONSTRAINT uk_formas_pago_nombre
        UNIQUE (nombre)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_formas_pago_activo
    ON formas_pago(activo);

CREATE INDEX idx_formas_pago_orden
    ON formas_pago(orden);


INSERT INTO formas_pago
(
    clave,
    nombre,
    descripcion,
    requiere_referencia,
    permite_cambio,
    permite_pago_parcial,
    activo,
    orden
)
VALUES

(
    'EFECTIVO',
    'Efectivo',
    'Pago realizado en efectivo',
    FALSE,
    TRUE,
    TRUE,
    TRUE,
    1
),

(
    'TARJETA_DEBITO',
    'Tarjeta de Débito',
    'Pago realizado mediante tarjeta de débito',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    2
),

(
    'TARJETA_CREDITO',
    'Tarjeta de Crédito',
    'Pago realizado mediante tarjeta de crédito',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    3
),

(
    'TRANSFERENCIA',
    'Transferencia',
    'Pago mediante transferencia bancaria',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    4
),

(
    'SPEI',
    'SPEI',
    'Pago mediante transferencia SPEI',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    5
),

(
    'CHEQUE',
    'Cheque',
    'Pago mediante cheque',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    6
),

(
    'VALE',
    'Vale',
    'Pago mediante vales',
    FALSE,
    FALSE,
    TRUE,
    TRUE,
    7
),

(
    'MONEDERO',
    'Monedero Electrónico',
    'Pago mediante saldo de monedero electrónico',
    FALSE,
    FALSE,
    TRUE,
    TRUE,
    8
),

(
    'CREDITO',
    'Crédito',
    'Venta registrada a crédito para el cliente',
    FALSE,
    FALSE,
    TRUE,
    TRUE,
    9
),

(
    'OTRO',
    'Otro',
    'Otra forma de pago',
    TRUE,
    FALSE,
    TRUE,
    TRUE,
    10
);