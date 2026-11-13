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
 * MIGRACION 010: MOVIMIENTOS DE CAJA
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra todos los movimientos de efectivo de una
 * sesion de caja.
 *
 * Incluye:
 * - Apertura de caja.
 * - Ingresos por ventas en efectivo.
 * - Ingresos por abonos de credito en efectivo.
 * - Entradas manuales.
 * - Salidas y retiros.
 * - Devoluciones de efectivo.
 * - Ajustes positivos y negativos.
 *
 * IMPORTANTE:
 * Los pagos con tarjeta y transferencia no generan
 * movimientos de efectivo.
 *
 * ============================================================
 */

CREATE TABLE movimientos_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sesion_caja_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    tipo ENUM(
        'APERTURA',
        'INGRESO',
        'SALIDA',
        'RETIRO',
        'DEVOLUCION',
        'AJUSTE_POSITIVO',
        'AJUSTE_NEGATIVO'
    ) NOT NULL,

    concepto VARCHAR(200) NOT NULL,

    monto DECIMAL(14,2) NOT NULL,

    /*
     * Identifica el registro que origino el movimiento.
     *
     * Ejemplos:
     * VENTA_PAGO
     * ABONO_CREDITO
     * DEVOLUCION_PAGO
     * APERTURA
     * RETIRO
     * AJUSTE
     * OTRO
     *
     * Se utiliza una referencia generica porque
     * los movimientos pueden tener diferentes origenes.
     */
    referencia_tipo VARCHAR(30) NULL,

    referencia_id BIGINT NULL,

    observaciones VARCHAR(255) NULL,

    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /* Clave compuesta para relacionar un pago con un movimiento
       perteneciente exactamente a su misma sesión de caja. */
    CONSTRAINT uk_movimiento_id_sesion
        UNIQUE (id, sesion_caja_id),

    /*
     * Una misma referencia no puede generar dos
     * movimientos con el mismo tipo de origen.
     *
     * MySQL permite multiples valores NULL en UNIQUE,
     * por lo que los movimientos manuales pueden
     * permanecer sin referencia.
     */
    CONSTRAINT uk_movimiento_referencia
        UNIQUE (referencia_tipo, referencia_id),

    /*
     * El concepto no puede estar vacio.
     */
    CONSTRAINT chk_movimiento_concepto
        CHECK (
            CHAR_LENGTH(TRIM(concepto)) > 0
        ),

    /*
     * El monto siempre se registra como positivo.
     * El tipo determina si suma o resta efectivo.
     */
    CONSTRAINT chk_movimiento_monto
        CHECK (monto > 0),

    /*
     * La referencia debe estar completa o no existir.
     */
    CONSTRAINT chk_movimiento_referencia
        CHECK (
            (
                referencia_tipo IS NULL
                AND referencia_id IS NULL
            )
            OR
            (
                referencia_tipo IS NOT NULL
                AND referencia_id IS NOT NULL
                AND CHAR_LENGTH(TRIM(referencia_tipo)) > 0
                AND referencia_id > 0
            )
        ),

    /*
     * Relacion con la sesion de caja.
     */
    CONSTRAINT fk_movimiento_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Conservar el historial aunque se desactive
     * un usuario.
     */
    CONSTRAINT fk_movimiento_usuario
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
 * Consultar movimientos por fecha.
 */
CREATE INDEX idx_movimiento_fecha
    ON movimientos_caja(fecha);

/*
 * Consultar movimientos de una sesion.
 */
CREATE INDEX idx_movimiento_sesion_fecha
    ON movimientos_caja(sesion_caja_id, fecha);

/*
 * Consultar movimientos realizados por usuario.
 */
CREATE INDEX idx_movimiento_usuario_fecha
    ON movimientos_caja(usuario_id, fecha);

/*
 * Consultar movimientos por tipo.
 */
CREATE INDEX idx_movimiento_tipo_fecha
    ON movimientos_caja(tipo, fecha);


/*
 * ============================================================
 * REGLAS PARA LA APLICACION JAVA
 * ============================================================
 *
 * 1. El monto siempre se almacena como valor positivo.
 *
 * 2. Aumentan el efectivo:
 *    - APERTURA
 *    - INGRESO
 *    - AJUSTE_POSITIVO
 *
 * 3. Disminuyen el efectivo:
 *    - SALIDA
 *    - RETIRO
 *    - DEVOLUCION
 *    - AJUSTE_NEGATIVO
 *
 * 4. No permitir movimientos en sesiones cerradas.
 *
 * 5. Validar que el usuario tenga permisos para
 *    registrar el movimiento.
 *
 * 6. No permitir modificar ni eliminar movimientos
 *    confirmados.
 *
 * 7. Las correcciones deben registrarse mediante
 *    movimientos compensatorios.
 *
 * 8. Los pagos con tarjeta, transferencia, SPEI
 *    u otras formas no efectivas no deben generar
 *    movimientos de efectivo.
 *
 * 9. Los pagos en efectivo de ventas deben generar
 *    un movimiento INGRESO.
 *
 * 10. Los abonos en efectivo de credito deben generar
 *     un movimiento INGRESO.
 *
 * 11. Las devoluciones de efectivo deben generar
 *     un movimiento DEVOLUCION.
 *
 * 12. Los movimientos automaticos deben relacionarse
 *     con su origen mediante referencia_tipo
 *     y referencia_id.
 *
 * 13. Para pagos en efectivo relacionados con ventas_pago:
 *
 *     a) Iniciar una transaccion.
 *     b) Insertar el movimiento de caja.
 *     c) Insertar el registro en ventas_pago,
 *        utilizando el ID del movimiento.
 *     d) Actualizar referencia_tipo con VENTA_PAGO
 *        o ABONO_CREDITO y referencia_id con el ID
 *        del registro de ventas_pago.
 *     e) Confirmar la transaccion.
 *
 *     Si cualquier paso falla, realizar ROLLBACK.
 *
 * 14. La referencia generica no tiene llave foranea
 *     porque puede apuntar a diferentes tablas.
 *     La aplicacion debe validar el origen.
 *
 * 15. El fondo inicial debe contabilizarse una sola vez.
 *
 *     Se recomienda utilizar fondo_inicial de
 *     sesiones_caja como fuente del fondo inicial.
 *
 *     Si se registra tambien un movimiento APERTURA,
 *     no volver a sumar ese movimiento al calcular
 *     el efectivo esperado.
 *
 * 16. El corte de caja debe considerar unicamente
 *     movimientos de efectivo y pagos efectivos.
 *
 * 17. Registrar las operaciones importantes en bitacora.
 *
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 */
