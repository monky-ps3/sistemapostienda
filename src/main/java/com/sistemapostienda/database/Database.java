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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de proporcionar conexiones a la base de datos.
 */
public final class Database {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistemapostienda"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Mexico_City";

    private static final String USER = "root";

    private static final String PASSWORD = "12345";

    private Database() {
    }

    /**
     * Obtiene una conexión con la base de datos.
     *
     * @return Connection
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}
