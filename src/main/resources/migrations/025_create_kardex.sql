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
 * MIGRACIÓN 025: KARDEX
 * ============================================================
 *
 * Historial de movimientos de inventario.
 *
 * Registra entradas, salidas, ajustes, compras,
 * ventas, devoluciones y traspasos.
 *
 * El kardex es un historial permanente:
 * los movimientos confirmados no deben modificarse
 * ni eliminarse desde la aplicación.
 *
 * ============================================================
 */

CREATE TABLE kardex (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    producto_id BIGINT NOT NULL,

    presentacion_id BIGINT NULL,

    inventario_id BIGINT NULL,

    usuario_id BIGINT NOT NULL,

    tipo_movimiento ENUM(
        'INVENTARIO_INICIAL',
        'COMPRA',
        'VENTA',
        'DEVOLUCION_CLIENTE',
        'DEVOLUCION_PROVEEDOR',
        'AJUSTE_ENTRADA',
        'AJUSTE_SALIDA',
        'TRASPASO_ENTRADA',
        'TRASPASO_SALIDA',
        'CANCELACION_COMPRA',
        'CANCELACION_VENTA'
    ) NOT NULL,

    /*
     * Identificador del documento relacionado.
     * Su interpretación depende del tipo de movimiento.
     */
    referencia_id BIGINT NULL,

    /*
     * Folio o referencia legible del documento.
     */
    referencia VARCHAR(100) NULL,

    lote VARCHAR(100) NULL,

    fecha_caducidad DATE NULL,

    entrada DECIMAL(15,4) NOT NULL DEFAULT 0,

    salida DECIMAL(15,4) NOT NULL DEFAULT 0,

    existencia_anterior DECIMAL(15,4) NOT NULL,

    existencia_actual DECIMAL(15,4) NOT NULL,

    costo_unitario DECIMAL(15,4) NOT NULL DEFAULT 0,

    costo_total DECIMAL(15,2) NOT NULL DEFAULT 0,

    observaciones VARCHAR(255) NULL,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Relaciones históricas.
     *
     * RESTRICT evita borrar registros relacionados
     * y perder la trazabilidad del movimiento.
     */
    CONSTRAINT fk_kardex_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_kardex_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_kardex_presentacion
        FOREIGN KEY (presentacion_id)
        REFERENCES presentaciones(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_kardex_inventario
        FOREIGN KEY (inventario_id)
        REFERENCES inventario(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_kardex_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Cantidades positivas.
     */
    CONSTRAINT chk_kardex_entrada
        CHECK (entrada >= 0),

    CONSTRAINT chk_kardex_salida
        CHECK (salida >= 0),

    CONSTRAINT chk_kardex_existencia_anterior
        CHECK (existencia_anterior >= 0),

    CONSTRAINT chk_kardex_existencia_actual
        CHECK (existencia_actual >= 0),

    /*
     * Validación de costos.
     */
    CONSTRAINT chk_kardex_costo
        CHECK (costo_unitario >= 0),

    CONSTRAINT chk_kardex_costo_total
        CHECK (costo_total >= 0),

    /*
     * Cada movimiento debe ser exclusivamente
     * una entrada o una salida.
     */
    CONSTRAINT chk_kardex_entrada_salida
        CHECK (
            (entrada > 0 AND salida = 0)
            OR
            (salida > 0 AND entrada = 0)
        ),

    /*
     * La existencia debe corresponder
     * al movimiento registrado.
     */
    CONSTRAINT chk_kardex_calculo_existencia
        CHECK (
            existencia_actual =
            existencia_anterior + entrada - salida
        ),

    /*
     * Si se registra caducidad, debe existir lote.
     */
    CONSTRAINT chk_kardex_lote_caducidad
        CHECK (
            fecha_caducidad IS NULL
            OR (
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
 * Consultar movimientos de un producto.
 */
CREATE INDEX idx_kardex_producto_fecha
ON kardex(producto_id, created_at);


/*
 * Consultar movimientos de una sucursal.
 */
CREATE INDEX idx_kardex_sucursal_fecha
ON kardex(sucursal_id, created_at);


/*
 * Consultar por presentación.
 */
CREATE INDEX idx_kardex_presentacion
ON kardex(presentacion_id);


/*
 * Consultar por registro de inventario.
 */
CREATE INDEX idx_kardex_inventario_fecha
ON kardex(inventario_id, created_at);


/*
 * Consultar movimientos realizados por usuario.
 */
CREATE INDEX idx_kardex_usuario_fecha
ON kardex(usuario_id, created_at);


/*
 * Filtrar por tipo de movimiento.
 */
CREATE INDEX idx_kardex_tipo_fecha
ON kardex(tipo_movimiento, created_at);


/*
 * Consultar documentos relacionados.
 */
CREATE INDEX idx_kardex_referencia
ON kardex(referencia_id);


/*
 * Consultar movimientos por lote.
 */
CREATE INDEX idx_kardex_lote
ON kardex(lote);
