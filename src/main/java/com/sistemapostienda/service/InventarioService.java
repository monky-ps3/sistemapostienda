/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

import com.sistemapostienda.dao.InventarioDAO;
import com.sistemapostienda.model.Inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Service para la gestión del inventario.
 *
 * Contiene las reglas de negocio relacionadas con las existencias.
 */
public class InventarioService {

    private final InventarioDAO inventarioDAO;

    public InventarioService() {
        this.inventarioDAO = new InventarioDAO();
    }

    /**
     * Crea un nuevo registro de inventario.
     *
     * @param inventario objeto Inventario
     * @return ID generado
     * @throws Exception si los datos no son válidos
     */
    public Long crear(Inventario inventario) throws Exception {

        validarInventario(inventario);

        Inventario existente = buscarPorSucursalProductoYLote(
                inventario.getSucursalId(),
                inventario.getProductoId(),
                inventario.getLote()
        );

        if (existente != null) {
            throw new Exception(
                    "Ya existe un registro de inventario para "
                    + "este producto, sucursal y lote."
            );
        }

        if (inventario.getExistencia() == null) {
            inventario.setExistencia(BigDecimal.ZERO);
        }

        if (inventario.getExistenciaReservada() == null) {
            inventario.setExistenciaReservada(BigDecimal.ZERO);
        }

        if (inventario.getExistenciaMinima() == null) {
            inventario.setExistenciaMinima(BigDecimal.ZERO);
        }

        if (inventario.getUltimoCosto() == null) {
            inventario.setUltimoCosto(BigDecimal.ZERO);
        }

        if (inventario.getCostoPromedio() == null) {
            inventario.setCostoPromedio(BigDecimal.ZERO);
        }

        if (inventario.getActivo() == null) {
            inventario.setActivo(true);
        }

        return inventarioDAO.guardar(inventario);
    }

    /**
     * Busca un inventario por ID.
     */
    public Inventario buscarPorId(Long id) throws Exception {

        validarId(id);

        return inventarioDAO.buscarPorId(id);
    }

    /**
     * Busca el inventario de un producto en una sucursal,
     * específicamente el registro sin lote.
     */
    public Inventario buscarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws Exception {

        validarId(sucursalId);
        validarId(productoId);

        return inventarioDAO.buscarPorSucursalYProducto(
                sucursalId,
                productoId
        );
    }

    /**
     * Busca inventario por sucursal, producto y lote.
     */
    public Inventario buscarPorSucursalProductoYLote(
            Long sucursalId,
            Long productoId,
            String lote
    ) throws Exception {

        validarId(sucursalId);
        validarId(productoId);

        if (lote == null || lote.trim().isEmpty()) {
            return inventarioDAO.buscarPorSucursalYProducto(
                    sucursalId,
                    productoId
            );
        }

        return inventarioDAO.buscarPorSucursalProductoYLote(
                sucursalId,
                productoId,
                lote.trim()
        );
    }

    /**
     * Lista todo el inventario.
     */
    public List<Inventario> listar() throws Exception {
        return inventarioDAO.listar();
    }

    /**
     * Lista el inventario de una sucursal.
     */
    public List<Inventario> listarPorSucursal(
            Long sucursalId
    ) throws Exception {

        validarId(sucursalId);

        return inventarioDAO.listarPorSucursal(sucursalId);
    }

    /**
     * Lista el inventario de un producto
     * en todas las sucursales.
     */
    public List<Inventario> listarPorProducto(
            Long productoId
    ) throws Exception {

        validarId(productoId);

        return inventarioDAO.listarPorProducto(productoId);
    }

    /**
     * Lista todos los registros de inventario de un producto
     * dentro de una sucursal.
     *
     * Puede devolver varios lotes.
     */
    public List<Inventario> listarPorSucursalYProducto(
            Long sucursalId,
            Long productoId
    ) throws Exception {

        validarId(sucursalId);
        validarId(productoId);

        return inventarioDAO.listarPorSucursalYProducto(
                sucursalId,
                productoId
        );
    }

    /**
     * Actualiza un registro completo de inventario.
     *
     * Este método está pensado para cambios administrativos
     * y de configuración.
     *
     * Los movimientos normales de existencia deberán realizarse
     * posteriormente mediante Kardex.
     */
    public boolean actualizar(Inventario inventario) throws Exception {

        validarInventario(inventario);

        if (inventario.getId() == null || inventario.getId() <= 0) {
            throw new Exception(
                    "El ID del inventario es obligatorio."
            );
        }

        Inventario actual = buscarPorId(inventario.getId());

        if (actual == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        Inventario existente = buscarPorSucursalProductoYLote(
                inventario.getSucursalId(),
                inventario.getProductoId(),
                inventario.getLote()
        );

        if (existente != null
                && !existente.getId().equals(inventario.getId())) {

            throw new Exception(
                    "Ya existe otro registro de inventario "
                    + "para este producto, sucursal y lote."
            );
        }

        return inventarioDAO.actualizar(inventario);
    }

    /**
     * Actualiza directamente la existencia.
     *
     * Este método será utilizado posteriormente por procesos
     * controlados como entradas, salidas y ajustes.
     */
    public boolean actualizarExistencia(
            Long id,
            BigDecimal existencia
    ) throws Exception {

        validarId(id);

        if (existencia == null) {
            throw new Exception(
                    "La existencia es obligatoria."
            );
        }

        if (existencia.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "La existencia no puede ser negativa."
            );
        }

        return inventarioDAO.actualizarExistencia(
                id,
                existencia
        );
    }

