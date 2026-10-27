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
 * MIGRACION 007: BITACORA DE AUDITORIA
 * SistemaPOSTienda
 * ============================================================
 *
 * Registra acciones importantes realizadas en el sistema.
 *
 * Se utiliza para auditoria de ventas, inventario, caja,
 * compras, usuarios, configuracion y otros modulos.
 *
 * No almacenar contrasenas, hashes, tokens ni datos secretos
 * en la descripcion o en los campos de auditoria.
 * ============================================================
 */

CREATE TABLE bitacora (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Usuario que realizo la accion.
     * NULL permite registrar eventos del sistema o conservar
     * el historial si la cuenta del usuario se elimina.
     */
    usuario_id BIGINT NULL,

    /*
     * Sucursal relacionada con la accion.
     * NULL permite registrar eventos generales del sistema.
     */
    sucursal_id BIGINT NULL,

    /*
     * Modulo que origino la accion.
     * Ejemplos: Ventas, Inventario, Caja, Usuarios.
     */
    modulo VARCHAR(100) NOT NULL,

    /*
     * Accion realizada.
     * Ejemplos: VENTA_CANCELAR, INVENTARIO_AJUSTAR.
     */
    accion VARCHAR(100) NOT NULL,

    /*
     * Resultado de la operacion.
     */
    resultado VARCHAR(20) NOT NULL DEFAULT 'OK',

    /*
     * Descripcion del evento.
     * No incluir contrasenas ni informacion secreta.
     */
    descripcion TEXT NULL,

    /*
     * Referencia opcional al registro afectado.
     *
     * referencia_tipo indica la entidad, por ejemplo VENTA.
     * referencia_id contiene su identificador.
     *
     * No se usa una llave foranea porque la referencia puede
     * corresponder a tablas diferentes segun el tipo.
     */
    referencia_tipo VARCHAR(50) NULL,

    referencia_id BIGINT NULL,

    /*
     * Direccion IP de origen.
     * VARCHAR(45) admite IPv4 e IPv6.
     */
    ip VARCHAR(45) NULL,

    /*
     * Identificacion del equipo o terminal, si se conoce.
     */
    equipo VARCHAR(150) NULL,

    /*
     * Fecha y hora del evento.
     */
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Solo se aceptan resultados conocidos.
     */
    CONSTRAINT chk_bitacora_resultado
        CHECK (resultado IN ('OK', 'ERROR', 'RECHAZADO')),

    /*
     * El modulo y la accion no deben estar vacios.
     */
    CONSTRAINT chk_bitacora_modulo
        CHECK (CHAR_LENGTH(TRIM(modulo)) > 0),

    CONSTRAINT chk_bitacora_accion
        CHECK (CHAR_LENGTH(TRIM(accion)) > 0),

    /*
     * La referencia debe contener tipo e ID al mismo tiempo,
     * o no contener ninguno.
     */
    CONSTRAINT chk_bitacora_referencia
        CHECK (
            (referencia_tipo IS NULL AND referencia_id IS NULL)
            OR
            (
                referencia_tipo IS NOT NULL
                AND CHAR_LENGTH(TRIM(referencia_tipo)) > 0
                AND referencia_id IS NOT NULL
                AND referencia_id > 0
            )
        ),

    /*
     * Si se elimina un usuario o sucursal, el evento se conserva
     * y la referencia se establece en NULL.
     */
    CONSTRAINT fk_bitacora_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE SET NULL,

    CONSTRAINT fk_bitacora_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE SET NULL

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES PARA CONSULTAS DE AUDITORIA
 * ============================================================
 */

/*
 * Consultar el historial de un usuario.
 */
CREATE INDEX idx_bitacora_usuario_fecha
ON bitacora(usuario_id, fecha);


/*
 * Consultar los eventos de una sucursal por fecha.
 */
CREATE INDEX idx_bitacora_sucursal_fecha
ON bitacora(sucursal_id, fecha);


/*
 * Consultar eventos por fecha.
 */
CREATE INDEX idx_bitacora_fecha
ON bitacora(fecha);


/*
 * Filtrar eventos por modulo y fecha.
 */
CREATE INDEX idx_bitacora_modulo_fecha
ON bitacora(modulo, fecha);


/*
 * Localizar operaciones con error o rechazadas.
 */
CREATE INDEX idx_bitacora_resultado_fecha
ON bitacora(resultado, fecha);


/*
 * Buscar eventos relacionados con una entidad.
 */
CREATE INDEX idx_bitacora_referencia
ON bitacora(referencia_tipo, referencia_id);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * La aplicacion Java debe:
 *
 * 1. Registrar eventos importantes.
 * 2. Utilizar consultas parametrizadas.
 * 3. Evitar registrar informacion secreta.
 * 4. Restringir la modificacion y eliminacion de bitacoras.
 * 5. Registrar los eventos dentro de la logica de negocio
 *    correspondiente, sin perder la consistencia de la operacion.
 * ============================================================
 */
