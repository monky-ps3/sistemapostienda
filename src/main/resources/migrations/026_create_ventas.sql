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
 * MIGRACION 026: VENTAS
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra las ventas realizadas en cada sucursal.
 *
 * Compatible con:
 * - Ventas de contado.
 * - Ventas a credito.
 * - Pagos en efectivo.
 * - Pagos con tarjeta.
 * - Pagos por transferencia y SPEI.
 * - Pagos combinados.
 * - Cancelaciones con historial.
 *
 * Los pagos individuales se registran en ventas_pago.
 * Esta tabla conserva el encabezado y los importes
 * generales de cada venta.
 * ============================================================
 */

CREATE TABLE ventas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    caja_id BIGINT NOT NULL,

    sesion_caja_id BIGINT NOT NULL,

    cliente_id BIGINT NULL,

    usuario_id BIGINT NOT NULL,

    lista_precio_id BIGINT NULL,

    /*
     * Folio unico dentro de cada sucursal.
     */
    folio VARCHAR(30) NOT NULL,

    fecha_venta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    fecha_confirmacion DATETIME NULL,

    fecha_cancelacion DATETIME NULL,

    cancelada_por_usuario_id BIGINT NULL,

    motivo_cancelacion VARCHAR(255) NULL,

    tipo_venta ENUM(
        'CONTADO',
        'CREDITO'
    ) NOT NULL DEFAULT 'CONTADO',

    subtotal DECIMAL(14,2) NOT NULL DEFAULT 0,

    descuento DECIMAL(14,2) NOT NULL DEFAULT 0,

    impuesto DECIMAL(14,2) NOT NULL DEFAULT 0,

    total DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Importe aplicado a la venta mediante pagos confirmados.
     *
     * No incluye el cambio entregado al cliente.
     * No representa necesariamente efectivo recibido.
     */
    total_pagado DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Cambio total entregado al cliente.
     * El detalle por forma de pago se conserva en ventas_pago.
     */
    cambio DECIMAL(14,2) NOT NULL DEFAULT 0,

    estado ENUM(
        'BORRADOR',
        'CONFIRMADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'BORRADOR',

    observaciones TEXT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Un folio puede repetirse en otra sucursal,
     * pero no dentro de la misma sucursal.
     */
    CONSTRAINT uk_venta_sucursal_folio
        UNIQUE (sucursal_id, folio),

    /*
     * Relaciones principales.
     */
    CONSTRAINT fk_venta_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_caja
        FOREIGN KEY (caja_id)
        REFERENCES cajas(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_lista_precio
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_venta_cancelada_usuario
        FOREIGN KEY (cancelada_por_usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validacion de importes.
     */
    CONSTRAINT chk_venta_importes
        CHECK (
            subtotal >= 0
            AND descuento >= 0
            AND impuesto >= 0
            AND total >= 0
            AND total_pagado >= 0
            AND cambio >= 0
        ),

    /*
     * El descuento no puede superar el subtotal.
     */
    CONSTRAINT chk_venta_descuento
        CHECK (descuento <= subtotal),

    /*
     * El total debe coincidir con el calculo de la venta.
     */
    CONSTRAINT chk_venta_total_calculo
        CHECK (
            total = subtotal - descuento + impuesto
        ),

    /*
     * El importe pagado no puede superar el total.
     */
    CONSTRAINT chk_venta_total_pagado
        CHECK (total_pagado <= total),

    /*
     * Las ventas a credito necesitan un cliente identificado.
     */
    CONSTRAINT chk_venta_cliente_credito
        CHECK (
            tipo_venta = 'CONTADO'
            OR cliente_id IS NOT NULL
        ),

    /*
     * Una venta de contado confirmada debe estar
     * completamente pagada.
     *
     * El pago puede ser efectivo, tarjeta, transferencia
     * o una combinacion de varias formas.
     */
    CONSTRAINT chk_venta_contado_pagado
        CHECK (
            estado <> 'CONFIRMADA'
            OR tipo_venta <> 'CONTADO'
            OR total_pagado = total
        ),

    /*
     * Coherencia entre estado y fechas.
     *
     * BORRADOR:
     * No tiene fecha de confirmacion ni cancelacion.
     *
     * CONFIRMADA:
     * Tiene fecha de confirmacion y no esta cancelada.
     *
     * CANCELADA:
     * Tiene fecha de cancelacion, usuario y motivo.
     * Puede haberse cancelado antes o despues de confirmar.
     */
    CONSTRAINT chk_venta_estado_fechas
        CHECK (
            (
                estado = 'BORRADOR'
                AND fecha_confirmacion IS NULL
                AND fecha_cancelacion IS NULL
                AND cancelada_por_usuario_id IS NULL
                AND motivo_cancelacion IS NULL
            )
            OR
            (
                estado = 'CONFIRMADA'
                AND fecha_confirmacion IS NOT NULL
                AND fecha_cancelacion IS NULL
                AND cancelada_por_usuario_id IS NULL
                AND motivo_cancelacion IS NULL
            )
            OR
            (
                estado = 'CANCELADA'
                AND fecha_cancelacion IS NOT NULL
                AND cancelada_por_usuario_id IS NOT NULL
                AND motivo_cancelacion IS NOT NULL
                AND CHAR_LENGTH(TRIM(motivo_cancelacion)) > 0
            )
        ),

    /*
     * La fecha de confirmacion no puede ser anterior
     * a la fecha de creacion de la venta.
     */
    CONSTRAINT chk_venta_fecha_confirmacion
        CHECK (
            fecha_confirmacion IS NULL
            OR fecha_confirmacion >= fecha_venta
        ),

    /*
     * La cancelacion no puede ser anterior
     * a la fecha de creacion de la venta.
     */
    CONSTRAINT chk_venta_fecha_cancelacion
        CHECK (
            fecha_cancelacion IS NULL
            OR fecha_cancelacion >= fecha_venta
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar ventas por sucursal y fecha.
 */
CREATE INDEX idx_venta_sucursal_fecha
    ON ventas(sucursal_id, fecha_venta);

/*
 * Consultar ventas de un cliente.
 */
CREATE INDEX idx_venta_cliente_fecha
    ON ventas(cliente_id, fecha_venta);

/*
 * Consultar ventas realizadas por usuario.
 */
CREATE INDEX idx_venta_usuario_fecha
    ON ventas(usuario_id, fecha_venta);

/*
 * Consultar ventas por caja y fecha.
 */
CREATE INDEX idx_venta_caja_fecha
    ON ventas(caja_id, fecha_venta);

/*
 * Consultar ventas por sesion y estado.
 */
CREATE INDEX idx_venta_sesion_estado
    ON ventas(sesion_caja_id, estado);

/*
 * Consultar ventas por estado y fecha.
 */
CREATE INDEX idx_venta_estado_fecha
    ON ventas(estado, fecha_venta);

/*
 * Consultar ventas por tipo y fecha.
 */
CREATE INDEX idx_venta_tipo_fecha
    ON ventas(tipo_venta, fecha_venta);

/*
 * Consultar ventas por lista de precios.
 */
CREATE INDEX idx_venta_lista_precio
    ON ventas(lista_precio_id);


/*
 * ============================================================
 * REGLAS PARA LA APLICACION JAVA
 * ============================================================
 *
 * 1. Toda venta nueva inicia como BORRADOR.
 *
 * 2. No actualizar inventario ni kardex mientras
 *    la venta permanezca como BORRADOR.
 *
 * 3. Al confirmar una venta, realizar dentro de una
 *    sola transaccion:
 *    - Validar productos y existencias.
 *    - Validar caja y sesion abierta.
 *    - Validar sucursal de la caja y sesion.
 *    - Calcular importes.
 *    - Registrar pagos en ventas_pago.
 *    - Registrar movimientos de efectivo cuando aplique.
 *    - Actualizar inventario.
 *    - Registrar kardex.
 *    - Actualizar total_pagado.
 *    - Cambiar estado a CONFIRMADA.
 *    - Registrar fecha_confirmacion.
 *
 * 4. Si cualquier operacion falla, realizar ROLLBACK.
 *
 * 5. Una venta CONTADO confirmada debe quedar pagada
 *    completamente, independientemente de la forma de pago.
 *
 * 6. Una venta CREDITO puede conservar saldo pendiente.
 *
 * 7. Las ventas a credito requieren cliente registrado
 *    y validacion de sus condiciones de credito.
 *
 * 8. Los abonos posteriores se registran en ventas_pago
 *    como ABONO_CREDITO.
 *
 * 9. No sumar pagos con tarjeta o transferencia
 *    al efectivo fisico de caja.
 *
 * 10. Para cancelar una venta confirmada:
 *     - Registrar usuario, fecha y motivo.
 *     - Revertir existencias.
 *     - Registrar movimientos de kardex compensatorios.
 *     - Resolver la devolucion o cancelacion de pagos.
 *     - Registrar salidas de efectivo cuando corresponda.
 *     - Actualizar el estado a CANCELADA.
 *     Todo dentro de una transaccion.
 *
 * 11. No eliminar ventas confirmadas ni canceladas.
 *
 * 12. Validar que la caja y la sesion pertenezcan
 *     a la sucursal indicada en la venta.
 *
 * 13. No permitir ventas en sesiones cerradas.
 *
 * 14. Actualizar total_pagado utilizando exclusivamente
 *     pagos CONFIRMADOS, sin contar pagos CANCELADOS.
 *
 * 15. El importe total_pagado representa el importe
 *     aplicado a la deuda, no el efectivo entregado.
 *
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 */
