package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Motivo de una cancelación total o parcial. */
public record CancelacionRequest(
        @NotBlank @Size(max = 300) String motivo
) {
}
