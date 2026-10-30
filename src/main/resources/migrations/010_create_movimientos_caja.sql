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
 * Registra entradas, salidas, retiros y ajustes de efectivo.
 *
 * Los movimientos pertenecen a una sesion de caja y forman
 * parte del historial financiero de la terminal.
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
        'AJUSTE_POSITIVO',
        'AJUSTE_NEGATIVO'
    ) NOT NULL,

    concepto VARCHAR(200) NOT NULL,

    monto DECIMAL(12,2) NOT NULL,

    observaciones VARCHAR(255) NULL,

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * El concepto no debe estar vacio.
     */
    CONSTRAINT chk_movimiento_concepto
        CHECK (CHAR_LENGTH(TRIM(concepto)) > 0),

    /*
     * El monto siempre se registra como cantidad positiva.
     * El tipo determina si suma o resta al efectivo.
     */
    CONSTRAINT chk_movimiento_monto
        CHECK (monto > 0),

    /*
     * No permitir eliminar sesiones con movimientos.
     */
    CONSTRAINT fk_movimiento_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Conservar el historial aunque se desactive un usuario.
     * No permitir eliminar usuarios con movimientos.
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
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION JAVA:
 *
 * 1. El monto se almacena siempre como valor positivo.
 * 2. INGRESO y AJUSTE_POSITIVO aumentan el efectivo.
 * 3. SALIDA, RETIRO y AJUSTE_NEGATIVO disminuyen el efectivo.
 * 4. APERTURA registra el fondo inicial, si se utiliza
 *    un movimiento de apertura.
 * 5. No permitir movimientos en sesiones cerradas.
 * 6. Validar permisos antes de registrar movimientos.
 * 7. Registrar los movimientos dentro de transacciones.
 * 8. No modificar ni eliminar movimientos confirmados.
 * 9. Las correcciones deben registrarse mediante
 *    movimientos compensatorios.
 *
 * IMPORTANTE:
 * El sistema debe evitar duplicar el fondo inicial:
 * si lo registra como movimiento APERTURA, no debe volver
 * a sumarlo desde fondo_inicial al calcular el efectivo.
 * ============================================================
 */
