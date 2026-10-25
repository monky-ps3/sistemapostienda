/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.database;

/**
 *
 * @author monky
 */

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {

    public static void main(String[] args) {

        try {

            Connection conexion = Database.getConnection();

            if (conexion != null) {
                System.out.println("====================================");
                System.out.println(" Conexion exitosa a MySQL");
                System.out.println("====================================");

                conexion.close();
            }

        } catch (SQLException e) {

            System.out.println("====================================");
            System.out.println(" Error al conectar con MySQL");
            System.out.println("====================================");
            e.printStackTrace();
        }
    }
}
