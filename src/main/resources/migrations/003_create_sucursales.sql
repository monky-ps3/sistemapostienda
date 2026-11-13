/*
 * ============================================================
 * Tabla: sucursales
 * Descripción:
 * Almacena las sucursales de la empresa.
 * La instalación inicial registra únicamente la sucursal Matriz.
 * Las demás sucursales se crearán desde el módulo de administración.
 * ============================================================
 */

CREATE TABLE sucursales (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /* Nombre de la sucursal. */
    nombre VARCHAR(150) NOT NULL,

    /* Código único de la sucursal. Ejemplo: MAT, CEN, NOR. */
    codigo VARCHAR(20) NOT NULL,

    /* Datos de contacto. */
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    correo VARCHAR(150),

    /* Estado de la sucursal. */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /* Auditoría. */
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_sucursales_codigo
        UNIQUE (codigo),

    CONSTRAINT chk_sucursales_nombre_no_vacio
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_sucursales_codigo_no_vacio
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

/* Índices para consultas y filtros del módulo de sucursales. */
CREATE INDEX idx_sucursales_activo_nombre
ON sucursales(activo, nombre);

CREATE INDEX idx_sucursales_nombre
ON sucursales(nombre);

/*
 * ============================================================
 * Datos iniciales
 * ============================================================
 * Solo se crea Matriz. Las demás sucursales se registran desde
 * el sistema, evitando datos de ejemplo en instalaciones reales.
 */

INSERT INTO sucursales
(
    nombre,
    codigo,
    activo
)
VALUES
(
    'Matriz',
    'MAT',
    TRUE
);
