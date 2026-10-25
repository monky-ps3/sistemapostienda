package com.sistemapostienda.migration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author monky
 */



import com.sistemapostienda.database.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ejecuta las migraciones SQL pendientes del sistema.
 *
 * Las migraciones se almacenan en:
 * src/main/resources/migrations
 */
public class MigrationRunner {

    private static final String RUTA_MIGRACIONES = "migrations/";

    private static final Pattern PATRON_MIGRACION =
            Pattern.compile("^(\\d{3})_.+\\.sql$", Pattern.CASE_INSENSITIVE);

    private final MigrationDAO migrationDAO = new MigrationDAO();

    /**
     * Ejecuta las migraciones pendientes.
     */
    public void runMigrations() {

        System.out.println("==================================");
        System.out.println(" INICIANDO MIGRACIONES");
        System.out.println("==================================");

        List<String> archivos;

        try {
            archivos = listarArchivos();

            if (archivos.isEmpty()) {
                throw new IllegalStateException(
                        "No se encontraron archivos SQL en "
                        + RUTA_MIGRACIONES
                );
            }

        } catch (Exception e) {
            System.err.println(
                    "No se pudieron cargar las migraciones."
            );
            e.printStackTrace();
            return;
        }

        List<Integer> ejecutadas;

        try {
            ejecutadas = migrationDAO.obtenerMigracionesEjecutadas();

        } catch (SQLException e) {

            /*
             * Error MySQL 1146: la tabla consultada no existe.
             * Solo en ese caso se considera una instalación inicial.
             */
            if (e.getErrorCode() == 1146) {

                System.out.println(
                        "No existe la tabla migrations."
                );
                System.out.println(
                        "Se intentará una instalación inicial."
                );

                ejecutadas = new ArrayList<>();

            } else {

                System.err.println(
                        "No se pudieron consultar las migraciones ejecutadas."
                );
                e.printStackTrace();
                return;
            }
        }

        for (String archivo : archivos) {

            int version;

            try {
                version = obtenerVersion(archivo);

            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }

            if (ejecutadas.contains(version)) {
                System.out.println("SKIP: " + archivo);
                continue;
            }

            System.out.println("----------------------------------");
            System.out.println("EJECUTANDO: " + archivo);
            System.out.println("VERSION: " + version);

            long inicio = System.currentTimeMillis();

            try {

                ejecutarSQL(archivo);

                long tiempo = System.currentTimeMillis() - inicio;

                MigrationHistory migracion = new MigrationHistory(
                        null,
                        version,
                        archivo,
                        LocalDateTime.now(),
                        (int) Math.min(tiempo, Integer.MAX_VALUE),
                        "OK"
                );

                /*
                 * Si falla el registro, se detiene el proceso.
                 * No se informa la migración como completada.
                 */
                migrationDAO.registrarMigracion(migracion);

                ejecutadas.add(version);

                System.out.println(
                        "OK: " + archivo + " (" + tiempo + " ms)"
                );

            } catch (Exception e) {

                System.err.println(
                        "ERROR en la migración: " + archivo
                );
                e.printStackTrace();

                System.err.println(
                        "Proceso detenido para evitar continuar "
                        + "con una instalación incompleta."
                );

                return;
            }
        }

        System.out.println("==================================");
        System.out.println(" MIGRACIONES COMPLETADAS");
        System.out.println("==================================");
    }

    /**
     * Obtiene los nombres de los archivos SQL.
     *
     * Funciona con recursos dentro de un directorio o de un JAR.
     *
     * @return nombres ordenados de los archivos SQL
     * @throws IOException si no se pueden leer los recursos
     */
    private List<String> listarArchivos() throws IOException {

        List<String> archivos = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL recurso = classLoader.getResource("migrations");

        if (recurso == null) {
            throw new IOException(
                    "No existe el recurso: " + RUTA_MIGRACIONES
            );
        }

        if ("file".equalsIgnoreCase(recurso.getProtocol())) {

            try {
                java.nio.file.Path carpeta =
                        java.nio.file.Paths.get(recurso.toURI());

                try (java.util.stream.Stream<java.nio.file.Path> rutas =
                        java.nio.file.Files.list(carpeta)) {

                    rutas.filter(java.nio.file.Files::isRegularFile)
                            .map(ruta -> ruta.getFileName().toString())
                            .filter(nombre ->
                                    nombre.toLowerCase(Locale.ROOT)
                                            .endsWith(".sql"))
                            .forEach(archivos::add);
                }

            } catch (java.net.URISyntaxException e) {
                throw new IOException(
                        "La ruta de migraciones no es válida.", e
                );
            }

        } else if ("jar".equalsIgnoreCase(recurso.getProtocol())) {

            JarURLConnection conexion =
                    (JarURLConnection) recurso.openConnection();

            try (JarFile jar = conexion.getJarFile()) {

                Enumeration<JarEntry> entradas = jar.entries();

                while (entradas.hasMoreElements()) {

                    JarEntry entrada = entradas.nextElement();
                    String nombre = entrada.getName();

                    if (!entrada.isDirectory()
                            && nombre.startsWith(RUTA_MIGRACIONES)
                            && nombre.indexOf(
                                    '/',
                                    RUTA_MIGRACIONES.length()
                            ) == -1
                            && nombre.toLowerCase(Locale.ROOT)
                                    .endsWith(".sql")) {

                        archivos.add(
                                nombre.substring(RUTA_MIGRACIONES.length())
                        );
                    }
                }
            }

        } else {
            throw new IOException(
                    "Protocolo de recursos no compatible: "
                    + recurso.getProtocol()
            );
        }

        archivos.sort(String::compareTo);

        return archivos;
    }

