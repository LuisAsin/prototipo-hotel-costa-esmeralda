import { EstadoHabitacion, TipoDescuento, TipoPromocion } from './enums';

export interface CambiarEstadoHabitacionRequest {
  estado: EstadoHabitacion;
}

export interface CambiarTarifaRequest {
  tarifaBase: number;
}

export interface CrearBloqueoRequest {
  habitacionId: number;
  fechaInicio: string;
  /** mayor que fechaInicio */
  fechaFin: string;
  motivo: string;
}

export interface CambiarActivaRequest {
  activa: boolean;
}

export interface HabitacionResponse {
  id: number;
  numero: string;
  piso: number;
  tipoId: number;
  nombreTipo: string;
  estado: EstadoHabitacion;
}

export interface BloqueoResponse {
  id: number;
  habitacionId: number;
  habitacionNumero: string;
  fechaInicio: string;
  fechaFin: string;
  motivo: string;
  creadoPor: number;
  creadoPorNombre: string;
  fechaCreacion: string;
}

export interface PromocionResponse {
  id: number;
  nombre: string;
  tipo: TipoPromocion;
  codigo: string | null;
  tipoDescuento: TipoDescuento;
  valor: number;
  descripcion: string;
  vigenciaInicio: string;
  vigenciaFin: string;
  tipoHabitacionId: number | null;
  nombreTipoHabitacion: string | null;
  estanciaMinimaNoches: number | null;
  usosMaximos: number | null;
  usosActuales: number;
  activa: boolean;
}
