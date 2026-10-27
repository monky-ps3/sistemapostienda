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
 * MIGRACION 005: CATALOGO DE PERMISOS
 * SistemaPOSTienda
 * ============================================================
 *
 * Los permisos describen las operaciones disponibles.
 * La asignacion de permisos a roles se realiza en la migracion 006.
 *
 * El control efectivo de permisos debe implementarse tambien
 * en la aplicacion Java, no solamente en la interfaz.
 * ============================================================
 */

CREATE TABLE permisos (

    id BIGINT NOT NULL AUTO_INCREMENT,

    /*
     * Clave interna, estable y unica.
     */
    clave VARCHAR(100) NOT NULL,

    /*
     * Nombre visible en la interfaz.
     */
    nombre VARCHAR(150) NOT NULL,

    /*
     * Modulo funcional.
     */
    modulo VARCHAR(100) NOT NULL,

    descripcion VARCHAR(255) NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_permisos_clave
        UNIQUE (clave),

    CONSTRAINT chk_permisos_clave_no_vacia
        CHECK (CHAR_LENGTH(TRIM(clave)) > 0),

    CONSTRAINT chk_permisos_nombre_no_vacio
        CHECK (CHAR_LENGTH(TRIM(nombre)) > 0),

    CONSTRAINT chk_permisos_modulo_no_vacio
        CHECK (CHAR_LENGTH(TRIM(modulo)) > 0)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * ============================================================
 * INDICES
 * ============================================================
 */

CREATE INDEX idx_permisos_modulo
ON permisos(modulo);

CREATE INDEX idx_permisos_activo
ON permisos(activo);


/*
 * ============================================================
 * CATALOGO INICIAL DE PERMISOS
 * ============================================================
 *
 * Estos permisos son generales y no dependen del giro comercial.
 * Las funciones especificas de cada negocio se configuran
 * mediante catalogos, unidades de medida y reglas del sistema.
 * ============================================================
 */

INSERT INTO permisos (clave, nombre, modulo, descripcion)
VALUES

/* Usuarios y seguridad */
('USUARIO_VER', 'Consultar usuarios', 'Usuarios',
 'Consultar cuentas de usuario'),

('USUARIO_CREAR', 'Crear usuarios', 'Usuarios',
 'Registrar nuevas cuentas'),

('USUARIO_EDITAR', 'Editar usuarios', 'Usuarios',
 'Modificar datos y estado de cuentas'),

('USUARIO_DESACTIVAR', 'Desactivar usuarios', 'Usuarios',
 'Desactivar cuentas de acceso'),

('ROL_VER', 'Consultar roles', 'Seguridad',
 'Consultar los roles del sistema'),

('ROL_GESTIONAR', 'Gestionar roles y permisos', 'Seguridad',
 'Crear y modificar roles y sus permisos'),

/* Productos y catalogos */
('PRODUCTO_VER', 'Consultar productos', 'Productos',
 'Consultar el catalogo de productos'),

('PRODUCTO_CREAR', 'Crear productos', 'Productos',
 'Registrar productos nuevos'),

('PRODUCTO_EDITAR', 'Editar productos', 'Productos',
 'Modificar productos y sus presentaciones'),

('PRODUCTO_DESACTIVAR', 'Desactivar productos', 'Productos',
 'Desactivar productos sin eliminar su historial'),

('CATEGORIA_GESTIONAR', 'Gestionar categorias', 'Catalogos',
 'Administrar categorias de productos'),

('MARCA_GESTIONAR', 'Gestionar marcas', 'Catalogos',
 'Administrar marcas de productos'),

('UNIDAD_GESTIONAR', 'Gestionar unidades', 'Catalogos',
 'Administrar unidades de medida'),

/* Precios y promociones */
('PRECIO_VER', 'Consultar precios', 'Precios',
 'Consultar listas de precios'),

('PRECIO_GESTIONAR', 'Gestionar precios', 'Precios',
 'Crear y modificar precios autorizados'),

('PROMOCION_GESTIONAR', 'Gestionar promociones', 'Promociones',
 'Crear y modificar promociones'),

/* Inventario */
('INVENTARIO_VER', 'Consultar inventario', 'Inventario',
 'Consultar existencias'),

('INVENTARIO_AJUSTAR', 'Ajustar inventario', 'Inventario',
 'Registrar ajustes autorizados de existencias'),

('INVENTARIO_MOVER', 'Registrar movimientos', 'Inventario',
 'Registrar entradas, salidas y traspasos autorizados'),

/* Compras y proveedores */
('PROVEEDOR_GESTIONAR', 'Gestionar proveedores', 'Compras',
 'Registrar y modificar proveedores'),

('COMPRA_VER', 'Consultar compras', 'Compras',
 'Consultar compras registradas'),

('COMPRA_CREAR', 'Registrar compras', 'Compras',
 'Registrar compras y recepciones de mercancia'),

/* Clientes */
('CLIENTE_VER', 'Consultar clientes', 'Clientes',
 'Consultar el catalogo de clientes'),

('CLIENTE_GESTIONAR', 'Gestionar clientes', 'Clientes',
 'Registrar y modificar clientes'),

/* Ventas */
('VENTA_VER', 'Consultar ventas', 'Ventas',
 'Consultar ventas registradas'),

('VENTA_CREAR', 'Realizar ventas', 'Ventas',
 'Registrar ventas y sus detalles'),

('VENTA_CANCELAR', 'Cancelar ventas', 'Ventas',
 'Cancelar ventas con validaciones y auditoria'),

/* Caja */
('CAJA_ABRIR', 'Abrir caja', 'Caja',
 'Abrir una sesion de caja'),

('CAJA_MOVIMIENTO', 'Registrar movimientos de caja', 'Caja',
 'Registrar entradas y salidas autorizadas de efectivo'),

('CAJA_CERRAR', 'Cerrar caja', 'Caja',
 'Realizar cortes y cierres de caja'),

('CAJA_VER_REPORTES', 'Consultar reportes de caja', 'Caja',
 'Consultar movimientos y cortes de caja'),

/* Reportes y configuracion */
('REPORTE_VER', 'Consultar reportes', 'Reportes',
 'Consultar reportes operativos'),

('CONFIGURACION_VER', 'Consultar configuracion', 'Configuracion',
 'Consultar la configuracion del sistema'),

('CONFIGURACION_EDITAR', 'Modificar configuracion', 'Configuracion',
 'Modificar datos y parametros del negocio'),

('SUCURSAL_GESTIONAR', 'Gestionar sucursales', 'Sucursales',
 'Administrar sucursales y sus datos');
