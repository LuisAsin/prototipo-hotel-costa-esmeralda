package pe.edu.idat.costaesmeralda.dto;

import java.time.LocalDate;
import java.util.List;

public record CeldaOcupacionResponse(LocalDate fecha, String estado, Integer ocupadas,
        Integer disponibles, List<String> reservas) {}
