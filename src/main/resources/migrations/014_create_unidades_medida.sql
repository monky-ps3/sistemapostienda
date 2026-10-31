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
 * MIGRACION 014: UNIDADES DE MEDIDA
 * SistemaPOSTienda
 * ============================================================
 *
 * Catalogo general de unidades utilizadas para comprar,
 * vender y controlar productos.
 *
 * Compatible con diferentes giros comerciales.
 * ============================================================
 */

CREATE TABLE unidades_medida (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(20) NOT NULL,

    abreviatura VARCHAR(20) NOT NULL,

    /*
     * Clasificacion de la unidad.
     */
    tipo ENUM(
        'UNIDAD',
        'PESO',
        'VOLUMEN',
        'LONGITUD',
        'AREA',
        'OTRO'
    ) NOT NULL DEFAULT 'UNIDAD',

    descripcion VARCHAR(255) NULL,

    /*
     * Indica si permite cantidades decimales.
     *
     * Ejemplos:
     * KG: 0.500
     * LT: 1.250
     * M:  2.500
     * PZA: normalmente cantidades enteras.
     */
    permite_decimales BOOLEAN NOT NULL DEFAULT FALSE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * No permitir nombres, codigos ni abreviaturas duplicadas.
     */
    CONSTRAINT uk_unidad_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_unidad_codigo
        UNIQUE (codigo),

    CONSTRAINT uk_unidad_abreviatura
        UNIQUE (abreviatura),

    /*
     * Validaciones.
     */
    CONSTRAINT chk_unidad_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_unidad_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_unidad_abreviatura
        CHECK (CHAR_LENGTH(TRIM(abreviatura)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICE
 * ============================================================
 */

CREATE INDEX idx_unidad_activo_tipo
    ON unidades_medida(activo, tipo);


/*
 * ============================================================
 * UNIDADES INICIALES
 * ============================================================
 *
 * Se incluyen unidades generales que pueden utilizarse
 * en distintos tipos de comercios.
 *
 * El negocio podra agregar otras unidades posteriormente.
 * ============================================================
 */

INSERT INTO unidades_medida
(nombre, codigo, abreviatura, tipo, descripcion, permite_decimales)
VALUES

/* CONTEO Y PRESENTACIONES */

('Pieza', 'PZA', 'PZA', 'UNIDAD',
 'Venta por pieza', FALSE),

('Caja', 'CAJ', 'CAJ', 'UNIDAD',
 'Venta por caja', FALSE),

('Paquete', 'PAQ', 'PAQ', 'UNIDAD',
 'Venta por paquete', FALSE),

('Bolsa', 'BOL', 'BOL', 'UNIDAD',
 'Venta por bolsa', FALSE),

('Botella', 'BOT', 'BOT', 'UNIDAD',
 'Venta por botella', FALSE),

('Lata', 'LAT', 'LAT', 'UNIDAD',
 'Venta por lata', FALSE),

('Rollo', 'ROL', 'ROL', 'UNIDAD',
 'Venta por rollo', FALSE),

('Sobre', 'SOB', 'SOB', 'UNIDAD',
 'Venta por sobre', FALSE),

('Costal', 'COS', 'COS', 'UNIDAD',
 'Venta por costal', FALSE),

('Bulto', 'BUL', 'BUL', 'UNIDAD',
 'Venta por bulto', FALSE),

('Docena', 'DOC', 'DOC', 'UNIDAD',
 'Venta por docena', FALSE),

('Par', 'PAR', 'PAR', 'UNIDAD',
 'Venta por par', FALSE),

('Cubeta', 'CUB', 'CUB', 'UNIDAD',
 'Venta por cubeta', FALSE),

/* PESO */

('Kilogramo', 'KG', 'KG', 'PESO',
 'Unidad de peso equivalente a mil gramos', TRUE),

('Gramo', 'GR', 'GR', 'PESO',
 'Unidad de peso', TRUE),

('Tonelada', 'TON', 'TON', 'PESO',
 'Unidad de peso equivalente a mil kilogramos', TRUE),

/* VOLUMEN */

('Litro', 'LT', 'LT', 'VOLUMEN',
 'Unidad de volumen', TRUE),

('Mililitro', 'ML', 'ML', 'VOLUMEN',
 'Unidad de volumen', TRUE),

('Galon', 'GAL', 'GAL', 'VOLUMEN',
 'Unidad de volumen', TRUE),

/* LONGITUD */

('Metro', 'M', 'M', 'LONGITUD',
 'Unidad de longitud', TRUE),

('Centimetro', 'CM', 'CM', 'LONGITUD',
 'Unidad de longitud', TRUE),

('Milimetro', 'MM', 'MM', 'LONGITUD',
 'Unidad de longitud', TRUE),

/* AREA */

('Metro cuadrado', 'M2', 'M2', 'AREA',
 'Unidad de superficie', TRUE);

 /*
  * ============================================================
  * FIN DE LA MIGRACION
  * ============================================================
  *
  * REGLAS PARA LA APLICACION JAVA:
  *
  * 1. Validar cantidades decimales segun la unidad y el producto.
  * 2. No permitir vender cantidades decimales cuando la unidad
  *    no las permita.
  * 3. Las conversiones entre unidades deben respetar su tipo.
  * 4. No convertir automaticamente caja, paquete o costal
  *    a piezas: su contenido depende de cada producto.
  * 5. Las unidades utilizadas por productos deben desactivarse
  *    en lugar de eliminarse.
  * 6. Permitir registrar nuevas unidades desde el catalogo.
  * ============================================================
  */
