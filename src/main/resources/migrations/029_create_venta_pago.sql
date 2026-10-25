/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 10 jul 2026
 */

CREATE TABLE ventas_pago (

    id BIGINT NOT NULL AUTO_INCREMENT,

    venta_id BIGINT NOT NULL,

    forma_pago_id BIGINT NOT NULL,

    usuario_id BIGINT NOT NULL,

    caja_id BIGINT NULL,

    sesion_caja_id BIGINT NULL,

    tipo_pago ENUM(
        'PAGO_VENTA',
        'ABONO_CREDITO'
    ) NOT NULL DEFAULT 'PAGO_VENTA',

    monto DECIMAL(12,2) NOT NULL,

    monto_recibido DECIMAL(12,2) NOT NULL,

    cambio DECIMAL(12,2) NOT NULL DEFAULT 0,

    referencia VARCHAR(150) NULL,

    numero_operacion VARCHAR(100) NULL,

    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observaciones VARCHAR(255) NULL,

    estado ENUM(
        'CONFIRMADO',
        'CANCELADO'
    ) NOT NULL DEFAULT 'CONFIRMADO',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_ventas_pago_venta
        FOREIGN KEY (venta_id)
        REFERENCES ventas(id),

    CONSTRAINT fk_ventas_pago_forma_pago
        FOREIGN KEY (forma_pago_id)
        REFERENCES formas_pago(id),

    CONSTRAINT fk_ventas_pago_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_ventas_pago_caja
        FOREIGN KEY (caja_id)
        REFERENCES cajas(id),

    CONSTRAINT fk_ventas_pago_sesion
        FOREIGN KEY (sesion_caja_id)
        REFERENCES sesiones_caja(id),

    CONSTRAINT chk_ventas_pago_monto
        CHECK (monto > 0),

    CONSTRAINT chk_ventas_pago_recibido
        CHECK (monto_recibido > 0),

    CONSTRAINT chk_ventas_pago_cambio
        CHECK (
            cambio >= 0
            AND monto_recibido = monto + cambio
        )

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


CREATE INDEX idx_ventas_pago_venta
    ON ventas_pago(venta_id);

CREATE INDEX idx_ventas_pago_forma
    ON ventas_pago(forma_pago_id);

CREATE INDEX idx_ventas_pago_usuario
    ON ventas_pago(usuario_id);

CREATE INDEX idx_ventas_pago_caja
    ON ventas_pago(caja_id);

CREATE INDEX idx_ventas_pago_sesion
    ON ventas_pago(sesion_caja_id);

CREATE INDEX idx_ventas_pago_fecha
    ON ventas_pago(fecha_pago);

CREATE INDEX idx_ventas_pago_estado
    ON ventas_pago(estado);

CREATE INDEX idx_ventas_pago_tipo
    ON ventas_pago(tipo_pago);
