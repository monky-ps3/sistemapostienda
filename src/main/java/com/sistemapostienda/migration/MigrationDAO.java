/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.migration;

/**
 *
 * @author monky
 */


import com.sistemapostienda.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de consultar y registrar las migraciones
 * ejecutadas en la base de datos.
 */
public class MigrationDAO {

    /**
     * Obtiene las versiones de las migraciones registradas.
     *
     * @return lista de versiones ejecutadas
     * @throws SQLException si ocurre un error al consultar
     */
    public List<Integer> obtenerMigracionesEjecutadas()
            throws SQLException {

        List<Integer> versiones = new ArrayList<>();

        String sql = "SELECT version FROM migrations ORDER BY version";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                versiones.add(rs.getInt("version"));
            }
        }

        return versiones;
    }

    /**
     * Registra una migración ejecutada.
     *
     * @param migracion datos de la migración
     * @throws IllegalArgumentException si la migración es nula
     * @throws SQLException si ocurre un error al registrar
     */
    public void registrarMigracion(MigrationHistory migracion)
            throws SQLException {

        if (migracion == null) {
            throw new IllegalArgumentException(
                    "La migración no puede ser nula."
            );
        }

        String sql = "INSERT INTO migrations "
                + "(version, nombre, ejecutada_en, tiempo_ms, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, migracion.getVersion());
            ps.setString(2, migracion.getNombre());

            if (migracion.getEjecutadaEn() == null) {
                ps.setTimestamp(3, null);
            } else {
                ps.setTimestamp(
                        3,
                        Timestamp.valueOf(migracion.getEjecutadaEn())
                );
            }

            ps.setInt(4, migracion.getTiempoMs());
            ps.setString(5, migracion.getEstado());

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas != 1) {
                throw new SQLException(
                        "No se pudo registrar la migración versión "
                        + migracion.getVersion() + "."
                );
            }
        }
    }
}

