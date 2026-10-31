/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */
/*

/*
 * ============================================================
 * MIGRACION 015: PRODUCTOS
 * SistemaPOSTienda
 * ============================================================
 *
 * Catalogo principal de productos.
 *
 * El inventario actual se controla en la tabla inventarios.
 * Los precios se administran mediante presentaciones y listas.
 * Los codigos de barras se registran por presentacion.
 * ============================================================
 */

CREATE TABLE productos (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Clasificacion del producto.
     */
    categoria_id BIGINT NOT NULL,

    /*
     * La marca es opcional.
     */
    marca_id BIGINT NULL,

    /*
     * Unidad base para controlar el producto.
     */
    unidad_medida_id BIGINT NOT NULL,

    /*
     * Codigo interno unico del producto.
     * Los codigos de barras se manejaran en otra tabla.
     */
    codigo VARCHAR(50) NOT NULL,

    /*
     * SKU opcional para control interno.
     */
    sku VARCHAR(100) NULL,

    nombre VARCHAR(200) NOT NULL,

    descripcion TEXT NULL,

    imagen VARCHAR(255) NULL,

    /*
     * Configuracion de IVA.
     *
     * Si aplica_iva es FALSE, porcentaje_iva debe ser 0.
     * Si aplica_iva es TRUE, puede manejar tasas de 0 a 100.
     *
     * La tasa aplicable debe configurarse segun el producto
     * y las disposiciones fiscales correspondientes.
     */
    aplica_iva BOOLEAN NOT NULL DEFAULT TRUE,

    porcentaje_iva DECIMAL(5,2) NOT NULL DEFAULT 16.00,

    /*
     * Control de inventario.
     */
    maneja_inventario BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Control de lotes y caducidad.
     */
    maneja_lotes BOOLEAN NOT NULL DEFAULT FALSE,

    maneja_caducidad BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Venta por peso o cantidades fraccionarias.
     */
    venta_por_peso BOOLEAN NOT NULL DEFAULT FALSE,

    permite_decimales BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Existencias minimas y maximas recomendadas.
     * El stock actual se controla en inventarios.
     */
    stock_minimo DECIMAL(12,3) NOT NULL DEFAULT 0,

    stock_maximo DECIMAL(12,3) NULL,

    /*
     * Permitir descuentos sobre el producto.
     */
    permite_descuento BOOLEAN NOT NULL DEFAULT TRUE,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Codigos unicos.
     */
    CONSTRAINT uk_producto_codigo
        UNIQUE (codigo),

    CONSTRAINT uk_producto_sku
        UNIQUE (sku),

    /*
     * Validaciones de datos.
     */
    CONSTRAINT chk_producto_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_producto_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_producto_iva
        CHECK (
            porcentaje_iva >= 0
            AND porcentaje_iva <= 100
            AND (
                aplica_iva = TRUE
                OR porcentaje_iva = 0
            )
        ),

    CONSTRAINT chk_producto_stock_minimo
        CHECK (stock_minimo >= 0),

    CONSTRAINT chk_producto_stock_maximo
        CHECK (
            stock_maximo IS NULL
            OR stock_maximo >= stock_minimo
        ),

    /*
     * Un producto vendido por peso debe aceptar decimales.
     */
    CONSTRAINT chk_producto_peso_decimales
        CHECK (
            venta_por_peso = FALSE
            OR permite_decimales = TRUE
        ),

    /*
     * Para controlar caducidad se requiere control por lotes.
     */
    CONSTRAINT chk_producto_caducidad_lotes
        CHECK (
            maneja_caducidad = FALSE
            OR maneja_lotes = TRUE
        ),

    /*
     * Relaciones.
     * No eliminar catalogos que tengan productos relacionados.
     */
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categorias(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_producto_marca
        FOREIGN KEY (marca_id)
        REFERENCES marcas(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    CONSTRAINT fk_producto_unidad
        FOREIGN KEY (unidad_medida_id)
        REFERENCES unidades_medida(id)
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
 * Busqueda por nombre.
 */
CREATE INDEX idx_producto_nombre
    ON productos(nombre);


/*
 * Productos por categoria y estado.
 */
CREATE INDEX idx_producto_categoria_activo
    ON productos(categoria_id, activo);


/*
 * Productos por marca.
 */
CREATE INDEX idx_producto_marca
    ON productos(marca_id);


/*
 * Productos por unidad de medida.
 */
CREATE INDEX idx_producto_unidad
    ON productos(unidad_medida_id);


/*
 * Consulta de productos activos.
 */
CREATE INDEX idx_producto_activo
    ON productos(activo);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * REGLAS PARA LA APLICACION JAVA:
 *
 * 1. No eliminar productos que tengan movimientos o ventas.
 *    Utilizar el estado activo/inactivo.
 * 2. Los codigos de barras se administran por presentacion.
 * 3. Validar que la unidad permita la cantidad capturada.
 * 4. Los productos por peso deben aceptar cantidades decimales.
 * 5. La caducidad requiere control por lotes.
 * 6. El stock actual se obtiene de inventarios.
 * 7. El porcentaje de IVA debe configurarse correctamente.
 * 8. Las operaciones de inventario deben quedar registradas
 *    en el kardex.
 * ============================================================
 */
