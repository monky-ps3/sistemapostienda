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
 * Tabla: sucursales
 * Descripción:
 * Almacena las sucursales de la empresa.
 * ============================================================
 */

CREATE TABLE sucursales (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Nombre de la sucursal.
     */
    nombre VARCHAR(150) NOT NULL,
    /*     * Código único de la sucursal.
     * Ejemplo:* MAT,* CEN,* NOR, */
    codigo VARCHAR(20) NOT NULL,

    /** Dirección completa.*/
    direccion VARCHAR(255),

    /* Teléfono de contacto. */
    telefono VARCHAR(20),

    /** Correo electrónico.*/
    correo VARCHAR(150),

    /* Pendiente de definir.
     * En el futuro podría convertirse en:
     * responsable_id BIGINT
     * con llave foránea hacia usuarios.
     */


    /* Estado de la sucursal.*/
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /* Auditoría.*/
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_sucursales_codigo
        UNIQUE (codigo)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

/* Índices*/

CREATE INDEX idx_sucursales_activo
ON sucursales(activo);

CREATE INDEX idx_sucursales_nombre
ON sucursales(nombre);

CREATE INDEX idx_sucursales_codigo
ON sucursales(codigo);

/*
 * ============================================================
 * Datos iniciales
 * ============================================================
 */

INSERT INTO sucursales
(
    nombre,
    codigo,
    direccion,
    telefono,
    correo,
    activo
)
VALUES
(
    'Matriz',
    'MAT',
    'Dirección pendiente',
    '9510000000',
    'matriz@empresa.com',
    TRUE
);

INSERT INTO sucursales
(
    nombre,
    codigo,
    direccion,
    telefono,
    correo,
    activo
)
VALUES
(
    'Sucursal Centro',
    'CEN',
    'Dirección pendiente',
    '9510000001',
    'centro@empresa.com',
    TRUE
);

INSERT INTO sucursales
(
    nombre,
    codigo,
    direccion,
    telefono,
    correo,
    activo
)
VALUES
(
    'Sucursal Norte',
    'NOR',
    'Dirección pendiente',
    '9510000002',
    'norte@empresa.com',
    TRUE
);
