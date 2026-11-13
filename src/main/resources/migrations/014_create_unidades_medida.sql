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
 *
 * Las conversiones generales se realizan entre unidades
 * de la misma dimension.
 *
 * Las conversiones de cajas, paquetes y presentaciones
 * se configuran individualmente por producto.
 * ============================================================
 */

CREATE TABLE unidades_medida (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(20) NOT NULL,

    abreviatura VARCHAR(20) NOT NULL,

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
     * Unidad de referencia para conversiones.
     *
     * NULL indica que esta unidad es una unidad base
     * o independiente.
     */
    unidad_base_id BIGINT NULL,

    /*
     * Cantidad de unidades base que representa
     * una unidad de esta medida.
     *
     * Ejemplo:
     * KG = 1
     * GR = 0.001 KG
     * TON = 1000 KG
     */
    factor_conversion DECIMAL(18,9) NOT NULL DEFAULT 1,

    /*
     * Indica si permite cantidades decimales.
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
     * Relacion con la unidad base.
     */
    CONSTRAINT fk_unidad_base
        FOREIGN KEY (unidad_base_id)
        REFERENCES unidades_medida(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validaciones.
     */
    CONSTRAINT chk_unidad_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_unidad_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_unidad_abreviatura
        CHECK (CHAR_LENGTH(TRIM(abreviatura)) > 0),

    CONSTRAINT chk_unidad_factor
        CHECK (factor_conversion > 0),


) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

CREATE INDEX idx_unidad_activo_tipo
    ON unidades_medida(activo, tipo);

CREATE INDEX idx_unidad_base
    ON unidades_medida(unidad_base_id);


/*
 * ============================================================
 * UNIDADES INICIALES
 * ============================================================
 *
 * No se establecen conversiones entre cajas, paquetes,
 * bolsas y piezas porque dependen de cada producto.
 *
 * Las unidades de peso, volumen, longitud y area
 * utilizan una unidad base de referencia.
 * ============================================================
 */

INSERT INTO unidades_medida
(nombre, codigo, abreviatura, tipo, descripcion,
 permite_decimales, factor_conversion)
VALUES

/* ============================================================
 * CONTEO Y PRESENTACIONES
 * ============================================================
 */

('Pieza', 'PZA', 'PZA', 'UNIDAD',
 'Venta por pieza', FALSE, 1),

('Caja', 'CAJ', 'CAJ', 'UNIDAD',
 'Venta por caja. Su contenido depende del producto',
 FALSE, 1),

('Paquete', 'PAQ', 'PAQ', 'UNIDAD',
 'Venta por paquete. Su contenido depende del producto',
 FALSE, 1),

('Bolsa', 'BOL', 'BOL', 'UNIDAD',
 'Venta por bolsa', FALSE, 1),

('Botella', 'BOT', 'BOT', 'UNIDAD',
 'Venta por botella', FALSE, 1),

('Lata', 'LAT', 'LAT', 'UNIDAD',
 'Venta por lata', FALSE, 1),

('Rollo', 'ROL', 'ROL', 'UNIDAD',
 'Venta por rollo', FALSE, 1),

('Sobre', 'SOB', 'SOB', 'UNIDAD',
 'Venta por sobre', FALSE, 1),

('Costal', 'COS', 'COS', 'UNIDAD',
 'Venta por costal', FALSE, 1),

('Bulto', 'BUL', 'BUL', 'UNIDAD',
 'Venta por bulto', FALSE, 1),

('Docena', 'DOC', 'DOC', 'UNIDAD',
 'Venta por docena', FALSE, 1),

('Par', 'PAR', 'PAR', 'UNIDAD',
 'Venta por par', FALSE, 1),

('Cubeta', 'CUB', 'CUB', 'UNIDAD',
 'Venta por cubeta', FALSE, 1),

/* ============================================================
 * PESO
 *
 * Unidad base: KG
 * ============================================================
 */

('Kilogramo', 'KG', 'KG', 'PESO',
 'Unidad base de peso equivalente a mil gramos',
 TRUE, 1),

('Gramo', 'GR', 'GR', 'PESO',
 'Mil gramos equivalen a un kilogramo',
 TRUE, 0.001),

('Tonelada', 'TON', 'TON', 'PESO',
 'Mil kilogramos equivalen a una tonelada',
 TRUE, 1000),

/* ============================================================
 * VOLUMEN
 *
 * Unidad base: LT
 * ============================================================
 */

('Litro', 'LT', 'LT', 'VOLUMEN',
 'Unidad base de volumen',
 TRUE, 1),

('Mililitro', 'ML', 'ML', 'VOLUMEN',
 'Mil mililitros equivalen a un litro',
 TRUE, 0.001),

('Galon', 'GAL', 'GAL', 'VOLUMEN',
 'Galon estadounidense: aproximadamente 3.785412 litros',
 TRUE, 3.785412),

/* ============================================================
 * LONGITUD
 *
 * Unidad base: M
 * ============================================================
 */

('Metro', 'M', 'M', 'LONGITUD',
 'Unidad base de longitud',
 TRUE, 1),

('Centimetro', 'CM', 'CM', 'LONGITUD',
 'Cien centimetros equivalen a un metro',
 TRUE, 0.01),

('Milimetro', 'MM', 'MM', 'LONGITUD',
 'Mil milimetros equivalen a un metro',
 TRUE, 0.001),

/* ============================================================
 * AREA
 *
 * Unidad base: M2
 * ============================================================
 */

('Metro cuadrado', 'M2', 'M2', 'AREA',
 'Unidad base de superficie',
 TRUE, 1);


/*
 * ============================================================
 * ASIGNACION DE UNIDADES BASE
 * ============================================================
 *
 * Las unidades derivadas apuntan a su unidad base.
 * El factor indica cuantas unidades base representa
 * una unidad de medida.
 * ============================================================
 */

/* PESO: KG como unidad base */

UPDATE unidades_medida u
INNER JOIN unidades_medida b
    ON b.codigo = 'KG'
SET u.unidad_base_id = b.id
WHERE u.codigo IN ('GR', 'TON');


/* VOLUMEN: LT como unidad base */

UPDATE unidades_medida u
INNER JOIN unidades_medida b
    ON b.codigo = 'LT'
SET u.unidad_base_id = b.id
WHERE u.codigo IN ('ML', 'GAL');


/* LONGITUD: M como unidad base */

UPDATE unidades_medida u
INNER JOIN unidades_medida b
    ON b.codigo = 'M'
SET u.unidad_base_id = b.id
WHERE u.codigo IN ('CM', 'MM');


/*
 * ============================================================
 * REGLAS DE IMPLEMENTACION EN JAVA
 * ============================================================
 *
 * 1. Validar cantidades decimales segun la unidad
 *    y las restricciones del producto.
 *
 * 2. No permitir cantidades decimales cuando la unidad
 *    y el producto no las permitan.
 *
 * 3. Las conversiones generales solo se permiten entre
 *    unidades de la misma dimension y unidad base.
 *
 * 4. Para convertir a unidad base:
 *
 *    cantidad_base = cantidad * factor_conversion
 *
 * 5. Para convertir desde unidad base:
 *
 *    cantidad_destino = cantidad_base / factor_destino
 *
 * 6. No convertir automaticamente cajas, paquetes,
 *    bolsas o costales a piezas.
 *
 * 7. El contenido de cada presentacion se configura
 *    mediante el factor_conversion de presentaciones,
 *    asociado al producto correspondiente.
 *
 * 8. La aplicacion debe impedir conversiones entre
 *    dimensiones diferentes.
 *
 * 9. Las unidades utilizadas por productos deben
 *    desactivarse en lugar de eliminarse.
 *
 * 10. Permitir registrar nuevas unidades desde
 *     el catalogo administrativo.
 *
 * 11. Los calculos de conversion deben utilizar
 *     BigDecimal en Java.
 *
 * ============================================================
 * FIN DE LA MIGRACION 014
 * ============================================================
 */