    /**
     * Extrae la versión del nombre del archivo.
     *
     * Ejemplo: 001_create_roles.sql -> 1.
     *
     * @param archivo nombre del archivo
     * @return versión numérica
     */
    private int obtenerVersion(String archivo) {

        Matcher matcher = PATRON_MIGRACION.matcher(archivo);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Nombre de migración inválido: " + archivo
                    + ". Formato esperado: 001_nombre.sql"
            );
        }

        return Integer.parseInt(matcher.group(1));
    }

    /**
     * Lee y ejecuta las sentencias SQL de una migración.
     *
     * @param archivo nombre del archivo SQL
     * @throws Exception si ocurre un error al leer o ejecutar SQL
     */
    private void ejecutarSQL(String archivo) throws Exception {

        String sql;

        try (InputStream entrada = getClass()
                .getClassLoader()
                .getResourceAsStream(RUTA_MIGRACIONES + archivo)) {

            if (entrada == null) {
                throw new IOException(
                        "No se encontró el archivo: " + archivo
                );
            }

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(
                            entrada,
                            StandardCharsets.UTF_8
                    ))) {

                StringBuilder contenido = new StringBuilder();
                String linea;

                while ((linea = lector.readLine()) != null) {
                    contenido.append(linea).append('\n');
                }

                sql = contenido.toString().trim();
            }
        }

        if (sql.isEmpty()) {
            throw new MigrationException(
                    "La migración '" + archivo + "' está vacía."
            );
        }

        List<String> sentencias = separarSentenciasSQL(sql);

        if (sentencias.isEmpty()) {
            throw new MigrationException(
                    "La migración '" + archivo
                    + "' no contiene sentencias SQL válidas."
            );
        }

        try (Connection conn = Database.getConnection()) {

            conn.setAutoCommit(false);

            try {

                for (String sentencia : sentencias) {

                    sentencia = sentencia.trim();

                    if (sentencia.isEmpty()
                            || esSoloComentario(sentencia)) {
                        continue;
                    }

                    try (PreparedStatement ps =
                            conn.prepareStatement(sentencia)) {

                        ps.execute();
                    }
                }

                conn.commit();

            } catch (Exception e) {

                try {
                    conn.rollback();
                } catch (SQLException errorRollback) {
                    e.addSuppressed(errorRollback);
                }

                throw new MigrationException(
                        "Error ejecutando la migración: " + archivo,
                        e
                );

            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(
                            "No se pudo restaurar autoCommit: "
                            + e.getMessage()
                    );
                }
            }
        }
    }

    /**
     * Separa sentencias SQL sin dividir los puntos y coma que estén
     * dentro de cadenas, identificadores o comentarios.
     *
     * Reconoce comillas simples, dobles, acentos graves,
     * comentarios de línea y comentarios de bloque.
     *
     * @param sql contenido SQL completo
     * @return lista de sentencias separadas
     */
    private List<String> separarSentenciasSQL(String sql) {

        List<String> sentencias = new ArrayList<>();
        StringBuilder sentencia = new StringBuilder();

        boolean comillaSimple = false;
        boolean comillaDoble = false;
        boolean acentoGrave = false;
        boolean comentarioLinea = false;
        boolean comentarioBloque = false;

        for (int i = 0; i < sql.length(); i++) {

            char actual = sql.charAt(i);
            char siguiente = i + 1 < sql.length()
                    ? sql.charAt(i + 1)
                    : '\0';

            // Dentro de un comentario de línea.
            if (comentarioLinea) {
                sentencia.append(actual);

                if (actual == '\n' || actual == '\r') {
                    comentarioLinea = false;
                }

                continue;
            }

            // Dentro de un comentario de bloque.
            if (comentarioBloque) {
                sentencia.append(actual);

                if (actual == '*' && siguiente == '/') {
                    sentencia.append(siguiente);
                    i++;
                    comentarioBloque = false;
                }

                continue;
            }

            // Dentro de una cadena o identificador entre comillas.
            if (comillaSimple || comillaDoble || acentoGrave) {

                sentencia.append(actual);

                // Manejar caracteres escapados con barra invertida.
                if (actual == '\\' && i + 1 < sql.length()) {
                    sentencia.append(sql.charAt(++i));
                    continue;
                }

                char delimitador = comillaSimple
                        ? '\''
                        : comillaDoble
                                ? '"'
                                : '`';

                if (actual == delimitador) {

                    // Manejar delimitadores duplicados.
                    if (siguiente == delimitador) {
                        sentencia.append(siguiente);
                        i++;
                    } else {
                        comillaSimple = false;
                        comillaDoble = false;
                        acentoGrave = false;
                    }
                }

                continue;
            }

            // Comentario de línea iniciado con #.
            if (actual == '#') {
                sentencia.append(actual);
                comentarioLinea = true;
                continue;
            }

            // Comentario SQL iniciado con -- y seguido de espacio
            // o de un carácter de control.
            if (actual == '-'
                    && siguiente == '-'
                    && (i + 2 >= sql.length()
                        || Character.isWhitespace(sql.charAt(i + 2))
                        || Character.isISOControl(sql.charAt(i + 2)))) {

                sentencia.append(actual).append(siguiente);
                i++;
                comentarioLinea = true;
                continue;
            }

            // Comentario de bloque.
            if (actual == '/' && siguiente == '*') {
                sentencia.append(actual).append(siguiente);
                i++;
                comentarioBloque = true;
                continue;
            }

            // Apertura de cadenas o identificadores.
            if (actual == '\'') {
                comillaSimple = true;
                sentencia.append(actual);
                continue;
            }

            if (actual == '"') {
                comillaDoble = true;
                sentencia.append(actual);
                continue;
            }

            if (actual == '`') {
                acentoGrave = true;
                sentencia.append(actual);
                continue;
            }

            // Separar únicamente fuera de cadenas y comentarios.
            if (actual == ';') {
                agregarSentencia(sentencias, sentencia);
                continue;
            }

            sentencia.append(actual);
        }

        // Agregar la última sentencia, aunque no tenga punto y coma.
        agregarSentencia(sentencias, sentencia);

        return sentencias;
    }

    /**
     * Agrega una sentencia a la lista y limpia el acumulador.
     */
    private void agregarSentencia(
            List<String> sentencias,
            StringBuilder sentencia) {

        String contenido = sentencia.toString().trim();

        if (!contenido.isEmpty()) {
            sentencias.add(contenido);
        }

        sentencia.setLength(0);
    }

    /**
     * Comprueba si el texto contiene únicamente comentarios y espacios.
     * Evita intentar ejecutar comentarios sueltos como sentencias SQL.
     */
    private boolean esSoloComentario(String sql) {

        int i = 0;

        while (i < sql.length()) {

            char actual = sql.charAt(i);
            char siguiente = i + 1 < sql.length()
                    ? sql.charAt(i + 1)
                    : '\0';

            if (Character.isWhitespace(actual)) {
                i++;
                continue;
            }

            // Comentario de línea con #.
            if (actual == '#') {
                while (i < sql.length()
                        && sql.charAt(i) != '\n'
                        && sql.charAt(i) != '\r') {
                    i++;
                }
                continue;
            }

            // Comentario de línea con --.
            if (actual == '-'
                    && siguiente == '-'
                    && (i + 2 >= sql.length()
                        || Character.isWhitespace(sql.charAt(i + 2))
                        || Character.isISOControl(sql.charAt(i + 2)))) {

                while (i < sql.length()
                        && sql.charAt(i) != '\n'
                        && sql.charAt(i) != '\r') {
                    i++;
                }
                continue;
            }

            // Comentario de bloque.
            if (actual == '/' && siguiente == '*') {

                int cierre = sql.indexOf("*/", i + 2);

                if (cierre == -1) {
                    return false;
                }

                i = cierre + 2;
                continue;
            }

            // Se encontró contenido que no es un comentario.
            return false;
        }

        return true;
    }
}
