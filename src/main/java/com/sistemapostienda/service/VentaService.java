/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */



import com.sistemapostienda.dao.VentaDAO;
import com.sistemapostienda.model.Venta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class VentaService {

    private static final String BORRADOR = "BORRADOR";
    private static final String CONFIRMADA = "CONFIRMADA";
    private static final String CANCELADA = "CANCELADA";

    private static final String CONTADO = "CONTADO";
    private static final String CREDITO = "CREDITO";

    private static final BigDecimal CERO =
            new BigDecimal("0.00");

    private final VentaDAO ventaDAO;

    public VentaService() {
        this.ventaDAO = new VentaDAO();
    }

    public VentaService(VentaDAO ventaDAO) {
        if (ventaDAO == null) {
            throw new IllegalArgumentException(
                    "El DAO de ventas no puede ser nulo.");
        }

        this.ventaDAO = ventaDAO;
    }

    // Registrar una venta nueva como borrador
    public Venta guardar(Venta venta) {
        validarVenta(venta);

        if (venta.getId() != null) {
            throw new IllegalArgumentException(
                    "Una venta nueva no debe tener un ID asignado.");
        }

        String folio = venta.getFolio().trim();

        if (ventaDAO.existeFolio(folio)) {
            throw new IllegalArgumentException(
                    "Ya existe una venta con ese folio.");
        }

        venta.setFolio(folio);
        venta.setTipoVenta(normalizar(venta.getTipoVenta()));
        venta.setEstado(BORRADOR);

        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDateTime.now());
        }

        normalizarImportes(venta);

        return ventaDAO.guardar(venta);
    }

    // Buscar una venta por ID
    public Venta buscarPorId(Long id) {
        validarId(id, "El ID de la venta");
        return ventaDAO.buscarPorId(id);
    }

    // Buscar una venta por folio
    public Venta buscarPorFolio(String folio) {
        if (folio == null || folio.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes proporcionar el folio de la venta.");
        }

        return ventaDAO.buscarPorFolio(folio.trim());
    }

    // Listar todas las ventas
    public List<Venta> listar() {
        return ventaDAO.listar();
    }

    // Consultas por criterios
    public List<Venta> listarPorSucursal(Long sucursalId) {
        validarId(sucursalId, "El ID de la sucursal");
        return ventaDAO.listarPorSucursal(sucursalId);
    }

    public List<Venta> listarPorCliente(Long clienteId) {
        validarId(clienteId, "El ID del cliente");
        return ventaDAO.listarPorCliente(clienteId);
    }

    public List<Venta> listarPorUsuario(Long usuarioId) {
        validarId(usuarioId, "El ID del usuario");
        return ventaDAO.listarPorUsuario(usuarioId);
    }

    public List<Venta> listarPorCaja(Long cajaId) {
        validarId(cajaId, "El ID de la caja");
        return ventaDAO.listarPorCaja(cajaId);
    }

    public List<Venta> listarPorSesionCaja(Long sesionCajaId) {
        validarId(sesionCajaId, "El ID de la sesión de caja");
        return ventaDAO.listarPorSesionCaja(sesionCajaId);
    }

    public List<Venta> listarPorEstado(String estado) {
        estado = normalizar(estado);
        validarEstado(estado);
        return ventaDAO.listarPorEstado(estado);
    }

    public List<Venta> listarPorTipoVenta(String tipoVenta) {
        tipoVenta = normalizar(tipoVenta);
        validarTipoVenta(tipoVenta);
        return ventaDAO.listarPorTipoVenta(tipoVenta);
    }

    // Actualizar únicamente ventas en borrador
    public boolean actualizar(Venta venta) {
        validarVenta(venta);

        if (venta.getId() == null || venta.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Debes indicar el ID de la venta.");
        }

        Venta existente = ventaDAO.buscarPorId(venta.getId());

        if (existente == null) {
            throw new IllegalArgumentException(
                    "La venta que intentas actualizar no existe.");
        }

        if (!BORRADOR.equals(existente.getEstado())) {
            throw new IllegalStateException(
                    "Solo se pueden modificar ventas en borrador.");
        }

        // El folio original se conserva
        venta.setFolio(existente.getFolio());
        venta.setEstado(BORRADOR);
        venta.setTipoVenta(normalizar(venta.getTipoVenta()));

        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(existente.getFechaVenta());
        }

        normalizarImportes(venta);

        return ventaDAO.actualizar(venta);
    }

    /*
     * Cancelar un borrador sin afectar inventario ni caja.
     * Las ventas confirmadas requieren un proceso de cancelación
     * transaccional que revierta sus efectos relacionados.
     */
    public boolean cancelarBorrador(Long id) {
        validarId(id, "El ID de la venta");

        Venta venta = ventaDAO.buscarPorId(id);

        if (venta == null) {
            throw new IllegalArgumentException(
                    "La venta no existe.");
        }

        if (!BORRADOR.equals(venta.getEstado())) {
            throw new IllegalStateException(
                    "Solo se pueden cancelar directamente "
                    + "ventas en borrador.");
        }

        return ventaDAO.cambiarEstado(id, CANCELADA);
    }

    // Validar datos y relaciones básicas de la venta
    private void validarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException(
                    "La venta no puede ser nula.");
        }

        validarId(venta.getSucursalId(), "La sucursal");
        validarId(venta.getCajaId(), "La caja");
        validarId(venta.getSesionCajaId(), "La sesión de caja");
        validarId(venta.getUsuarioId(), "El usuario");

        if (venta.getClienteId() != null) {
            validarId(venta.getClienteId(), "El cliente");
        }

        if (venta.getListaPrecioId() != null) {
            validarId(venta.getListaPrecioId(), "La lista de precios");
        }

        if (venta.getFolio() == null
                || venta.getFolio().isBlank()) {
            throw new IllegalArgumentException(
                    "El folio de la venta es obligatorio.");
        }

        if (venta.getFolio().trim().length() > 30) {
            throw new IllegalArgumentException(
                    "El folio no puede superar 30 caracteres.");
        }

        if (venta.getTipoVenta() == null
                || venta.getTipoVenta().isBlank()) {
            venta.setTipoVenta(CONTADO);
        }

        String tipoVenta = normalizar(venta.getTipoVenta());
        validarTipoVenta(tipoVenta);
        venta.setTipoVenta(tipoVenta);

        if (CREDITO.equals(tipoVenta)
                && venta.getClienteId() == null) {
            throw new IllegalArgumentException(
                    "Las ventas a crédito requieren un cliente registrado.");
        }

        if (venta.getObservaciones() != null
                && venta.getObservaciones().length() > 65535) {
            throw new IllegalArgumentException(
                    "Las observaciones son demasiado extensas.");
        }

        validarImporteNoNegativo(venta.getSubtotal(), "El subtotal");
        validarImporteNoNegativo(venta.getDescuento(), "El descuento");
        validarImporteNoNegativo(venta.getImpuesto(), "El impuesto");
        validarImporteNoNegativo(venta.getTotal(), "El total");
        validarImporteNoNegativo(
                venta.getTotalPagado(), "El total pagado");
        validarImporteNoNegativo(venta.getCambio(), "El cambio");

        BigDecimal subtotal = importe(venta.getSubtotal());
        BigDecimal descuento = importe(venta.getDescuento());
        BigDecimal impuesto = importe(venta.getImpuesto());
        BigDecimal total = importe(venta.getTotal());
        BigDecimal totalPagado = importe(venta.getTotalPagado());
        BigDecimal cambio = importe(venta.getCambio());

        if (descuento.compareTo(subtotal) > 0) {
            throw new IllegalArgumentException(
                    "El descuento no puede superar el subtotal.");
        }

        BigDecimal totalCalculado = subtotal
                .subtract(descuento)
                .add(impuesto)
                .setScale(2, RoundingMode.HALF_UP);

        if (total.compareTo(totalCalculado) != 0) {
            throw new IllegalArgumentException(
                    "El total debe ser igual al subtotal menos "
                    + "el descuento más el impuesto. "
                    + "Total esperado: " + totalCalculado);
        }

        if (totalPagado.compareTo(total) > 0) {
            throw new IllegalArgumentException(
                    "El total pagado no puede superar el total "
                    + "de la venta.");
        }

        if (CREDITO.equals(tipoVenta)
                && cambio.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException(
                    "Una venta a crédito no debe registrar cambio.");
        }

        if (cambio.compareTo(BigDecimal.ZERO) > 0
                && totalPagado.compareTo(total) != 0) {
            throw new IllegalArgumentException(
                    "El cambio solo se permite cuando la venta "
                    + "está cubierta por completo.");
        }
    }

    // Validar los tipos permitidos por la migración
    private void validarTipoVenta(String tipoVenta) {
        if (!CONTADO.equals(tipoVenta)
                && !CREDITO.equals(tipoVenta)) {
            throw new IllegalArgumentException(
                    "Tipo de venta inválido. Utiliza CONTADO o CREDITO.");
        }
    }

    // Validar los estados definidos en la tabla
    private void validarEstado(String estado) {
        if (!BORRADOR.equals(estado)
                && !CONFIRMADA.equals(estado)
                && !CANCELADA.equals(estado)) {
            throw new IllegalArgumentException(
                    "Estado inválido. Utiliza BORRADOR, "
                    + "CONFIRMADA o CANCELADA.");
        }
    }

    private void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    campo + " debe ser mayor que cero.");
        }
    }

    private void validarImporteNoNegativo(
            BigDecimal valor, String campo) {

        if (valor != null && valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    campo + " no puede ser negativo.");
        }
    }

    // Completar importes opcionales y redondearlos a dos decimales
    private void normalizarImportes(Venta venta) {
        venta.setSubtotal(importe(venta.getSubtotal()));
        venta.setDescuento(importe(venta.getDescuento()));
        venta.setImpuesto(importe(venta.getImpuesto()));
        venta.setTotal(importe(venta.getTotal()));
        venta.setTotalPagado(importe(venta.getTotalPagado()));
        venta.setCambio(importe(venta.getCambio()));
    }

    private BigDecimal importe(BigDecimal valor) {
        if (valor == null) {
            return CERO;
        }

        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return null;
        }

        return texto.trim().toUpperCase(Locale.ROOT);
    }
}
