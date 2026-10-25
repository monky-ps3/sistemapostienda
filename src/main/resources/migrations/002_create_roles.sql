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
    CONSTRAINT uk_roles_nombre
        UNIQUE (nombre)

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
    nombre,
    descripcion,
    activo
)
VALUES
(
    'Administrador',
    'Acceso completo al sistema',
    TRUE
),
(
    'Supervisor',
    'Supervisión y reportes',
    TRUE
),
(
    'Cajero',
    'Realiza ventas y cobros',
    TRUE
),
(
    'Bodega',
    'Control de inventario y compras',
    TRUE
);