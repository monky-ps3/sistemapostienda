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
 * MIGRACION 012: CATEGORIAS
 * SistemaPOSTienda
 * ============================================================
 *
 * Catalogo general de categorias y subcategorias.
 *
 * Compatible con diferentes giros comerciales:
 * - Abarrotes
 * - Farmacias
 * - Ferreterias
 * - Electronica
 * - Refaccionarias
 * - Ropa y calzado
 * - Papelerias
 * - Otros negocios
 *
 * Las categorias se configuraran durante la instalacion
 * inicial del sistema.
 * ============================================================
 */

CREATE TABLE categorias (

    id BIGINT NOT NULL AUTO_INCREMENT,

    categoria_padre_id BIGINT NULL,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(30) NOT NULL,

    descripcion VARCHAR(255) NULL,

    color VARCHAR(20) NULL,

    icono VARCHAR(100) NULL,

    orden INT NOT NULL DEFAULT 0,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Evitar nombres y codigos duplicados.
     */
    CONSTRAINT uk_categoria_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_categoria_codigo
        UNIQUE (codigo),

    /*
     * Relacion entre categorias principales
     * y subcategorias.
     */
    CONSTRAINT fk_categoria_padre
        FOREIGN KEY (categoria_padre_id)
        REFERENCES categorias(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validaciones de datos.
     */
    CONSTRAINT chk_categoria_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_categoria_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_categoria_orden
        CHECK (orden >= 0),

    CONSTRAINT chk_categoria_color
        CHECK (
            color IS NULL
            OR CHAR_LENGTH(TRIM(color)) > 0
        ),

    CONSTRAINT chk_categoria_icono
        CHECK (
            icono IS NULL
            OR CHAR_LENGTH(TRIM(icono)) > 0
        ),

    /*
     * Una categoria no puede ser su propio padre.
     */
    CONSTRAINT chk_categoria_padre
        CHECK (
            categoria_padre_id IS NULL
            OR categoria_padre_id <> id
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar categorias por categoria principal.
 */
CREATE INDEX idx_categoria_padre
    ON categorias(categoria_padre_id);


/*
 * Consultar categorias activas.
 */
CREATE INDEX idx_categoria_activo
    ON categorias(activo);


/*
 * Consultar categorias activas ordenadas.
 */
CREATE INDEX idx_categoria_activo_orden
    ON categorias(activo, orden);


/*
 * Consultar subcategorias activas.
 */
CREATE INDEX idx_categoria_padre_activo
    ON categorias(categoria_padre_id, activo);


/*
 * ============================================================
 * REGLAS DE IMPLEMENTACION EN JAVA
 * ============================================================
 *
 * 1. categoria_padre_id NULL indica una categoria principal.
 *
 * 2. categoria_padre_id con un ID indica una subcategoria.
 *
 * 3. No se insertan categorias predeterminadas.
 *    Se configuraran segun el giro comercial.
 *
 * 4. Una categoria utilizada por productos debe
 *    desactivarse en lugar de eliminarse.
 *
 * 5. No permitir que una categoria sea su propio padre.
 *
 * 6. Evitar ciclos indirectos entre categorias.
 *
 * 7. Validar que la categoria padre exista y este activa.
 *
 * 8. No permitir eliminar categorias que tengan productos
 *    o subcategorias relacionadas.
 *
 * 9. Al desactivar una categoria principal, validar que
 *    sus subcategorias no queden disponibles para nuevos
 *    productos.
 *
 * 10. La aplicacion debe validar nombres y codigos
 *     duplicados antes de guardar.
 *
 * ============================================================
 * FIN DE LA MIGRACION 012
 * ============================================================
 */
