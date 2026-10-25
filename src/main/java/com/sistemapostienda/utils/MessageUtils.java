/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.utils;


import javax.swing.*;
import java.awt.Component;

/**
 * Clase utilitaria para mostrar mensajes al usuario.
 *
 * Centraliza todos los JOptionPane del sistema.
 *
 * @author monky
 * @version 1.0.0
 */
public final class MessageUtils {

    private MessageUtils() {
    }

    /**
     * Muestra un mensaje de información.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje a mostrar.
     */
    public static void informacion(Component parent, String mensaje) {

        JOptionPane.showMessageDialog(
                parent,
                mensaje,
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );

    }
    /**
     * Muestra un mensaje de advertencia.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje a mostrar.
     */
    public static void advertencia(Component parent, String mensaje) {

        JOptionPane.showMessageDialog(
                parent,
                mensaje,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );

    }

    /**
     * Muestra un mensaje de error.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje a mostrar.
     */
    public static void error(Component parent, String mensaje) {

        JOptionPane.showMessageDialog(
                parent,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

    }
    /**
     * Muestra un mensaje de éxito.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje a mostrar.
     */
    public static void exito(Component parent, String mensaje) {

        JOptionPane.showMessageDialog(
                parent,
                mensaje,
                "Éxito",
                JOptionPane.PLAIN_MESSAGE
        );

    }

    /**
     * Muestra un cuadro de confirmación.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje.
     * @return true si el usuario seleccionó Sí.
     */
    public static boolean confirmar(Component parent, String mensaje) {

        int opcion = JOptionPane.showConfirmDialog(
                parent,
                mensaje,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return opcion == JOptionPane.YES_OPTION;

    }

    /**
     * Solicita un texto al usuario.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje.
     * @return Texto capturado o null.
     */
    public static String ingresarTexto(Component parent, String mensaje) {

        return JOptionPane.showInputDialog(
                parent,
                mensaje
        );

    }
    /**
     * Solicita un valor al usuario.
     *
     * @param parent Componente padre.
     * @param mensaje Mensaje.
     * @param valorInicial Valor inicial.
     * @return Texto capturado o null.
     */
    public static String ingresarTexto(Component parent,
                                       String mensaje,
                                       String valorInicial) {

        return (String) JOptionPane.showInputDialog(
                parent,
                mensaje,
                "Capturar",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                valorInicial
        );

    }
}
