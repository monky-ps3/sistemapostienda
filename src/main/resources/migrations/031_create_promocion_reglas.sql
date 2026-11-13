/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */


/*
 * ============================================================
 * MIGRACION 031: REGLAS DE PROMOCIONES
 * SistemaPOSTienda
 * ============================================================
 *
 * Define los objetivos sobre los que se aplican
 * las promociones.
 *
 * Objetivos disponibles:
 * PRODUCTO
 * CATEGORIA
 * MARCA
 * PRESENTACION
 * TODA_TIENDA
 *
 * Tipos de regla:
 * INCLUIR
 * EXCLUIR
 *
 * La validacion de objetivo_id se realiza desde Java
 * porque puede hacer referencia a diferentes tablas.
 * ============================================================
 */

CREATE TABLE promocion_reglas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    promocion_id BIGINT NOT NULL,

    /*
     * Tipo de objetivo de la promocion.
     */
    tipo_objetivo VARCHAR(30) NOT NULL,

    /*
     * Identificador del objetivo.
     *
     * PRODUCTO      -> productos.id
     * CATEGORIA     -> categorias.id
     * MARCA         -> marcas.id
     * PRESENTACION  -> presentaciones.id
     *
     * TODA_TIENDA   -> NULL
     */
    objetivo_id BIGINT NULL,

    /*
     * INCLUIR: el objetivo participa.
     * EXCLUIR: el objetivo no participa.
     */
    tipo_regla VARCHAR(20) NOT NULL DEFAULT 'INCLUIR',

    /*
     * Orden de evaluacion.
     */
    orden INT NOT NULL DEFAULT 1,

    /*
     * Normaliza el objetivo NULL para poder impedir
     * reglas duplicadas de tipo TODA_TIENDA.
     *
     * El valor cero se reserva para TODA_TIENDA.
     * Los identificadores reales deben ser positivos.
     */
    objetivo_clave BIGINT GENERATED ALWAYS AS (
        COALESCE(objetivo_id, 0)
    ) STORED,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Evitar duplicar la misma regla para una promocion.
     */
    CONSTRAINT uk_promocion_regla_objetivo
        UNIQUE (
            promocion_id,
            tipo_objetivo,
            objetivo_clave,
            tipo_regla
        ),

    /*
     * Relacion con la promocion.
     *
     * Se utiliza RESTRICT para proteger el historial.
     */
    CONSTRAINT fk_promocion_reglas_promocion
        FOREIGN KEY (promocion_id)
        REFERENCES promociones(id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,

    /*
     * Validaciones.
     */
    CONSTRAINT chk_promocion_regla_tipo_objetivo
        CHECK (
            tipo_objetivo IN (
                'PRODUCTO',
                'CATEGORIA',
                'MARCA',
                'PRESENTACION',
                'TODA_TIENDA'
            )
        ),

    CONSTRAINT chk_promocion_regla_objetivo
        CHECK (
            (
                tipo_objetivo = 'TODA_TIENDA'
                AND objetivo_id IS NULL
            )
            OR
            (
                tipo_objetivo <> 'TODA_TIENDA'
                AND objetivo_id IS NOT NULL
                AND objetivo_id > 0
            )
        ),

    CONSTRAINT chk_promocion_regla_tipo
        CHECK (
            tipo_regla IN ('INCLUIR', 'EXCLUIR')
        ),

    CONSTRAINT chk_promocion_regla_orden
        CHECK (orden > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

/*
 * Consultar reglas por promocion.
 */
CREATE INDEX idx_promocion_reglas_promocion
    ON promocion_reglas(promocion_id);


/*
 * Consultar reglas por tipo de objetivo.
 */
CREATE INDEX idx_promocion_reglas_tipo_objetivo
    ON promocion_reglas(tipo_objetivo);


/*
 * Consultar objetivos relacionados.
 */
CREATE INDEX idx_promocion_reglas_objetivo
    ON promocion_reglas(tipo_objetivo, objetivo_id);


/*
 * Consultar reglas de inclusion y exclusion.
 */
CREATE INDEX idx_promocion_reglas_tipo
    ON promocion_reglas(tipo_regla);


/*
 * ============================================================
 * REGLAS DE IMPLEMENTACION EN JAVA
 * ============================================================
 *
 * 1. PRODUCTO:
 *    objetivo_id debe existir en productos.
 *
 * 2. CATEGORIA:
 *    objetivo_id debe existir en categorias.
 *
 * 3. MARCA:
 *    objetivo_id debe existir en marcas.
 *
 * 4. PRESENTACION:
 *    objetivo_id debe existir en presentaciones.
 *
 * 5. TODA_TIENDA:
 *    objetivo_id debe ser NULL.
 *
 * 6. Validar que los objetivos existan y esten activos.
 *
 * 7. No permitir reglas duplicadas.
 *
 * 8. Si una promocion no tiene reglas, no debe aplicarse
 *    automaticamente a toda la tienda.
 *
 * 9. Si existe una regla TODA_TIENDA de inclusion,
 *    la promocion puede aplicarse a todos los productos
 *    elegibles, respetando las reglas de exclusion.
 *
 * 10. Las reglas EXCLUIR deben tener prioridad sobre
 *     las reglas INCLUIR para el mismo objetivo.
 *
 * 11. La aplicacion debe definir como resolver reglas
 *     de diferentes niveles: producto, presentacion,
 *     categoria y marca.
 *
 * 12. No eliminar reglas de promociones que ya hayan
 *     sido utilizadas en ventas.
 *
 * 13. No eliminar promociones que tengan reglas
 *     relacionadas o historial de uso.
 *
 * ============================================================
 * FIN DE LA MIGRACION 031
 * ============================================================
 */
