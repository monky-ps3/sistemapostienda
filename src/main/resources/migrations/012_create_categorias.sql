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
 * abarrotes, farmacia, ferreteria, electronica,
 * refaccionaria, ropa, entre otros.
 *
 * Las categorias iniciales se configuraran durante
 * la instalacion del sistema.
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
     * Relacion de categoria principal y subcategoria.
     */
    CONSTRAINT fk_categoria_padre
        FOREIGN KEY (categoria_padre_id)
        REFERENCES categorias(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validaciones.
     */
    CONSTRAINT chk_categoria_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_categoria_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_categoria_orden
        CHECK (orden >= 0),

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
 * Consultar categorias por estado y orden.
 */
CREATE INDEX idx_categoria_activo_orden
    ON categorias(activo, orden);


/*
 * Consultar subcategorias activas de una categoria.
 */
CREATE INDEX idx_categoria_padre_activo
    ON categorias(categoria_padre_id, activo);


/*
 * ============================================================
 * NOTAS DE IMPLEMENTACION
 * ============================================================
 *
 * 1. categoria_padre_id NULL indica que es una categoria
 *    principal.
 *
 * 2. categoria_padre_id con un ID indica que es una
 *    subcategoria.
 *
 * 3. No se insertan categorias predeterminadas.
 *    Se configuraran segun el giro comercial.
 *
 * 4. Una categoria utilizada por productos debe
 *    desactivarse en lugar de eliminarse.
 *
 * 5. La aplicacion debe validar que una categoria
 *    no se asigne a si misma ni se creen ciclos
 *    entre categorias y subcategorias.
 *
 * 6. La aplicacion debe validar que la categoria
 *    padre exista y se encuentre activa al registrar
 *    una subcategoria.
 *
 * 7. No permitir eliminar categorias que tengan
 *    productos o subcategorias relacionadas.
 *
 * ============================================================
 * FIN DE LA MIGRACION 012
 * ============================================================
 */
