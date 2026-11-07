package pe.edu.idat.costaesmeralda.dto;

/** Resultado del flujo de pago ficticio; no representa un cargo financiero. */
public record PagoFicticioResponse(Long reservaId, String codigoReserva, String estadoReserva,
        String estadoPago, String mensaje) {}
