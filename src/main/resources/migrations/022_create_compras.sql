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
 * MIGRACIÓN 022: COMPRAS
 * ============================================================
 *
 * Registro de compras realizadas a proveedores.
 *
 * Una compra pertenece a una sucursal, un proveedor
 * y un usuario responsable.
 *
 * Estados:
 * BORRADOR    = En captura, todavía no afecta inventario.
 * CONFIRMADA  = Compra registrada y recibida.
 * CANCELADA   = Compra cancelada.
 *
 * ============================================================
 */

CREATE TABLE compras (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    proveedor_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    folio VARCHAR(30) NOT NULL,

    factura VARCHAR(100) NULL,

    referencia VARCHAR(100) NULL,

    fecha_compra DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    tipo_compra ENUM(
        'CONTADO',
        'CREDITO'
    ) NOT NULL DEFAULT 'CONTADO',

    subtotal DECIMAL(14,2) NOT NULL DEFAULT 0,

    descuento DECIMAL(14,2) NOT NULL DEFAULT 0,

    impuesto DECIMAL(14,2) NOT NULL DEFAULT 0,

    total DECIMAL(14,2) NOT NULL DEFAULT 0,

    observaciones TEXT NULL,

    estado ENUM(
        'BORRADOR',
        'CONFIRMADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'BORRADOR',

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_compra_folio
        UNIQUE (folio),

    CONSTRAINT fk_compra_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_compra_proveedor
        FOREIGN KEY (proveedor_id)
        REFERENCES proveedores(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_compra_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT chk_compra_folio
        CHECK (CHAR_LENGTH(TRIM(folio)) > 0),

    CONSTRAINT chk_compra_subtotal
        CHECK (subtotal >= 0),

    CONSTRAINT chk_compra_descuento
        CHECK (descuento >= 0),

    CONSTRAINT chk_compra_impuesto
        CHECK (impuesto >= 0),

    CONSTRAINT chk_compra_total
        CHECK (total >= 0),

    CONSTRAINT chk_compra_descuento_subtotal
        CHECK (descuento <= subtotal),

    CONSTRAINT chk_compra_total_calculo
        CHECK (
            total = subtotal - descuento + impuesto
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_compra_sucursal_fecha
ON compras(sucursal_id, fecha_compra);

CREATE INDEX idx_compra_proveedor_fecha
ON compras(proveedor_id, fecha_compra);

CREATE INDEX idx_compra_usuario_fecha
ON compras(usuario_id, fecha_compra);

CREATE INDEX idx_compra_estado_fecha
ON compras(estado, fecha_compra);

CREATE INDEX idx_compra_tipo
ON compras(tipo_compra);
