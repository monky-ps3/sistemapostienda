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
TABLA: productos
============================================================

Catálogo principal de productos del sistema.

Relaciones:
    categorias
    marcas
    unidades_medida

El inventario actual NO se almacena aquí.
Posteriormente será manejado por la tabla:
    inventarios

============================================================
*/

CREATE TABLE productos (

    /*
     * Identificador único del producto.
     */
    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Categoría a la que pertenece el producto.
     */
    categoria_id BIGINT NOT NULL,

    /*
     * Marca del producto.
     *
     * Es opcional porque puede haber productos
     * sin una marca específica.
     */
    marca_id BIGINT,

    /*
     * Unidad utilizada para comprar, vender
     * y controlar el producto.
     */
    unidad_medida_id BIGINT NOT NULL,

    /*
     * Código principal del producto.
     *
     * Puede utilizarse con el lector de códigos
     * de barras.
     */
    codigo VARCHAR(50) NOT NULL,

    /*
     * Código interno utilizado para identificar
     * el producto dentro del negocio.
     */
    sku VARCHAR(100),

    /*
     * Nombre del producto.
     */
    nombre VARCHAR(200) NOT NULL,

    /*
     * Descripción adicional.
     */
    descripcion TEXT,

    /*
     * Ruta o nombre de la imagen del producto.
     */
    imagen VARCHAR(255),

    /*
     * Indica si el producto causa IVA.
     */
    aplica_iva BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Porcentaje de IVA específico del producto.
     *
     * Por defecto utiliza 16%.
     */
    porcentaje_iva DECIMAL(5,2) NOT NULL DEFAULT 16.00,

    /*
     * Indica si el producto participa
     * en el control de inventario.
     */
    maneja_inventario BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Indica si el producto utiliza lotes.
     */
    maneja_lotes BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Indica si el producto controla
     * fechas de caducidad.
     */
    maneja_caducidad BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Indica si el producto se vende por peso.
     *
     * Ejemplo:
     * azúcar, arroz, frutas, etc.
     */
    venta_por_peso BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Indica si permite cantidades decimales.
     *
     * Ejemplo:
     * 0.500 kg
     * 1.250 kg
     * 2.350 metros
     */
    permite_decimales BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Cantidad mínima recomendada de inventario.
     *
     * El stock actual se manejará posteriormente
     * en la tabla inventarios.
     */
    stock_minimo DECIMAL(12,3) NOT NULL DEFAULT 0,

    /*
     * Cantidad máxima recomendada de inventario.
     */
    stock_maximo DECIMAL(12,3),

    /*
     * Indica si se pueden aplicar descuentos
     * directamente al producto.
     */
    permite_descuento BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Estado del producto.
     */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Fecha de creación.
     */
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    /*
     * Fecha de última actualización.
     */
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,


    /*
    ========================================================
    LLAVE PRIMARIA
    ========================================================
    */

    PRIMARY KEY (id),


    /*
    ========================================================
    RESTRICCIONES UNIQUE
    ========================================================
    */

    /*
     * No permite dos productos con el mismo código.
     */
    CONSTRAINT uk_producto_codigo
        UNIQUE (codigo),

    /*
     * No permite dos productos con el mismo SKU.
     */
    CONSTRAINT uk_producto_sku
        UNIQUE (sku),


    /*
    ========================================================
    LLAVES FORÁNEAS
    ========================================================
    */

    /*
     * Relación con categorías.
     */
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categorias(id),

    /*
     * Relación con marcas.
     */
    CONSTRAINT fk_producto_marca
        FOREIGN KEY (marca_id)
        REFERENCES marcas(id),

    /*
     * Relación con unidades de medida.
     */
    CONSTRAINT fk_producto_unidad
        FOREIGN KEY (unidad_medida_id)
        REFERENCES unidades_medida(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
============================================================
ÍNDICES
============================================================
*/

/*
 * Facilita las búsquedas por nombre.
 */
CREATE INDEX idx_producto_nombre
ON productos(nombre);


/*
 * Facilita búsquedas y consultas
 * por categoría.
 */
CREATE INDEX idx_producto_categoria
ON productos(categoria_id);


/*
 * Facilita búsquedas y consultas
 * por marca.
 */
CREATE INDEX idx_producto_marca
ON productos(marca_id);


/*
 * Facilita consultas de productos activos.
 */
CREATE INDEX idx_producto_activo
ON productos(activo);