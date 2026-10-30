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
 * MIGRACION 008: CAJAS
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra las cajas o terminales de cobro de cada sucursal.
 *
 * Una caja puede tener multiples sesiones a lo largo del tiempo,
 * pero no debe eliminarse si existen sesiones relacionadas.
 * ============================================================
 */

CREATE TABLE cajas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    sucursal_id BIGINT NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    descripcion VARCHAR(255) NULL,

    activa BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * El nombre de caja debe ser unico dentro de su sucursal.
     */
    CONSTRAINT uk_caja_sucursal_nombre
        UNIQUE (sucursal_id, nombre),

    /*
     * No permitir nombres vacios o compuestos solo por espacios.
     */
    CONSTRAINT chk_caja_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    /*
     * Relacion con la sucursal.
     * No permitir borrar una sucursal que tenga cajas registradas.
     */
    CONSTRAINT fk_caja_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
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
 * Facilita la consulta de cajas activas.
 */
CREATE INDEX idx_caja_activa
    ON cajas(activa);


/*
 * Facilita la consulta de cajas por sucursal y estado.
 */
CREATE INDEX idx_caja_sucursal_activa
    ON cajas(sucursal_id, activa);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION:
 *
 * 1. Una caja inactiva no debe aceptar nuevas aperturas.
 * 2. No eliminar cajas que tengan sesiones o movimientos.
 * 3. Para retirar una caja de uso, marcarla como inactiva.
 * 4. Validar los permisos del usuario antes de administrar cajas.
 * 5. Registrar en la bitacora las operaciones importantes.
 * ============================================================
 */
