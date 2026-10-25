/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.ClienteDAO;
import com.sistemapostienda.dao.ListaPrecioDAO;
import com.sistemapostienda.model.Cliente;
import com.sistemapostienda.model.ListaPrecio;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio para la gestión de clientes.
 *
 * Contiene las reglas de negocio relacionadas
 * con los clientes.
 *
 * Arquitectura:
 *
 * ClienteController
 *        ↓
 * ClienteService
 *        ↓
 * ClienteDAO
 *        ↓
 * Database
 */
public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final ListaPrecioDAO listaPrecioDAO;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ClienteService() {

        this.clienteDAO = new ClienteDAO();
        this.listaPrecioDAO = new ListaPrecioDAO();
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    /**
     * Registra un nuevo cliente.
     */
    public boolean guardar(Cliente cliente) {

        validarCliente(cliente);

        // -----------------------------------------------------
        // Verificar código duplicado
        // -----------------------------------------------------

        Cliente existenteCodigo =
                clienteDAO.buscarPorCodigo(
                        cliente.getCodigo()
                );

        if (existenteCodigo != null) {

            throw new IllegalArgumentException(
                    "Ya existe un cliente con el código: "
                    + cliente.getCodigo()
            );
        }

        // -----------------------------------------------------
        // Verificar RFC duplicado
        // -----------------------------------------------------

        if (tieneTexto(cliente.getRfc())) {

            Cliente existenteRfc =
                    clienteDAO.buscarPorRfc(
                            cliente.getRfc()
                    );

            if (existenteRfc != null) {

                throw new IllegalArgumentException(
                        "Ya existe un cliente con el RFC: "
                        + cliente.getRfc()
                );
            }
        }

        // -----------------------------------------------------
        // Verificar lista de precios
        // -----------------------------------------------------

        validarListaPrecio(
                cliente.getListaPrecioId()
        );

        // -----------------------------------------------------
        // Valores predeterminados
        // -----------------------------------------------------

        if (cliente.getLimiteCredito() == null) {

            cliente.setLimiteCredito(
                    BigDecimal.ZERO
            );
        }

        if (cliente.getDiasCredito() == null) {

            cliente.setDiasCredito(0);
        }

        if (cliente.getDescuento() == null) {

            cliente.setDescuento(
                    BigDecimal.ZERO
            );
        }

        if (cliente.getPermiteCredito() == null) {

            cliente.setPermiteCredito(false);
        }

        if (cliente.getActivo() == null) {

            cliente.setActivo(true);
        }

        // -----------------------------------------------------
        // Si no permite crédito, no debe tener crédito
        // -----------------------------------------------------

        if (!cliente.tieneCredito()) {

            cliente.setLimiteCredito(
                    BigDecimal.ZERO
            );

            cliente.setDiasCredito(0);
        }

        return clienteDAO.guardar(cliente);
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    public Cliente buscarPorId(Long id) {

        validarId(id);

        return clienteDAO.buscarPorId(id);
    }

    // =========================================================
    // BUSCAR POR CÓDIGO
    // =========================================================

    public Cliente buscarPorCodigo(String codigo) {

        validarTexto(
                codigo,
                "El código del cliente es obligatorio."
        );

        return clienteDAO.buscarPorCodigo(
                codigo.trim()
        );
    }

    // =========================================================
    // BUSCAR POR RFC
    // =========================================================

    public Cliente buscarPorRfc(String rfc) {

        validarTexto(
                rfc,
                "El RFC es obligatorio."
        );

        return clienteDAO.buscarPorRfc(
                rfc.trim().toUpperCase()
        );
    }

    // =========================================================
    // BUSCAR PÚBLICO EN GENERAL
    // =========================================================

    /**
     * Obtiene el cliente especial utilizado
     * para ventas sin cliente registrado.
     */
    public Cliente buscarPublicoGeneral() {

        Cliente cliente =
                clienteDAO.buscarPublicoGeneral();

        if (cliente == null) {

            throw new IllegalStateException(
                    "No existe el cliente PÚBLICO EN GENERAL."
            );
        }

        if (!cliente.estaActivo()) {

            throw new IllegalStateException(
                    "El cliente PÚBLICO EN GENERAL está inactivo."
            );
        }

        if (!cliente.tieneListaPrecio()) {

            throw new IllegalStateException(
                    "El cliente PÚBLICO EN GENERAL "
                    + "no tiene lista de precios asignada."
            );
        }

        return cliente;
    }

    // =========================================================
    // LISTAR TODOS
    // =========================================================

    public List<Cliente> listarTodos() {

        return clienteDAO.listarTodos();
    }

    // =========================================================
    // LISTAR ACTIVOS
    // =========================================================

    public List<Cliente> listarActivos() {

        return clienteDAO.listarActivos();
    }

    // =========================================================
    // BUSCAR POR NOMBRE
    // =========================================================

    public List<Cliente> buscarPorNombre(
            String nombre) {

        validarTexto(
                nombre,
                "El nombre de búsqueda es obligatorio."
        );

        return clienteDAO.buscarPorNombre(
                nombre.trim()
        );
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    public boolean actualizar(Cliente cliente) {

        if (cliente == null
                || cliente.getId() == null
                || cliente.getId() <= 0) {

            throw new IllegalArgumentException(
                    "El cliente no es válido."
            );
        }

        validarCliente(cliente);

        // -----------------------------------------------------
        // Verificar que exista
        // -----------------------------------------------------

        Cliente actual =
                clienteDAO.buscarPorId(
                        cliente.getId()
                );

        if (actual == null) {

            throw new IllegalArgumentException(
                    "El cliente que intenta actualizar "
                    + "no existe."
            );
        }

        // -----------------------------------------------------
        // Verificar código duplicado
        // -----------------------------------------------------

        Cliente mismoCodigo =
                clienteDAO.buscarPorCodigo(
                        cliente.getCodigo()
                );

        if (mismoCodigo != null
                && !mismoCodigo.getId()
                        .equals(cliente.getId())) {

            throw new IllegalArgumentException(
                    "El código ya pertenece a otro cliente."
            );
        }

        // -----------------------------------------------------
        // Verificar RFC duplicado
        // -----------------------------------------------------

        if (tieneTexto(cliente.getRfc())) {

            Cliente mismoRfc =
                    clienteDAO.buscarPorRfc(
                            cliente.getRfc()
                    );

            if (mismoRfc != null
                    && !mismoRfc.getId()
                            .equals(cliente.getId())) {

                throw new IllegalArgumentException(
                        "El RFC ya pertenece a otro cliente."
                );
            }
        }

        // -----------------------------------------------------
        // Verificar lista de precios
        // -----------------------------------------------------

        validarListaPrecio(
                cliente.getListaPrecioId()
        );

        // -----------------------------------------------------
        // Público General
        // -----------------------------------------------------

        if (actual.esPublicoGeneral()) {

            /*
             * No permitimos cambiar el código
             * del cliente especial.
             */
            if (!"PUBLICO".equalsIgnoreCase(
                    cliente.getCodigo())) {

                throw new IllegalArgumentException(
                        "No se puede cambiar el código "
                        + "del cliente PÚBLICO."
                );
            }

            /*
             * El cliente Público General debe
             * permanecer activo.
             */
            if (!cliente.estaActivo()) {

                throw new IllegalArgumentException(
                        "El cliente PÚBLICO EN GENERAL "
                        + "no puede desactivarse."
                );
            }

            /*
             * Tampoco debe habilitarse crédito.
             */
            if (cliente.tieneCredito()) {

                throw new IllegalArgumentException(
                        "El cliente PÚBLICO EN GENERAL "
                        + "no puede manejar crédito."
                );
            }
        }

        aplicarValoresCredito(cliente);

        return clienteDAO.actualizar(cliente);
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    public boolean eliminar(Long id) {

        validarId(id);

        Cliente cliente =
                clienteDAO.buscarPorId(id);

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        // -----------------------------------------------------
        // No eliminar Público General
        // -----------------------------------------------------

        if (cliente.esPublicoGeneral()) {

            throw new IllegalStateException(
                    "El cliente PÚBLICO EN GENERAL "
                    + "no puede eliminarse."
            );
        }

        return clienteDAO.eliminar(id);
    }

    // =========================================================
    // ACTIVAR
    // =========================================================

    public boolean activar(Long id) {

        validarId(id);

        Cliente cliente =
                clienteDAO.buscarPorId(id);

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        return clienteDAO.cambiarEstado(
                id,
                true
        );
    }

    // =========================================================
    // DESACTIVAR
    // =========================================================

    public boolean desactivar(Long id) {

        validarId(id);

        Cliente cliente =
                clienteDAO.buscarPorId(id);

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        // -----------------------------------------------------
        // Público General no se puede desactivar
        // -----------------------------------------------------

        if (cliente.esPublicoGeneral()) {

            throw new IllegalStateException(
                    "El cliente PÚBLICO EN GENERAL "
                    + "no puede desactivarse."
            );
        }

        return clienteDAO.cambiarEstado(
                id,
                false
        );
    }

    // =========================================================
    // CAMBIAR LISTA DE PRECIOS
    // =========================================================

    /**
     * Cambia la lista de precios asignada a un cliente.
     */
    public boolean cambiarListaPrecio(
            Long clienteId,
            Long listaPrecioId) {

        validarId(clienteId);

        validarId(listaPrecioId);

        Cliente cliente =
                clienteDAO.buscarPorId(
                        clienteId
                );

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no existe."
            );
        }

        validarListaPrecio(listaPrecioId);

        return clienteDAO.actualizarListaPrecio(
                clienteId,
                listaPrecioId
        );
    }

    // =========================================================
    // VALIDAR CLIENTE
    // =========================================================

    private void validarCliente(
            Cliente cliente) {

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        // -----------------------------------------------------
        // Código
        // -----------------------------------------------------

        validarTexto(
                cliente.getCodigo(),
                "El código del cliente es obligatorio."
        );

        if (cliente.getCodigo().length() > 30) {

            throw new IllegalArgumentException(
                    "El código del cliente no puede "
                    + "tener más de 30 caracteres."
            );
        }

        // -----------------------------------------------------
        // Nombre
        // -----------------------------------------------------

        validarTexto(
                cliente.getNombre(),
                "El nombre del cliente es obligatorio."
        );

        if (cliente.getNombre().length() > 200) {

            throw new IllegalArgumentException(
                    "El nombre no puede tener "
                    + "más de 200 caracteres."
            );
        }

        // -----------------------------------------------------
        // RFC
        // -----------------------------------------------------

        if (tieneTexto(cliente.getRfc())) {

            String rfc =
                    cliente.getRfc()
                            .trim()
                            .toUpperCase();

            cliente.setRfc(rfc);

            if (rfc.length() > 20) {

                throw new IllegalArgumentException(
                        "El RFC no puede tener "
                        + "más de 20 caracteres."
                );
            }
        }

        // -----------------------------------------------------
        // Límite de crédito
        // -----------------------------------------------------

        if (cliente.getLimiteCredito() != null
                && cliente.getLimiteCredito()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "El límite de crédito no puede ser negativo."
            );
        }

        // -----------------------------------------------------
        // Días de crédito
        // -----------------------------------------------------

        if (cliente.getDiasCredito() != null
                && cliente.getDiasCredito() < 0) {

            throw new IllegalArgumentException(
                    "Los días de crédito no pueden ser negativos."
            );
        }

        // -----------------------------------------------------
        // Descuento
        // -----------------------------------------------------

        if (cliente.getDescuento() != null) {

            if (cliente.getDescuento()
                    .compareTo(BigDecimal.ZERO) < 0) {

                throw new IllegalArgumentException(
                        "El descuento no puede ser negativo."
                );
            }

            if (cliente.getDescuento()
                    .compareTo(
                            new BigDecimal("100")
                    ) > 0) {

                throw new IllegalArgumentException(
                        "El descuento no puede ser mayor a 100%."
                );
            }

            if (cliente.getDescuento().scale() > 2) {

                throw new IllegalArgumentException(
                        "El descuento no puede tener "
                        + "más de dos decimales."
                );
            }
        }
    }

    // =========================================================
    // VALIDAR LISTA DE PRECIOS
    // =========================================================

    private void validarListaPrecio(
            Long listaPrecioId) {

        if (listaPrecioId == null
                || listaPrecioId <= 0) {

            throw new IllegalArgumentException(
                    "El cliente debe tener una "
                    + "lista de precios válida."
            );
        }

        ListaPrecio listaPrecio =
                listaPrecioDAO.buscarPorId(
                        listaPrecioId
                );

        if (listaPrecio == null) {

            throw new IllegalArgumentException(
                    "La lista de precios indicada no existe."
            );
        }

        if (!Boolean.TRUE.equals(
                listaPrecio.getActiva())) {

            throw new IllegalArgumentException(
                    "La lista de precios indicada "
                    + "está inactiva."
            );
        }
    }

    // =========================================================
    // APLICAR VALORES DE CRÉDITO
    // =========================================================

    private void aplicarValoresCredito(
            Cliente cliente) {

        if (cliente.getLimiteCredito() == null) {

            cliente.setLimiteCredito(
                    BigDecimal.ZERO
            );
        }

        if (cliente.getDiasCredito() == null) {

            cliente.setDiasCredito(0);
        }

        if (cliente.getDescuento() == null) {

            cliente.setDescuento(
                    BigDecimal.ZERO
            );
        }

        if (cliente.getPermiteCredito() == null) {

            cliente.setPermiteCredito(false);
        }

        if (cliente.getActivo() == null) {

            cliente.setActivo(true);
        }

        /*
         * Si no tiene permitido crédito,
         * eliminamos cualquier configuración
         * accidental de crédito.
         */
        if (!cliente.tieneCredito()) {

            cliente.setLimiteCredito(
                    BigDecimal.ZERO
            );

            cliente.setDiasCredito(0);
        }

        /*
         * Si tiene crédito, el límite debe ser
         * mayor que cero.
         */
        if (cliente.tieneCredito()
                && cliente.getLimiteCredito()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Un cliente con crédito debe "
                    + "tener un límite de crédito mayor a cero."
            );
        }

        /*
         * Si tiene crédito, debe tener días definidos.
         */
        if (cliente.tieneCredito()
                && cliente.getDiasCredito() <= 0) {

            throw new IllegalArgumentException(
                    "Un cliente con crédito debe "
                    + "tener días de crédito mayores a cero."
            );
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
            String valor,
            String mensaje) {

        if (valor == null
                || valor.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    mensaje
            );
        }
    }

    // =========================================================
    // SABER SI TIENE TEXTO
    // =========================================================

    private boolean tieneTexto(String valor) {

        return valor != null
                && !valor.trim().isEmpty();
    }
}