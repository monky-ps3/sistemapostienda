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
 * MIGRACIÓN 021: PROVEEDORES
 * ============================================================
 *
 * Registro general de proveedores.
 *
 * Compatible con diferentes tipos de negocios:
 * - Abarrotes
 * - Farmacias
 * - Ferreterías
 * - Electrónica
 * - Refaccionarias
 * - Otros comercios
 *
 * Incluye información fiscal, comercial
 * y condiciones de crédito.
 *
 * ============================================================
 */

CREATE TABLE proveedores (

    id BIGINT NOT NULL AUTO_INCREMENT,

    codigo VARCHAR(30) NOT NULL,

    nombre VARCHAR(200) NOT NULL,

    razon_social VARCHAR(255) NULL,

    rfc VARCHAR(20) NULL,

    contacto VARCHAR(150) NULL,

    telefono VARCHAR(30) NULL,

    celular VARCHAR(30) NULL,

    email VARCHAR(150) NULL,

    direccion VARCHAR(255) NULL,

    ciudad VARCHAR(100) NULL,

    estado VARCHAR(100) NULL,

    codigo_postal VARCHAR(10) NULL,

    dias_credito INT NOT NULL DEFAULT 0,

    limite_credito DECIMAL(12,2) NOT NULL DEFAULT 0,

    permite_credito BOOLEAN NOT NULL DEFAULT FALSE,

    observaciones TEXT NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_proveedor_codigo
        UNIQUE (codigo),

    /*
     * Se permiten varios proveedores sin RFC.
     * Los RFC registrados no pueden repetirse.
     */
    CONSTRAINT uk_proveedor_rfc
        UNIQUE (rfc),

    CONSTRAINT chk_proveedor_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_proveedor_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_proveedor_dias_credito
        CHECK (dias_credito >= 0),

    CONSTRAINT chk_proveedor_limite_credito
        CHECK (limite_credito >= 0),

    /*
     * Si no se permite crédito, no deben existir
     * condiciones de crédito configuradas.
     */
    CONSTRAINT chk_proveedor_condiciones_credito
        CHECK (
            permite_credito = TRUE
            OR (
                limite_credito = 0
                AND dias_credito = 0
            )
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * ÍNDICES
 * ============================================================
 */

/*
 * Búsqueda por nombre.
 */
CREATE INDEX idx_proveedor_nombre
ON proveedores(nombre);


/*
 * Consultar proveedores activos.
 */
CREATE INDEX idx_proveedor_activo_nombre
ON proveedores(activo, nombre);


/*
 * Búsqueda por RFC.
 */
CREATE INDEX idx_proveedor_rfc
ON proveedores(rfc);
