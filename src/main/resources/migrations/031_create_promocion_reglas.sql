/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */

CREATE TABLE promocion_reglas (

    id BIGINT NOT NULL AUTO_INCREMENT,

    promocion_id BIGINT NOT NULL,

    /*
     * Define sobre qué aplica la promoción.
     *
     * Valores sugeridos:
     * PRODUCTO
     * CATEGORIA
     * MARCA
     * PRESENTACION
     * TODA_TIENDA
     */
    tipo_objetivo VARCHAR(30) NOT NULL,

    /*Indica si aplica a un PRODUCTO, CATEGORIA, MARCA, PRESENTACION o TODA_TIENDA.
     * Id del elemento.
     *
     * PRODUCTO      -> productos.id
     * CATEGORIA     -> categorias.id
     * MARCA         -> marcas.id
     * PRESENTACION  -> presentaciones.id
     *
     * Para TODA_TIENDA se deja NULL.
     */
    objetivo_id BIGINT NULL,

    /*ID del elemento al que aplica la regla. En TODA_TIENDA será NULL.
     * Tipo de regla.
     *
     * INCLUIR
     * EXCLUIR
     */
    tipo_regla VARCHAR(20) NOT NULL DEFAULT 'INCLUIR',

    /*Define si la regla INCLUYE o EXCLUYE el objetivo.
     * Orden de evaluación.
     */
    orden INT NOT NULL DEFAULT 1,
   /*Permite ordenar las reglas si en el futuro es necesario. */
    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),

    CONSTRAINT fk_promocion_reglas_promocion
        FOREIGN KEY (promocion_id)
        REFERENCES promociones(id)
        ON DELETE CASCADE

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;



CREATE INDEX idx_promocion_reglas_promocion
ON promocion_reglas(promocion_id);



CREATE INDEX idx_promocion_reglas_tipo
ON promocion_reglas(tipo_objetivo);



CREATE INDEX idx_promocion_reglas_objetivo
ON promocion_reglas(objetivo_id);



CREATE INDEX idx_promocion_reglas_regla
ON promocion_reglas(tipo_regla);