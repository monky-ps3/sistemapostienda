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
 * MIGRACION 016: PRESENTACIONES
 * SistemaPOSTienda
 * ============================================================
 *
 * Permite manejar diferentes presentaciones de un producto.
 *
 * Ejemplos:
 * Producto: Refresco
 * - Pieza: factor 1
 * - Paquete de 6: factor 6
 * - Caja de 12: factor 12
 *
 * El factor indica cuantas unidades base representa
 * cada presentacion.
 * ============================================================
 */

CREATE TABLE presentaciones (

    id BIGINT NOT NULL AUTO_INCREMENT,

    producto_id BIGINT NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    abreviatura VARCHAR(20) NULL,

    /*
     * Cantidad de unidades base que contiene
     * una unidad de esta presentacion.
     *
     * Ejemplo:
     * Pieza = 1
     * Caja de 12 = 12
     * Paquete de 6 = 6
     */
    factor_conversion DECIMAL(15,6) NOT NULL DEFAULT 1,

    /*
     * Presentacion utilizada inicialmente
     * al seleccionar el producto.
     */
    es_predeterminada BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Indica si se puede vender esta presentacion.
     */
    permite_venta BOOLEAN NOT NULL DEFAULT TRUE,

    activa BOOLEAN NOT NULL DEFAULT TRUE,

    orden INT NOT NULL DEFAULT 1,

    observaciones VARCHAR(255) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    /*
     * Columna calculada para garantizar que cada producto
     * tenga como maximo una presentacion predeterminada.
     */
    producto_predeterminado_id BIGINT
        GENERATED ALWAYS AS (
            CASE
                WHEN es_predeterminada = TRUE THEN producto_id
                ELSE NULL
            END
        ) STORED,

    PRIMARY KEY (id),

    /*
     * No repetir el nombre de una presentacion
     * dentro del mismo producto.
     */
    CONSTRAINT uk_presentacion_producto_nombre
        UNIQUE (producto_id, nombre),

    /*
     * Solo una presentacion predeterminada por producto.
     */
    CONSTRAINT uk_presentacion_predeterminada
        UNIQUE (producto_predeterminado_id),

    /*
     * Validaciones.
     */
    CONSTRAINT chk_presentacion_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_presentacion_abreviatura
        CHECK (
            abreviatura IS NULL
            OR CHAR_LENGTH(TRIM(abreviatura)) > 0
        ),

    CONSTRAINT chk_presentacion_factor
        CHECK (factor_conversion > 0),

    CONSTRAINT chk_presentacion_orden
        CHECK (orden > 0),

    /*
     * Relacion con productos.
     * No eliminar productos que tengan presentaciones.
     */
    CONSTRAINT fk_presentacion_producto
        FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar presentaciones activas de cada producto.
 */
CREATE INDEX idx_presentacion_producto_activa
    ON presentaciones(producto_id, activa);


/*
 * Consultar presentaciones disponibles para venta.
 */
CREATE INDEX idx_presentacion_venta
    ON presentaciones(permite_venta, activa);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION JAVA:
 *
 * 1. Cada producto debe tener una presentacion base
 *    con factor_conversion = 1.
 *
 * 2. Cada producto debe tener una sola presentacion
 *    predeterminada.
 *
 * 3. El factor representa la cantidad de unidades base
 *    contenidas en la presentacion.
 *
 * 4. Al vender una presentacion, el inventario debe
 *    descontarse multiplicando la cantidad vendida
 *    por su factor de conversion.
 *
 * 5. No permitir factores iguales o menores que cero.
 *
 * 6. No permitir vender presentaciones inactivas.
 *
 * 7. No eliminar presentaciones que tengan precios,
 *    codigos de barras o ventas relacionadas.
 *
 * 8. La aplicacion debe crear automaticamente la
 *    presentacion base al registrar un producto.
 *
 * 9. Los precios se administran por presentacion
 *    en la tabla precios.
 *
 * IMPORTANTE:
 * La aplicacion debe validar que exista una presentacion
 * predeterminada y una presentacion base por producto.
 * La base de datos garantiza como maximo una predeterminada,
 * pero no garantiza por si sola que exista una.
 * ============================================================
 */
