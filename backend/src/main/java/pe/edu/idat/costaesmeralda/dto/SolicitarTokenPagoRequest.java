package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SolicitarTokenPagoRequest(@NotNull @Positive Long reservaId) {}
