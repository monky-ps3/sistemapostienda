/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.migration;

/**
 *
 * @author monky
 */
public class Migration {

    private int version;
    private String nombre;
    private String archivo;

    public Migration() {
    }

    public Migration(int version, String nombre, String archivo) {
        this.version = version;
        this.nombre = nombre;
        this.archivo = archivo;
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

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

}