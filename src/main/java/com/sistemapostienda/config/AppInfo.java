/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.config;

/**
 *
 * @author monky
 * Información general de la aplicación.
 *
 * Esta clase proporciona información estática del sistema y del entorno
 * donde se está ejecutando la aplicación.
 *
 */
public final class AppInfo {

    /**
     * Evita la creación de instancias.
     */
    private AppInfo() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada.");
    }

    /*==================================================
     * INFORMACIÓN DE LA APLICACIÓN
     *==================================================*/

    public static final String NOMBRE = Constants.NOMBRE_SISTEMA;

    public static final String VERSION = Constants.VERSION;

    public static final String EMPRESA = "SistemaPOSTienda";

    public static final String AUTOR = "SistemaPOSTienda";

    public static final String COPYRIGHT =
            "© 2026 SistemaPOSTienda. Todos los derechos reservados.";

    public static final String BASE_DATOS = "MySQL";

    /*==================================================
     * INFORMACIÓN DEL ENTORNO
     *==================================================*/

    public static final String JAVA_VERSION =
            System.getProperty("java.version");

    public static final String SISTEMA_OPERATIVO =
            System.getProperty("os.name");

    public static final String ARQUITECTURA =
            System.getProperty("os.arch");

    public static final String VERSION_SO =
            System.getProperty("os.version");

    public static final String USUARIO =
            System.getProperty("user.name");

}