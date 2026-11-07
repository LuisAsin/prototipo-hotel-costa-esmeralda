package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import java.util.List;

public record MapaOcupacionResponse(LocalDate desde, List<LocalDate> fechas,
        List<FilaOcupacionResponse> habitaciones) {}
