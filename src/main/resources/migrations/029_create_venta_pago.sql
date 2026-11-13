/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */


/*
 * ============================================================
 * MIGRACION 029: PAGOS DE VENTAS Y ABONOS
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra los pagos reales realizados por los clientes.
 *
 * Una venta puede pagarse mediante:
 * - Efectivo.
 * - Tarjeta de debito.
 * - Tarjeta de credito.
 * - Transferencia bancaria.
 * - SPEI.
 * - Otras formas configuradas.
 *
 * Tambien permite combinar varias formas de pago.
 *
 * El credito pendiente NO es un pago recibido.
 * Los abonos posteriores se registran como ABONO_CREDITO.
 *
 * Solo el efectivo fisico debe generar un movimiento
 * de ingreso en la caja.
 * ============================================================
 */

CREATE TABLE ventas_pago (

    id BIGINT NOT NULL AUTO_INCREMENT,

    venta_id BIGINT NOT NULL,

    forma_pago_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    caja_id BIGINT NOT NULL,

    sesion_caja_id BIGINT NOT NULL,

    /*
     * Identificador del movimiento de caja relacionado.
     *
     * Solo se utiliza cuando el pago recibe efectivo.
     * Los pagos con tarjeta o transferencia no generan
     * movimiento de efectivo.
     */
    movimiento_caja_id BIGINT NULL,

    tipo_pago ENUM(
        'PAGO_VENTA',
        'ABONO_CREDITO'
    ) NOT NULL DEFAULT 'PAGO_VENTA',

    /*
     * Importe aplicado a la venta o al saldo pendiente.
     */
    monto DECIMAL(12,2) NOT NULL,

    /*
     * Cantidad entregada por el cliente.
     * Puede ser mayor que monto cuando existe cambio.
     */
    monto_recibido DECIMAL(12,2) NOT NULL,

    cambio DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Datos historicos de la forma de pago.
     * Se conservan aunque posteriormente se modifique
     * el catalogo de formas de pago.
     */
    forma_pago_clave VARCHAR(30) NOT NULL,

    forma_pago_nombre VARCHAR(100) NOT NULL,

    afecta_efectivo_caja BOOLEAN NOT NULL DEFAULT FALSE,

    referencia VARCHAR(150) NULL,

    numero_operacion VARCHAR(100) NULL,

    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observaciones VARCHAR(255) NULL,

    estado ENUM(
        'CONFIRMADO',
        'CANCELADO'
    ) NOT NULL DEFAULT 'CONFIRMADO',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Un movimiento de caja solo puede relacionarse
     * con un registro de pago.
     */
    CONSTRAINT uk_ventas_pago_movimiento
        UNIQUE (movimiento_caja_id),

    /*
     * Relaciones principales.
     */
    CONSTRAINT fk_ventas_pago_venta
        FOREIGN KEY (venta_id)
        REFERENCES ventas(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_ventas_pago_forma_pago
        FOREIGN KEY (forma_pago_id)
        REFERENCES formas_pago(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_ventas_pago_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_ventas_pago_caja
        FOREIGN KEY (caja_id)
        REFERENCES cajas(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_ventas_pago_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_ventas_pago_movimiento
        FOREIGN KEY (movimiento_caja_id)
        REFERENCES movimientos_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validacion de importes.
     */
    CONSTRAINT chk_ventas_pago_monto
        CHECK (monto > 0),

    CONSTRAINT chk_ventas_pago_recibido
        CHECK (monto_recibido > 0),

    CONSTRAINT chk_ventas_pago_cambio
        CHECK (
            cambio >= 0
            AND monto_recibido = monto + cambio
        ),

    /*
     * El cambio solo puede entregarse cuando
     * el pago se realiza con efectivo.
     */
    CONSTRAINT chk_ventas_pago_cambio_efectivo
        CHECK (
            cambio = 0
            OR afecta_efectivo_caja = TRUE
        ),

    /*
     * Todo pago en efectivo debe tener su movimiento
     * de ingreso relacionado.
     *
     * Los pagos que no son efectivo no deben tener
     * un movimiento de efectivo asociado.
     */
    CONSTRAINT chk_ventas_pago_movimiento_efectivo
        CHECK (
            (
                afecta_efectivo_caja = TRUE
                AND movimiento_caja_id IS NOT NULL
            )
            OR
            (
                afecta_efectivo_caja = FALSE
                AND movimiento_caja_id IS NULL
            )
        ),

    /*
     * Datos historicos obligatorios.
     */
    CONSTRAINT chk_ventas_pago_forma_clave
        CHECK (CHAR_LENGTH(TRIM(forma_pago_clave)) > 0),

    CONSTRAINT chk_ventas_pago_forma_nombre
        CHECK (CHAR_LENGTH(TRIM(forma_pago_nombre)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

CREATE INDEX idx_ventas_pago_venta
    ON ventas_pago(venta_id);

CREATE INDEX idx_ventas_pago_forma
    ON ventas_pago(forma_pago_id);

CREATE INDEX idx_ventas_pago_usuario
    ON ventas_pago(usuario_id);

CREATE INDEX idx_ventas_pago_caja
    ON ventas_pago(caja_id);

CREATE INDEX idx_ventas_pago_sesion
    ON ventas_pago(sesion_caja_id);

CREATE INDEX idx_ventas_pago_fecha
    ON ventas_pago(fecha_pago);

CREATE INDEX idx_ventas_pago_estado
    ON ventas_pago(estado);

CREATE INDEX idx_ventas_pago_tipo
    ON ventas_pago(tipo_pago);


/*
 * ============================================================
 * REGLAS PARA LA APLICACION JAVA
 * ============================================================
 *
 * 1. Una venta puede tener uno o varios registros de pago.
 *
 * 2. Una venta pagada unicamente en efectivo genera
 *    un movimiento de ingreso de efectivo.
 *
 * 3. Una venta pagada unicamente con tarjeta no genera
 *    ingreso de efectivo en caja.
 *
 * 4. Una venta pagada unicamente por transferencia
 *    tampoco genera ingreso de efectivo en caja.
 *
 * 5. En pagos combinados, solamente la parte en efectivo
 *    genera movimiento de ingreso de efectivo.
 *
 * 6. Los pagos con tarjeta y transferencia deben conservar
 *    su referencia o numero de operacion cuando corresponda.
 *
 * 7. ABONO_CREDITO solo se permite para ventas a credito
 *    que tengan saldo pendiente.
 *
 * 8. La suma de pagos CONFIRMADOS debe actualizar
 *    ventas.total_pagado dentro de una transaccion.
 *
 * 9. No permitir que los pagos superen el saldo pendiente,
 *    considerando el cambio como dinero entregado y no
 *    como importe aplicado a la venta.
 *
 * 10. Al cancelar un pago confirmado, registrar la
 *     compensacion correspondiente y actualizar el saldo.
 *
 * 11. No eliminar pagos confirmados.
 *
 * 12. Validar que caja y sesion correspondan a la sucursal
 *     de la venta y que la sesion siga abierta.
 *
 * 13. Al registrar efectivo:
 *     - Insertar primero el movimiento INGRESO.
 *     - Guardar su ID en movimiento_caja_id.
 *     - Insertar el pago.
 *     Todo dentro de una misma transaccion.
 *
 * 14. Para tarjeta y transferencia:
 *     - No insertar movimiento de efectivo.
 *     - Guardar movimiento_caja_id como NULL.
 *
 * 15. El corte de caja debe considerar exclusivamente
 *     los pagos que afectan el efectivo fisico.
 *
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 */
