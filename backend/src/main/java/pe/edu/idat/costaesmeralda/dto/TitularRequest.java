package pe.edu.idat.costaesmeralda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

/** Datos del titular de la reserva. */
public record TitularRequest(
        @NotBlank String nombre,
        @NotBlank @Pattern(regexp = "^\\d{8}$", message = "El documento debe tener 8 dígitos") String documento,
        @NotBlank @Email String correo,
        @NotBlank @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe tener 9 dígitos y empezar con 9") String telefono
) {
}
