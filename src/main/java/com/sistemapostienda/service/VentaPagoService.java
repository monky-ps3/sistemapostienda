/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemapostienda.service;

/**
 *
 * @author monky
 */


import com.sistemapostienda.dao.VentaPagoDAO;
import com.sistemapostienda.model.VentaPago;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class VentaPagoService {

    private final VentaPagoDAO ventaPagoDAO;

    public VentaPagoService() {
        this.ventaPagoDAO = new VentaPagoDAO();
    }

    // Constructor para permitir inyectar el DAO
    public VentaPagoService(VentaPagoDAO ventaPagoDAO) {
        if (ventaPagoDAO == null) {
            throw new IllegalArgumentException(
                    "El DAO de pagos no puede ser nulo.");
        }

        this.ventaPagoDAO = ventaPagoDAO;
    }

    // Registrar un pago después de validar sus datos
    public VentaPago guardar(VentaPago pago) {
        validarPago(pago);

        pago.setTipoPago(normalizarTexto(pago.getTipoPago()));
        pago.setEstado(normalizarTexto(pago.getEstado()));

        if (pago.getReferencia() != null) {
            pago.setReferencia(pago.getReferencia().trim());
        }

        if (pago.getNumeroOperacion() != null) {
            pago.setNumeroOperacion(pago.getNumeroOperacion().trim());
        }

        if (pago.getObservaciones() != null) {
            pago.setObservaciones(pago.getObservaciones().trim());
        }

        if (pago.getCambio() == null) {
            pago.setCambio(BigDecimal.ZERO.setScale(2));
        }

        pago.setMonto(redondearImporte(pago.getMonto()));
        pago.setMontoRecibido(
                redondearImporte(pago.getMontoRecibido()));
        pago.setCambio(redondearImporte(pago.getCambio()));

        return ventaPagoDAO.guardar(pago);
    }

    // Buscar un pago por ID
    public VentaPago buscarPorId(Long id) {
        validarId(id, "El ID del pago");

        return ventaPagoDAO.buscarPorId(id);
    }

    // Listar todos los pagos
    public List<VentaPago> listar() {
        return ventaPagoDAO.listar();
    }

    // Listar los pagos de una venta
    public List<VentaPago> listarPorVenta(Long ventaId) {
        validarId(ventaId, "El ID de la venta");

        return ventaPagoDAO.listarPorVenta(ventaId);
    }

    // Listar pagos por forma de pago
    public List<VentaPago> listarPorFormaPago(Long formaPagoId) {
        validarId(formaPagoId, "El ID de la forma de pago");

        return ventaPagoDAO.listarPorFormaPago(formaPagoId);
    }

    // Listar pagos realizados por usuario
    public List<VentaPago> listarPorUsuario(Long usuarioId) {
        validarId(usuarioId, "El ID del usuario");

        return ventaPagoDAO.listarPorUsuario(usuarioId);
    }

    // Listar pagos por estado
    public List<VentaPago> listarPorEstado(String estado) {
        estado = normalizarTexto(estado);
        validarEstado(estado);

        return ventaPagoDAO.listarPorEstado(estado);
    }

    // Listar pagos por tipo
    public List<VentaPago> listarPorTipoPago(String tipoPago) {
        tipoPago = normalizarTexto(tipoPago);
        validarTipoPago(tipoPago);

        return ventaPagoDAO.listarPorTipoPago(tipoPago);
    }

    // Cancelación lógica: conserva el registro del pago
    public boolean cancelar(Long id) {
        validarId(id, "El ID del pago");

        VentaPago pago = ventaPagoDAO.buscarPorId(id);

        if (pago == null) {
            throw new IllegalArgumentException(
                    "El pago que intentas cancelar no existe.");
        }

        if ("CANCELADO".equals(pago.getEstado())) {
            throw new IllegalStateException(
                    "El pago ya se encuentra cancelado.");
        }

        /*
         * IMPORTANTE:
         * Antes de utilizar esta operación en producción, hay que
         * coordinar la cancelación con la venta, el saldo del crédito
         * y los movimientos de caja dentro de una transacción.
         */
        return ventaPagoDAO.cancelar(id);
    }

    // Validaciones generales del pago
    private void validarPago(VentaPago pago) {
        if (pago == null) {
            throw new IllegalArgumentException(
                    "El pago no puede ser nulo.");
        }

        validarId(pago.getVentaId(), "El ID de la venta");
        validarId(pago.getFormaPagoId(), "El ID de la forma de pago");
        validarId(pago.getUsuarioId(), "El ID del usuario");

        if (pago.getCajaId() != null) {
            validarId(pago.getCajaId(), "El ID de la caja");
        }

        if (pago.getSesionCajaId() != null) {
            validarId(
                    pago.getSesionCajaId(),
                    "El ID de la sesión de caja");
        }

        if ((pago.getCajaId() == null)
                != (pago.getSesionCajaId() == null)) {
            throw new IllegalArgumentException(
                    "Debes indicar tanto la caja como la sesión, "
                    + "o dejar ambos campos vacíos.");
        }

        if (pago.getTipoPago() == null
                || pago.getTipoPago().isBlank()) {
            throw new IllegalArgumentException(
                    "Debes indicar el tipo de pago.");
        }

        String tipoPago = normalizarTexto(pago.getTipoPago());
        validarTipoPago(tipoPago);

        if (pago.getMonto() == null
                || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El monto aplicado debe ser mayor que cero.");
        }

        if (pago.getMontoRecibido() == null
                || pago.getMontoRecibido()
                        .compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El monto recibido debe ser mayor que cero.");
        }

        if (pago.getCambio() != null
                && pago.getCambio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El cambio no puede ser negativo.");
        }

        BigDecimal monto = redondearImporte(pago.getMonto());
        BigDecimal recibido =
                redondearImporte(pago.getMontoRecibido());

        BigDecimal cambio = pago.getCambio() == null
                ? BigDecimal.ZERO.setScale(2)
                : redondearImporte(pago.getCambio());

        if (recibido.compareTo(monto) < 0) {
            throw new IllegalArgumentException(
                    "El monto recibido no puede ser menor "
                    + "que el monto aplicado.");
        }

        if (recibido.compareTo(monto.add(cambio)) != 0) {
            throw new IllegalArgumentException(
                    "El monto recibido debe ser igual al monto "
                    + "aplicado más el cambio.");
        }

        if (pago.getReferencia() != null
                && pago.getReferencia().length() > 150) {
            throw new IllegalArgumentException(
                    "La referencia no puede superar 150 caracteres.");
        }

        if (pago.getNumeroOperacion() != null
                && pago.getNumeroOperacion().length() > 100) {
            throw new IllegalArgumentException(
                    "El número de operación no puede superar "
                    + "100 caracteres.");
        }

        if (pago.getObservaciones() != null
                && pago.getObservaciones().length() > 255) {
            throw new IllegalArgumentException(
                    "Las observaciones no pueden superar "
                    + "255 caracteres.");
        }

        if (pago.getEstado() != null) {
            validarEstado(normalizarTexto(pago.getEstado()));
        } else {
            pago.setEstado("CONFIRMADO");
        }

        /*
         * La validación de permisos de cambio, forma de pago activa,
         * saldo pendiente y sesión abierta se realizará consultando
         * sus respectivos servicios antes de registrar el pago.
         */
    }

    // Validar los tipos permitidos por la tabla
    private void validarTipoPago(String tipoPago) {
        if (!"PAGO_VENTA".equals(tipoPago)
                && !"ABONO_CREDITO".equals(tipoPago)) {
            throw new IllegalArgumentException(
                    "Tipo de pago inválido. Utiliza PAGO_VENTA "
                    + "o ABONO_CREDITO.");
        }
    }

    // Validar los estados permitidos por la tabla
    private void validarEstado(String estado) {
        if (!"CONFIRMADO".equals(estado)
                && !"CANCELADO".equals(estado)) {
            throw new IllegalArgumentException(
                    "Estado inválido. Utiliza CONFIRMADO "
                    + "o CANCELADO.");
        }
    }

    // Validar identificadores
    private void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    campo + " debe ser mayor que cero.");
        }
    }

    // Normalizar textos para comparaciones
    private String normalizarTexto(String texto) {
        if (texto == null) {
            return null;
        }

        return texto.trim().toUpperCase(Locale.ROOT);
    }

    // Redondear importes a dos decimales
    private BigDecimal redondearImporte(BigDecimal importe) {
        if (importe == null) {
            return BigDecimal.ZERO.setScale(2);
        }

        return importe.setScale(2, RoundingMode.HALF_UP);
    }
}
