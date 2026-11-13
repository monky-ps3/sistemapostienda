/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE roles (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Nombre del rol.
     * Ejemplo:
     * Administrador
     * Supervisor
     * Cajero
     * Bodega
     */
    /* Clave interna estable; no debe cambiar aunque cambie el nombre visible. */
    clave VARCHAR(30) NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    /*
     * Descripción de las funciones del rol.
     */
    descripcion VARCHAR(255),

    /*
     * Permite activar o desactivar un rol
     * sin eliminarlo.
     */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Auditoría.
     */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    /*
     * Llave primaria.
     */
    PRIMARY KEY (id),

    /*
     * No puede existir otro rol con el mismo nombre.
     */
    CONSTRAINT uk_roles_clave
        UNIQUE (clave),

    CONSTRAINT uk_roles_nombre
        UNIQUE (nombre),

    CONSTRAINT chk_roles_clave_no_vacia
        CHECK (CHAR_LENGTH(TRIM(clave)) > 0),

    CONSTRAINT chk_roles_nombre_no_vacio
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

CREATE INDEX idx_roles_activo
ON roles(activo);


/*
 * ============================================================
 * DATOS INICIALES
 * ============================================================
 */

INSERT INTO roles
(
    clave,
    nombre,
    descripcion,
    activo
)
VALUES
(
    'ADMIN',
    'Administrador',
    'Acceso completo al sistema',
    TRUE
),
(
    'SUPERVISOR',
    'Supervisor',
    'Supervisión y reportes',
    TRUE
),
(
    'CAJERO',
    'Cajero',
    'Realiza ventas y cobros',
    TRUE
),
(
    'BODEGA',
    'Bodega',
    'Control de inventario y compras',
    TRUE
);