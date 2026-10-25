/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
CREATE TABLE compras (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Sucursal donde se realiza/recibe la compra.
     */
    sucursal_id BIGINT NOT NULL,

    /*
     * Proveedor al que se realiza la compra.
     */
    proveedor_id BIGINT NOT NULL,

    /*
     * Usuario que registra la compra.
     */
    usuario_id BIGINT NOT NULL,

    /*
     * Folio interno de la compra.
     */
    folio VARCHAR(30) NOT NULL,

    /*
     * Número de factura del proveedor.
     */
    factura VARCHAR(100),

    /*
     * Referencia adicional.
     * Ejemplo:
     * número de pedido, remisión, orden de compra, etc.
     */
    referencia VARCHAR(100),

    /*
     * Fecha en que se realizó la compra.
     */
    fecha_compra DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    /*
     * Forma de la operación de compra.
     */
    tipo_compra ENUM(
        'CONTADO',
        'CREDITO'
    ) NOT NULL DEFAULT 'CONTADO',

    /*
     * Importes generales de la compra.
     */
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,

    descuento DECIMAL(12,2) NOT NULL DEFAULT 0,

    impuesto DECIMAL(12,2) NOT NULL DEFAULT 0,

    total DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Observaciones.
     */
    observaciones TEXT,

    /*
     * Estado de la compra.
     */
    estado ENUM(
        'BORRADOR',
        'CONFIRMADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'BORRADOR',

    /*
     * Auditoría.
     */
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Folio único.
     */
    CONSTRAINT uk_compra_folio
        UNIQUE (folio),

    /*
     * Relaciones.
     */
    CONSTRAINT fk_compra_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id),

    CONSTRAINT fk_compra_proveedor
        FOREIGN KEY (proveedor_id)
        REFERENCES proveedores(id),

    CONSTRAINT fk_compra_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- ÍNDICES
-- ============================================================

CREATE INDEX idx_compra_fecha
ON compras(fecha_compra);

CREATE INDEX idx_compra_proveedor
ON compras(proveedor_id);

CREATE INDEX idx_compra_sucursal
ON compras(sucursal_id);

CREATE INDEX idx_compra_usuario
ON compras(usuario_id);

CREATE INDEX idx_compra_estado
ON compras(estado);

CREATE INDEX idx_compra_tipo
ON compras(tipo_compra);