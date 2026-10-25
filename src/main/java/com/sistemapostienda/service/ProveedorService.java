/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.ProveedorDAO;
import com.sistemapostienda.model.Proveedor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service para la gestión de proveedores.
 *
 * Aquí se encuentran las reglas de negocio.
 *
 * El Service NO realiza SQL directamente.
 * Para acceder a la base de datos utiliza ProveedorDAO.
 */
public class ProveedorService {

    private final ProveedorDAO proveedorDAO;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ProveedorService() {

        this.proveedorDAO = new ProveedorDAO();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    /**
     * Registra un nuevo proveedor.
     */
    public boolean guardar(Proveedor proveedor) {

        validarProveedor(proveedor);

        // Validar código duplicado
        Proveedor existente =
                proveedorDAO.buscarPorCodigo(
                        proveedor.getCodigo()
                );

        if (existente != null) {

            throw new IllegalArgumentException(
                    "Ya existe un proveedor con el código: "
                    + proveedor.getCodigo()
            );
        }

        // Validar RFC solamente si fue proporcionado
        if (tieneTexto(proveedor.getRfc())) {

            Proveedor proveedorRfc =
                    proveedorDAO.buscarPorRfc(
                            proveedor.getRfc()
                    );

            if (proveedorRfc != null) {

                throw new IllegalArgumentException(
                        "Ya existe un proveedor con el RFC: "
                        + proveedor.getRfc()
                );
            }
        }

        prepararValores(proveedor);

        return proveedorDAO.guardar(proveedor);
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Proveedor buscarPorId(Long id) {

        if (id == null || id <= 0) {

            throw new IllegalArgumentException(
                    "El ID del proveedor no es válido."
            );
        }

        return proveedorDAO.buscarPorId(id);
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public Proveedor buscarPorCodigo(
            String codigo) {

        validarTexto(
                codigo,
                "El código del proveedor es obligatorio."
        );

        return proveedorDAO.buscarPorCodigo(
                codigo.trim()
        );
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    public Proveedor buscarPorRfc(
            String rfc) {

        if (!tieneTexto(rfc)) {

            return null;
        }

        return proveedorDAO.buscarPorRfc(
                rfc.trim().toUpperCase()
        );
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Proveedor> listarTodos() {

        return proveedorDAO.listarTodos();
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Proveedor> listarActivos() {

        return proveedorDAO.listarActivos();
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public List<Proveedor> buscarPorNombre(
            String nombre) {

        validarTexto(
                nombre,
                "El nombre de búsqueda es obligatorio."
        );

        return proveedorDAO.buscarPorNombre(
                nombre.trim()
        );
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(
            Proveedor proveedor) {

        if (proveedor == null) {

            throw new IllegalArgumentException(
                    "El proveedor no puede ser null."
            );
        }

        if (proveedor.getId() == null
                || proveedor.getId() <= 0) {

            throw new IllegalArgumentException(
                    "El ID del proveedor no es válido."
            );
        }

        validarProveedor(proveedor);

        // Comprobar código
        Proveedor existente =
                proveedorDAO.buscarPorCodigo(
                        proveedor.getCodigo()
                );

        if (existente != null
                && !existente.getId().equals(
                        proveedor.getId())) {

            throw new IllegalArgumentException(
                    "El código ya pertenece a otro proveedor."
            );
        }

        // Comprobar RFC
        if (tieneTexto(proveedor.getRfc())) {

            Proveedor proveedorRfc =
                    proveedorDAO.buscarPorRfc(
                            proveedor.getRfc()
                    );

            if (proveedorRfc != null
                    && !proveedorRfc.getId().equals(
                            proveedor.getId())) {

                throw new IllegalArgumentException(
                        "El RFC ya pertenece a otro proveedor."
                );
            }
        }

        prepararValores(proveedor);

        return proveedorDAO.actualizar(
                proveedor
        );
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina un proveedor.
     *
     * IMPORTANTE:
     *
     * Más adelante, cuando exista el módulo Compras,
     * probablemente cambiaremos esta operación por
     * una eliminación lógica.
     */
    public boolean eliminar(Long id) {

        if (id == null || id <= 0) {

            throw new IllegalArgumentException(
                    "El ID del proveedor no es válido."
            );
        }

        Proveedor proveedor =
                proveedorDAO.buscarPorId(id);

        if (proveedor == null) {

            throw new IllegalArgumentException(
                    "El proveedor no existe."
            );
        }

        return proveedorDAO.eliminar(id);
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    public boolean activar(Long id) {

        validarId(id);

        Proveedor proveedor =
                proveedorDAO.buscarPorId(id);

        if (proveedor == null) {

            throw new IllegalArgumentException(
                    "El proveedor no existe."
            );
        }

        return proveedorDAO.cambiarEstado(
                id,
                true
        );
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    public boolean desactivar(Long id) {

        validarId(id);

        Proveedor proveedor =
                proveedorDAO.buscarPorId(id);

        if (proveedor == null) {

            throw new IllegalArgumentException(
                    "El proveedor no existe."
            );
        }

        return proveedorDAO.cambiarEstado(
                id,
                false
        );
    }

    // =========================================================
    // VALIDAR PROVEEDOR
    // =========================================================

    private void validarProveedor(
            Proveedor proveedor) {

        if (proveedor == null) {

            throw new IllegalArgumentException(
                    "El proveedor no puede ser null."
            );
        }

        validarTexto(
                proveedor.getCodigo(),
                "El código del proveedor es obligatorio."
        );

        validarTexto(
                proveedor.getNombre(),
                "El nombre del proveedor es obligatorio."
        );

        // Código
        if (proveedor.getCodigo().trim().length() > 30) {

            throw new IllegalArgumentException(
                    "El código del proveedor no puede "
                    + "superar los 30 caracteres."
            );
        }

        // Nombre
        if (proveedor.getNombre().trim().length() > 200) {

            throw new IllegalArgumentException(
                    "El nombre del proveedor no puede "
                    + "superar los 200 caracteres."
            );
        }

        // RFC
        if (tieneTexto(proveedor.getRfc())) {

            if (proveedor.getRfc().trim().length() > 20) {

                throw new IllegalArgumentException(
                        "El RFC no puede superar "
                        + "los 20 caracteres."
                );
            }
        }

        // Días de crédito
        if (proveedor.getDiasCredito() == null) {

            proveedor.setDiasCredito(0);
        }

        if (proveedor.getDiasCredito() < 0) {

            throw new IllegalArgumentException(
                    "Los días de crédito no pueden "
                    + "ser negativos."
            );
        }

        // Límite de crédito
        if (proveedor.getLimiteCredito() == null) {

            proveedor.setLimiteCredito(
                    BigDecimal.ZERO
            );
        }

        if (proveedor.getLimiteCredito()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "El límite de crédito no puede "
                    + "ser negativo."
            );
        }

        // Reglas de crédito
        boolean permiteCredito =
                Boolean.TRUE.equals(
                        proveedor.getPermiteCredito()
                );

        if (!permiteCredito) {

            proveedor.setDiasCredito(0);

            proveedor.setLimiteCredito(
                    BigDecimal.ZERO
            );
        }

        // Si permite crédito debe tener
        // configuración válida.
        if (permiteCredito) {

            if (proveedor.getDiasCredito() <= 0) {

                throw new IllegalArgumentException(
                        "Si el proveedor permite crédito, "
                        + "debe tener días de crédito mayores "
                        + "a cero."
                );
            }

            if (proveedor.getLimiteCredito()
                    .compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException(
                        "Si el proveedor permite crédito, "
                        + "debe tener un límite de crédito "
                        + "mayor a cero."
                );
            }
        }
    }

    // =========================================================
    // PREPARAR VALORES
    // =========================================================

    /**
     * Normaliza los datos antes de enviarlos al DAO.
     */
    private void prepararValores(
            Proveedor proveedor) {

        proveedor.setCodigo(
                proveedor.getCodigo().trim()
        );

        proveedor.setNombre(
                proveedor.getNombre().trim()
        );

        if (tieneTexto(proveedor.getRfc())) {

            proveedor.setRfc(
                    proveedor.getRfc()
                            .trim()
                            .toUpperCase()
            );
        } else {

            proveedor.setRfc(null);
        }

        if (tieneTexto(proveedor.getRazonSocial())) {

            proveedor.setRazonSocial(
                    proveedor.getRazonSocial().trim()
            );
        }

        if (tieneTexto(proveedor.getContacto())) {

            proveedor.setContacto(
                    proveedor.getContacto().trim()
            );
        }

        if (tieneTexto(proveedor.getTelefono())) {

            proveedor.setTelefono(
                    proveedor.getTelefono().trim()
            );
        }

        if (tieneTexto(proveedor.getCelular())) {

            proveedor.setCelular(
                    proveedor.getCelular().trim()
            );
        }

        if (tieneTexto(proveedor.getEmail())) {

            proveedor.setEmail(
                    proveedor.getEmail().trim()
            );
        }

        if (tieneTexto(proveedor.getDireccion())) {

            proveedor.setDireccion(
                    proveedor.getDireccion().trim()
            );
        }

        if (tieneTexto(proveedor.getCiudad())) {

            proveedor.setCiudad(
                    proveedor.getCiudad().trim()
            );
        }

        if (tieneTexto(proveedor.getEstado())) {

            proveedor.setEstado(
                    proveedor.getEstado().trim()
            );
        }

        if (tieneTexto(proveedor.getCodigoPostal())) {

            proveedor.setCodigoPostal(
                    proveedor.getCodigoPostal().trim()
            );
        }

        if (tieneTexto(proveedor.getObservaciones())) {

            proveedor.setObservaciones(
                    proveedor.getObservaciones().trim()
            );
        }

        if (proveedor.getActivo() == null) {

            proveedor.setActivo(true);
        }

        if (proveedor.getPermiteCredito() == null) {

            proveedor.setPermiteCredito(false);
        }
    }

    // =========================================================
    // VALIDAR ID
    // =========================================================

    private void validarId(Long id) {

        if (id == null || id <= 0) {

            throw new IllegalArgumentException(
                    "El ID no es válido."
            );
        }
    }

    // =========================================================
    // VALIDAR TEXTO
    // =========================================================

    private void validarTexto(
            String texto,
            String mensaje) {

        if (!tieneTexto(texto)) {

            throw new IllegalArgumentException(
                    mensaje
            );
        }
    }

    // =========================================================
    // TIENE TEXTO
    // =========================================================

    private boolean tieneTexto(String texto) {

        return texto != null
                && !texto.trim().isEmpty();
    }
}