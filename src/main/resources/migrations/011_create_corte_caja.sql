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
 * Almacena el resultado del arqueo final de una sesion.
 *
 * Cada sesion puede tener un solo corte final.
 * ============================================================
 */

CREATE TABLE corte_caja (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sesion_caja_id BIGINT NOT NULL,

    /*
     * Totales calculados durante el cierre.
     */
    ventas DECIMAL(12,2) NOT NULL DEFAULT 0,

    entradas DECIMAL(12,2) NOT NULL DEFAULT 0,

    salidas DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Efectivo que deberia existir segun los movimientos.
     */
    efectivo_esperado DECIMAL(12,2) NOT NULL,

    /*
     * Efectivo contado fisicamente por el cajero.
     */
    efectivo_contado DECIMAL(12,2) NOT NULL,

    /*
     * Diferencia = efectivo contado - efectivo esperado.
     *
     * Positiva: sobrante.
     * Negativa: faltante.
     * Cero: corte exacto.
     */
    diferencia DECIMAL(12,2) NOT NULL,

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Un solo corte final por sesion.
     */
    CONSTRAINT uk_corte_sesion
        UNIQUE (sesion_caja_id),

    /*
     * Los totales no pueden ser negativos.
     */
    CONSTRAINT chk_corte_ventas
        CHECK (ventas >= 0),

    CONSTRAINT chk_corte_entradas
        CHECK (entradas >= 0),

    CONSTRAINT chk_corte_salidas
        CHECK (salidas >= 0),

    CONSTRAINT chk_corte_efectivo_esperado
        CHECK (efectivo_esperado >= 0),

    CONSTRAINT chk_corte_efectivo_contado
        CHECK (efectivo_contado >= 0),

    /*
     * La diferencia debe corresponder al arqueo.
     */
    CONSTRAINT chk_corte_diferencia
        CHECK (
            diferencia = efectivo_contado - efectivo_esperado
        ),

    /*
     * No permitir eliminar sesiones que ya tienen corte.
     */
    CONSTRAINT fk_corte_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
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
 * Consultar cortes por fecha.
 */
CREATE INDEX idx_corte_fecha
    ON corte_caja(fecha);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION JAVA:
 *
 * 1. El corte se calcula utilizando las ventas y movimientos
 *    confirmados de la sesion.
 * 2. El efectivo esperado debe incluir el fondo inicial
 *    exactamente una vez.
 * 3. La diferencia se calcula como:
 *    efectivo_contado - efectivo_esperado.
 * 4. El cierre de sesion y el registro del corte deben
 *    realizarse dentro de una misma transaccion.
 * 5. No permitir ventas ni movimientos despues del cierre.
 * 6. No modificar ni eliminar cortes confirmados.
 * 7. Las correcciones deben quedar registradas en bitacora.
 *
 * IMPORTANTE:
 * Esta tabla almacena el corte final. Si posteriormente
 * necesitas arqueos parciales durante un turno, se diseñara
 * una tabla independiente para esos eventos.
 * ============================================================
 */
