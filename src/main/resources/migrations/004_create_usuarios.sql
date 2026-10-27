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
 * MIGRACION 004: USUARIOS Y ASIGNACION A SUCURSALES
 * SistemaPOSTienda
 * ============================================================
 *
 * Esta migracion crea la estructura de usuarios.

 * Compatible con negocios de diferentes giros comerciales.
 * ============================================================
 */

CREATE TABLE usuarios (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Rol principal del usuario.
     */
    rol_id BIGINT NOT NULL,

    /*
     * Datos personales.
     */
    nombre VARCHAR(100) NOT NULL,

    apellido_paterno VARCHAR(100) NULL,

    apellido_materno VARCHAR(100) NULL,

    /*
     * Nombre de acceso al sistema.
     */
    usuario VARCHAR(50) NOT NULL,

    /*
     * Hash de la contrasena.
     * Nunca almacenar contrasenas en texto plano.
     */
    password_hash VARCHAR(255) NOT NULL,

    /*
     * Datos de contacto.
     */
    correo VARCHAR(150) NULL,

    telefono VARCHAR(20) NULL,

    /*
     * Fecha y hora del ultimo acceso.
     * NULL indica que nunca ha iniciado sesion.
     */
    ultimo_acceso TIMESTAMP NULL DEFAULT NULL,

    /*
     * Estado de la cuenta.
     */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Obliga al cambio de contrasena en el siguiente acceso,
     * cuando la aplicacion implemente este flujo.
     */
    requiere_cambio_password BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Auditoria.
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
     * El nombre de usuario debe ser unico.
     */
    CONSTRAINT uk_usuarios_usuario
        UNIQUE (usuario),

    /*
     * Relacion con el catalogo de roles.
     */
    CONSTRAINT fk_usuarios_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES DE USUARIOS
 * ============================================================
 */

/*
 * Busqueda por nombre.
 */
CREATE INDEX idx_usuarios_nombre
ON usuarios(nombre);


/*
 * Filtros por rol.
 * La restriccion UNIQUE de usuario ya crea un indice
 * para las busquedas por nombre de acceso.
 */
CREATE INDEX idx_usuarios_rol
ON usuarios(rol_id);


/*
 * Filtros por estado de la cuenta.
 */
CREATE INDEX idx_usuarios_activo
ON usuarios(activo);


/*
 * Consultas por ultimo acceso.
 */
CREATE INDEX idx_usuarios_ultimo_acceso
ON usuarios(ultimo_acceso);


/*
 * ============================================================
 * TABLA: usuario_sucursal
 * ============================================================
 *
 * Relaciona los usuarios con las sucursales a las que
 * tienen acceso.
 *
 * Un usuario puede pertenecer a una o varias sucursales.
 * Una sucursal puede tener varios usuarios.
 *
 * Esta estructura permite operar con una sola sucursal
 * o con multiples sucursales.
 *
 * La autorizacion efectiva por sucursal tambien debe
 * validarse desde la aplicacion Java.
 * ============================================================
 */

CREATE TABLE usuario_sucursal (

    usuario_id BIGINT NOT NULL,

    sucursal_id BIGINT NOT NULL,

    /*
     * Una misma pareja usuario-sucursal solo puede
     * registrarse una vez.
     */
    PRIMARY KEY (usuario_id, sucursal_id),

    /*
     * Relacion con usuarios.
     * Si se elimina un usuario, se eliminan sus asignaciones.
     */
    CONSTRAINT fk_usuario_sucursal_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE,

    /*
     * Relacion con sucursales.
     * Si se elimina una sucursal, se eliminan sus asignaciones.
     */
    CONSTRAINT fk_usuario_sucursal_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICE DE ASIGNACIONES
 * ============================================================
 *
 * La llave primaria ya permite buscar por usuario_id.
 * Este indice facilita obtener los usuarios de una sucursal.
 * ============================================================
 */

CREATE INDEX idx_usuario_sucursal_sucursal
ON usuario_sucursal(sucursal_id);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * Los roles deben existir previamente en la migracion 002.
 * Las sucursales deben existir previamente en la migracion 003.
 *
 * La creacion del administrador inicial se implementara
 * desde Java despues de crear la estructura de la base de datos.
 * ============================================================
 */
