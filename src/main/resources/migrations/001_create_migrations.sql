/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  monky
 * Created: 2 jul 2026 
 */

CREATE TABLE migrations (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    version INT NOT NULL,

    nombre VARCHAR(150) NOT NULL,

    ejecutada_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    tiempo_ms INT DEFAULT 0,

    estado VARCHAR(20) DEFAULT 'OK'

);