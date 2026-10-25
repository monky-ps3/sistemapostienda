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
 * TABLA: usuarios
 * ============================================================
 *
 * Contiene los usuarios que pueden acceder al sistema.
 *
 * Relación:
 *
 * usuarios
 *     │
 *     ├── rol_id ───────────► roles
 *     │
 *     └── usuario_sucursal ─► sucursales
 *
 * Un usuario puede pertenecer a una o varias sucursales.
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

    apellido_paterno VARCHAR(100),

    apellido_materno VARCHAR(100),

    /*
     * Nombre utilizado para iniciar sesión.
     */
    usuario VARCHAR(50) NOT NULL,

    /*
     * Hash de la contraseña.
     */
    password_hash VARCHAR(255) NOT NULL,

    /** Datos de contacto.*/
    correo VARCHAR(150),

    telefono VARCHAR(20),

    /*
     * Último acceso del usuario.
     *
     * NULL = nunca ha iniciado sesión.
     */
    ultimo_acceso TIMESTAMP NULL,

    /*
     * Estado del usuario.
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
     * ========================================================
     * LLAVE PRIMARIA
     * ========================================================
     */
    PRIMARY KEY (id),

    /*
     * ========================================================
     * RESTRICCIONES
     * ========================================================
     */
    /*
     * No puede existir otro usuario con
     * el mismo nombre de acceso.
     */
    CONSTRAINT uk_usuarios_usuario
        UNIQUE (usuario),
    /*
     * Relación con roles.
     */
    CONSTRAINT fk_usuarios_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Búsquedas por nombre.
 */
CREATE INDEX idx_usuarios_nombre
ON usuarios(nombre);


/*
 * Búsquedas o filtros por rol.
 */
CREATE INDEX idx_usuarios_rol
ON usuarios(rol_id);


/*
 * Búsqueda de usuarios activos/inactivos.
 */
CREATE INDEX idx_usuarios_activo
ON usuarios(activo);

/*
 * Consultas relacionadas con último acceso.
 */
CREATE INDEX idx_usuarios_ultimo_acceso
ON usuarios(ultimo_acceso);


/*
 * ============================================================
 * TABLA: usuario_sucursal
 * ============================================================
 *
 * Tabla intermedia entre usuarios y sucursales.
 *
 * Permite:
 *
 * Usuario 1 → Sucursal 1
 *
 * Usuario 2 → Sucursal 1
 * Usuario 2 → Sucursal 2
 *
 * Usuario 3 → Sucursal 1
 * Usuario 3 → Sucursal 2
 * Usuario 3 → Sucursal 3
 *
 * Esto permite que el sistema sea desde una sola
 * sucursal hasta múltiples sucursales.
 * ============================================================
 */

CREATE TABLE usuario_sucursal (
    /*
     * Usuario relacionado.
     */
    usuario_id BIGINT NOT NULL,
    /*
     * Sucursal relacionada.
     */
    sucursal_id BIGINT NOT NULL,
    /*
     * Evita duplicar la misma relación.
     *
     * Ejemplo:
     *
     * usuario 1 + sucursal 2
     *
     * solamente puede existir una vez.
     */
    PRIMARY KEY (usuario_id, sucursal_id),

    /*
     * Relación con usuarios.
     *
     * Si se elimina el usuario,
     * se eliminan sus asignaciones.
     */
    CONSTRAINT fk_usuario_sucursal_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE,
    /*
     * Relación con sucursales.
     *
     * Si se elimina una sucursal,
     * se eliminan sus asignaciones.
     */
    CONSTRAINT fk_usuario_sucursal_sucursal
        FOREIGN KEY (sucursal_id)
        REFERENCES sucursales(id)
        ON DELETE CASCADE

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

/*
 * ============================================================
 * ÍNDICE
 * ============================================================
 *
 * Permite buscar rápidamente todos los usuarios
 * pertenecientes a una determinada sucursal.
 */
CREATE INDEX idx_usuario_sucursal_sucursal
ON usuario_sucursal(sucursal_id);
/*
 * ============================================================
 * DATOS INICIALES
 * ============================================================
 *
 * IMPORTANTE:
 * Estos usuarios son únicamente para pruebas.
 *
 * Los password_hash deben sustituirse por hashes reales
 * generados desde la aplicación.
 * ============================================================
 */


/*
 * ADMINISTRADOR
 *
 * Su rol debe corresponder al ID real de Administrador.
 *
 * Aquí utilizamos rol_id = 1 como ejemplo.
 */
INSERT INTO usuarios (
    rol_id,
    nombre,
    apellido_paterno,
    apellido_materno,
    usuario,
    password_hash,
    correo,
    telefono,
    activo
)
VALUES (
    1,
    'Administrador',
    'General',
    NULL,
    'admin',
    'HASH_PENDIENTE',
    'admin@empresa.com',
    '9510000000',
    TRUE
);


/*
 * GERENTE
 *
 * Ejemplo con rol_id = 2.
 */
INSERT INTO usuarios (
    rol_id,
    nombre,
    apellido_paterno,
    apellido_materno,
    usuario,
    password_hash,
    correo,
    telefono,
    activo
)
VALUES (
    2,
    'Juan',
    'Hernández',
    'López',
    'juan.hernandez',
    'HASH_PENDIENTE',
    'juan@empresa.com',
    '9510000001',
    TRUE
);


/*
 * CAJERO
 *
 * Ejemplo con rol_id = 3.
 */
INSERT INTO usuarios (
    rol_id,
    nombre,
    apellido_paterno,
    apellido_materno,
    usuario,
    password_hash,
    correo,
    telefono,
    activo
)
VALUES (
    3,
    'María',
    'García',
    'Martínez',
    'maria.garcia',
    'HASH_PENDIENTE',
    'maria@empresa.com',
    '9510000002',
    TRUE
);


/*
 * ============================================================
 * ASIGNACIÓN DE USUARIOS A SUCURSALES
 * ============================================================
 *
 * Recordemos que tenemos:
 *
 * 1 = Matriz
 * 2 = Sucursal Centro
 * 3 = Sucursal Norte
 *
 * según nuestra migración de sucursales.
 */


/*
 * Administrador:
 * acceso a todas las sucursales.
 */
INSERT INTO usuario_sucursal
(usuario_id, sucursal_id)
VALUES
(1, 1),
(1, 2),
(1, 3);


/*
 * Gerente:
 * acceso a Matriz y Centro.
 */
INSERT INTO usuario_sucursal
(usuario_id, sucursal_id)
VALUES
(2, 1),
(2, 2);


/*
 * Cajero:
 * solamente Sucursal Centro.
 */
INSERT INTO usuario_sucursal
(usuario_id, sucursal_id)
VALUES
(3, 2);