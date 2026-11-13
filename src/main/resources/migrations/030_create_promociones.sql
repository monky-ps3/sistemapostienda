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
 * MIGRACION 030: PROMOCIONES
 * SistemaPOSTienda
 * ============================================================
 *
 * Catalogo general de promociones.
 *
 * Compatible con diferentes giros comerciales.
 *
 * Tipos contemplados:
 * DESCUENTO_PORCENTAJE
 * DESCUENTO_MONTO
 * PRECIO_FIJO
 * COMPRA_X_PAGA_Y
 * N_POR_PRECIO
 *
 * La aplicacion Java sera responsable de calcular
 * y aplicar correctamente cada tipo de promocion.
 * ============================================================
 */

CREATE TABLE promociones (

    id BIGINT NOT NULL AUTO_INCREMENT,

    codigo VARCHAR(40) NOT NULL,

    nombre VARCHAR(150) NOT NULL,

    descripcion VARCHAR(500) NULL,

    tipo_promocion VARCHAR(50) NOT NULL,

    /*
     * Vigencia de la promocion.
     */
    fecha_inicio DATE NOT NULL,

    fecha_fin DATE NOT NULL,

    hora_inicio TIME NULL,

    hora_fin TIME NULL,

    /*
     * Dias de aplicacion.
     *
     * Si todos son FALSE, aplica todos los dias.
     */
    lunes BOOLEAN NOT NULL DEFAULT FALSE,

    martes BOOLEAN NOT NULL DEFAULT FALSE,

    miercoles BOOLEAN NOT NULL DEFAULT FALSE,

    jueves BOOLEAN NOT NULL DEFAULT FALSE,

    viernes BOOLEAN NOT NULL DEFAULT FALSE,

    sabado BOOLEAN NOT NULL DEFAULT FALSE,

    domingo BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Valores segun el tipo de promocion.
     */
    cantidad_compra DECIMAL(15,4) NULL,

    cantidad_paga DECIMAL(15,4) NULL,

    porcentaje_descuento DECIMAL(6,2) NULL,

    monto_descuento DECIMAL(14,2) NULL,

    precio_promocion DECIMAL(14,2) NULL,

    /*
     * Limite maximo de descuento monetario.
     * NULL significa sin limite adicional.
     */
    descuento_maximo DECIMAL(14,2) NULL,

    /*
     * Importe minimo de compra para aplicar.
     * NULL significa que no existe minimo.
     */
    compra_minima DECIMAL(14,2) NULL,

    /*
     * Limites de uso.
     *
     * NULL significa sin limite.
     */
    limite_aplicaciones INT NULL,

    limite_por_cliente INT NULL,

    /*
     * Prioridad:
     * Un numero menor representa mayor prioridad.
     */
    prioridad INT NOT NULL DEFAULT 1,

    /*
     * Indica si puede combinarse con otras promociones.
     */
    acumulable BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Estado administrativo.
     */
    estado ENUM(
        'BORRADOR',
        'PROGRAMADA',
        'ACTIVA',
        'PAUSADA',
        'FINALIZADA',
        'CANCELADA'
    ) NOT NULL DEFAULT 'BORRADOR',

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    /*
     * Identificadores unicos.
     */
    CONSTRAINT uk_promocion_codigo
        UNIQUE (codigo),

    /*
     * Validaciones generales.
     */
    CONSTRAINT chk_promocion_codigo
        CHECK (CHAR_LENGTH(TRIM(codigo)) > 0),

    CONSTRAINT chk_promocion_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_promocion_tipo
        CHECK (CHAR_LENGTH(TRIM(tipo_promocion)) > 0),

    CONSTRAINT chk_promocion_fechas
        CHECK (fecha_fin >= fecha_inicio),

    CONSTRAINT chk_promocion_horarios
        CHECK (
            (hora_inicio IS NULL AND hora_fin IS NULL)
            OR
            (
                hora_inicio IS NOT NULL
                AND hora_fin IS NOT NULL
                AND hora_fin > hora_inicio
            )
        ),

    /*
     * Validaciones de cantidades.
     */
    CONSTRAINT chk_promocion_cantidad_compra
        CHECK (
            cantidad_compra IS NULL
            OR cantidad_compra > 0
        ),

    CONSTRAINT chk_promocion_cantidad_paga
        CHECK (
            cantidad_paga IS NULL
            OR cantidad_paga > 0
        ),

    /*
     * Validaciones de descuentos.
     */
    CONSTRAINT chk_promocion_porcentaje
        CHECK (
            porcentaje_descuento IS NULL
            OR (
                porcentaje_descuento > 0
                AND porcentaje_descuento <= 100
            )
        ),

    CONSTRAINT chk_promocion_monto
        CHECK (
            monto_descuento IS NULL
            OR monto_descuento > 0
        ),

    CONSTRAINT chk_promocion_precio
        CHECK (
            precio_promocion IS NULL
            OR precio_promocion >= 0
        ),

    CONSTRAINT chk_promocion_descuento_maximo
        CHECK (
            descuento_maximo IS NULL
            OR descuento_maximo > 0
        ),

    CONSTRAINT chk_promocion_compra_minima
        CHECK (
            compra_minima IS NULL
            OR compra_minima >= 0
        ),

    /*
     * Validaciones de limites.
     */
    CONSTRAINT chk_promocion_limite
        CHECK (
            limite_aplicaciones IS NULL
            OR limite_aplicaciones > 0
        ),

    CONSTRAINT chk_promocion_limite_cliente
        CHECK (
            limite_por_cliente IS NULL
            OR limite_por_cliente > 0
        ),

    CONSTRAINT chk_promocion_prioridad
        CHECK (prioridad > 0),

    /*
     * Validacion de parametros segun el tipo.
     */
    CONSTRAINT chk_promocion_parametros
        CHECK (
            (
                tipo_promocion = 'DESCUENTO_PORCENTAJE'
                AND porcentaje_descuento IS NOT NULL
            )
            OR
            (
                tipo_promocion = 'DESCUENTO_MONTO'
                AND monto_descuento IS NOT NULL
            )
            OR
            (
                tipo_promocion = 'PRECIO_FIJO'
                AND precio_promocion IS NOT NULL
            )
            OR
            (
                tipo_promocion = 'COMPRA_X_PAGA_Y'
                AND cantidad_compra IS NOT NULL
                AND cantidad_paga IS NOT NULL
                AND cantidad_paga < cantidad_compra
            )
            OR
            (
                tipo_promocion = 'N_POR_PRECIO'
                AND cantidad_compra IS NOT NULL
                AND precio_promocion IS NOT NULL
            )
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
 * Consultar promociones por nombre.
 */
CREATE INDEX idx_promocion_nombre
    ON promociones(nombre);


/*
 * Buscar promociones por tipo.
 */
CREATE INDEX idx_promocion_tipo
    ON promociones(tipo_promocion);


/*
 * Consultar promociones por vigencia.
 */
CREATE INDEX idx_promocion_fechas
    ON promociones(fecha_inicio, fecha_fin);


/*
 * Consultar promociones por estado y vigencia.
 */
CREATE INDEX idx_promocion_estado_fechas
    ON promociones(estado, fecha_inicio, fecha_fin);


/*
 * Consultar promociones por prioridad.
 */
CREATE INDEX idx_promocion_prioridad
    ON promociones(prioridad);


/*
 * ============================================================
 * REGLAS DE IMPLEMENTACION EN JAVA
 * ============================================================
 *
 * 1. La aplicacion debe comprobar la vigencia por fecha,
 *    horario y dia de la semana.
 *
 * 2. Si todos los dias estan en FALSE, aplica todos los dias.
 *
 * 3. El estado ACTIVA no sustituye la validacion de fechas.
 *
 * 4. PROGRAMADA, PAUSADA, FINALIZADA y CANCELADA
 *    no deben aplicarse a nuevas ventas.
 *
 * 5. El limite global y el limite por cliente deben
 *    comprobarse dentro de una transaccion.
 *
 * 6. El descuento nunca debe superar el importe elegible.
 *
 * 7. descuento_maximo limita el descuento calculado.
 *
 * 8. compra_minima se evalua sobre el importe elegible
 *    definido por las reglas de la promocion.
 *
 * 9. La prioridad y acumulabilidad deben utilizarse
 *    para resolver promociones coincidentes.
 *
 * 10. Los importes deben calcularse con BigDecimal
 *     y reglas de redondeo monetario consistentes.
 *
 * 11. No eliminar promociones que ya hayan sido
 *     utilizadas en ventas.
 *
 * 12. La venta debe conservar el descuento aplicado
 *     como dato historico en sus detalles.
 *
 * 13. Los tipos de promocion adicionales requieren
 *     soporte explicito en Java y validacion de parametros.
 *
 * ============================================================
 * FIN DE LA MIGRACION 030
 * ============================================================
 */
