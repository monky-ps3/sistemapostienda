/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

/*
============================================================
TABLA: unidades_medida
============================================================
*/

CREATE TABLE unidades_medida (

    id BIGINT AUTO_INCREMENT,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(20) NOT NULL,

    abreviatura VARCHAR(20) NOT NULL,

    descripcion VARCHAR(255),

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT uk_unidad_nombre
        UNIQUE(nombre),

    CONSTRAINT uk_unidad_codigo
        UNIQUE(codigo),

    CONSTRAINT uk_unidad_abreviatura
        UNIQUE(abreviatura)

);

/*
============================================================
ÍNDICES
============================================================
*/

CREATE INDEX idx_unidad_activo
ON unidades_medida(activo);

/*
============================================================
DATOS INICIALES
============================================================
*/

INSERT INTO unidades_medida
(nombre,codigo,abreviatura,descripcion,activo)
VALUES

('Pieza','PZA','PZA','Venta por pieza',TRUE),

('Kilogramo','KG','KG','Unidad de peso',TRUE),

('Gramo','GR','GR','Unidad de peso',TRUE),

('Litro','LT','LT','Unidad de volumen',TRUE),

('Mililitro','ML','ML','Unidad de volumen',TRUE),

('Metro','M','M','Unidad de longitud',TRUE),

('Centímetro','CM','CM','Unidad de longitud',TRUE),

('Caja','CAJ','CAJ','Venta por caja',TRUE),

('Paquete','PAQ','PAQ','Venta por paquete',TRUE),

('Bolsa','BOL','BOL','Venta por bolsa',TRUE),

('Botella','BOT','BOT','Venta por botella',TRUE),

('Lata','LAT','LAT','Venta por lata',TRUE),

('Rollo','ROL','ROL','Venta por rollo',TRUE),

('Sobre','SOB','SOB','Venta por sobre',TRUE),

('Costal','COS','COS','Venta por costal',TRUE),

('Bulto','BUL','BUL','Venta por bulto',TRUE),

('Docena','DOC','DOC','Venta por docena',TRUE),

('Par','PAR','PAR','Venta por par',TRUE),

('Galón','GAL','GAL','Unidad de volumen',TRUE),

('Cubeta','CUB','CUB','Venta por cubeta',TRUE);