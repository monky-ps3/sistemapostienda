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
 * MIGRACIÓN 020: CLIENTES
 * ============================================================
 *
 * Registro general de clientes para diferentes
 * tipos de negocios.
 *
 * Incluye:
 * - Datos fiscales y de contacto.
 * - Lista de precios asignada.
 * - Condiciones de crédito.
 * - Descuentos comerciales.
 *
 * El cliente PUBLICO representa ventas sin
 * identificación de un cliente específico.
 *
 * ============================================================
 */

CREATE TABLE clientes (

    id BIGINT NOT NULL AUTO_INCREMENT,

    lista_precio_id BIGINT NULL,

    codigo VARCHAR(30) NOT NULL,

    nombre VARCHAR(200) NOT NULL,

    razon_social VARCHAR(255) NULL,

    rfc VARCHAR(20) NULL,

    telefono VARCHAR(30) NULL,

    celular VARCHAR(30) NULL,

    email VARCHAR(150) NULL,

    direccion VARCHAR(255) NULL,

    ciudad VARCHAR(100) NULL,

    estado VARCHAR(100) NULL,

    codigo_postal VARCHAR(10) NULL,

    limite_credito DECIMAL(12,2) NOT NULL DEFAULT 0,

    dias_credito INT NOT NULL DEFAULT 0,

    descuento DECIMAL(5,2) NOT NULL DEFAULT 0,

    permite_credito BOOLEAN NOT NULL DEFAULT FALSE,

    referencia_domicilio VARCHAR(255) NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_cliente_codigo
        UNIQUE (codigo),

    /*
     * MySQL permite múltiples valores NULL
     * en una llave UNIQUE.
     */
    CONSTRAINT uk_cliente_rfc
        UNIQUE (rfc),

    CONSTRAINT fk_cliente_lista_precio
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT chk_cliente_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_cliente_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_cliente_limite_credito
        CHECK (limite_credito >= 0),

    CONSTRAINT chk_cliente_dias_credito
        CHECK (dias_credito >= 0),

    CONSTRAINT chk_cliente_descuento
        CHECK (descuento >= 0 AND descuento <= 100),

    /*
     * Si no se permite crédito, no deben existir
     * condiciones de crédito configuradas.
     */
    CONSTRAINT chk_cliente_condiciones_credito
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

CREATE INDEX idx_cliente_nombre
ON clientes(nombre);

CREATE INDEX idx_cliente_activo_nombre
ON clientes(activo, nombre);

CREATE INDEX idx_cliente_lista_precio
ON clientes(lista_precio_id);


/*
 * ============================================================
 * CLIENTE PÚBLICO EN GENERAL
 * ============================================================
 *
 * Se obtiene la lista predeterminada global
 * sin depender de un ID fijo.
 *
 * ============================================================
 */

INSERT INTO clientes (
    lista_precio_id,
    codigo,
    nombre,
    activo
)
SELECT
    id,
    'PUBLICO',
    'PÚBLICO EN GENERAL',
    TRUE
FROM listas_precios
WHERE codigo = 'PUBLICO'
  AND alcance = 'GLOBAL'
  AND es_predeterminada = TRUE
  AND activa = TRUE
LIMIT 1;
