package pe.edu.idat.costaesmeralda.dto;

import java.time.Instant;

/** Token visible únicamente para simular la validación de pago durante la demostración. */
public record TokenPagoResponse(Long reservaId, String codigoReserva, String token, Instant expiraEn, String entorno) {}
