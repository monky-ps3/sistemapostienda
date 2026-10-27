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
 * MIGRACION 006: ASIGNACION DE PERMISOS A ROLES
 * SistemaPOSTienda
 * ============================================================
 *
 * Depende de:
 * 002_create_roles.sql
 * 005_create_permisos.sql
 *
 * Los roles y permisos se relacionan por sus claves naturales,
 * nunca por IDs numericos asumidos.
 * ============================================================
 */

CREATE TABLE rol_permiso (

    id BIGINT NOT NULL AUTO_INCREMENT,

    rol_id BIGINT NOT NULL,

    permiso_id BIGINT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_rol_permiso
        UNIQUE (rol_id, permiso_id),

    CONSTRAINT fk_rol_permiso_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE,

    CONSTRAINT fk_rol_permiso_permiso
        FOREIGN KEY (permiso_id)
        REFERENCES permisos(id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;


/*
 * La restriccion UNIQUE (rol_id, permiso_id) ya genera
 * un indice para consultas por rol.
 * Solo necesitamos el indice inverso por permiso.
 */

CREATE INDEX idx_rol_permiso_permiso
ON rol_permiso(permiso_id);


/*
 * ============================================================
 * PERMISOS DEL ADMINISTRADOR
 * ============================================================
 *
 * El administrador recibe todos los permisos activos.
 * Los permisos futuros no se agregan automaticamente a esta
 * relacion; la asignacion se realiza durante esta migracion.
 * ============================================================
 */

INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permisos p
WHERE r.nombre = 'Administrador'
  AND r.activo = TRUE
  AND p.activo = TRUE;


/*
 * ============================================================
 * PERMISOS DEL SUPERVISOR
 * ============================================================
 *
 * Puede consultar informacion operativa y gestionar tareas
 * de supervision, sin administrar cuentas, roles ni parametros
 * criticos del sistema.
 * ============================================================
 */

INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
JOIN permisos p
  ON p.clave IN (
      'PRODUCTO_VER',
      'CATEGORIA_GESTIONAR',
      'MARCA_GESTIONAR',
      'UNIDAD_GESTIONAR',
      'PRECIO_VER',
      'PRECIO_GESTIONAR',
      'PROMOCION_GESTIONAR',
      'INVENTARIO_VER',
      'INVENTARIO_AJUSTAR',
      'INVENTARIO_MOVER',
      'PROVEEDOR_GESTIONAR',
      'COMPRA_VER',
      'COMPRA_CREAR',
      'CLIENTE_VER',
      'CLIENTE_GESTIONAR',
      'VENTA_VER',
      'VENTA_CREAR',
      'VENTA_CANCELAR',
      'CAJA_VER_REPORTES',
      'REPORTE_VER',
      'CONFIGURACION_VER'
  )
WHERE r.nombre = 'Supervisor'
  AND r.activo = TRUE
  AND p.activo = TRUE;


/*
 * ============================================================
 * PERMISOS DEL CAJERO
 * ============================================================
 *
 * Puede operar ventas y caja. No recibe permisos para cambiar
 * roles, configuracion general ni administrar precios.
 * ============================================================
 */

INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
JOIN permisos p
  ON p.clave IN (
      'PRODUCTO_VER',
      'PRECIO_VER',
      'CLIENTE_VER',
      'CLIENTE_GESTIONAR',
      'VENTA_VER',
      'VENTA_CREAR',
      'CAJA_ABRIR',
      'CAJA_MOVIMIENTO',
      'CAJA_CERRAR'
  )
WHERE r.nombre = 'Cajero'
  AND r.activo = TRUE
  AND p.activo = TRUE;


/*
 * ============================================================
 * PERMISOS DE BODEGA
 * ============================================================
 *
 * Puede consultar productos, gestionar inventario y registrar
 * compras y proveedores. No recibe permisos de ventas ni caja.
 * ============================================================
 */

INSERT INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
JOIN permisos p
  ON p.clave IN (
      'PRODUCTO_VER',
      'PRODUCTO_CREAR',
      'PRODUCTO_EDITAR',
      'CATEGORIA_GESTIONAR',
      'MARCA_GESTIONAR',
      'UNIDAD_GESTIONAR',
      'INVENTARIO_VER',
      'INVENTARIO_AJUSTAR',
      'INVENTARIO_MOVER',
      'PROVEEDOR_GESTIONAR',
      'COMPRA_VER',
      'COMPRA_CREAR'
  )
WHERE r.nombre = 'Bodega'
  AND r.activo = TRUE
  AND p.activo = TRUE;
