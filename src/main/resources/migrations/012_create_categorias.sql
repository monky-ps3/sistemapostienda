/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

CREATE TABLE categorias (

    id BIGINT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(100) NOT NULL,

    codigo VARCHAR(20) NOT NULL,

    descripcion VARCHAR(255),

    color VARCHAR(20),

    icono VARCHAR(100),

    orden INT NOT NULL DEFAULT 0,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT uk_categoria_nombre
        UNIQUE (nombre),

    CONSTRAINT uk_categoria_codigo
        UNIQUE (codigo)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_categoria_activa
ON categorias(activo);

CREATE INDEX idx_categoria_orden
ON categorias(orden);


INSERT INTO categorias
(nombre,codigo,descripcion)
VALUES

('Abarrotes','ABAR','Productos de abarrotes'),

('Bebidas','BEB','Refrescos, jugos y agua'),

('Botanas','BOT','Botanas y frituras'),

('Lácteos','LACT','Productos lácteos'),

('Carnes y Embutidos','CAR','Carnes frías y embutidos'),

('Frutas y Verduras','FYV','Frutas y verduras'),

('Panadería','PAN','Pan y repostería'),

('Limpieza','LIMP','Productos de limpieza'),

('Higiene Personal','HIG','Productos de higiene'),

('Papelería','PAP','Artículos escolares y oficina'),

('Mascotas','MAS','Productos para mascotas'),

('Cigarros','CIG','Cigarros y tabacos'),

('Farmacia','FAR','Medicamentos de libre venta'),

('Electrónicos','ELEC','Accesorios electrónicos'),

('Otros','OTR','Productos diversos');