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
 * Clasificacion general de productos.
 *
 * Las categorias iniciales se configuraran segun el giro
 * comercial seleccionado al instalar el sistema.
 * ============================================================
 */

CREATE TABLE categorias (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(20) NOT NULL,

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
     * No repetir nombres ni codigos de categoria.
     */
    CONSTRAINT uk_categoria_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_categoria_codigo
        UNIQUE (codigo),

    /*
     * Validaciones de datos.
     */
    CONSTRAINT chk_categoria_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_categoria_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_categoria_orden
        CHECK (orden >= 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar categorias activas.
 */
CREATE INDEX idx_categoria_activo
    ON categorias(activo);


/*
 * Ordenar categorias en catalogos y formularios.
 */
CREATE INDEX idx_categoria_orden
    ON categorias(orden);


/*
 * ============================================================
 * FIN DE LA MIGRACION
 * ============================================================
 *
 * NOTAS:
 *
 * 1. No se insertan categorias predeterminadas aqui.
 * 2. Las categorias se crearan durante la configuracion
 *    inicial segun el giro comercial.
 * 3. Una categoria utilizada por productos debe desactivarse
 *    en lugar de eliminarse.
 * 4. La aplicacion debe validar que no se repitan nombres
 *    ni codigos.
 * ============================================================
 */
