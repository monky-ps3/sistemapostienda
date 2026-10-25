/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */
CREATE TABLE configuraciones (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Datos generales del negocio
     */
    nombre_empresa VARCHAR(150) NOT NULL,

    razon_social VARCHAR(200) NULL,

    rfc VARCHAR(20) NULL,

    telefono VARCHAR(20) NULL,

    correo VARCHAR(150) NULL,

    sitio_web VARCHAR(150) NULL,

    direccion VARCHAR(300) NULL,

    logo VARCHAR(255) NULL,

    /*
     * Configuración de impuestos
     */
    usar_iva BOOLEAN NOT NULL DEFAULT TRUE,

    porcentaje_iva DECIMAL(5,2) NOT NULL DEFAULT 16.00,

    incluir_iva_en_precio BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Configuración de ventas
     */
    permitir_venta_sin_existencia BOOLEAN NOT NULL DEFAULT FALSE,

    permitir_precio_libre BOOLEAN NOT NULL DEFAULT FALSE,

    permitir_descuentos BOOLEAN NOT NULL DEFAULT TRUE,

    solicitar_cliente_en_venta BOOLEAN NOT NULL DEFAULT FALSE,

    permitir_cliente_generico BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Configuración de caja
     */
    solicitar_apertura_caja BOOLEAN NOT NULL DEFAULT TRUE,

    solicitar_cierre_caja BOOLEAN NOT NULL DEFAULT TRUE,

    fondo_inicial_sugerido DECIMAL(12,2) NOT NULL DEFAULT 0.00,

    /*
     * Inventario
     */
    manejar_inventario BOOLEAN NOT NULL DEFAULT TRUE,

    manejar_caducidades BOOLEAN NOT NULL DEFAULT FALSE,

    manejar_lotes BOOLEAN NOT NULL DEFAULT FALSE,

    permitir_existencias_negativas BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Promociones
     */
    usar_promociones BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Hardware
     */
    usar_lector_codigo BOOLEAN NOT NULL DEFAULT TRUE,

    usar_bascula BOOLEAN NOT NULL DEFAULT FALSE,

    usar_cajon_dinero BOOLEAN NOT NULL DEFAULT FALSE,

    usar_impresora_ticket BOOLEAN NOT NULL DEFAULT TRUE,

    /*
     * Ticket
     */
    mensaje_ticket VARCHAR(255) NULL,

    mostrar_logo_ticket BOOLEAN NOT NULL DEFAULT FALSE,

    imprimir_copia_ticket BOOLEAN NOT NULL DEFAULT FALSE,

    /*
     * Estado
     */
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY(id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;



CREATE INDEX idx_configuracion_activo
ON configuraciones(activo);