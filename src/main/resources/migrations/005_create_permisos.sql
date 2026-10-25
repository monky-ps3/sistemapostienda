/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE permisos (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Clave interna utilizada por el sistema.
     *
     * Ejemplos:
     *
     * USUARIO_CREAR
     * USUARIO_EDITAR
     * USUARIO_ELIMINAR
     * VENTA_CREAR
     * VENTA_CANCELAR
     * PRODUCTO_EDITAR
     */
    clave VARCHAR(100) NOT NULL,

    /*
     * Nombre que se mostrará en la interfaz.
     */
    nombre VARCHAR(150) NOT NULL,

    /*
     * Módulo al que pertenece el permiso.
     *
     * Ejemplos:
     *
     * Usuarios
     * Ventas
     * Productos
     * Compras
     * Inventario
     * Caja
     */
    modulo VARCHAR(100) NOT NULL,

    /*
     * Descripción del permiso.
     */
    descripcion VARCHAR(255),

    /*
     * Permite activar o desactivar el permiso.
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
     * La clave debe ser única.
     */
    CONSTRAINT uk_permisos_clave
        UNIQUE (clave)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */
/*
 * Permite consultar permisos por módulo.
 */
CREATE INDEX idx_permisos_modulo
ON permisos(modulo);
/*
 * Permite filtrar permisos activos.
 */
CREATE INDEX idx_permisos_activo
ON permisos(activo);