    /**
     * Incrementa la existencia.
     *
     * Ejemplo:
     *
     * Existencia actual: 50 kg
     * Entrada: 30 kg
     * Nueva existencia: 80 kg
     */
    public boolean aumentarExistencia(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        validarCantidadPositiva(cantidad);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existenciaActual =
                valorCeroSiNull(inventario.getExistencia());

        BigDecimal nuevaExistencia =
                existenciaActual.add(cantidad);

        return inventarioDAO.actualizarExistencia(
                id,
                nuevaExistencia
        );
    }

    /**
     * Disminuye la existencia.
     *
     * No permite que el inventario quede negativo.
     */
    public boolean disminuirExistencia(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        validarCantidadPositiva(cantidad);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existenciaActual =
                valorCeroSiNull(inventario.getExistencia());

        BigDecimal nuevaExistencia =
                existenciaActual.subtract(cantidad);

        if (nuevaExistencia.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "No hay existencia suficiente para realizar "
                    + "esta operación."
            );
        }

        return inventarioDAO.actualizarExistencia(
                id,
                nuevaExistencia
        );
    }

    /**
     * Obtiene la existencia disponible.
     *
     * Fórmula:
     *
     * existencia - existenciaReservada
     */
    public BigDecimal obtenerExistenciaDisponible(
            Long id
    ) throws Exception {

        validarId(id);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existencia =
                valorCeroSiNull(inventario.getExistencia());

        BigDecimal reservada =
                valorCeroSiNull(
                        inventario.getExistenciaReservada()
                );

        BigDecimal disponible =
                existencia.subtract(reservada);

        if (disponible.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return disponible;
    }

    /**
     * Actualiza la existencia reservada.
     */
    public boolean actualizarExistenciaReservada(
            Long id,
            BigDecimal existenciaReservada
    ) throws Exception {

        validarId(id);

        if (existenciaReservada == null) {
            throw new Exception(
                    "La existencia reservada es obligatoria."
            );
        }

        if (existenciaReservada.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception(
                    "La existencia reservada no puede ser negativa."
            );
        }

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existencia =
                valorCeroSiNull(inventario.getExistencia());

        if (existenciaReservada.compareTo(existencia) > 0) {
            throw new Exception(
                    "La existencia reservada no puede ser mayor "
                    + "que la existencia total."
            );
        }

        return inventarioDAO.actualizarExistenciaReservada(
                id,
                existenciaReservada
        );
    }

    /**
     * Reserva una cantidad de inventario.
     */
    public boolean reservar(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        validarCantidadPositiva(cantidad);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existencia =
                valorCeroSiNull(inventario.getExistencia());

        BigDecimal reservada =
                valorCeroSiNull(
                        inventario.getExistenciaReservada()
                );

        BigDecimal disponible =
                existencia.subtract(reservada);

        if (cantidad.compareTo(disponible) > 0) {
            throw new Exception(
                    "No existe suficiente inventario disponible "
                    + "para realizar la reserva."
            );
        }

        BigDecimal nuevaReservada =
                reservada.add(cantidad);

        return inventarioDAO.actualizarExistenciaReservada(
                id,
                nuevaReservada
        );
    }

    /**
     * Libera una cantidad previamente reservada.
     */
    public boolean liberarReserva(
            Long id,
            BigDecimal cantidad
    ) throws Exception {

        validarCantidadPositiva(cantidad);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal reservada =
                valorCeroSiNull(
                        inventario.getExistenciaReservada()
                );

        if (cantidad.compareTo(reservada) > 0) {
            throw new Exception(
                    "La cantidad a liberar no puede ser mayor "
                    + "que la existencia reservada."
            );
        }

        BigDecimal nuevaReservada =
                reservada.subtract(cantidad);

        return inventarioDAO.actualizarExistenciaReservada(
                id,
                nuevaReservada
        );
    }

    /**
     * Actualiza los costos del inventario.
     */
    public boolean actualizarCostos(
            Long id,
            BigDecimal ultimoCosto,
            BigDecimal costoPromedio
    ) throws Exception {

        validarId(id);

        if (ultimoCosto == null
                || costoPromedio == null) {

            throw new Exception(
                    "Los costos son obligatorios."
            );
        }

        if (ultimoCosto.compareTo(BigDecimal.ZERO) < 0
                || costoPromedio.compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "Los costos no pueden ser negativos."
            );
        }

        return inventarioDAO.actualizarCostos(
                id,
                ultimoCosto,
                costoPromedio
        );
    }

    /**
     * Activa o desactiva un registro de inventario.
     */
    public boolean actualizarActivo(
            Long id,
            boolean activo
    ) throws Exception {

        validarId(id);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        return inventarioDAO.actualizarActivo(id, activo);
    }

    /**
     * Elimina un registro de inventario.
     *
     * Se recomienda utilizar este método únicamente cuando
     * el registro todavía no tenga movimientos de Kardex.
     */
    public boolean eliminar(Long id) throws Exception {

        validarId(id);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        if (valorCeroSiNull(inventario.getExistencia())
                .compareTo(BigDecimal.ZERO) != 0) {

            throw new Exception(
                    "No se puede eliminar un inventario "
                    + "que todavía tiene existencia."
            );
        }

        if (valorCeroSiNull(
                inventario.getExistenciaReservada()
        ).compareTo(BigDecimal.ZERO) != 0) {

            throw new Exception(
                    "No se puede eliminar un inventario "
                    + "que tiene existencia reservada."
            );
        }

        return inventarioDAO.eliminar(id);
    }

    /**
     * Indica si el inventario está por debajo de su existencia mínima.
     */
    public boolean estaBajoMinimo(Long id) throws Exception {

        validarId(id);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        BigDecimal existencia =
                valorCeroSiNull(inventario.getExistencia());

        BigDecimal minima =
                valorCeroSiNull(
                        inventario.getExistenciaMinima()
                );

        return existencia.compareTo(minima) <= 0;
    }

    /**
     * Indica si el inventario está por encima de su máximo.
     */
    public boolean estaSobreMaximo(Long id) throws Exception {

        validarId(id);

        Inventario inventario = buscarPorId(id);

        if (inventario == null) {
            throw new Exception(
                    "El registro de inventario no existe."
            );
        }

        if (inventario.getExistenciaMaxima() == null) {
            return false;
        }

        BigDecimal existencia =
                valorCeroSiNull(inventario.getExistencia());

        return existencia.compareTo(
                inventario.getExistenciaMaxima()
        ) >= 0;
    }

    /**
     * Valida los datos generales de un inventario.
     */
    private void validarInventario(
            Inventario inventario
    ) throws Exception {

        if (inventario == null) {
            throw new Exception(
                    "El inventario es obligatorio."
            );
        }

        validarId(inventario.getSucursalId());
        validarId(inventario.getProductoId());

        if (inventario.getExistencia() != null
                && inventario.getExistencia()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "La existencia no puede ser negativa."
            );
        }

        if (inventario.getExistenciaReservada() != null
                && inventario.getExistenciaReservada()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "La existencia reservada no puede ser negativa."
            );
        }

        if (inventario.getExistenciaMinima() != null
                && inventario.getExistenciaMinima()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "La existencia mínima no puede ser negativa."
            );
        }

        if (inventario.getExistenciaMaxima() != null
                && inventario.getExistenciaMinima() != null
                && inventario.getExistenciaMaxima()
                .compareTo(inventario.getExistenciaMinima()) < 0) {

            throw new Exception(
                    "La existencia máxima no puede ser menor "
                    + "que la existencia mínima."
            );
        }

        if (inventario.getUltimoCosto() != null
                && inventario.getUltimoCosto()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "El último costo no puede ser negativo."
            );
        }

        if (inventario.getCostoPromedio() != null
                && inventario.getCostoPromedio()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new Exception(
                    "El costo promedio no puede ser negativo."
            );
        }

        if (inventario.getExistencia() != null
                && inventario.getExistenciaReservada() != null
                && inventario.getExistenciaReservada()
                .compareTo(inventario.getExistencia()) > 0) {

            throw new Exception(
                    "La existencia reservada no puede ser mayor "
                    + "que la existencia."
            );
        }
    }

    /**
     * Valida un ID.
     */
    private void validarId(Long id) throws Exception {

        if (id == null || id <= 0) {
            throw new Exception(
                    "El ID debe ser mayor que cero."
            );
        }
    }

    /**
     * Valida una cantidad positiva.
     */
    private void validarCantidadPositiva(
            BigDecimal cantidad
    ) throws Exception {

        if (cantidad == null
                || cantidad.compareTo(BigDecimal.ZERO) <= 0) {

            throw new Exception(
                    "La cantidad debe ser mayor que cero."
            );
        }
    }

    /**
     * Devuelve cero cuando un valor es null.
     */
    private BigDecimal valorCeroSiNull(
            BigDecimal valor
    ) {

        return valor != null
                ? valor
                : BigDecimal.ZERO;
    }
}