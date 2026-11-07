package pe.edu.idat.costaesmeralda.dto;

import java.util.List;

public record FilaOcupacionResponse(Long tipoHabitacionId, String habitacionNumero, String nombre,
        String estado, Integer inventario, List<CeldaOcupacionResponse> dias) {}
