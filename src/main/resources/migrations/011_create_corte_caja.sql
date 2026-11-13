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
 * MIGRACION 011: CORTE DE CAJA
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra el arqueo final de una sesion de caja.
 *
 * Distingue entre:
 * - Total de ventas.
 * - Cobros en efectivo.
 * - Cobros no efectivos.
 * - Otros ingresos.
 * - Retiros y salidas.
 * - Devoluciones.
 * - Ajustes positivos y negativos.
 *
 * El efectivo esperado se calcula considerando
 * exclusivamente movimientos que afectan el dinero fisico.
 *
 * ============================================================
 */

CREATE TABLE corte_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sesion_caja_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    /*
     * Total de ventas confirmadas de la sesion.
     * Incluye ventas pagadas en efectivo, tarjeta,
     * transferencia y pagos combinados.
     *
     * Es un dato informativo y NO se suma directamente
     * al efectivo esperado.
     */
    ventas_total DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Cobros aplicados mediante efectivo.
     *
     * Incluye pagos de ventas y abonos de credito
     * realizados en efectivo.
     */
    cobros_efectivo DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Cobros realizados mediante tarjeta, transferencia,
     * SPEI y otras formas que no representan efectivo fisico.
     *
     * Es informativo y NO se suma al efectivo esperado.
     */
    cobros_no_efectivo DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Entradas manuales y otros ingresos de efectivo.
     *
     * No deben incluir pagos de ventas ni abonos
     * que ya esten contabilizados en cobros_efectivo.
     */
    otros_ingresos DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Salidas de efectivo y retiros de caja.
     */
    salidas_efectivo DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Devoluciones de dinero realizadas en efectivo.
     */
    devoluciones_efectivo DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Ajustes que aumentan el efectivo.
     */
    ajustes_positivos DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Ajustes que disminuyen el efectivo.
     */
    ajustes_negativos DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Fondo inicial tomado de sesiones_caja.
     *
     * Se conserva como fotografia historica del corte.
     */
    fondo_inicial DECIMAL(14,2) NOT NULL DEFAULT 0,

    /*
     * Efectivo que deberia existir fisicamente.
     *
     * Formula:
     *
     * fondo_inicial
     * + cobros_efectivo
     * + otros_ingresos
     * + ajustes_positivos
     * - salidas_efectivo
     * - devoluciones_efectivo
     * - ajustes_negativos
     *
     * Los cobros no efectivos no participan.
     */
    efectivo_esperado DECIMAL(14,2) NOT NULL,

    /*
     * Efectivo contado fisicamente por el cajero.
     */
    efectivo_contado DECIMAL(14,2) NOT NULL,

    /*
     * Diferencia:
     * efectivo_contado - efectivo_esperado.
     *
     * Positiva: sobrante.
     * Negativa: faltante.
     * Cero: corte exacto.
     */
    diferencia DECIMAL(14,2) NOT NULL,

    fecha_corte DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observaciones VARCHAR(500) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Una sesion solo puede tener un corte final.
     */
    CONSTRAINT uk_corte_sesion
        UNIQUE (sesion_caja_id),

    /*
     * Relaciones.
     */
    CONSTRAINT fk_corte_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_corte_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Los importes informativos y movimientos
     * siempre deben ser cantidades no negativas.
     */
    CONSTRAINT chk_corte_importes
        CHECK (
            ventas_total >= 0
            AND cobros_efectivo >= 0
            AND cobros_no_efectivo >= 0
            AND otros_ingresos >= 0
            AND salidas_efectivo >= 0
            AND devoluciones_efectivo >= 0
            AND ajustes_positivos >= 0
            AND ajustes_negativos >= 0
            AND fondo_inicial >= 0
            AND efectivo_esperado >= 0
            AND efectivo_contado >= 0
        ),

    /*
     * El efectivo esperado debe coincidir
     * con la formula del corte.
     */
    CONSTRAINT chk_corte_efectivo_esperado
        CHECK (
            efectivo_esperado =
                fondo_inicial
                + cobros_efectivo
                + otros_ingresos
                + ajustes_positivos
                - salidas_efectivo
                - devoluciones_efectivo
                - ajustes_negativos
        ),

    /*
     * La diferencia debe corresponder al arqueo.
     */
    CONSTRAINT chk_corte_diferencia
        CHECK (
            diferencia = efectivo_contado - efectivo_esperado
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
 * Consultar cortes por fecha.
 */
CREATE INDEX idx_corte_fecha
    ON corte_caja(fecha_corte);

/*
 * Consultar cortes realizados por usuario.
 */
CREATE INDEX idx_corte_usuario_fecha
    ON corte_caja(usuario_id, fecha_corte);


/*
 * ============================================================
 * REGLAS PARA LA APLICACION JAVA
 * ============================================================
 *
 * 1. El corte se realiza sobre una sesion de caja.
 *
 * 2. No permitir mas de un corte final por sesion.
 *
 * 3. Antes del corte, bloquear la sesion y evitar
 *    que se registren nuevas operaciones.
 *
 * 4. Obtener ventas_total de las ventas CONFIRMADAS
 *    de la sesion, sin sumar ventas CANCELADAS.
 *
 * 5. Obtener cobros_efectivo de los pagos CONFIRMADOS
 *    de ventas_pago que afectan efectivo de caja.
 *
 * 6. Obtener cobros_no_efectivo de los pagos CONFIRMADOS
 *    que no afectan efectivo de caja.
 *
 * 7. Los cobros en efectivo incluyen los pagos de ventas
 *    y los abonos de credito realizados en efectivo.
 *
 * 8. Obtener otros_ingresos de movimientos INGRESO
 *    que no correspondan a pagos de ventas ni abonos
 *    ya contabilizados en cobros_efectivo.
 *
 * 9. Obtener salidas_efectivo de movimientos SALIDA
 *    y RETIRO.
 *
 * 10. Obtener devoluciones_efectivo de movimientos
 *     DEVOLUCION.
 *
 * 11. Obtener ajustes_positivos y ajustes_negativos
 *     de sus respectivos tipos de movimiento.
 *
 * 12. No sumar APERTURA al efectivo esperado.
 *     El fondo inicial se obtiene de sesiones_caja
 *     y se suma exactamente una vez.
 *
 * 13. Los pagos con tarjeta, transferencia, SPEI
 *     y otros medios no efectivos nunca aumentan
 *     el efectivo esperado.
 *
 * 14. Calcular efectivo_esperado con la formula
 *     establecida en esta tabla.
 *
 * 15. Calcular diferencia:
 *     efectivo_contado - efectivo_esperado.
 *
 * 16. Registrar el corte y cerrar la sesion
 *     dentro de una misma transaccion.
 *
 * 17. Si cualquier operacion falla, realizar ROLLBACK.
 *
 * 18. No modificar ni eliminar cortes confirmados.
 *
 * 19. Registrar el cierre y cualquier diferencia
 *     en la bitacora.
 *
 * 20. Las correcciones posteriores deben quedar
 *     documentadas mediante registros compensatorios.
 *
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 */
