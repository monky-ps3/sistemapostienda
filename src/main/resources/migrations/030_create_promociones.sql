/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */

CREATE TABLE promociones (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(150) NOT NULL,

    descripcion VARCHAR(500),

    /*
     * Tipo de promoción.
     *
     * Ejemplos:
     * DESCUENTO_PORCENTAJE
     * DESCUENTO_MONTO
     * PRECIO_FIJO
     * COMPRA_X_PAGA_Y
     * N_POR_PRECIO
     *
     * Se deja VARCHAR para permitir agregar nuevos tipos
     * sin modificar la estructura de la BD.
     */
    tipo_promocion VARCHAR(50) NOT NULL,

    fecha_inicio DATE NOT NULL,

    fecha_fin DATE NOT NULL,

    hora_inicio TIME NULL,

    hora_fin TIME NULL,

    /*
     * Días de aplicación.
     *
     * Si todos están en FALSE,
     * la promoción aplica cualquier día.
     */

    lunes BOOLEAN NOT NULL DEFAULT FALSE,

    martes BOOLEAN NOT NULL DEFAULT FALSE,

    miercoles BOOLEAN NOT NULL DEFAULT FALSE,

    jueves BOOLEAN NOT NULL DEFAULT FALSE,

    viernes BOOLEAN NOT NULL DEFAULT FALSE,

    sabado BOOLEAN NOT NULL DEFAULT FALSE,

    domingo BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Campos utilizados dependiendo del tipo
     * de promoción.
     */

    cantidad_compra DECIMAL(10,2) NULL,

    cantidad_paga DECIMAL(10,2) NULL,

    porcentaje_descuento DECIMAL(6,2) NULL,

    monto_descuento DECIMAL(12,2) NULL,

    precio_promocion DECIMAL(12,2) NULL,

    /*
     * Número máximo de veces que puede
     * aplicarse la promoción.
     *
     * NULL = Sin límite.
     */

    limite_aplicaciones INT NULL,

    /*
     * Prioridad.
     *
     * Entre menor número,
     * mayor prioridad.
     */

    prioridad INT NOT NULL DEFAULT 1,

    /*
     * ¿Puede combinarse con otras promociones?
     */

    acumulable BOOLEAN NOT NULL DEFAULT FALSE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;



CREATE INDEX idx_promociones_nombre
ON promociones(nombre);



CREATE INDEX idx_promociones_tipo
ON promociones(tipo_promocion);



CREATE INDEX idx_promociones_fechas
ON promociones(fecha_inicio, fecha_fin);



CREATE INDEX idx_promociones_activo
ON promociones(activo);



CREATE INDEX idx_promociones_prioridad
ON promociones(prioridad);