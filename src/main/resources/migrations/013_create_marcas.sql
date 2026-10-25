/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026
 */

/*
============================================================
TABLA: marcas
============================================================
*/

CREATE TABLE marcas (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    nombre VARCHAR(150) NOT NULL,

    codigo VARCHAR(30) NOT NULL,

    descripcion VARCHAR(255),

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_marcas_nombre UNIQUE (nombre),

    CONSTRAINT uk_marcas_codigo UNIQUE (codigo)

);

/*
============================================================
ÍNDICES
============================================================
*/

CREATE INDEX idx_marcas_nombre
ON marcas(nombre);

CREATE INDEX idx_marcas_codigo
ON marcas(codigo);

CREATE INDEX idx_marcas_activo
ON marcas(activo);

/*
============================================================
DATOS INICIALES
============================================================
*/

INSERT INTO marcas
(nombre,codigo,descripcion,activo)
VALUES
('Coca-Cola','COC','Bebidas Coca-Cola',TRUE),

('Pepsi','PEP','Bebidas Pepsi',TRUE),

('Bimbo','BIM','Panificación y alimentos',TRUE),

('Lala','LAL','Lácteos',TRUE),

('Gamesa','GAM','Galletas',TRUE),

('Sabritas','SAB','Botanas',TRUE),

('Barcel','BAR','Botanas',TRUE),

('Marinela','MAR','Pastelillos y pan dulce',TRUE),

('Nestlé','NES','Alimentos y bebidas',TRUE),

('Herdez','HER','Conservas y alimentos',TRUE),

('La Costeña','COS','Conservas',TRUE),

('Maseca','MAS','Harina de maíz',TRUE),

('Great Value','GVA','Marca propia',TRUE),

('Member''s Mark','MEM','Marca propia',TRUE),

('Del Valle','DEL','Jugos y néctares',TRUE),

('Jumex','JUM','Jugos',TRUE),

('Danone','DAN','Lácteos',TRUE),

('Santa Clara','SCL','Lácteos',TRUE),

('Nescafé','NCA','Café soluble',TRUE),

('Kellogg''s','KEL','Cereales',TRUE);