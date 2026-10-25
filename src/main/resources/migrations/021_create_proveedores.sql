/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
CREATE TABLE proveedores (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Código interno del proveedor.
     * Ejemplo:
     * PROV000001
     */
    codigo VARCHAR(30) NOT NULL,

    /*
     * Nombre comercial o nombre del proveedor.
     */
    nombre VARCHAR(200) NOT NULL,

    /*
     * Razón social.
     */
    razon_social VARCHAR(255),

    /*
     * RFC del proveedor.
     */
    rfc VARCHAR(20),

    /*
     * Persona de contacto.
     */
    contacto VARCHAR(150),

    /*
     * Teléfono.
     */
    telefono VARCHAR(30),

    /*
     * Celular.
     */
    celular VARCHAR(30),

    /*
     * Correo electrónico.
     */
    email VARCHAR(150),

    /*
     * Dirección.
     */
    direccion VARCHAR(255),

    ciudad VARCHAR(100),

    estado VARCHAR(100),

    codigo_postal VARCHAR(10),

    /*
     * Días de crédito otorgados
     * por el proveedor.
     *
     * Ejemplo:
     * 0  = contado
     * 7  = 7 días
     * 15 = 15 días
     * 30 = 30 días
     */
    dias_credito INT NOT NULL DEFAULT 0,

    /*
     * Límite de crédito que el proveedor
     * nos permite manejar.
     */
    limite_credito DECIMAL(12,2) NOT NULL DEFAULT 0,

    /*
     * Indica si podemos realizar compras
     * a crédito con este proveedor.
     */
    permite_credito BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Observaciones adicionales.
     */
    observaciones TEXT,

    /*
     * Estado del proveedor.
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

    PRIMARY KEY (id),

    /*
     * Código único.
     */
    CONSTRAINT uk_proveedor_codigo
        UNIQUE (codigo),

    /*
     * RFC único.
     *
     * MySQL permite múltiples NULL,
     * por lo que proveedores sin RFC
     * no tendrán problema.
     */
    CONSTRAINT uk_proveedor_rfc
        UNIQUE (rfc)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- ÍNDICES
-- ============================================================

CREATE INDEX idx_proveedor_nombre
ON proveedores(nombre);

CREATE INDEX idx_proveedor_activo
ON proveedores(activo);