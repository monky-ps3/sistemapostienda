package com.sistemapostienda.migration;

import java.time.LocalDateTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author monky
 */


public class MigrationHistory {

    private Long id;
    private int version;
    private String nombre;
    private LocalDateTime ejecutadaEn;
    private int tiempoMs;
    private String estado;

    public MigrationHistory() {
    }

    public MigrationHistory(Long id, int version, String nombre,
            LocalDateTime ejecutadaEn,
            int tiempoMs,
            String estado) {
        this.id = id;
        this.version = version;
        this.nombre = nombre;
        this.ejecutadaEn = ejecutadaEn;
        this.tiempoMs = tiempoMs;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getEjecutadaEn() {
        return ejecutadaEn;
    }

    public void setEjecutadaEn(LocalDateTime ejecutadaEn) {
        this.ejecutadaEn = ejecutadaEn;
    }

    public int getTiempoMs() {
        return tiempoMs;
    }

    public void setTiempoMs(int tiempoMs) {
        this.tiempoMs = tiempoMs;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "MigrationHistory{"
                + "id=" + id
                + ", version=" + version
                + ", nombre='" + nombre + '\''
                + ", ejecutadaEn=" + ejecutadaEn
                + ", tiempoMs=" + tiempoMs
                + ", estado='" + estado + '\''
                + '}';
    }
}
