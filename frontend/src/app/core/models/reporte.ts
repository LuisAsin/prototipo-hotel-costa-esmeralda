import { EstadoPago } from './enums';

export interface OcupacionDia {
  fecha: string;
  habitacionesOcupadas: number;
  porcentaje: number;
}

export interface ConteoClave {
  clave: string;
  cantidad: number;
}

export interface PagosPorEstado {
  estado: EstadoPago;
  cantidad: number;
  monto: number;
}

export interface ReporteOperativoResponse {
  desde: string;
  hasta: string;
  /** 32 */
  totalHabitaciones: number;
  ocupacionPorDia: OcupacionDia[];
  reservasPorEstado: ConteoClave[];
  reservasPorCanal: ConteoClave[];
  pagosPorEstado: PagosPorEstado[];
  montoAprobado: number;
  /** «Montos simulados — no son ingresos reales» */
  rotuloMontos: string;
}
