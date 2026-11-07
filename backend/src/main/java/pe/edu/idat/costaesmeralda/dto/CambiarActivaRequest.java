package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotNull;

/** Activa o desactiva una promoción. */
public record CambiarActivaRequest(
        @NotNull Boolean activa
) {
}
