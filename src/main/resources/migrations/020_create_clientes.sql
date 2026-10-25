/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE clientes (

    id BIGINT NOT NULL AUTO_INCREMENT,

    lista_precio_id BIGINT,

    codigo VARCHAR(30) NOT NULL,

    nombre VARCHAR(200) NOT NULL,

    razon_social VARCHAR(255),

    rfc VARCHAR(20),

    telefono VARCHAR(30),

    celular VARCHAR(30),

    email VARCHAR(150),

    direccion VARCHAR(255),

    ciudad VARCHAR(100),

    estado VARCHAR(100),

    codigo_postal VARCHAR(10),

    limite_credito DECIMAL(12,2) NOT NULL DEFAULT 0,

    dias_credito INT NOT NULL DEFAULT 0,

    descuento DECIMAL(5,2) NOT NULL DEFAULT 0,

    permite_credito BOOLEAN NOT NULL DEFAULT FALSE,

    referencia_domicilio VARCHAR(255),

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_cliente_codigo
        UNIQUE (codigo),

    CONSTRAINT uk_cliente_rfc
        UNIQUE (rfc),

    CONSTRAINT fk_cliente_lista_precio
        FOREIGN KEY (lista_precio_id)
        REFERENCES listas_precios(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_cliente_nombre
ON clientes(nombre);


CREATE INDEX idx_cliente_activo
ON clientes(activo);


INSERT INTO clientes
(
    lista_precio_id,
    codigo,
    nombre,
    activo
)
VALUES
(
    1,
    'PUBLICO',
    'PÚBLICO EN GENERAL',
    TRUE
);