/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
CREATE TABLE rol_permiso (

    /*
     * Identificador de la relación.
     */
    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Rol al que pertenece el permiso.
     */
    rol_id BIGINT NOT NULL,

    /*
     * Permiso asignado al rol.
     */
    permiso_id BIGINT NOT NULL,

    /*
     * Fecha en la que se realizó la asignación.
     */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    /*
     * Llave primaria.
     */
    PRIMARY KEY (id),

    /*
     * Relación con roles.
     *
     * Si se elimina el rol,
     * se eliminan sus permisos asociados.
     */
    CONSTRAINT fk_rol_permiso_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id)
        ON DELETE CASCADE,

    /*
     * Relación con permisos.
     *
     * Si se elimina el permiso,
     * se eliminan sus asignaciones.
     */
    CONSTRAINT fk_rol_permiso_permiso
        FOREIGN KEY (permiso_id)
        REFERENCES permisos(id)
        ON DELETE CASCADE,

    /*
     * Evita asignar dos veces
     * el mismo permiso al mismo rol.
     */
    CONSTRAINT uk_rol_permiso
        UNIQUE (rol_id, permiso_id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Buscar rápidamente los permisos
 * pertenecientes a un rol.
 */
CREATE INDEX idx_rol_permiso_rol
ON rol_permiso(rol_id);


/*
 * Buscar rápidamente los roles
 * que tienen un determinado permiso.
 */
CREATE INDEX idx_rol_permiso_permiso
ON rol_permiso(permiso_id);